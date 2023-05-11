package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class SkipCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();


        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }

        
        
        
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        
        if(audioPlayer.getPlayingTrack() == null){
            channel.sendMessage("Nothing to skip").queue();
            return;
        }
        
        int ammountToSkip = 1;
        
        try {
            if(context.getArgs().size()!=0){
                ammountToSkip = Integer.valueOf(context.getArgs().get(0));
            }
        }
        catch (NumberFormatException ex){
            channel.sendMessage("Bad argument format skipping `1`").queue();
        }finally {
            final int queueSize = musicManager.queueScheduler.queue.size();
            if(queueSize < ammountToSkip){
                ammountToSkip = queueSize + 1;
            }
            musicManager.queueScheduler.skipTrack(ammountToSkip, channel);
        }
        
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "Skips track";
    }

    @Override
    public List<String> getAliases(){
        return List.of("s");
    }

}
