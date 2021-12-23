package com.goddessbot.command.commands;

import java.util.List;

import com.goddessbot.Config;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.CommandManager;
import com.goddessbot.command.ICommand;

import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager){
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext context) {
        List<String> args = context.getArgs();
        TextChannel channel = context.getTextChannel();
        
        if(args.isEmpty()){
            StringBuilder builder = new StringBuilder();

            builder.append("List of commands\n");

            manager.getCommands().stream().map(ICommand::getName).forEach(
                (it) -> builder.append('`').append(Config.get("default_prefix")).append(it).append("`\n")
            );

            channel.sendMessage(builder.toString()).queue();


            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if(command == null){
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        
        return "Shows list of commands\n"+
        "Usage: `>help [command]`";
    }

    @Override
    public List<String> getAliases(){
        return List.of("commands","cmds","commandlist");
    }
    
}
