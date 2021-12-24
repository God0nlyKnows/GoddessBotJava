package com.goddessbot.command.commands.audio;

import com.goddessbot.Config;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class AudioCommandBase {

    protected boolean isBotInVoiceChannel(GuildVoiceState myVoiceState,TextChannel channel){
        
        if(!myVoiceState.inAudioChannel()){
            channel.sendMessage("I must be in a voice channel :C").queue();
            return false;
        }
        return true;
    }

    protected boolean isMemberInVoiceChannel(Member member,TextChannel channel){

        if(!member.getVoiceState().inAudioChannel() && member.getId() != Config.get("owner_id")){
            channel.sendMessage("You need to be in a voice channel :/").queue();
            return false;
        }
        return true;
    }


    protected boolean isMemberSameInVoiceChannel(Member member,GuildVoiceState myVoiceState,TextChannel channel){

        
        if(!member.getVoiceState().getChannel().equals(myVoiceState.getChannel()) && member.getId() != Config.get("owner_id")){
            channel.sendMessage("You need to be in the same voice channel :/").queue();
            return false;
        }
        return true;
    }

    protected boolean validateVoiceChannel(Member member,GuildVoiceState myVoiceState,TextChannel channel) {
        if(!isBotInVoiceChannel(myVoiceState,channel)){
            return false;
        }

        if(!isMemberInVoiceChannel(member,channel)){
            return false;
        }

        if(!isMemberSameInVoiceChannel(member,myVoiceState,channel)){
            return false;
        }
        
        return true;
    }


}
