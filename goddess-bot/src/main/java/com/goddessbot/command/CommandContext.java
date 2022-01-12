package com.goddessbot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContext {

    private final MessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(MessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    public MessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public JDA getJDA() {
        return this.event.getJDA();
    }

    public MessageChannel getChannel() {
        return this.event.getChannel();
    }

    public TextChannel getTextChannel() {
        return this.event.getTextChannel();
    }

    public Member getSelfMember() {
        return this.event.getGuild().retrieveMemberById(this.event.getJDA().getSelfUser().getIdLong()).complete();
    }

    public Member getMember() {
        return this.event.getMember();
    }

    public GuildVoiceState getMyVoiceState() {
        return this.getSelfMember().getVoiceState();
    }

}
