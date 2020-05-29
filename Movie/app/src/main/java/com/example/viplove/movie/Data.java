package com.example.viplove.movie;

public class Data {


    private String Poster;
    private String title;
    private String overview;
    private String rating;
    private String release_date;

    public Data(String mposter, String mtitle, String moverview, String mrating, String mrelease_date) {

        Poster = mposter;
        title = mtitle;
        overview = moverview;
        rating = mrating;
        release_date = mrelease_date;
    }

    public String getPoster() {
        return Poster;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getRelease_date() {
        return release_date;
    }
}
