package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.KonachanGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class KonachanCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        
        TextChannel channel = context.getTextChannel();

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new KonachanGetPosts());

    }

    @Override
    public String getName() {
        
        return "konachan";
    }

    @Override
    public String getHelp() {
        
        return "Get random post from Konachan";
    }
    
}
