package com.example.chuck.movie_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.chuck.movie_app.data.MovieItem;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;

    ArrayList<MovieItem> items = new ArrayList<MovieItem>();

    OnItemClickListener listener;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.movie_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int i) {

        MovieItem item = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    public void addItem(MovieItem item) {
        items.add(item);
    }

    public MovieItem getItem(int position) {
        return items.get(position);
    }

    public void removeItemAll() {
        items.clear();
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        RatingBar userRating;
        TextView pubDate;
        TextView director;
        TextView actor;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.title);
            userRating = itemView.findViewById(R.id.userRating);
            pubDate = itemView.findViewById(R.id.pubDate);
            director = itemView.findViewById(R.id.director);
            actor = itemView.findViewById(R.id.actor);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(MovieItem item) {

            sendImageRequest(item.getImage());
            title.setText(item.getTitle().replaceAll("\\<.*?>", "")); // for removing <b>, </b> with regex
            userRating.setRating(item.getUserRating() / 2);
            pubDate.setText(item.getPubDate());
            director.setText(item.getDirector());
            actor.setText(item.getActor());
        }

        // request image from URL
        public void sendImageRequest(String urlStr) {
            String url = urlStr;
            ImageLoadTask task = new ImageLoadTask(url, imageView);
            task.execute();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

}
