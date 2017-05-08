package io.bramkorsten.cuesystem;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.bramkorsten.cuesystem.classes.Cue;
import io.bramkorsten.cuesystem.classes.mediamanager;

public class MainActivity extends AppCompatActivity {

    mediamanager mainCueSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDatabase();
    }

    public void setDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Cue currentCue = (Cue) dataSnapshot.getValue(Cue.class);
                TextView msgView = (TextView)findViewById(R.id.message);
                TextView senderView = (TextView)findViewById(R.id.sender);
                TextView timeView = (TextView)findViewById(R.id.time);
                TextView console = (TextView)findViewById(R.id.consoleMsg);
                console.setText("Show Started! Waiting for cues...");
                msgView.setText(currentCue.getMessage());
                senderView.setText(currentCue.getSender());
                timeView.setText(currentCue.getTime());
                play(MainActivity.this, getResourceId(currentCue.getMessage(), "raw", getPackageName()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Cue currentCue = (Cue) dataSnapshot.getValue(Cue.class);
                TextView msgView = (TextView)findViewById(R.id.message);
                TextView senderView = (TextView)findViewById(R.id.sender);
                TextView timeView = (TextView)findViewById(R.id.time);
                msgView.setText(currentCue.getMessage());
                senderView.setText(currentCue.getSender());
                timeView.setText(currentCue.getTime());
                play(MainActivity.this, getResourceId(currentCue.getMessage(), "raw", getPackageName()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private MediaPlayer mMediaPlayer;

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play(Context c, int rid) {
        stop();

        mMediaPlayer = MediaPlayer.create(c, rid);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mMediaPlayer.start();
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
