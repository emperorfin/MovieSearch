package emperorfin.android.moviesearch.ui.screen.moviedetails.stateholders

import emperorfin.android.moviesearch.ui.model.movie.MovieUiModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 02th July, 2024.
 */


data class MovieDetailsUiState(
    val movie: MovieUiModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val messageSnackBar: Int? = null,
)
