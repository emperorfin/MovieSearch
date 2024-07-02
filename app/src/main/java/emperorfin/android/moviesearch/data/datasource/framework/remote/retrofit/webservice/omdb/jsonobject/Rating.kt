package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject

import com.squareup.moshi.Json
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class Rating(
//    @Json(name="Source")
    @SerializedName("Source")
    val source: String,
//    @Json(name="Value")
    @SerializedName("Value")
    val value: String
) : Serializable
