package com.goddessbot.command.commands.audio;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class PauseCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();

        if(!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        if(musicManager.audioPlayer.isPaused()){
            channel.sendMessage("Track already paused").queue();
            return;
        }
        musicManager.queueScheduler.player.setPaused(true);

        channel.sendMessage("Track paused").queue();
        
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "Pauses current track";
    }
    
}
