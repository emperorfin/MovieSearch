package emperorfin.android.moviesearch.ui.model.movie

import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieUiModelParams
import emperorfin.android.moviesearch.ui.model.movie.embedded.Rating


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


data class MovieUiModel private constructor(
    override val imdbId: String,
    override val title: String,
    override val year: String,
    override val rated: String,
    override val released: String,
    override val runtime: String,
    override val genre: String,
    override val director: String,
    override val writer: String,
    override val actors: String,
    override val plot: String,
    override val language: String,
    override val country: String,
    override val awards: String,
    override val poster: String,
    override val metascore: String,
    override val imdbRating: String,
    override val imdbVotes: String,
    override val type: String,
    override val dvd: String,
    override val boxOffice: String,
    override val production: String,
    override val website: String,
    override val response: String,
    override val ratings: List<Rating>,
    override val keywords: List<String>
) : MovieUiModelParams {

    companion object {

        fun newInstance(
            imdbId: String,
            title: String,
            year: String,
            rated: String,
            released: String,
            runtime: String,
            genre: String,
            director: String,
            writer: String,
            actors: String,
            plot: String,
            language: String,
            country: String,
            awards: String,
            poster: String,
            metascore: String,
            imdbRating: String,
            imdbVotes: String,
            type: String,
            dvd: String,
            boxOffice: String,
            production: String,
            website: String,
            response: String,
            ratings: List<Rating>,
            keywords: List<String>,
        ): MovieUiModel {
            return MovieUiModel(
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
                ratings = ratings,
                keywords = keywords,
            )
        }

    }

}
