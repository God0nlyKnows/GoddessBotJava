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

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new GelbooruGetPosts());

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