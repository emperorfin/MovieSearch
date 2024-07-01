package emperorfin.android.moviesearch.domain.uilayer.event.input.movie

import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieModelParams
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.embedded.RatingParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


data class MovieParams(
    override val imdbId: String? = null,
    override val title: String? = null,
    override val year: String? = null,
    override val rated: String? = null,
    override val released: String? = null,
    override val runtime: String? = null,
    override val genre: String? = null,
    override val director: String? = null,
    override val writer: String? = null,
    override val actors: String? = null,
    override val plot: String? = null,
    override val language: String? = null,
    override val country: String? = null,
    override val awards: String? = null,
    override val poster: String? = null,
    override val metascore: String? = null,
    override val imdbRating: String? = null,
    override val imdbVotes: String? = null,
    override val type: String? = null,
    override val dvd: String? = null,
    override val boxOffice: String? = null,
    override val production: String? = null,
    override val website: String? = null,
    override val response: String? = null,
    override val ratings: List<RatingParams>? = null
) : MovieModelParams
