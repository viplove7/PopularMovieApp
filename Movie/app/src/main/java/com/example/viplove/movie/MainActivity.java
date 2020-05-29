package com.example.viplove.movie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final ArrayList<Data> movie = new ArrayList<>();
    RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    MovieAdapter movieAdapter;
    TextView Emptystate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        Emptystate = (TextView) findViewById(R.id.empty);
        mErrorMessageDisplay = (TextView) findViewById(R.id.error);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        movieAdapter = new MovieAdapter(this, movie, new MovieAdapter.MovieItemClickListener() {
            @Override
            public void onClick(Data dataselected) {
                Class destinationClass = Details.class;
                Intent intentToStartDetailActivity = new Intent(MainActivity.this, destinationClass);
                intentToStartDetailActivity.putExtra("poster", dataselected.getPoster());
                intentToStartDetailActivity.putExtra("title", dataselected.getTitle());
                intentToStartDetailActivity.putExtra("rating", dataselected.getRating());
                intentToStartDetailActivity.putExtra("release", dataselected.getRelease_date());
                intentToStartDetailActivity.putExtra("overview", dataselected.getOverview());
                startActivity(intentToStartDetailActivity);
            }
        });


        mRecyclerView.setAdapter(movieAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.indicator);

        if (networkInfo != null && networkInfo.isConnected()) {

            loadMovieData();
        } else {


            mLoadingIndicator.setVisibility(View.GONE);
            Emptystate.setText(R.string.internet);
        }
        //loadMovieData();

    }

    public void onClick(String movieString) {
        Context context = this;
        Class destinationClass = Details.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieString);
        startActivity(intentToStartDetailActivity);
    }

    private void loadMovieData() {
        new FetchMovie().execute();
        showMovieDataView();

    }

    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovie extends AsyncTask<String, Void, ArrayList<Data>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Data> doInBackground(String... params) {

            URL movieRequestPopularityUrl = NetworkUtils.buildpopularUrl();

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestPopularityUrl);

                ArrayList<Data> simpleJsonMovieData = NetworkUtils.parseMovieJson(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(final ArrayList<Data> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                movieAdapter.setMovieData(movieData);

            } else {
                showErrorMessage();
            }
        }
    }

    public class getTopRatedMovies extends AsyncTask<String, Void, ArrayList<Data>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Data> doInBackground(String... params) {
            URL movieRequestPopularityUrl = NetworkUtils.buildRatedUrl();
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestPopularityUrl);

                ArrayList<Data> simpleJsonMovieData = NetworkUtils
                        .parseMovieJson(jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(final ArrayList<Data> simpleJsonMovieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (simpleJsonMovieData != null) {
                showMovieDataView();

                movieAdapter.setMovieData(simpleJsonMovieData);

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.topRated:

                new getTopRatedMovies().execute();
                setTitle(R.string.toprated_movies);
                break;
            case R.id.popular:

                new FetchMovie().execute();
                setTitle(R.string.popular_movies);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
