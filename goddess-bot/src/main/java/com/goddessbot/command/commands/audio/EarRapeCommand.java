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

        if (context.getArgs().isEmpty()) {
            channel.sendMessage("You need to provide arg").queue();
        }

        if (!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)) {
            return;
        }

        String link = String.join(" ", context.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
        musicManager.audioPlayer.setVolume(Integer.MAX_VALUE); // yeee
        PlayerManager.getInstance().loadAndPlay(channel, link);
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
