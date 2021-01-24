package com.example.songchimp;

import android.text.Editable;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface saavnAPI {

    @GET("result/")
    Call<List<Songlist>> getsongdetails(@Query("query") String query);

}
