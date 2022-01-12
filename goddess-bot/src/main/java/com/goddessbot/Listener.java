package com.goddessbot;

import javax.annotation.Nonnull;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.CommandManager;
import com.goddessbot.services.audio.PlayerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandContext.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        String raw = message.getContentRaw();
        String prefix = Config.get("default_prefix");

        if (user.isBot() || event.isWebhookMessage())
            return;

        if (!event.isFromGuild()) {
            message.reply("Fuck off").queue();
            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event);
        }

    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (checkItsMe(event)) {
            // there is a persistance queue option if this line disabled
            PlayerManager.getInstance().flushMusicManager(event.getGuild());
        }
        return;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("%#s is ready", event.getJDA().getSelfUser().getAsTag());
    }

    private boolean checkItsMe(GenericGuildVoiceEvent event) {
        if (event.getMember().getId() == event.getJDA().getSelfUser().getId()) {
            return true;
        }
        return false;

    }
}
