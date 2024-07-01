package emperorfin.android.moviesearch.data.util

import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.embedded.Rating


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 01th July, 2024.
 */


object SampleData {

    fun getEntityMovies(): MutableList<MovieEntity> {

        val ratings = listOf(
            Rating(source = "Internet Movie Database", value = "7.6/10"),
            Rating(source = "Rotten Tomatoes", value = "85%"),
            Rating(source = "Metacritic", value = "67/100"),
        )

        val movies = mutableListOf<MovieEntity>()

        var movie = MovieEntity(
            title = "Guardians of the Galaxy Vol. 2",
            year = "2017",
            rated = "PG-13",
            released = "05 May 2017",
            runtime = "136 min",
            genre = "Action, Adventure, Comedy",
            director = "James Gunn",
            writer = "James Gunn, Dan Abnett, Andy Lanning",
            actors = "Chris Pratt, Zoe Saldana, Dave Bautista",
            plot = "The Guardians struggle to keep together as a team while dealing with their personal family issues, notably Star-Lord's encounter with his father, the ambitious celestial being Ego.",
            language = "English",
            country = "United States",
            awards = "Nominated for 1 Oscar. 15 wins & 60 nominations total",
            poster = "https://m.media-amazon.com/images/M/MV5BNjM0NTc0NzItM2FlYS00YzEwLWE0YmUtNTA2ZWIzODc2OTgxXkEyXkFqcGdeQXVyNTgwNzIyNzg@._V1_SX300.jpg",
            metascore = "67",
            imdbRating = "7.6",
            imdbVotes = "762,391",
            imdbId = "tt3896198",
            type = "movie",
            dvd = "N/A",
            boxOffice = "$389,813,101",
            production = "N/A",
            website = "N/A",
            response = "True",
            ratings = ratings
        )

        movies.add(movie)

        movie = movie.copy(
            title = "Luca",
            poster = "https://m.media-amazon.com/images/M/MV5BZTQyNTU0MDktYTFkYi00ZjNhLWE2ODctMzBkM2U1ZTk3YTMzXkEyXkFqcGdeQXVyNTI4MzE4MDU@._V1_SX300.jpg",
            imdbId = "tt12801262"
        )

        movies.add(movie)

        movie = movie.copy(
            title = "Louis & Luca and the Snow Machine",
            poster = "https://m.media-amazon.com/images/M/MV5BMjIxOTAwMjQwOF5BMl5BanBnXkFtZTgwMTQ5MDkzMzE@._V1_SX300.jpg",
            imdbId = "tt2769896"
        )

        movies.add(movie)

        movie = movie.copy(
            title = "Louis & Luca - The Big Cheese Race",
            poster = "https://m.media-amazon.com/images/M/MV5BNjgwYjNlMWEtOTUwMS00ZWQyLWE2ZDAtNWZjZTgzNThkNzliXkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_SX300.jpg",
            imdbId = "tt4970552"
        )

        movies.add(movie)

        movie = movie.copy(
            title = "Justice League: Throne of Atlantis",
            poster = "https://m.media-amazon.com/images/M/MV5BMGYwYTBjYTEtODE1NS00OTA2LTk5ZTctM2I0ZWViMTc4NDljXkEyXkFqcGdeQXVyNTgyNTA4MjM@._V1_SX300.jpg",
            imdbId = "tt3878542"
        )

        movies.add(movie)

        movie = movie.copy(
            title = "The Throne",
            poster = "https://m.media-amazon.com/images/M/MV5BMjI1NjY5MDEzOV5BMl5BanBnXkFtZTgwODg2NTEwNzE@._V1_SX300.jpg",
            imdbId = "tt4010918"
        )

        movies.add(movie)

        movie = movie.copy(
            title = "Warcraft III: The Frozen Throne",
            poster = "https://m.media-amazon.com/images/M/MV5BNTMwOGQzYjMtYTQ5MS00NjNmLTk4OTktMDljZDYzNjgwMjUzXkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg",
            imdbId = "tt0372023"
        )

        movies.add(movie)

        return movies

    }

}