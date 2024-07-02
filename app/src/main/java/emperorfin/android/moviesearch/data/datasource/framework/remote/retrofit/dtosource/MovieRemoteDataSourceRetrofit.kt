package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dtosource

import android.util.Log
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dto.movie.MovieDataTransferObject
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dto.movie.MovieDataTransferObjectMapper
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dto.movie.embedded.Rating
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieDetailsResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieSearchResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.jsonobject.Movie
import emperorfin.android.moviesearch.di.IoDispatcher
import emperorfin.android.moviesearch.di.MainDispatcher
import emperorfin.android.moviesearch.di.RemoteMovieDao
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import emperorfin.android.moviesearch.domain.datalayer.datasource.MovieDataSource
import emperorfin.android.moviesearch.domain.exception.MovieFailure
import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.domain.model.movie.MovieModelMapper
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.MovieParams
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.None
import emperorfin.android.moviesearch.domain.uilayer.event.output.ResultData
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class MovieRemoteDataSourceRetrofit @Inject internal constructor(
    @RemoteMovieDao private val movieDao: IMovieDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val movieDtoMapper: MovieDataTransferObjectMapper,
    private val movieModelMapper: MovieModelMapper
) : MovieDataSource {
    override suspend fun countAllMovies(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovies(params: Params): ResultData<List<MovieModel>> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is MovieParams -> {

                return@withContext try {

                    val response = movieDao.getRemoteMovies(
                        search = params.title!!,
                    ) as Response<MovieSearchResponse>

                    withContext(mainDispatcher){
                        if (response.isSuccessful){

                            response.body()?.let {

                                val moviesModel: List<MovieModel> =
                                    buildMovieModelList(movies = it.search)

                                // try block doesn't seem to return without return@withContext
                                return@withContext ResultData.Success(moviesModel)
                            }
                        }

//                        println("HTTP status code: ${response.code()}")

                        return@withContext ResultData.Error(failure = MovieFailure.GetMovieRemoteError())
                    }

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieRemoteError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun getMovie(params: Params): ResultData<MovieModel> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            is MovieParams -> {

                return@withContext try {

                    val response = movieDao.getRemoteMovie(
                        imdbId = params.imdbId!!,
                    ) as Response<MovieDetailsResponse>

                    withContext(mainDispatcher){
                        if (response.isSuccessful){

                            response.body()?.let {

                                val movieModel: MovieModel = buildMovieModel(movie = it)

                                // try block doesn't seem to return without return@withContext
                                return@withContext ResultData.Success(movieModel)
                            }
                        }

//                        println("HTTP status code: ${response.code()}")

                        return@withContext ResultData.Error(failure = MovieFailure.GetMovieRemoteError())
                    }

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieRemoteError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun saveMovie(movie: MovieModel): ResultData<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(params: Params): ResultData<Int> {
        TODO("Not yet implemented")
    }

    private fun buildMovieModelList(movies: List<Movie>): List<MovieModel> {
        val moviesDto = mutableListOf<MovieDataTransferObject>()

        movies.forEach {

            val movieDto = MovieDataTransferObject.newInstance(
                imdbId = it.imdbId,
                title = it.title,
                year = it.year,
                poster = it.poster,
                type = it.type
            )

            moviesDto.add(movieDto)
        }

        return moviesDto.map {
            movieModelMapper.transform(it)
        }
    }

    private fun buildMovieModel(movie: MovieDetailsResponse): MovieModel {

        val ratings = mutableListOf<Rating>()

        movie.ratings.forEach {
            val rating = Rating(
                source = it.source,
                value = it.value
            )

            ratings.add(rating)
        }

        val movieDto = MovieDataTransferObject.newInstance(
            imdbId = movie.imdbId,
            title = movie.title,
            year = movie.year,
            rated = movie.rated,
            released = movie.released,
            runtime = movie.runtime,
            genre = movie.genre,
            director = movie.director,
            writer = movie.writer,
            actors = movie.actors,
            plot = movie.plot,
            language = movie.language,
            country = movie.country,
            awards = movie.awards,
            poster = movie.poster,
            metascore = movie.metascore,
            imdbRating = movie.imdbRating,
            imdbVotes = movie.imdbVotes,
            type = movie.type,
            dvd = movie.dvd,
            boxOffice = movie.boxOffice,
            production = movie.production,
            website = movie.website,
            response = movie.response,
            ratings = ratings
        )

        return movieModelMapper.transform(movieDto)
    }
}
