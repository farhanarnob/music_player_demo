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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_SONG = "song";
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mDownloadButton, mPlayPauseButton;
    private boolean mBound = false;
    private Messenger mServiceMessenger;
    private int currentStateOfMusic = PlayMusicService.STOP_MUSIC;
    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG,"onServiceConnected");
            mServiceMessenger = new Messenger(binder);
            currentStateOfMusic = PlayMusicService.PLAY_MUSIC;
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
            mBound = false;
        }
    };



    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG,"onPostResume");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(TAG,"onPostCreate");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,PlayMusicService.class);
        startService(intent);
        Log.d(TAG,"onCreate");
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
                if(mBound){
                    if (currentStateOfMusic == PlayMusicService.PLAY_MUSIC) {
                        Message message = Message.obtain();
                        message.arg1 = PlayMusicService.PLAY_MUSIC;
                        currentStateOfMusic = PlayMusicService.STOP_MUSIC;
                        try {
                            mServiceMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        mPlayPauseButton.setText("Pause");
                    }else {
                        Message message = Message.obtain();
                        message.arg1 = PlayMusicService.STOP_MUSIC;
                        currentStateOfMusic = PlayMusicService.PLAY_MUSIC;
                        try {
                            mServiceMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        mPlayPauseButton.setText("Play");
                    }
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
            Log.d(TAG,"unBound");
            unbindService(mConnection);
            mBound = false;
        }
    }
}
