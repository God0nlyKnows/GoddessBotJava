package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.TextChannel;

public class ResumeCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();

        if(!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)){
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        if(!musicManager.audioPlayer.isPaused()){
            channel.sendMessage("Track is already resumed").queue();
            return;
        }
        musicManager.queueScheduler.player.setPaused(false);

        channel.sendMessage("Track resumed").queue();
        
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getHelp() {
        return "Resumes current track";
    }

    @Override
    public List<String> getAliases(){
        return List.of("r");
    }
    
}
