package com.example.viplove.movie;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=put_your_api_here";
    private static final String RATE_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=put_your_api_here";

    final static String QUERY_PARAM = "q";
    final static String APPID_PARAM = "api_key";

    public static URL buildpopularUrl() {
        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("TAG", "Built URI " + url);

        return url;
    }

    public static URL buildRatedUrl() {
        Uri builtUri = Uri.parse(RATE_BASE_URL).buildUpon()
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("TAG", "Built URI " + url);

        return url;
    }

    public static ArrayList<Data> parseMovieJson(String json) {

        String title;
        String rating;
        String overview;
        String image;
        String release_date;
        ArrayList<Data> movies = new ArrayList<>();

        try {
            JSONObject basejson = new JSONObject(json);
            JSONArray result = basejson.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                JSONObject movie_result = result.getJSONObject(i);
                title = movie_result.getString("original_title");
                rating = movie_result.getString("vote_average");
                overview = movie_result.getString("overview");
                image = movie_result.getString("poster_path");
                release_date = movie_result.getString("release_date");
                Data movie = new Data(image, title, overview, rating, release_date);
                movies.add(movie);
            }

            return movies;

        } catch (JSONException e) {
            Log.e("Json Utils", "problem parsing json data", e);
        }
        return null;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
