package emperorfin.android.moviesearch.domain.datalayer.datasource

import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.domain.uilayer.event.output.ResultData
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface MovieDataSource {

    suspend fun countAllMovies(params: Params): ResultData<Int>

    suspend fun getMovies(params: Params): ResultData<List<MovieModel>>

    suspend fun getMovie(params: Params): ResultData<MovieModel>

    suspend fun saveMovie(movie: MovieModel): ResultData<Long>

    suspend fun deleteMovie(params: Params): ResultData<Int>

}