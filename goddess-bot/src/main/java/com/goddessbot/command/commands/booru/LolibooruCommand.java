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

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new LolibooruGetPosts());

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