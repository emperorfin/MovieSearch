package emperorfin.android.moviesearch.data.datasource.framework.local.room.entitysource

import emperorfin.android.moviesearch.R
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntityMapper
import emperorfin.android.moviesearch.di.IoDispatcher
import emperorfin.android.moviesearch.di.LocalMovieDao
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
import javax.inject.Inject


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class MovieLocalDataSourceRoom @Inject internal constructor(
    @LocalMovieDao private val movieDao: IMovieDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val movieEntityMapper: MovieEntityMapper,
    private val movieModelMapper: MovieModelMapper
) : MovieDataSource {

    private companion object {
        const val NUM_OF_MOVIES_0: Int = 0
        const val NUM_OF_ROWS_DELETED_1: Int = 1

        const val TABLE_ROW_ID_1: Long = 1L
    }

    override suspend fun countAllMovies(params: Params): ResultData<Int> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                return@withContext try {

                    val numOfMovies: Int = movieDao.countAllMovies()

                    if (numOfMovies > NUM_OF_MOVIES_0) {
                        return@withContext ResultData.Success(data = numOfMovies)
                    } else if (numOfMovies == NUM_OF_MOVIES_0) {
                        return@withContext ResultData.Error(failure = MovieFailure.NonExistentMovieDataLocalError())
                    }

                    return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError())

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError(cause = e))
                }
            }
            is MovieParams -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }


    }

    override suspend fun getMovies(params: Params): ResultData<List<MovieModel>> = withContext(ioDispatcher) {

        when(params){
            is None -> {
                return@withContext try {
                    val entityMovies: List<MovieEntity> = movieDao.getMovies() as List<MovieEntity>

                    if (entityMovies == null) // Deliberate check but shouldn't do this
                        return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError())
                    else if (entityMovies.isEmpty())
                        return@withContext ResultData.Error(failure = MovieFailure.MovieListNotAvailableLocalError())

                    val modelMovies = entityMovies.map {
                        movieModelMapper.transform(it)
                    }

                    return@withContext ResultData.Success(modelMovies)

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError(cause = e))
                }
            }
            is MovieParams -> {
                return@withContext try {

                    val entityMovies: List<MovieEntity> = movieDao.getMovies(params.title!!) as List<MovieEntity>

                    if (entityMovies == null) // Deliberate check but shouldn't do this
                        return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError())
                    else if (entityMovies.isEmpty())
                        return@withContext ResultData.Error(failure = MovieFailure.MovieListNotAvailableLocalError())

                    val modelMovies = entityMovies.map {
                        movieModelMapper.transform(it)
                    }

                    return@withContext ResultData.Success(modelMovies)

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError(cause = e))
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

                    val entityMovie: MovieEntity = movieDao.getMovie(params.title!!) as MovieEntity

                    if (entityMovie == null) // Deliberate check but shouldn't do this
                        return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError())

                    val modelMovie = movieModelMapper.transform(entityMovie)

                    return@withContext ResultData.Success(modelMovie)

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.MovieLocalError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }

    override suspend fun saveMovie(movie: MovieModel): ResultData<Long> = withContext(ioDispatcher){

        val entityMovie = movieEntityMapper.transform(movie)

        // TODO: Consider putting this in a try/catch block and then write test cases for it.
        val tableRowId: Long = movieDao.insertMovie(entityMovie)

        if (tableRowId < TABLE_ROW_ID_1)
            return@withContext ResultData.Error(
                MovieFailure.InsertMovieLocalError(message = R.string.error_local_insert_movie)
            )

        return@withContext ResultData.Success(tableRowId)
    }

    override suspend fun deleteMovie(params: Params): ResultData<Int> = withContext(ioDispatcher) {
        when(params){
            is None -> {
                throw IllegalArgumentException(ERROR_MESSAGE_INAPPROPRIATE_ARGUMENT_PASSED)
            }

            is MovieParams -> {
                return@withContext try {

                    val numOfMovieDeleted: Int = movieDao.deleteMovie(params.imdbId!!)

                    if (numOfMovieDeleted < NUM_OF_ROWS_DELETED_1) {
                        return@withContext ResultData.Error(failure = MovieFailure.DeleteMovieLocalError(R.string.error_local_delete_movie))
                    }

                    return@withContext ResultData.Success(numOfMovieDeleted)

                } catch (e: Exception){
                    return@withContext ResultData.Error(failure = MovieFailure.DeleteMovieLocalError(cause = e))
                }
            }
            else -> throw NotImplementedError(ERROR_MESSAGE_NOT_YET_IMPLEMENTED)
        }
    }
}
