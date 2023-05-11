package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();

        if(!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)){
            return;
        }

        final Guild guild = context.getGuild();

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        
        musicManager.queueScheduler.loop = false;
        musicManager.queueScheduler.queue.clear();
        musicManager.audioPlayer.stopTrack();

        final AudioManager audioManager = guild.getAudioManager();

        audioManager.closeAudioConnection();

        channel.sendMessage("Adios!").queue();
        
    }

    @Override
    public String getName() {
        
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Leaves a voice channel";
    }

    @Override
    public List<String> getAliases(){
        return List.of("l");
    }
    
}
