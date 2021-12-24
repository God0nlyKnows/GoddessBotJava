package com.goddessbot.services.audio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class TrackQueue extends AudioEventAdapter {

    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public boolean loop = false;

    public TrackQueue(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track){
        if(!this.player.startTrack(track, true)){
            this.queue.offer(track);
        }
    }

    public void nextTrack(){
        this.player.startTrack(this.queue.poll(),false);
    }
    
    public void skipTrack(int amount){
        while (amount > 1) {
            this.queue.remove();
            amount--;
        }
        this.player.startTrack(this.queue.poll(),false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track,AudioTrackEndReason endReason){
        if (endReason.mayStartNext) {
            if(this.loop){
                this.player.startTrack(track.makeClone(), false);
                return;
            }
            nextTrack();
        }
    }
}
