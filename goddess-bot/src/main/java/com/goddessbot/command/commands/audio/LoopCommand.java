package com.goddessbot.command.commands.audio;


import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.*;

public class LoopCommand extends AudioCommandBase implements ICommand {
    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();


        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }

        
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        final boolean loop = !musicManager.queueScheduler.loop;

        musicManager.queueScheduler.loop = loop;

        channel.sendMessageFormat("Loop **%s**", loop ? "enabled" : "disabled").queue();
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