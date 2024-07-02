package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dto.movie

import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dto.movie.embedded.Rating
import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import javax.inject.Inject


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


class MovieDataTransferObjectMapper @Inject constructor() {

    fun transform(movie: MovieModel): MovieDataTransferObject {

        val imdbId: String = movie.imdbId
        val title: String = movie.title
        val year: String = movie.year
        val rated: String? = movie.rated
        val released: String? = movie.released
        val runtime: String? = movie.runtime
        val genre: String? = movie.genre
        val director: String? = movie.director
        val writer: String? = movie.writer
        val actors: String? = movie.actors
        val plot: String? = movie.plot
        val language: String? = movie.language
        val country: String? = movie.country
        val awards: String? = movie.awards
        val poster: String = movie.poster
        val metascore: String? = movie.metascore
        val imdbRating: String? = movie.imdbRating
        val imdbVotes: String? = movie.imdbVotes
        val type: String = movie.type
        val dvd: String? = movie.dvd
        val boxOffice: String? = movie.boxOffice
        val production: String? = movie.production
        val website: String? = movie.website
        val response: String? = movie.response

        val ratings: MutableList<Rating> = mutableListOf()

        movie.ratings?.forEach {

            val rating = Rating(
                source = it.source,
                value = it.value,
            )

            ratings.add(rating)

        }

        return MovieDataTransferObject.newInstance(
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
            ratings = ratings.ifEmpty { null }
        )
    }

}