package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.LolibooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class LolibooruCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        TextChannel channel = context.getTextChannel();

        if (channel.isNSFW())
            try {
                BooruService.sendRandomPost(channel, context.getArgs().get(0), new LolibooruGetPosts());
            } catch (Exception e) {
                channel.sendMessage(
                        "Bad luck, no `" + context.getArgs().get(0) + "` for you. (psst.. there was an error)").queue();
            }
        else
            channel.sendMessage("It's not NSFW channel! Baka! >///<").queue();
    }

    @Override
    public String getName() {

        return "loli";
    }

    @Override
    public String getHelp() {

        return "Get random post from Lolibooru";
    }

}