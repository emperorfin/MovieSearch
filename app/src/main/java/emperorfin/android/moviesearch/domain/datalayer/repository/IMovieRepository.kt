package emperorfin.android.moviesearch.domain.datalayer.repository

import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.domain.uilayer.event.output.ResultData
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface IMovieRepository {

    suspend fun countAllMovies(params: Params, countRemotely: Boolean = false): ResultData<Int>

    suspend fun getMovies(params: Params, forceUpdate: Boolean = false): ResultData<List<MovieModel>>

    suspend fun getMovie(params: Params, forceUpdate: Boolean = false): ResultData<MovieModel>

    suspend fun saveMovie(movie: MovieModel, saveRemotely: Boolean = false): ResultData<Long>

    suspend fun deleteMovie(params: Params, deleteRemotely: Boolean = false): ResultData<Int>

}