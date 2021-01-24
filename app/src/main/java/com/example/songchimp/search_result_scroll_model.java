package com.example.songchimp;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;
public class search_result_scroll_model implements Parcelable {


    private String song_image;
    private String song_title;
    private String song_artists;
    private String song_media_url;
    private String song_duration;

    public search_result_scroll_model(String song_image, String song_title, String song_artists, String song_media_url, String song_duration) {
        this.song_image = song_image;
        this.song_title = song_title;
        this.song_artists = song_artists;
        this.song_media_url = song_media_url;
        this.song_duration = song_duration;
    }

    public String getSong_media_url() {
        return song_media_url;
    }

    public void setSong_media_url(String song_media_url) {
        this.song_media_url = song_media_url;
    }

    public String getSong_duration() {
        return song_duration;
    }

    public void setSong_duration(String song_duration) {
        this.song_duration = song_duration;
    }

    public String getSong_image() {
        return song_image;
    }

    public void setSong_image(String song_image) {
        this.song_image = song_image;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_artists() {
        return song_artists;
    }

    public void setSong_artists(String song_artists) {
        this.song_artists = song_artists;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(song_image);
        dest.writeString(song_title);
        dest.writeString(song_artists);
        dest.writeString(song_media_url);
        dest.writeString(song_duration);
    }
    protected search_result_scroll_model(Parcel in) {
        song_image = in.readString();
        song_title = in.readString();
        song_artists = in.readString();
        song_media_url = in.readString();
        song_duration = in.readString();
    }

    public static final Creator<search_result_scroll_model> CREATOR = new Creator<search_result_scroll_model>() {
        @Override
        public search_result_scroll_model createFromParcel(Parcel in) {
            return new search_result_scroll_model(in);
        }

        @Override
        public search_result_scroll_model[] newArray(int size) {
            return new search_result_scroll_model[0];
        }
    };
}
