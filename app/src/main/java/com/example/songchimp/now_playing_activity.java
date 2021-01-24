package com.example.songchimp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class now_playing_activity extends AppCompatActivity {

    private ImageView songimage;
    private TextView songtitle;
    private TextView songartist;
    private ImageView back_btn;
    private ImageView play_control;
    private static MediaPlayer media_player;
    private TextView totaltime;
    private TextView currenttime;
    private SeekBar seekBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!media_player.isPlaying()){
            media_player.reset();
            media_player = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_);
        songimage = findViewById(R.id.sng_img_nw_plying);
        songtitle = findViewById(R.id.sng_ttl_nw_plying);
        songartist = findViewById(R.id.sng_artist_nw_playing);
        play_control = findViewById(R.id.play_pause_control);
        back_btn = findViewById(R.id.back_btn_nw_playing);
        seekBar = findViewById(R.id.seekBar_nw_playing);
        totaltime = findViewById(R.id.total_duration);
        currenttime = findViewById(R.id.duration_start);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent i = getIntent();
        final search_result_scroll_model song_details = i.getParcelableExtra("song_details");
        Picasso.get().load(song_details.getSong_image()).resize(900,900).into(songimage);
        songtitle.setText(song_details.getSong_title());
        songartist.setText(song_details.getSong_artists());
        media_player = new MediaPlayer();
        media_player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String media_url =  song_details.getSong_media_url();
        playsong(media_url);
        mediaplayeractivities();
        seekbaractivities(seekBar);
        thread();

    }

    static void destroy() {
        if(media_player != null){
            if(media_player.isPlaying()){
                media_player.stop();
            }
            media_player.release();
            media_player = null ;
        }
    }

    private void thread() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                while (media_player!=null ){
                    try {
                        if(media_player.isPlaying()){
                            Message message = new Message();
                            message.what = media_player.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            currenttime.setText(timer_label(msg.what));
            seekBar.setProgress(msg.what);
        }
    };

    private void seekbaractivities(final SeekBar s) {
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(media_player!=null && fromUser){
                    media_player.seekTo(progress);
                    s.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void mediaplayeractivities() {
        media_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play_control.setImageResource(R.drawable.exo_controls_play);
            }
        });
        play_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleplaypause();
            }
        });
        media_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totime = timer_label(media_player.getDuration());
                totaltime.setText(totime);
                seekBar.setMax(media_player.getDuration());
                toggleplaypause();
            }
        });
    }

    private void playsong(String url) {
        if (media_player.isPlaying()){
            media_player.stop();
            media_player.reset();
        }
        try {
            media_player.setDataSource(url);
            media_player.prepareAsync();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public  String timer_label(int duration){
        String timerlabel = "";
        int min = duration / 1000 / 60;
        int sec = duration /1000 % 60;
        timerlabel += min + ":";
        if (sec < 10) timerlabel +="0";
        timerlabel +=sec;
        return  timerlabel;
    }

    private void toggleplaypause() {
        if (media_player.isPlaying()){
            media_player.pause();
            play_control.setImageResource(R.drawable.exo_controls_play);
        }
        else {
            media_player.start();
            play_control.setImageResource(R.drawable.exo_controls_pause);
        }
    }
}