package emperorfin.android.moviesearch.ui.model.movie

import emperorfin.android.moviesearch.domain.constant.StringConstants
import emperorfin.android.moviesearch.domain.constant.StringConstants.NULL
import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.ui.model.movie.embedded.Rating
import javax.inject.Inject


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


class MovieUiModelMapper @Inject constructor() {

    fun transform(movie: MovieModel): MovieUiModel {

        val imdbId: String = movie.imdbId
        val title: String = movie.title
        val year: String = movie.year
        val rated: String = movie.rated ?: NULL
        val released: String = movie.released ?: NULL
        val runtime: String = movie.runtime ?: NULL
        val genre: String = movie.genre ?: NULL
        val director: String = movie.director ?: NULL
        val writer: String = movie.writer ?: NULL
        val actors: String = movie.actors ?: NULL
        val plot: String = movie.plot ?: NULL
        val language: String = movie.language ?: NULL
        val country: String = movie.country ?: NULL
        val awards: String = movie.awards ?: NULL
        val poster: String = movie.poster
        val metascore: String = movie.metascore ?: NULL
        val imdbRating: String = movie.imdbRating ?: NULL
        val imdbVotes: String = movie.imdbVotes ?: NULL
        val type: String = movie.type
        val dvd: String = movie.dvd ?: NULL
        val boxOffice: String = movie.boxOffice ?: NULL
        val production: String = movie.production ?: NULL
        val website: String = movie.website ?: NULL
        val response: String = movie.response ?: NULL

        val ratings: MutableList<Rating> = mutableListOf()

        movie.ratings?.forEach {

            val rating = Rating(
                source = it.source,
                value = it.value,
            )

            ratings.add(rating)

        }

        val keywords: List<String> = movie
            .genre?.split(',')?.map { it.trim() }?.toList() ?: emptyList()

        return MovieUiModel.newInstance(
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