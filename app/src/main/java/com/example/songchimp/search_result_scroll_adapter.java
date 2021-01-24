package com.example.songchimp;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v4.media.session.IMediaControllerCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class search_result_scroll_adapter extends RecyclerView.Adapter<search_result_scroll_adapter.Viewholder> {

    public search_result_scroll_adapter(List<search_result_scroll_model> search_result_scroll_models) {
        this.search_result_scroll_models = search_result_scroll_models;
    }

    private List<search_result_scroll_model> search_result_scroll_models;
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String resource = search_result_scroll_models.get(position).getSong_image();
        String songTitle = search_result_scroll_models.get(position).getSong_title();
        String song_artists = search_result_scroll_models.get(position).getSong_artists();
        String song_duration = search_result_scroll_models.get(position).getSong_duration();
        String media_url = search_result_scroll_models.get(position).getSong_media_url();

        holder.setSong_image(resource);
        holder.setSong_title(songTitle);
        holder.setSong_artists(song_artists);
        holder.sng_title(songTitle);
    }

    @Override
    public int getItemCount() {
        return search_result_scroll_models.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView song_image;
        private TextView song_title;
        private TextView song_artists;
        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            song_image = itemView.findViewById(R.id.search_result_image);
            song_title = itemView.findViewById(R.id.search_result_song_name);
            song_artists = itemView.findViewById(R.id.search_result_artists);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    now_playing_activity.destroy();
                    Intent intent = new Intent(itemView.getContext(),now_playing_activity.class);
                    intent.putExtra("song_details", search_result_scroll_models.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);

                }
            });
        }
        private void setSong_image(String resource){
            Picasso.get().load(resource).resize(170,170).into(song_image);
        }
        private void setSong_title(String songTitle){
            song_title.setText(songTitle);
        }
        private  void setSong_artists(String songArtists){
            song_artists.setText(songArtists);
        }

        private  String sng_title(String title){
            return title;
        }
    }
}
