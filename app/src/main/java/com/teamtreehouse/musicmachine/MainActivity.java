package com.teamtreehouse.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.teamtreehouse.musicmachine.PlayMusicService.CHANGE_ONLY_PLAY_BUTTON_TEXT;
import static com.teamtreehouse.musicmachine.PlayMusicService.IS_PLAYING;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_SONG = "song";
    private static final String TAG = MainActivity.class.getSimpleName();
    public Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    Messenger mPlayMessenger;
    private Button mDownloadButton, mPlayPauseButton;
    private boolean mBound = false;
    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBound = true;
            mPlayMessenger = new Messenger(binder);
            Message message = Message.obtain();
            message.arg1 = IS_PLAYING;
            message.arg2 = CHANGE_ONLY_PLAY_BUTTON_TEXT;
            message.replyTo = mActivityMessenger;
            try {
                mPlayMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate MainActivity");
        mDownloadButton = (Button) findViewById(R.id.downloadButton);

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();

                // Send Messages to Handler for processing
                for (String song : Playlist.songs) {
                    Intent intent = new Intent(MainActivity.this, DownloadService.class);
                    intent.putExtra(KEY_SONG, song);
                    startService(intent);
                }
            }
        });

        mPlayPauseButton = (Button) findViewById(R.id.play_pause_button);
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
                startService(intent);
                Message message = Message.obtain();
                message.arg1 = IS_PLAYING;
                message.replyTo = mActivityMessenger;
                try {
                    mPlayMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        Log.d(TAG,"onStart");
        super.onStart();
        Intent intent = new Intent(this,PlayMusicService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Defines callback service for service binding, passed to bindService
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void changePlayButtonText(String buttonText) {
        mPlayPauseButton.setText(buttonText);
    }
}
