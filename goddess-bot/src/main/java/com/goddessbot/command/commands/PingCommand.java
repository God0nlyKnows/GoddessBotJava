package com.goddessbot.command.commands;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;

import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        JDA jda = context.getJDA();
        
        jda.getRestPing().queue((ping)-> context.getChannel().sendMessageFormat("Rest ping %sms\nWebsocket ping %sms", ping,jda.getGatewayPing()).queue());
    }

    @Override
    public String getHelp(){
        return "Shows latency between rest and websocket";
    }

    @Override
    public String getName() {
        return "ping";
    }
    
}
