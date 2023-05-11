package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.YandeGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class YandeCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        String arg = null;
        if (!context.getArgs().isEmpty())
            arg = context.getArgs().get(0);

        MessageChannel channel = context.getMessageChannel();
            try {
                BooruService.sendRandomPost(channel, arg, new YandeGetPosts());
            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + arg + "` for you. (psst.. there was an error)").queue();
            }
    }

    @Override
    public String getName() {
        return "yande";
    }

    @Override
    public String getHelp() {

        return "Get random post from Yande.re";
    }

}
