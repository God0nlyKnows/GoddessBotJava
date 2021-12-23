package com.goddessbot.command.commands;

import java.util.List;

import com.goddessbot.Config;
import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();
        final Member me = context.getSelfMember();
        final GuildVoiceState myVoiceState = me.getVoiceState();

        if(!myVoiceState.inAudioChannel()){
            JoinCommand join = new JoinCommand();
            join.handle(context);
            //channel.sendMessage("I must be in a voice channel :C").queue();
            //return;
        }

        final Member member = context.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel() && member.getId() != Config.get("owner_id")){
            channel.sendMessage("You need to be in a voice channel :/").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(myVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same voice channel :/").queue();
            return;
        }

        PlayerManager.getInstance().loadAndPlay(channel, context.getArgs().get(0));
        
    }

    @Override
    public String getName() {

        return "play";
    }

    @Override
    public String getHelp() {

        return "Plays a song";
    }

    @Override
    public List<String> getAliases(){
        return List.of("p","paly","music");
    }
    
}
