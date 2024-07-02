package emperorfin.android.moviesearch.domain.datalayer.dao

import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieDetailsResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieSearchResponse
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieEntityParams
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params
import retrofit2.Response
import retrofit2.Call


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface IMovieDao {

    suspend fun countAllMovies(): Int

//    suspend fun getMovies(): List<Params>
//    suspend fun getMovies(): Any
    suspend fun getMovies(): List<MovieEntity>

//    suspend fun getMovies(search: String): List<Params>
//    suspend fun getMovies(search: String): Any
    suspend fun getMovies(search: String): List<MovieEntity>
//    suspend fun getRemoteMovies(search: String): Any
    suspend fun getRemoteMovies(search: String): Response<MovieSearchResponse>

    suspend fun getMovie(imdbId: String): MovieEntity
//    suspend fun getRemoteMovie(imdbId: String): Any
    suspend fun getRemoteMovie(imdbId: String): Response<MovieDetailsResponse>

//    suspend fun insertMovie(movie: Params): Long
    suspend fun insertMovie(movie: MovieEntity): Long

    suspend fun deleteMovie(imdbId: String): Int

}