package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();

        if (context.getArgs().isEmpty()) {
            channel.sendMessage("You need to provide arg").queue();
        }
        if (!context.getMyVoiceState().inAudioChannel()) {
            JoinCommand joinCmd = new JoinCommand();
            joinCmd.handle(context);
        } else {
            if (!validateVoiceChannel(context.getMember(), context.getMyVoiceState(), channel)) {
                return;
            }
        }
        String link = String.join(" ", context.getArgs());

        if (link.equals("god")) {
            link = "https://www.youtube.com/playlist?list=PLHXbqPfGR9PVAqLAeJ3gh6cR8Fi8DJyq3"; // yes it's personal
        } else if (link.equals("sheep")) {
            link = "https://www.youtube.com/playlist?list=PL4d2_5a1Du99MUsh4fd3e-B7G-2EEWzSi";
        } else if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }
        PlayerManager.getInstance().loadAndPlay(channel, link);

    }

    @Override
    public String getName() {

        return "play";
    }

    @Override
    public String getHelp() {

        return "Plays a song";
    }

    @Override
    public List<String> getAliases() {
        return List.of("p", "paly", "music");
    }

}
