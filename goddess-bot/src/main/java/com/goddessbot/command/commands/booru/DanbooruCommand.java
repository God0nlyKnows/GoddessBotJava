package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.DanbooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class DanbooruCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        String arg = null;
        if (!context.getArgs().isEmpty())
            arg = context.getArgs().get(0);

        TextChannel channel = context.getTextChannel();

        if (channel.isNSFW())
            try {
                BooruService.sendRandomPost(channel, arg, new DanbooruGetPosts());

            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + arg + "` for you. (psst.. there was an error)").queue();
            }
        else
            channel.sendMessage("It's not NSFW channel! Baka! >///<").queue();
    }

    @Override
    public String getName() {

        return "danbooru";
    }

    @Override
    public String getHelp() {

        return "Get random post from Danbooru";
    }

}
