package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject

import com.squareup.moshi.Json


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class Rating(
    @Json(name="Source")
    val source: String,
    @Json(name="Value")
    val value: String
)
