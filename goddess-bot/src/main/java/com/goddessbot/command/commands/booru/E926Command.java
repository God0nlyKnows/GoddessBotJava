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

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new E926GetPosts());

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