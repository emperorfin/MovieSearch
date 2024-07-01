package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject

import com.squareup.moshi.Json


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class Movie(
    @Json(name="Title")
    val title: String,
    @Json(name="Year")
    val year: String,
    @Json(name="imdbID")
    val imdbId: String,
    @Json(name="Type")
    val type: String,
    @Json(name="Poster")
    val poster: String,
)
