package com.goddessbot.command.commands.booru;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;

import net.dv8tion.jda.api.entities.TextChannel;

public class YandeCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        TextChannel channel = context.getTextChannel();
        
        if (context.getArgs().isEmpty()) {
            channel.sendMessage("You need to provide tag").queue();
        }

        

    }

    @Override
    public String getName() {
        return "yande";
    }

    @Override
    public String getHelp() {
        
        return "Get random post from yande.re";
    }
    
}
