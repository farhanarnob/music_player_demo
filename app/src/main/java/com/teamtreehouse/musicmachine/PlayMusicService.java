package com.teamtreehouse.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class PlayMusicService extends Service {
    public static final int PLAY_MUSIC = 1;
    public static final int STOP_MUSIC = 2;
    public static final int IS_PLAYING = 3;
    public static final int NOT_PLAYING = 4;
    public static final int CHANGE_ONLY_PLAY_BUTTON_TEXT = 5;
    private static final String TAG = PlayMusicService.class.getSimpleName();

    Messenger mMessenger = new Messenger(new PlayHandler(this));
    MediaPlayer mp;
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate Service");
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
        Log.d(TAG, "Play");
        mp.start();
    }

    public void pause(){
        Log.d(TAG, "Pause");
        mp.pause();

    }

    public boolean isPlaying() {
        Log.d(TAG, mp.isPlaying() + "Is playing");
        return mp.isPlaying();
    }
}
