package emperorfin.android.moviesearch.data.repository

import emperorfin.android.moviesearch.R
import emperorfin.android.moviesearch.di.IoDispatcher
import emperorfin.android.moviesearch.di.MovieLocalDataSource
import emperorfin.android.moviesearch.di.MovieRemoteDataSource
import emperorfin.android.moviesearch.domain.datalayer.datasource.MovieDataSource
import emperorfin.android.moviesearch.domain.datalayer.repository.IMovieRepository
import emperorfin.android.moviesearch.domain.exception.MovieFailure
import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.MovieParams
import emperorfin.android.moviesearch.domain.uilayer.event.output.ResultData
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


data class MovieRepository @Inject constructor(
    @MovieLocalDataSource private val movieLocalDataSource: MovieDataSource,
    @MovieRemoteDataSource private val movieRemoteDataSource: MovieDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IMovieRepository {

    private var cachedMovies: ConcurrentMap<String, MovieModel>? = null

    override suspend fun countAllMovies(params: Params, countRemotely: Boolean): ResultData<Int> = withContext(ioDispatcher) {
        if (countRemotely) {
            return@withContext movieRemoteDataSource.countAllMovies(params = params)
        } else {
            return@withContext movieLocalDataSource.countAllMovies(params = params)
        }
    }

    override suspend fun getMovies(params: Params, forceUpdate: Boolean): ResultData<List<MovieModel>> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedMovies?.let { movies ->
                    return@withContext ResultData.Success(movies.values.sortedBy { it.title })
                }
            }

            val newMovies: ResultData<List<MovieModel>> =
                fetchMoviesFromRemoteOrLocal(params = params, forceUpdate = forceUpdate)

            // Refresh the cache with the new productsOverviews
            (newMovies as? ResultData.Success)?.let { refreshCache(it.data) }

            cachedMovies?.values?.let { movies ->
                return@withContext ResultData.Success(movies.sortedBy { it.title })
            }

            (newMovies as? ResultData.Success)?.let {
                if (it.data.isNotEmpty()) { // it.data.isEmpty()
                    return@withContext ResultData.Success(it.data)
                }
            }

            return@withContext newMovies as ResultData.Error
        }
    }

    override suspend fun getMovie(params: Params, forceUpdate: Boolean): ResultData<MovieModel> {
        return withContext(ioDispatcher) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {

                if (params is MovieParams) {
                    if (cachedMovies?.containsKey(params.imdbRating) == true) {

                        val movieCached = cachedMovies?.get(params.imdbId)

                        if (movieCached?.rated != null) {
                            return@withContext ResultData.Success(movieCached)
                        }
                    }
                }
            }

            val newMovie: ResultData<MovieModel> =
                fetchMovieFromRemoteOrLocal(params = params, forceUpdate = forceUpdate)

            // Refresh the cache with the new productsOverviews
            (newMovie as? ResultData.Success)?.let { refreshCache(it.data) }

            if (params is MovieParams) {
                if (cachedMovies?.containsKey(params.imdbRating) == true) {

                    val movieCached = cachedMovies?.get(params.imdbId)

                    if (movieCached?.rated != null) {
                        return@withContext ResultData.Success(movieCached)
                    }
                }
            }

            (newMovie as? ResultData.Success)?.let {
                if (it.data.rated != null) {
                    return@withContext ResultData.Success(it.data)
                }
            }

            return@withContext newMovie as ResultData.Error
        }
    }

    override suspend fun saveMovie(movie: MovieModel, saveRemotely: Boolean): ResultData<Long> = withContext(ioDispatcher) {

        if (saveRemotely) {
            return@withContext movieRemoteDataSource.saveMovie(movie = movie)
        } else {
            return@withContext movieLocalDataSource.saveMovie(movie = movie)
        }

    }

    override suspend fun deleteMovie(params: Params, deleteRemotely: Boolean): ResultData<Int> = withContext(ioDispatcher) {

        if (deleteRemotely) {
            return@withContext movieRemoteDataSource.deleteMovie(params = params)
        } else {
            return@withContext movieLocalDataSource.deleteMovie(params = params)
        }
    }

    private suspend fun fetchMoviesFromRemoteOrLocal(params: Params, forceUpdate: Boolean): ResultData<List<MovieModel>> {
        var isRemoteException = false

        // Remote first
        if (forceUpdate) {
            when (val moviesRemote = movieRemoteDataSource.getMovies(params = params)) {
//             is Error -> return remoteMovies // Timber.w("Remote data source fetch failed")
                is ResultData.Error -> {
                    if (moviesRemote.failure is MovieFailure.MovieRemoteError)
                        isRemoteException = true
                }
                is ResultData.Success -> {
                    refreshLocalDataSource(movies = moviesRemote.data)

                    return moviesRemote
                }
//             else -> throw IllegalStateException()
                else -> {}
            }
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            if (isRemoteException)
                return ResultData.Error(
                    MovieFailure.GetMovieRepositoryError(
                        message = R.string.exception_occurred_remote
                    )
                )

            return ResultData.Error(
                // TODO: Change GetMovieRemoteError to GetMovieRepositoryError and update
                //  test cases too.
                MovieFailure.GetMovieRemoteError(
                    message = R.string.error_cant_force_refresh_movies_remote_data_source_unavailable
                )
            )
        }

        // Local if remote fails
        val moviesLocal = movieLocalDataSource.getMovies(params = params)

        if (moviesLocal is ResultData.Success) return moviesLocal

        if ((moviesLocal as ResultData.Error).failure is MovieFailure.MovieLocalError)
            return ResultData.Error(
                MovieFailure.GetMovieRepositoryError(
                    R.string.exception_occurred_local
                )
            )

//        return Error((moviesLocal as Error).failure)
        return ResultData.Error(
            MovieFailure.GetMovieRepositoryError(
                R.string.error_fetching_from_remote_and_local
            )
        )
    }

    private suspend fun fetchMovieFromRemoteOrLocal(params: Params, forceUpdate: Boolean): ResultData<MovieModel> {
        var isRemoteException = false

        // Remote first
        if (forceUpdate) {
            when (val movieRemote = movieRemoteDataSource.getMovie(params = params)) {
//             is Error -> return remoteMovie // Timber.w("Remote data source fetch failed")
                is ResultData.Error -> {
                    if (movieRemote.failure is MovieFailure.MovieRemoteError)
                        isRemoteException = true
                }
                is ResultData.Success -> {
                    refreshLocalDataSource(movie = movieRemote.data)

                    return movieRemote
                }
//             else -> throw IllegalStateException()
                else -> {}
            }
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            if (isRemoteException)
                return ResultData.Error(
                    MovieFailure.GetMovieRepositoryError(
                        message = R.string.exception_occurred_remote
                    )
                )

            return ResultData.Error(
                // TODO: Change GetMovieRemoteError to GetMovieRepositoryError and update
                //  test cases too.
                MovieFailure.GetMovieRemoteError(
                    message = R.string.error_cant_force_refresh_movies_remote_data_source_unavailable
                )
            )
        }

        // Local if remote fails
        val movieLocal = movieLocalDataSource.getMovie(params = params)

        if (movieLocal is ResultData.Success) return movieLocal

        if ((movieLocal as ResultData.Error).failure is MovieFailure.MovieLocalError)
            return ResultData.Error(
                MovieFailure.GetMovieRepositoryError(
                    R.string.exception_occurred_local
                )
            )

//        return Error((movieLocal as Error).failure)
        return ResultData.Error(
            MovieFailure.GetMovieRepositoryError(
                R.string.error_fetching_from_remote_and_local
            )
        )
    }

    private fun refreshCache(movies: List<MovieModel>) {
        cachedMovies?.clear()

        movies.sortedBy { it.title }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun refreshCache(movie: MovieModel) {
        cachedMovies?.remove(movie.imdbId)

        cacheAndPerform(movie) {}
    }

    private suspend fun refreshLocalDataSource(movies: List<MovieModel>) {

        movies.forEach {
            val params = MovieParams(title = it.title)

            movieLocalDataSource.deleteMovie(params = params)

            movieLocalDataSource.saveMovie(movie = it)
        }
    }

    private suspend fun refreshLocalDataSource(movie: MovieModel) {

        val params = MovieParams(imdbId = movie.imdbId)

        movieLocalDataSource.deleteMovie(params = params)

        movieLocalDataSource.saveMovie(movie = movie)
    }

    private fun cacheMovie(movie: MovieModel): MovieModel {

        val cachedMovie = MovieModel.newInstance(
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
            ratings = movie.ratings
        )

        // Create if it doesn't exist.
        if (cachedMovies == null) {
            cachedMovies = ConcurrentHashMap()
        }

//        cachedMovies?.put(cachedMovie.title, cachedMovie)
        cachedMovies?.put(cachedMovie.imdbId, cachedMovie)

        return cachedMovie
    }

    private inline fun cacheAndPerform(movie: MovieModel, perform: (MovieModel) -> Unit) {

        val cachedMovie = cacheMovie(movie)

        perform(cachedMovie)
    }
}
