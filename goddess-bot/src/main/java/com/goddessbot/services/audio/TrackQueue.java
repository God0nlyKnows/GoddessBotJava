package com.goddessbot.services.audio;

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class TrackQueue extends AudioEventAdapter {

    public final AudioPlayer player;
    public final LinkedList<AudioTrack> queue;
    public TextChannel lastUsedChannel;
    public boolean loop = false;

    public TrackQueue(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedList<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }

    public void skipTrack(int amount) {
        while (amount > 1) {
            this.queue.remove();
            amount--;
        }
        this.player.startTrack(this.queue.poll(), false);
    }

    public void shuffle() {
        Collections.shuffle(this.queue);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // set default volume after earrape
        if (player.getVolume() == Integer.MAX_VALUE) {
            player.setVolume(100);
        }
        if (endReason.mayStartNext) {
            if (this.loop) {
                this.player.startTrack(track.makeClone(), false);
                return;
            }
            nextTrack();
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        if (lastUsedChannel == null) {
            return;
        }

        Message lastMessage = lastUsedChannel.retrieveMessageById(lastUsedChannel.getLatestMessageId()).complete();
        if (checkIfLastMsgIsMine(lastMessage, lastUsedChannel)
                && (checkContentOfEmbed(lastMessage.getEmbeds().get(0), "Now playing:")
                        || checkContentOfEmbed(lastMessage.getEmbeds().get(0), "Now streaming:"))) {
            lastMessage.delete().queue();
        }
        lastUsedChannel.sendMessageEmbeds(List.of(createEmbed(track).build())).queue(msg -> {
            msg.addReaction("🔂").queue();
            msg.addReaction("⏹️").queue();
            msg.addReaction("⏭").queue();
        });
    }

    public TextChannel getLastUsedChannel() {
        return lastUsedChannel;
    }

    public void setLastUsedChannel(TextChannel lastUsedChannel) {
        this.lastUsedChannel = lastUsedChannel;
    }

    private EmbedBuilder createEmbed(AudioTrack track) {
        String status = "Now playing: ";
        if (track.getInfo().isStream) {
            status = "Now streaming: ";
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(track.getInfo().author);
        builder.setColor(Color.magenta);
        builder.setTitle(status + track.getInfo().title);
        builder.setDescription(track.getInfo().uri);
        return builder;
    }

    private boolean checkIfLastMsgIsMine(Message message, TextChannel channel) {
        if (message.getAuthor().getIdLong() == channel.getJDA().getSelfUser().getIdLong()) {
            return true;
        }
        return false;
    }

    private boolean checkContentOfEmbed(MessageEmbed embed, String content) {
        if (embed == null) {
            return false;
        }

        if (embed.getTitle().contains(content)) {
            return true;
        }
        return false;
    }
}
