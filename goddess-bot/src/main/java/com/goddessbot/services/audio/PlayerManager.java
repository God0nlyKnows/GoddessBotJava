package com.goddessbot.services.audio;

import java.awt.Color;
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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
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

    public void flushMusicManager(Guild guild) {
        musicManagers.remove(guild.getIdLong());
    }

    public void flushMusicManager(long guildId) {
        musicManagers.remove(guildId);
    }

    public void loadAndPlay(TextChannel channel, String url) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        musicManager.queueScheduler.setLastUsedChannel(channel);
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

                if (playlist.isSearchResult()) {
                    trackLoaded(tracks.get(0));
                    return;
                }
                Message lastMessage = channel.retrieveMessageById(channel.getLatestMessageId()).complete();
                if (checkIfLastMsgIsMine(lastMessage, channel)
                        && (checkContentOfEmbed(lastMessage.getEmbeds().get(0), "Now playing:")
                                || checkContentOfEmbed(lastMessage.getEmbeds().get(0), "Now streaming:"))) {
                    lastMessage.delete().queue();
                }
                channel.sendMessageEmbeds(List.of(createEmbed(playlist).build())).queue();

                for (final AudioTrack track : tracks) {
                    musicManager.queueScheduler.queue(track);
                }

            }

            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.queueScheduler.queue(track);

                channel.sendMessageEmbeds(List.of(createEmbed(track).build())).queue();

            }

        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    private EmbedBuilder createEmbed(AudioTrack track) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.magenta);
        builder.setTitle("Added: " + track.getInfo().title);
        builder.setDescription("Author: " + track.getInfo().author + "\n" + track.getInfo().uri);
        return builder;
    }

    private EmbedBuilder createEmbed(AudioPlaylist playlist) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.magenta);
        builder.setTitle("Adding to queue: " + playlist.getTracks().size() + " tracks from " + playlist.getName());
        return builder;
    }

    private boolean checkIfLastMsgIsMine(Message message, TextChannel channel) {
        if (message.getAuthor().getIdLong() == channel.getJDA().getSelfUser().getIdLong()) {
            return true;
        }
        return false;
    }

    private boolean checkContentOfEmbed(MessageEmbed embed, String content) {
        if (embed == null) {
            return false;
        }

        if (embed.getTitle().contains(content)) {
            return true;
        }
        return false;
    }
}
