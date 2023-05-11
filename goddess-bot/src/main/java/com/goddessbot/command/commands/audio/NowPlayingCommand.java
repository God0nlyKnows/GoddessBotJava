package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;


public class NowPlayingCommand extends AudioCommandBase implements  ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();


        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }

        
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();
        
        if(track == null){
            channel.sendMessage("Nothing playing").queue();
            return;
        }
        
        final AudioTrackInfo info = track.getInfo();

        channel.sendMessageFormat("Now playing `%s` by `%s`\n <%s>",info.title,info.author,info.uri).queue();
        
    }

    @Override
    public String getName() {
        
        return "nowplaying";
    }

    @Override
    public String getHelp() {
        
        return "Displays current track";
    }

    @Override
    public List<String> getAliases(){
        return List.of("np");
    }

}
