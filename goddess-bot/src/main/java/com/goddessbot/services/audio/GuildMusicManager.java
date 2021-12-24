package com.goddessbot.services.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;


public class GuildMusicManager {
    public final AudioPlayer audioPlayer;
    private final AudioPlayerSendHandler sendHandler;

    public final TrackQueue queueScheduler;


    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.queueScheduler = new TrackQueue(this.audioPlayer);
        this.audioPlayer.addListener(this.queueScheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }


    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

}
