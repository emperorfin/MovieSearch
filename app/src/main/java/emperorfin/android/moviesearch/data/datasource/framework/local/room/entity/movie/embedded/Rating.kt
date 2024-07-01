package emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.embedded

import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.embedded.RatingParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


data class Rating(
    override val source: String,
    override val value: String
) : RatingParams
