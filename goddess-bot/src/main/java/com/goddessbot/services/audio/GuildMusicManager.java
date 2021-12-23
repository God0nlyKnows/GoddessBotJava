package com.goddessbot.services.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;


public class GuildMusicManager {
    private final AudioPlayer audioPlayer;
    private final AudioPlayerSendHandler sendHandler;

    public final TrackQueue queue;


    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.queue = new TrackQueue(this.audioPlayer);
        this.audioPlayer.addListener(this.queue);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }


    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

}
