package emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.embedded.Rating


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


class RatingsTypeConverter {

    @TypeConverter
    fun fromRatingsJson(ratings: List<Rating>): String {
        return Gson().toJson(ratings)
    }

    @TypeConverter
    fun toRatingsList(jsonRatings: String): List<Rating> {
        val notesType = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson<List<Rating>>(jsonRatings, notesType)
    }

}