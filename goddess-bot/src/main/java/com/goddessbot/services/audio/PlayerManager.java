package com.goddessbot.services.audio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public synchronized GuildMusicManager getMusicManager(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(audioPlayerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String url) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());


        this.audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + url).queue();

            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {


                final List<AudioTrack> tracks = playlist.getTracks();

                channel.sendMessage("Adding to queue `" + tracks.size() + "` tracks from  `"
                + playlist.getName() + "`").queue();


                for(final AudioTrack track : tracks){
                    musicManager.queueScheduler.queue(track);
                }
                

            }

            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.queueScheduler.queue(track);

                channel.sendMessage("Adding to queue: `")
                        .append(track.getInfo().title)
                        .append("` by `")
                        .append(track.getInfo().author)
                        .append("`")
                        .queue();

            }

        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}
