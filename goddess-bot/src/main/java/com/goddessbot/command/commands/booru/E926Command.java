package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.E926GetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class E926Command implements ICommand {

    @Override
    public void handle(CommandContext context) {

        TextChannel channel = context.getTextChannel();

        if (channel.isNSFW())
            try {
                BooruService.sendRandomPost(channel, context.getArgs().get(0), new E926GetPosts());
            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + context.getArgs().get(0) + "` for you. (psst.. there was an error)").queue();
            }
        else
            channel.sendMessage("It's not NSFW channel! Baka! >///<").queue();
    }

    @Override
    public String getName() {

        return "e926";
    }

    @Override
    public String getHelp() {

        return "Don't use that too OK?";
    }

}