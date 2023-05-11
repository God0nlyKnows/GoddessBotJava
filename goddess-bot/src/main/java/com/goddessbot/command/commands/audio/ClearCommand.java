package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class ClearCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();

        if(!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        if(!musicManager.queueScheduler.queue.isEmpty()){
            musicManager.queueScheduler.queue.clear();
            channel.sendMessage("Queue cleared").queue();
            return;
        }

        channel.sendMessage("Queue was empty!").queue();        
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Clears current queue";
    }

    @Override
    public List<String> getAliases(){
        return List.of("c");
    }
    
}
