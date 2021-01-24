package com.example.songchimp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private SearchView searchView;
    private ProgressBar progressBar;
    private RecyclerView searchresultrecyclerview;
    private LinearLayout linear_layout_home;
    private ImageView about_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progressBar_home);
        progressBar.setVisibility(View.INVISIBLE);
        searchView = findViewById(R.id.search_bar_home);
        about_button = findViewById(R.id.three_line_home);
        linear_layout_home = findViewById(R.id.linear_layout_home);

        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(i);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                linear_layout_home.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                getsongdetails(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }

    private void getsongdetails(String Query) {
        //////////Fetching Song Details using API////////////
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://song-chimp.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        saavnAPI saavnAPI = retrofit.create(com.example.songchimp.saavnAPI.class);
        Call<List<Songlist>> call = saavnAPI.getsongdetails(Query);
        call.enqueue(new Callback<List<Songlist>>() {
            @Override
            public void onResponse(Call<List<Songlist>> call, Response<List<Songlist>> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(HomeActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                List<Songlist> songlist = response.body();
                ArrayList<search_result_scroll_model> content = new ArrayList<search_result_scroll_model>();
                List<Integer> list1 = new ArrayList<Integer>();

                for (Songlist fetchsongdetails : songlist) {

                  ///////////////////passing song details to recycler view/////////////////////

                    content.add(new search_result_scroll_model(fetchsongdetails.getImage(),fetchsongdetails.getTitle(),fetchsongdetails.getArtists(),fetchsongdetails.getMedia_url(),fetchsongdetails.getDuration()));
                }
                searchresultrecyclerview = findViewById(R.id.search_results_recycler_view);
                search_result_scroll_adapter searchResultScrollAdapter = new search_result_scroll_adapter(content);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                searchresultrecyclerview.setAdapter(searchResultScrollAdapter);
                searchresultrecyclerview.setLayoutManager(linearLayoutManager);
                searchResultScrollAdapter.notifyDataSetChanged();

                ////////////////////////////////////////////////////////////////////////////////
            }


            @Override
            public void onFailure(Call<List<Songlist>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(HomeActivity.this,"No internet Connection",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
