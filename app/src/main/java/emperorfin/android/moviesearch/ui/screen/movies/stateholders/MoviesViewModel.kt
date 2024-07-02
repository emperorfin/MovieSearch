package emperorfin.android.moviesearch.ui.screen.movies.stateholders

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import emperorfin.android.moviesearch.R
import emperorfin.android.moviesearch.di.DefaultDispatcher
import emperorfin.android.moviesearch.di.IoDispatcher
import emperorfin.android.moviesearch.domain.datalayer.repository.IMovieRepository
import emperorfin.android.moviesearch.domain.exception.MovieFailure
import emperorfin.android.moviesearch.domain.model.movie.MovieModel
import emperorfin.android.moviesearch.domain.model.movie.MovieModelMapper
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.MovieParams
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.None
import emperorfin.android.moviesearch.domain.uilayer.event.output.ResultData
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params
import emperorfin.android.moviesearch.domain.uilayer.event.output.succeeded
import emperorfin.android.moviesearch.ui.model.movie.MovieUiModel
import emperorfin.android.moviesearch.ui.model.movie.MovieUiModelMapper
import emperorfin.android.moviesearch.ui.utils.InternetConnectivityUtil.hasInternetConnection
import emperorfin.android.moviesearch.ui.utils.WhileUiSubscribed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */


@HiltViewModel
class MoviesViewModel @Inject constructor(
//    @ApplicationContext application: Application,
//    application: Application,
    val application: Application,
    private val movieRepository: IMovieRepository,
    private val movieModelMapper: MovieModelMapper,
    private val movieUiModelMapper: MovieUiModelMapper,
    @IoDispatcher private val coroutineDispatcherIo: CoroutineDispatcher,
    @DefaultDispatcher private val coroutineDispatcherDefault: CoroutineDispatcher,
) : ViewModel() {

    companion object {
        private const val NUM_OF_MOVIES_MINUS_1: Int = -1
        private const val NUM_OF_MOVIES_0: Int = 0
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    val errorMessage: StateFlow<Int?> = _errorMessage

    private val _messageSnackBar: MutableStateFlow<Int?> = MutableStateFlow(null)
    val messageSnackBar: StateFlow<Int?> = _messageSnackBar

    private val _movies: MutableStateFlow<ResultData<List<MovieUiModel>>> =  MutableStateFlow(ResultData.Loading)
    val movies: StateFlow<ResultData<List<MovieUiModel>>> = _movies

    init {

        loadMovies(
//            params = MovieParams(title = "Lucas"),
            params = MovieParams(title = ""),
            isRefresh = false
//            isRefresh = true
        )
    }

    val uiState: StateFlow<MoviesUiState> = combine(
        isLoading, errorMessage, movies, messageSnackBar
    ) { isLoading, errorMessage, movies, messageSnackBar ->

        when (movies) {

            ResultData.Loading -> {
                MoviesUiState(isLoading = true)
            }
            is ResultData.Error -> {
                MoviesUiState(
//                    errorMessage = (movies.failure as MovieFailure.GetMovieRepositoryError).message,
//                    errorMessage = (movies.failure as MovieFailure.GetMovieRemoteError).message,
                    errorMessage = (movies.failure as MovieFailure).message,
                    messageSnackBar = messageSnackBar
                )
            }
            is ResultData.Success<List<MovieUiModel>> -> {

                val _movies: List<MovieUiModel> = movies.data

                val moviesSorted = _movies.sortedBy {
                    it.title
                }

                MoviesUiState(
                    movies = moviesSorted,
                    isLoading = isLoading,
//                    errorMessage = errorMessage,
                    errorMessage = null,
                    messageSnackBar = messageSnackBar
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = MoviesUiState(isLoading = true)
        )

    fun snackBarMessageShown() {
        _messageSnackBar.value = null
    }

    private fun getMoviesViaRepository(
        params: Params,
        isRefresh: Boolean = false,
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        _movies.value = ResultData.Loading

        val moviesResultData: ResultData<List<MovieModel>> =
            movieRepository.getMovies(params = params, forceUpdate = isRefresh)

        if (moviesResultData.succeeded) {
            val moviesEntity = (moviesResultData as ResultData.Success).data

            val moviesUiModel: List<MovieUiModel> = moviesEntity.map {
                movieModelMapper.transform(it)
            }.map {
                movieUiModelMapper.transform(it)
            }

            _movies.value = ResultData.Success(data = moviesUiModel)

        } else {
            val error: ResultData.Error = (moviesResultData as ResultData.Error)
            _movies.value = error

        }

    }

    private fun searchMovies(params: MovieParams, isRefresh: Boolean = true) {
        getMoviesViaRepository(params = params, isRefresh = isRefresh)
    }

    private fun getAllMovies(params: None = None(), isRefresh: Boolean = false) {
        getMoviesViaRepository(params = params, isRefresh = isRefresh)
    }

    /**
     * To get all movies, pass None() as params.
     * To search for movies, pass MovieParams() as params.
     */
    fun loadMovies(params: Params, isRefresh: Boolean){
        viewModelScope.launch/*(context = coroutineDispatcherIo)*/ {

            var moviesCount by Delegates.notNull<Int>()

//            val moviesCountDataResultEvent = movieRepository.countAllMovies(params = params)
            val moviesCountDataResultEvent = movieRepository.countAllMovies(params = None())

            moviesCount = if (moviesCountDataResultEvent.succeeded)
                (moviesCountDataResultEvent as ResultData.Success).data
            else
                NUM_OF_MOVIES_MINUS_1

            if (moviesCount > NUM_OF_MOVIES_0 || isRefresh){

                if (hasInternetConnection(application)){

                    searchMovies(
                        params = params as MovieParams,
                        isRefresh = true,
                    )
                } else {

                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            application,
                            R.string.message_no_internet_loading_cached_movies,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    getAllMovies(
//                        params = params as None,
                        params = None(),
                        isRefresh = false
                    )
                }
            } else {

                if (hasInternetConnection(application)){
                    searchMovies(
                        params = params as MovieParams,
                        isRefresh = true
                    )
                } else {

                    _messageSnackBar.value = R.string.message_no_internet_connectivity

                    _movies.value = ResultData.Error(
                        failure = MovieFailure.MovieRemoteError(
                            message = R.string.message_no_internet_connectivity
                        )
                    )
                }
            }

        }
    }

}