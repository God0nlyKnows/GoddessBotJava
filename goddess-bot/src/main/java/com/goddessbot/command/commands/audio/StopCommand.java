package com.goddessbot.command.commands.audio;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.TextChannel;

public class StopCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();


        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        
        musicManager.queueScheduler.player.stopTrack();
        musicManager.queueScheduler.queue.clear();
        channel.sendMessage("Queue stopped and cleared").queue();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "Stops current track";
    }
    
}
