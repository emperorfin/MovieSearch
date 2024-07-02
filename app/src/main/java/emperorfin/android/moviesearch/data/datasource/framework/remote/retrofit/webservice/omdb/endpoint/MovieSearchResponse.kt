package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint

import com.squareup.moshi.Json
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject.Movie


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class MovieSearchResponse(
//    @Json(name="Search")
    @SerializedName("Search")
    val search: List<Movie>
) : Serializable
