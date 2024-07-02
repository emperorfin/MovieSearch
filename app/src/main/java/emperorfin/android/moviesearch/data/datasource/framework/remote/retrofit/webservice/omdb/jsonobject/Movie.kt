package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject

import com.google.gson.annotations.SerializedName
import java.io.Serializable;
import com.squareup.moshi.Json


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class Movie(
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
) : Serializable
