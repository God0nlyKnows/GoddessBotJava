package com.goddessbot.command.commands.audio;


import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;


public class LoopCommand extends AudioCommandBase implements ICommand {
    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();


        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }

        
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());

        musicManager.queueScheduler.loop(channel);
    }

    @Override
    public String getName() {
        return "loop";
    }

    @Override
    public String getHelp() {
        return "Loops the current song";
    }

}