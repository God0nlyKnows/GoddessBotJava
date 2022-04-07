package com.goddessbot.services.booru;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Random;

import com.god0nlyknows.jbooru.IGetPosts;

public class BooruService {

    public static void sendRandomPost(TextChannel channel, String tag, IGetPosts getPosts) {
        var posts = getPosts.getPosts(tag);

        Random rand = new Random();
        var theChoosenOne = posts.get(rand.nextInt(posts.size()));
        if (checkIfVideo(theChoosenOne.getFileUrl(), List.of("webm", "mp4", "mkv", "gif", "avi"))) {
            channel.sendMessage("No support for videos right now").queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder();
        System.out.println(theChoosenOne.getFileUrl());
        builder.setImage(theChoosenOne.getFileUrl());
        channel.sendMessageEmbeds(List.of(builder.build())).queue();
    }

    private static boolean checkIfVideo(String str, List<String> match) {
        for (String item : match) {
            if (str.contains(item)) {
                return true;
            }
        }
        return false;
    }
}
