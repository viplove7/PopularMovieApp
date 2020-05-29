package com.example.viplove.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private ArrayList<Data> MovieArrayList;
    final private MovieItemClickListener mOnMovieItemClickListener;


    public interface MovieItemClickListener {
        void onClick(Data dataselected);
    }

    Context context;

    public MovieAdapter(Context context, ArrayList<Data> MovieArrayList, MovieItemClickListener listener) {
        this.context = context;
        this.MovieArrayList = MovieArrayList;
        mOnMovieItemClickListener = listener;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (MovieArrayList == null) {
            return 0;
        } else {
            Log.d("TAG", "movie-url:" + MovieArrayList.size());

            return MovieArrayList.size();
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        String url = "https://image.tmdb.org/t/p/w500";
        String complement = MovieArrayList.get(i).getPoster();

        String finalUrlPoster = url + complement;
        Picasso.get().load(finalUrlPoster).into(myViewHolder.image);

    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.image_file;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView image;
        TextView title;
        TextView date;
        TextView overview;
        TextView rating;

        public MyViewHolder(View itemview) {
            super(itemview);
            image = (ImageView) itemview.findViewById(R.id.image);
            title = (TextView) itemview.findViewById(R.id.title);
            date = (TextView) itemview.findViewById(R.id.release_date);
            overview = (TextView) itemview.findViewById(R.id.overview);
            rating = (TextView) itemview.findViewById(R.id.rating);
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Data dataselected = MovieArrayList.get(clickedPosition);
            mOnMovieItemClickListener.onClick(dataselected);
        }
    }

    public void setMovieData(ArrayList<Data> movieData) {
        MovieArrayList = movieData;
        notifyDataSetChanged();

    }


}
