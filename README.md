# PopularMovieApp Using RecyclerView
A Movie App that gets data from movieDB api.
This App allow the users to discover the most popular movies playing.
Present the user with a grid arrangement of movie posters upon launch.
Allow your user to change sort order via a setting:
The sort order can be by most popular or by highest-rated
Allow the user to tap on a movie poster and transition to a details screen with additional information such as: original title,movie poster image thumbnail,A plot synopsis (called overview in the api) ,user rating (called vote_average in the api), release date

Library that you will need:

You’ll need to modify the build.gradle file for your app. These modifications will happen in the build.gradle file for your module’s directory, not the project root directory.
In your app/build.gradle file, add:


repositories {

    mavenCentral()

}
Next, add implementation 'com.squareup.picasso:picasso:2.71828' to your dependencies block.



To fetch popular movies, you will use the API from themoviedb.org.
If you don’t already have an account, you will need to create one in order to request an API Key.
In your request for a key, state that your usage will be for educational/non-commercial use. You will also need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.
In order to request popular movies you will want to request data from the /movie/popular and /movie/top_rated endpoints (documentation). An API Key is required.
Once you obtain your key, you append it to your HTTP request as a URL parameter like so:
http://api.themoviedb.org/3/movie/popular?api_key=[YOUR_API_KEY]
You will extract the movie id from this request. You will need this in subsequent requests.

Put your api_key in the NetworkUtils.java file 






