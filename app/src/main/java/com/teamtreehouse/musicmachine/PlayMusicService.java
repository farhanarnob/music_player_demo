package com.teamtreehouse.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class PlayMusicService extends Service {
    private final IBinder mBinder = new LocalBinder();
    MediaPlayer mp;
    @Override
    public void onCreate() {
        mp = MediaPlayer.create(this,R.raw.jaholey);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
    }

    /**
     * Method for clients
     */
    public void play(){
        if(!mp.isPlaying()){
            mp.start();
        }
    }

    public void pause(){
        if(mp.isPlaying()){
            mp.pause();
        }
    }

    public boolean isPlaying(){
        return mp.isPlaying();
    }

    public class LocalBinder extends Binder {
        PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

}
