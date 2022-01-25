package com.goddessbot.command.commands.booru;

import com.god0nlyknows.jbooru.DanbooruGetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;

public class DanbooruCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        
        TextChannel channel = context.getTextChannel();

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new DanbooruGetPosts());

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
