package com.goddessbot.command.commands;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;

import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.managers.AudioManager;


public class JoinCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getChannel();
        final Member me = context.getSelfMember();
        final GuildVoiceState myVoiceState = me.getVoiceState();

        if(myVoiceState.inAudioChannel()){
            //channel.sendMessage("I'm in other audio channel :C").queue();
            //return;
        }

        final Member member = context.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            channel.sendMessage("You need to be in a voice channel :/").queue();
            return;
        }

        final AudioManager audioManager = context.getGuild().getAudioManager();
        final AudioChannel memberChannel = memberVoiceState.getChannel();
        
        audioManager.openAudioConnection(memberChannel);

        
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Joins to a channel";
    }
    
}
