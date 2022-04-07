package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.GelbooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class GelbooruCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        TextChannel channel = context.getTextChannel();

        if (channel.isNSFW())
            try {
                BooruService.sendRandomPost(channel, context.getArgs().get(0), new GelbooruGetPosts());
            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + context.getArgs().get(0) + "` for you. (psst.. there was an error)");
            }
        else
            channel.sendMessage("It's not NSFW channel! Baka! >///<");
    }

    @Override
    public String getName() {

        return "gelbooru";
    }

    @Override
    public String getHelp() {

        return "Get random post from Gelbooru";
    }

}