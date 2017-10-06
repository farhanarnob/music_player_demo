package com.teamtreehouse.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class PlayMusicService extends Service {
    public static final int PLAY_MUSIC = 1;
    public static final int STOP_MUSIC = 2;
    Messenger mMessenger;
    PlayingMusicHandler mHandler = new PlayingMusicHandler();
    MediaPlayer mp;
    @Override
    public void onCreate() {
        mp = MediaPlayer.create(this,R.raw.jaholey);
        mMessenger = new Messenger(mHandler);
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
        return mMessenger.getBinder();
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

    class PlayingMusicHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case PLAY_MUSIC: {
                    play();
                    break;
                }
                case STOP_MUSIC: {
                    pause();
                    break;
                }
            }
        }
    }


}
