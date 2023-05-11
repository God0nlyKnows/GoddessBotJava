package com.goddessbot.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


import com.goddessbot.Config;
import com.goddessbot.command.commands.HelpCommand;
import com.goddessbot.command.commands.PingCommand;
import com.goddessbot.command.commands.audio.ClearCommand;
import com.goddessbot.command.commands.audio.EarRapeCommand;
import com.goddessbot.command.commands.audio.JoinCommand;
import com.goddessbot.command.commands.audio.LeaveCommand;
import com.goddessbot.command.commands.audio.LoopCommand;
import com.goddessbot.command.commands.audio.NowPlayingCommand;
import com.goddessbot.command.commands.audio.PauseCommand;
import com.goddessbot.command.commands.audio.PlayCommand;
import com.goddessbot.command.commands.audio.QueueCommand;
import com.goddessbot.command.commands.audio.ResumeCommand;
import com.goddessbot.command.commands.audio.ReverseCommand;
import com.goddessbot.command.commands.audio.ShuffleCommand;
import com.goddessbot.command.commands.audio.SkipCommand;
import com.goddessbot.command.commands.audio.StopCommand;
import com.goddessbot.command.commands.booru.DanbooruCommand;
import com.goddessbot.command.commands.booru.E621Command;
import com.goddessbot.command.commands.booru.E926Command;
import com.goddessbot.command.commands.booru.GelbooruCommand;
import com.goddessbot.command.commands.booru.KonachanCommand;
import com.goddessbot.command.commands.booru.LolibooruCommand;
import com.goddessbot.command.commands.booru.R34Command;
import com.goddessbot.command.commands.booru.SafeCommand;
import com.goddessbot.command.commands.booru.YandeCommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        // audio cmds
        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new LoopCommand());
        addCommand(new QueueCommand());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new PauseCommand());
        addCommand(new ResumeCommand());
        addCommand(new ClearCommand());
        addCommand(new ShuffleCommand());
        addCommand(new EarRapeCommand());
        addCommand(new ReverseCommand());
        // booru cmds
        addCommand(new R34Command());
        addCommand(new DanbooruCommand());
        addCommand(new E621Command());
        addCommand(new E926Command());
        addCommand(new GelbooruCommand());
        addCommand(new KonachanCommand());
        addCommand(new LolibooruCommand());
        //addCommand(new RealbooruCommand());
        addCommand(new SafeCommand());
        addCommand(new YandeCommand());

    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command whith this name already exists.");
        }

        commands.add(cmd);

    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("default_prefix")), "").split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext context = new CommandContext(event, args);

            cmd.handle(context);
        }
    }
}
