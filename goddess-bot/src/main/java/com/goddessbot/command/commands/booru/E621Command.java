package com.goddessbot.command.commands.booru;

import java.util.List;

import com.god0nlyknows.jbooru.E621GetPosts;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.booru.BooruService;

import net.dv8tion.jda.api.entities.TextChannel;


public class E621Command implements ICommand {

    @Override
    public void handle(CommandContext context) {
        
        TextChannel channel = context.getTextChannel();

        BooruService.sendRandomPost(channel, context.getArgs().get(0), new E621GetPosts());

    }

    @Override
    public String getName() {
        
        return "e621";
    }

    @Override
    public List<String> getAliases(){
        return List.of("furry");
    }
    

    @Override
    public String getHelp() {
        
        return "Just don't OK?";
    }
    
}