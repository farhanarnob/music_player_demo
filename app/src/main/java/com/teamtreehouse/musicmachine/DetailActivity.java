package com.teamtreehouse.musicmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.teamtreehouse.musicmachine.models.Song;

import static com.teamtreehouse.musicmachine.MainActivity.EXTRA_FAVORITE;
import static com.teamtreehouse.musicmachine.MainActivity.EXTRA_LIST_POSITION;
import static com.teamtreehouse.musicmachine.MainActivity.EXTRA_SONG;

public class DetailActivity extends AppCompatActivity {

    private Song mSong;
    private int mListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        TextView titleLabel = (TextView) findViewById(R.id.songTitleLabel);
//        if(intent.getStringExtra(SONG_TITLE)!=null){
//            String songTile = intent.getStringExtra(SONG_TITLE);
//            titleLabel.setText(songTile);
//        }
        final CheckBox favoriteCheckbox = (CheckBox) findViewById(R.id.checkBox);
        if (intent.getParcelableExtra(EXTRA_SONG) != null) {
            Song song = intent.getParcelableExtra(EXTRA_SONG);
            titleLabel.setText(song.getTitle());
            favoriteCheckbox.setChecked(song.isFavorite());
            mListPosition = intent.getIntExtra(EXTRA_LIST_POSITION, 0);
        }

        favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_FAVORITE, isChecked);
                resultIntent.putExtra(EXTRA_LIST_POSITION, mListPosition);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
