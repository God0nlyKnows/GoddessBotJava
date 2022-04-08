package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.SafebooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class SafeCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        TextChannel channel = context.getTextChannel();

        if (context.getArgs().isEmpty()) {
            channel.sendMessage("You need to provide tag").queue();
            return;
        }
        try {
            BooruService.sendRandomPost(channel, context.getArgs().get(0), new SafebooruGetPosts());
        } catch (Exception e) {
            channel.sendMessage(
                    "Bad luck, no `" + context.getArgs().get(0) + "` for you. (psst.. there was an error)").queue();
        }

    }

    @Override
    public String getName() {

        return "safe";
    }

    @Override
    public String getHelp() {

        return "Get random post from Safebooru";
    }

}
