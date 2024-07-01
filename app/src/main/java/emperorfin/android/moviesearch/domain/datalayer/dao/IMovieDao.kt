package emperorfin.android.moviesearch.domain.datalayer.dao

import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieEntityParams
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface IMovieDao {

    suspend fun countAllMovies(): Int

//    suspend fun getMovies(): List<Params>
    suspend fun getMovies(): Any

//    suspend fun getMovies(search: String): List<Params>
    suspend fun getMovies(search: String): Any

    suspend fun getMovie(omdbId: String): Any

    suspend fun insertMovie(movie: MovieEntityParams): Long

    suspend fun deleteMovie(omdbId: String): Int

}