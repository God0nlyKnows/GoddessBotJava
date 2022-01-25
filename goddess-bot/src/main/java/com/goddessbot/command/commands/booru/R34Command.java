package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.R34GetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class R34Command implements ICommand {

    @Override
    public void handle(CommandContext context) {
        
        TextChannel channel = context.getTextChannel();

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new R34GetPosts());

    }

    @Override
    public String getName() {
        
        return "rule";
    }

    @Override
    public String getHelp() {
        
        return "Get random post from Rule34";
    }
    
}
