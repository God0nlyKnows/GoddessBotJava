package com.goddessbot;



import com.goddessbot.command.CommandContext;
import com.goddessbot.command.CommandManager;
import com.goddessbot.services.audio.AudioReactionManager;
import com.goddessbot.services.audio.PlayerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandContext.class);
    private final CommandManager commandManager = new CommandManager();
    private final AudioReactionManager audioReactionManager = new AudioReactionManager();

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
            commandManager.handle(event);
        }

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        Message message = event.retrieveMessage().complete();

        if(checkItsMyMsg(event) && !checkItsBot(event) && (checkContentOfEmbed(message.getEmbeds().get(0), "Now playing:") || checkContentOfEmbed(message.getEmbeds().get(0), "Now streaming:"))){
            
            audioReactionManager.handle(event);
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (checkItsMe(event)) {
            // there is a persistance queue option if this line disabled
            PlayerManager.getInstance().flushMusicManager(event.getGuild());
        }
        return;
    }

    @Override
    public void onReady(net.dv8tion.jda.api.events.session.ReadyEvent event) {
        LOGGER.info("%#s is ready", event.getJDA().getSelfUser().getAsTag());
    }

    private boolean checkItsMe(GenericGuildVoiceEvent event) {
        if (event.getMember().getId() == event.getJDA().getSelfUser().getId()) {
            return true;
        }
        return false;

    }

    private boolean checkItsMyMsg(GenericMessageReactionEvent event) {
        var msg = event.retrieveMessage().complete();
        var id = event.getJDA().getSelfUser().getId();
        if (msg.getAuthor().getId().equals(id)) {
            return true;
        }
        return false;

    }

    private boolean checkItsBot(GenericMessageReactionEvent event) {

        if (event.getUser().isBot()) {
            return true;
        }
        return false;

    }

    private boolean checkContentOfEmbed(MessageEmbed embed, String content) {
        if (embed == null) {
            return false;
        }

        if (embed.getTitle().contains(content)) {
            return true;
        }
        return false;
    }
}
