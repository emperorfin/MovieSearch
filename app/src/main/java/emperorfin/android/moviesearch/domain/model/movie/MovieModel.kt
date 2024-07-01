package emperorfin.android.moviesearch.domain.model.movie

import emperorfin.android.moviesearch.domain.model.movie.embedded.Rating
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieModelParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


data class MovieModel private constructor(
    override val imdbId: String,
    override val title: String,
    override val year: String,
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
    override val poster: String,
    override val metascore: String? = null,
    override val imdbRating: String? = null,
    override val imdbVotes: String? = null,
    override val type: String,
    override val dvd: String? = null,
    override val boxOffice: String? = null,
    override val production: String? = null,
    override val website: String? = null,
    override val response: String? = null,
    override val ratings: List<Rating>? = null
) : MovieModelParams {

    companion object {

        fun newInstance(
            imdbId: String,
            title: String,
            year: String,
            rated: String? = null,
            released: String? = null,
            runtime: String? = null,
            genre: String? = null,
            director: String? = null,
            writer: String? = null,
            actors: String? = null,
            plot: String? = null,
            language: String? = null,
            country: String? = null,
            awards: String? = null,
            poster: String,
            metascore: String? = null,
            imdbRating: String? = null,
            imdbVotes: String? = null,
            type: String,
            dvd: String? = null,
            boxOffice: String? = null,
            production: String? = null,
            website: String? = null,
            response: String? = null,
            ratings: List<Rating>? = null
        ): MovieModel {
            return MovieModel(
                imdbId = imdbId,
                title = title,
                year = year,
                rated = rated,
                released = released,
                runtime = runtime,
                genre = genre,
                director = director,
                writer = writer,
                actors = actors,
                plot = plot,
                language = language,
                country = country,
                awards = awards,
                poster = poster,
                metascore = metascore,
                imdbRating = imdbRating,
                imdbVotes = imdbVotes,
                type = type,
                dvd = dvd,
                boxOffice = boxOffice,
                production = production,
                website = website,
                response = response,
                ratings = ratings
            )
        }

    }

}
