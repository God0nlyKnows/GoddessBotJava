package com.goddessbot.services.audio;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class AudioReactionManager {
    public void handle(MessageReactionAddEvent event) {
        var emote = event.getReactionEmote();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

        System.out.println(emote.getEmoji());
        System.out.println(emote.getName());
        System.out.println(emote.getAsReactionCode());
        System.out.println(emote.getAsCodepoints());

        if (emote.getAsCodepoints().equals("U+23ed")) {

            musicManager.queueScheduler.skipTrack(1, event.getTextChannel());

        } else if (emote.getAsCodepoints().equals("U+23f9U+fe0f")) {

            cleanReaction(event);
            musicManager.queueScheduler.player.stopTrack();
            musicManager.queueScheduler.queue.clear();

        } else if (emote.getAsCodepoints().equals("U+1f502")) {
            cleanReaction(event);

            musicManager.queueScheduler.loop(event.getTextChannel());

        } else if (emote.getAsCodepoints().equals("U+1f500")) {
            cleanReaction(event);

            musicManager.queueScheduler.shuffle(event.getTextChannel());
        }

    }

    private void cleanReaction(MessageReactionAddEvent event) {
        var channel = event.getChannel();
        var msg = channel.retrieveMessageById(event.getMessageId()).complete();
        channel.deleteMessageById(event.getMessageId()).queue();
        channel.sendMessage(msg).queue(x -> {
            x.addReaction("🔂").queue();
            x.addReaction("🔀").queue();
            x.addReaction("⏹️").queue();
            x.addReaction("⏭").queue();
        });
    }
}
