package com.goddessbot.services.audio;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class AudioReactionManager {
    public void handle(MessageReactionAddEvent event) {
        var emote = event.getReaction().getEmoji();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        

        if (emote.getAsReactionCode().equals(Emoji.fromUnicode("⏭").getAsReactionCode())) {

            musicManager.queueScheduler.skipTrack(1, event.getChannel());

        } else if (emote.getAsReactionCode().equals(Emoji.fromUnicode("⏹️").getAsReactionCode())) {

            cleanReaction(event);
            musicManager.queueScheduler.player.stopTrack();
            musicManager.queueScheduler.queue.clear();

        } else if (emote.getAsReactionCode().equals(Emoji.fromUnicode("🔂").getAsReactionCode())) {
            cleanReaction(event);

            musicManager.queueScheduler.loop(event.getChannel());

        } else if (emote.getAsReactionCode().equals(Emoji.fromUnicode("🔀").getAsReactionCode())) {
            cleanReaction(event);

            musicManager.queueScheduler.shuffle(event.getChannel());
        }

    }

    private void cleanReaction(MessageReactionAddEvent event) {
        var channel = event.getChannel();
        var msg = channel.retrieveMessageById(event.getMessageId()).complete();
        msg.clearReactions().queue();
        msg.addReaction(Emoji.fromUnicode("🔂")).queue();
            msg.addReaction(Emoji.fromUnicode("🔀")).queue();
            msg.addReaction(Emoji.fromUnicode("⏹️")).queue();
            msg.addReaction(Emoji.fromUnicode("⏭")).queue();
    }
}
