package com.goddessbot.command.commands.audio;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.TextChannel;

public class EarRapeCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();

        if (!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)) {
            return;
        }


        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        if(Integer.toString(musicManager.audioPlayer.getVolume()).equalsIgnoreCase("100")){
            musicManager.audioPlayer.setVolume(Integer.MAX_VALUE); // yeee
        } else {
            musicManager.audioPlayer.setVolume(100);
        }

        
    }

    @Override
    public String getName() {
        return "earrape";
    }

    @Override
    public String getHelp() {
        return "Ears blowing volume";
    }

}
