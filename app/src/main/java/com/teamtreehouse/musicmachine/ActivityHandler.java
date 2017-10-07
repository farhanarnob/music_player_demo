package com.teamtreehouse.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import static com.teamtreehouse.musicmachine.PlayMusicService.CHANGE_ONLY_PLAY_BUTTON_TEXT;
import static com.teamtreehouse.musicmachine.PlayMusicService.IS_PLAYING;
import static com.teamtreehouse.musicmachine.PlayMusicService.NOT_PLAYING;
import static com.teamtreehouse.musicmachine.PlayMusicService.PLAY_MUSIC;
import static com.teamtreehouse.musicmachine.PlayMusicService.STOP_MUSIC;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class ActivityHandler extends Handler {
    MainActivity mMainActivity;

    public ActivityHandler(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case IS_PLAYING: {
                if (msg.arg2 == CHANGE_ONLY_PLAY_BUTTON_TEXT) {
                    mMainActivity.changePlayButtonText("Pause");
                } else {
                    Message message = Message.obtain();
                    message.arg1 = STOP_MUSIC;
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mMainActivity.changePlayButtonText("Play");
                }
                break;
            }
            case NOT_PLAYING: {
                if (msg.arg2 == CHANGE_ONLY_PLAY_BUTTON_TEXT) {
                    mMainActivity.changePlayButtonText("Play");
                } else {
                    Message message = Message.obtain();
                    message.arg1 = PLAY_MUSIC;
                    try {
                        msg.replyTo.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    mMainActivity.changePlayButtonText("Pause");
                }
                break;
            }
        }
    }
}
