package com.goddessbot.command.commands.audio;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.goddessbot.command.CommandContext;
import com.goddessbot.command.ICommand;
import com.goddessbot.services.audio.PlayerManager;

import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand extends AudioCommandBase implements ICommand {

    @Override
    public void handle(CommandContext context) {
        final TextChannel channel = context.getTextChannel();

        if(context.getArgs().isEmpty()){
            channel.sendMessage("You need to provide arg").queue();
        }



        if(!validateVoiceChannel(context.getMember(),context.getMyVoiceState(),channel)){
            return;
        }


        String link = String.join(" ", context.getArgs());

        if(!isUrl(link)){
            link = "ytsearch:" + link;
        }
        PlayerManager.getInstance().loadAndPlay(channel, link);
        
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

    private boolean isUrl(String url){
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e){
            return false;
        }
    }
    
}
