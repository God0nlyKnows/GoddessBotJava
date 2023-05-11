package com.goddessbot.command.commands.audio;

import java.net.URI;
import java.net.URISyntaxException;

import com.goddessbot.Config;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public abstract class AudioCommandBase {

    protected boolean isBotInVoiceChannel(GuildVoiceState myVoiceState, MessageChannel channel) {

        if (!myVoiceState.inAudioChannel()) {
            channel.sendMessage("I must be in a voice channel :C").queue();
            return false;
        }
        return true;
    }

    protected boolean isMemberInVoiceChannel(Member member, MessageChannel channel) {

        if (!member.getVoiceState().inAudioChannel() && member.getId() != Config.get("owner_id")) {
            channel.sendMessage("You need to be in a voice channel :/").queue();
            return false;
        }
        return true;
    }

    protected boolean isMemberSameInVoiceChannel(Member member, GuildVoiceState myVoiceState, MessageChannel channel) {

        if (!member.getVoiceState().getChannel().equals(myVoiceState.getChannel())
                && member.getId() != Config.get("owner_id")) {
            channel.sendMessage("You need to be in the same voice channel :/").queue();
            return false;
        }
        return true;
    }

    protected boolean validateVoiceChannel(Member member, GuildVoiceState myVoiceState, MessageChannel channel) {
        if (!isBotInVoiceChannel(myVoiceState, channel)) {
            return false;
        }

        if (!isMemberInVoiceChannel(member, channel)) {
            return false;
        }

        if (!isMemberSameInVoiceChannel(member, myVoiceState, channel)) {
            return false;
        }

        return true;
    }

    protected boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
