package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.SafebooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class SafeCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        
        TextChannel channel = context.getTextChannel();
        
        if (context.getArgs().isEmpty()) {
            channel.sendMessage("You need to provide tag").queue();
            return;
        }

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new SafebooruGetPosts());

    }

    @Override
    public String getName() {
        
        return "safe";
    }

    @Override
    public String getHelp() {
        
        return "Get random post from Safebooru";
    }
    
}
