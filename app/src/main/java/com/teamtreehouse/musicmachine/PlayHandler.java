package com.teamtreehouse.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import static com.teamtreehouse.musicmachine.PlayMusicService.CHANGE_ONLY_PLAY_BUTTON_TEXT;
import static com.teamtreehouse.musicmachine.PlayMusicService.IS_PLAYING;
import static com.teamtreehouse.musicmachine.PlayMusicService.NOT_PLAYING;
import static com.teamtreehouse.musicmachine.PlayMusicService.PLAY_MUSIC;
import static com.teamtreehouse.musicmachine.PlayMusicService.STOP_MUSIC;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class PlayHandler extends Handler {
    private static final String TAG = PlayHandler.class.getSimpleName();
    private PlayMusicService mPlayMusicService;

    public PlayHandler(PlayMusicService playMusicService) {
        mPlayMusicService = playMusicService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case PLAY_MUSIC: {
                mPlayMusicService.play();
                break;
            }
            case STOP_MUSIC: {
                mPlayMusicService.pause();
                break;
            }
            case IS_PLAYING: {
                int isPlaying = mPlayMusicService.isPlaying() ? IS_PLAYING : NOT_PLAYING;
                Log.d(TAG, isPlaying + "Music Status");
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                if (msg.arg2 == CHANGE_ONLY_PLAY_BUTTON_TEXT) {
                    message.arg2 = CHANGE_ONLY_PLAY_BUTTON_TEXT;
                }
                message.replyTo = mPlayMusicService.mMessenger;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
