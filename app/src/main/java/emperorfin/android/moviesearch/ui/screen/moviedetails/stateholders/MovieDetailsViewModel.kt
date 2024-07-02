package emperorfin.android.moviesearch.ui.screen.moviedetails.stateholders

import android.app.Application
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
import emperorfin.android.moviesearch.ui.screen.movies.stateholders.MoviesViewModel
import emperorfin.android.moviesearch.ui.utils.InternetConnectivityUtil
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
class MovieDetailsViewModel @Inject constructor(
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

    private val _movie: MutableStateFlow<ResultData<MovieUiModel>> =  MutableStateFlow(ResultData.Loading)
    val movie: StateFlow<ResultData<MovieUiModel>> = _movie

    val uiState: StateFlow<MovieDetailsUiState> = combine(
        isLoading, errorMessage, movie, messageSnackBar
    ) { isLoading, errorMessage, movie, messageSnackBar ->

        when (movie) {

            ResultData.Loading -> {
                MovieDetailsUiState(isLoading = true)
            }
            is ResultData.Error -> {
                MovieDetailsUiState(
                    errorMessage = (movie.failure as MovieFailure).message,
                    messageSnackBar = messageSnackBar
                )
            }
            is ResultData.Success<MovieUiModel> -> {

                val _movie: MovieUiModel = movie.data

                MovieDetailsUiState(
                    movie = _movie,
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
            initialValue = MovieDetailsUiState(isLoading = true)
        )

    fun snackBarMessageShown() {
        _messageSnackBar.value = null
    }

    private fun getMovieViaRepository(
        params: Params,
        isRefresh: Boolean = false,
    ) = viewModelScope.launch(context = coroutineDispatcherIo) {

        _movie.value = ResultData.Loading

        val movieResultData: ResultData<MovieModel> =
            movieRepository.getMovie(params = params, forceUpdate = isRefresh)

        if (movieResultData.succeeded) {
            val movieEntity = (movieResultData as ResultData.Success).data

            val movieModel: MovieModel = movieModelMapper.transform(movieEntity)

            val movieUiModel: MovieUiModel = movieUiModelMapper.transform(movieModel)

            _movie.value = ResultData.Success(data = movieUiModel)

        } else {
            val error: ResultData.Error = (movieResultData as ResultData.Error)
            _movie.value = error
        }

    }

    fun loadMovie(params: MovieParams, isRefresh: Boolean){
        viewModelScope.launch/*(context = coroutineDispatcherIo)*/ {

            var moviesCount by Delegates.notNull<Int>()

            val moviesCountDataResultEvent = movieRepository.countAllMovies(params = None())

            moviesCount = if (moviesCountDataResultEvent.succeeded)
                (moviesCountDataResultEvent as ResultData.Success).data
            else
                NUM_OF_MOVIES_MINUS_1

            if (moviesCount > NUM_OF_MOVIES_0 || isRefresh){

                if (hasInternetConnection(application)){

                    getMovieViaRepository(
                        params = params,
                        isRefresh = true,
                    )
                } else {

                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            application,
                            R.string.message_no_internet_searching_cached_movies,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    getMovieViaRepository(
                        params = params,
                        isRefresh = false
                    )
                }
            } else {

                if (hasInternetConnection(application)){
                    getMovieViaRepository(
                        params = params,
                        isRefresh = true
                    )
                } else {

                    _messageSnackBar.value = R.string.message_no_internet_connectivity

                    _movie.value = ResultData.Error(
                        failure = MovieFailure.MovieRemoteError(
                            message = R.string.message_no_internet_connectivity
                        )
                    )
                }
            }

        }
    }

}