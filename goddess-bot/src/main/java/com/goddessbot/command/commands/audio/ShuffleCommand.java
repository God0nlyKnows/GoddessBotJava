package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.GuildMusicManager;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class ShuffleCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();

        if (!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)) {
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());

        musicManager.queueScheduler.shuffle(channel);

    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getHelp() {
        return "Randomizes current queue";
    }

    @Override
    public List<String> getAliases() {
        return List.of("random", "rand", "randomize");
    }

}
