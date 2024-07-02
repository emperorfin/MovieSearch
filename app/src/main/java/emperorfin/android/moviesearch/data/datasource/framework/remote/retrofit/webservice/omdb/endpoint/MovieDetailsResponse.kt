package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint

import com.squareup.moshi.Json
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject.Rating
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class MovieDetailsResponse(
//    @Json(name="Title")
    @SerializedName("Title")
    val title: String,
//    @Json(name="Year")
    @SerializedName("Year")
    val year: String,
//    @Json(name="imdbID")
    @SerializedName("imdbID")
    val imdbId: String,
//    @Json(name="Type")
    @SerializedName("Type")
    val type: String,
//    @Json(name="Poster")
    @SerializedName("Poster")
    val poster: String,
//    @Json(name="Rated")
    @SerializedName("Rated")
    val rated: String,
//    @Json(name="Released")
    @SerializedName("Released")
    val released: String,
//    @Json(name="Runtime")
    @SerializedName("Runtime")
    val runtime: String,
//    @Json(name="Genre")
    @SerializedName("Genre")
    val genre: String,
//    @Json(name="Director")
    @SerializedName("Director")
    val director: String,
//    @Json(name="Writer")
    @SerializedName("Writer")
    val writer: String,
//    @Json(name="Actors")
    @SerializedName("Actors")
    val actors: String,
//    @Json(name="Plot")
    @SerializedName("Plot")
    val plot: String,
//    @Json(name="Language")
    @SerializedName("Language")
    val language: String,
//    @Json(name="Country")
    @SerializedName("Country")
    val country: String,
//    @Json(name="Awards")
    @SerializedName("Awards")
    val awards: String,
//    @Json(name="Metascore")
    @SerializedName("Metascore")
    val metascore: String,
//    @Json(name="imdbRating")
    @SerializedName("imdbRating")
    val imdbRating: String,
//    @Json(name="imdbVotes")
    @SerializedName("imdbVotes")
    val imdbVotes: String,
//    @Json(name="DVD")
    @SerializedName("DVD")
    val dvd: String,
//    @Json(name="BoxOffice")
    @SerializedName("BoxOffice")
    val boxOffice: String,
//    @Json(name="Production")
    @SerializedName("Production")
    val production: String,
//    @Json(name="Website")
    @SerializedName("Website")
    val website: String,
//    @Json(name="Response")
    @SerializedName("Response")
    val response: String,
//    @Json(name="Ratings")
    @SerializedName("Ratings")
    val ratings: List<Rating>
) : Serializable

