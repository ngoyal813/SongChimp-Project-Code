
package com.example.songchimp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Songlist {


        private String duration;
        private String image;

        private String media_url;

    @SerializedName("song")
    private String title;



    @SerializedName("perma_url")
    private  String saavnlink;

    @SerializedName("primary_artists")
    private String artists ;




    public String getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }
    public String getMedia_url() {
        return media_url;
    }

    public String getTitle() {
        return title;
    }



    public String getSaavnlink() {
        return saavnlink;
    }
    public String getArtists() {
        return artists;
    }



}



