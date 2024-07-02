package emperorfin.android.moviesearch.ui.screen.movies.stateholders

import emperorfin.android.moviesearch.ui.model.movie.MovieUiModel


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 01th July, 2024.
 */


data class MoviesUiState(
    val movies: List<MovieUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val messageSnackBar: Int? = null,
)
