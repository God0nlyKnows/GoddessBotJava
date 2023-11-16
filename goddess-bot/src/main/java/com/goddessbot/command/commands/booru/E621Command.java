package com.goddessbot.command.commands.booru;

import java.util.List;

import com.god0nlyknows.jbooru.E621GetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class E621Command implements ICommand {

    @Override
    public void handle(CommandContext context) {

        String arg = null;
        if (!context.getArgs().isEmpty())
            arg = context.getArgs().get(0);

        MessageChannel channel = context.getMessageChannel();


            try {
                BooruService.sendRandomPost(channel, arg, new E621GetPosts());
            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + arg + "` for you. (psst.. there was an error)").queue();
            }

    }

    @Override
    public String getName() {

        return "e621";
    }

    @Override
    public List<String> getAliases() {
        return List.of("furry");
    }

    @Override
    public String getHelp() {

        return "Just don't OK?";
    }

}