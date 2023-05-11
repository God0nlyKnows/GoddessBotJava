package com.goddessbot.command.commands.audio;

import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.managers.AudioManager;


public class JoinCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final MessageChannel channel = context.getMessageChannel();
        final Member member = context.getMember();


        if(!isMemberInVoiceChannel(member, channel)){
            return;
        }


        final AudioManager audioManager = context.getGuild().getAudioManager();
        final AudioChannel memberChannel = member.getVoiceState().getChannel();
        
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

    @Override
    public List<String> getAliases(){
        return List.of("j");
    }
    
}
