package com.goddessbot.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.goddessbot.Config;
import com.goddessbot.command.commands.HelpCommand;
import com.goddessbot.command.commands.PingCommand;
import com.goddessbot.command.commands.audio.ClearCommand;
import com.goddessbot.command.commands.audio.JoinCommand;
import com.goddessbot.command.commands.audio.LoopCommand;
import com.goddessbot.command.commands.audio.NowPlayingCommand;
import com.goddessbot.command.commands.audio.PauseCommand;
import com.goddessbot.command.commands.audio.PlayCommand;
import com.goddessbot.command.commands.audio.QueueCommand;
import com.goddessbot.command.commands.audio.ResumeCommand;
import com.goddessbot.command.commands.audio.SkipCommand;
import com.goddessbot.command.commands.audio.StopCommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();



    public CommandManager(){
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new JoinCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new LoopCommand());
        addCommand(new QueueCommand());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new PauseCommand());
        addCommand(new ResumeCommand());
        addCommand(new ClearCommand());
        

    }


    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if(nameFound){
            throw new IllegalArgumentException("A command whith this name already exists.");
        }

        commands.add(cmd);


    }

    public List<ICommand> getCommands(){
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search){
        String searchLower = search.toLowerCase();

        for(ICommand cmd : this.commands){
            if(cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(MessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(Config.get("default_prefix")),"").split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if(cmd != null){
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext context = new CommandContext(event, args);

            cmd.handle(context);
        }
    }
}
