package emperorfin.android.moviesearch.domain.uilayer.event.output.movie

import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.embedded.RatingParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface MovieDataTransferObjectParams : Params {
    val imdbId: String?
    val title: String?
    val year: String?
    val rated: String?
    val released: String?
    val runtime: String?
    val genre: String?
    val director: String?
    val writer: String?
    val actors: String?
    val plot: String?
    val language: String?
    val country: String?
    val awards: String?
    val poster: String?
    val metascore: String?
    val imdbRating: String?
    val imdbVotes: String?
    val type: String?
    val dvd: String?
    val boxOffice: String?
    val production: String?
    val website: String?
    val response: String?
    val ratings: List<RatingParams>?
}
