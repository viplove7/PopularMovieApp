package com.example.viplove.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView mPosterMovieDisplay = (ImageView) findViewById(R.id.image1);
        TextView mTitleMovieDisplay = (TextView) findViewById(R.id.title);

        TextView mDescriptionMovieDisplay = (TextView) findViewById(R.id.overview);

        TextView mrealsedate = (TextView) findViewById(R.id.release_date);
        TextView mrating = (TextView) findViewById(R.id.rating);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            String poster = intentThatStartedThisActivity.getStringExtra("poster");
            String title = intentThatStartedThisActivity.getStringExtra("title");
            String rating = intentThatStartedThisActivity.getStringExtra("rating");
            String realsedate = intentThatStartedThisActivity.getStringExtra("release");
            String overview = intentThatStartedThisActivity.getStringExtra("overview");
            String url = "https://image.tmdb.org/t/p/w500";
            String finalUrlPoster = url + poster;
            mTitleMovieDisplay.setText(title);
            mDescriptionMovieDisplay.setText(overview);
            Picasso.get().load(finalUrlPoster).into(mPosterMovieDisplay);
            mrating.setText(rating);
            mrealsedate.setText(realsedate);

        }
    }
}
