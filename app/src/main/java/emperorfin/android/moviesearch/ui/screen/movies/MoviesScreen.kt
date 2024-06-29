package emperorfin.android.moviesearch.ui.screen.movies

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
//import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.google.accompanist.insets.statusBarsPadding
import emperorfin.android.moviesearch.ui.components.AppBar
import emperorfin.android.moviesearch.ui.components.MoviePoster
import emperorfin.android.moviesearch.ui.navigation.NavigationActions
import emperorfin.android.moviesearch.temp.SampleMovies
import emperorfin.android.moviesearch.ui.components.EmptyContent
import emperorfin.android.moviesearch.ui.components.LoadingContent
import emperorfin.android.moviesearch.ui.components.LoadingIndicator
import emperorfin.android.moviesearch.ui.components.SearchField


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 27th June, 2024.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navigationActions: NavigationActions?,
//    viewModel: MainViewModel,
//    selectPoster: (MainScreenHomeTab, Long) -> Unit,
//    lazyListState: LazyListState,
) {
//    val networkState: NetworkState by viewModel.movieLoadingState
//    val movies by viewModel.movies

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { AppBar() },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->

        Content(
            modifier = Modifier.padding(paddingValues),
            navigationActions = navigationActions
        )

        // Check for SnackBar messages to display on the screen
//        uiState.messageSnackBar?.let { message ->
//            val snackBarText = stringResource(message)
//            LaunchedEffect(snackbarHostState, viewModel, message, snackBarText) {
//                snackbarHostState.showSnackbar(message = snackBarText)
//                viewModel.snackBarMessageShown()
//            }
//        }

    }



//    networkState.onLoading {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
}

@Composable
private fun Content(
    modifier: Modifier,
    navigationActions: NavigationActions?,
    context: Context = LocalContext.current,
) {
    val movies = SampleMovies.getMovies()

    Column(
        modifier = Modifier
//            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(58.dp))

        SearchField(
            modifier = Modifier
                .fillMaxWidth(),
            searchInput = "",
            onSearchInputChanged = {},
            onSearchClicked = {
                Toast.makeText(
                    context,
                    "Search clicked.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        LoadingContent(
//            loading = loading,
            loading = false,
//            empty = movies.isEmpty() && !loading,
            empty = false,
//            emptyContent = { EmptyContent(label, icon, modifier, onRetry) },
            emptyContent = {
                EmptyContent(
                    onRetry = {}
                )
            },
            loadingIndicator = {
                LoadingIndicator(modifier = modifier)
            }
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                modifier = Modifier
//                    .statusBarsPadding()
//                    .offset(0.dp, (-58).dp)
                    .background(MaterialTheme.colorScheme.background),
//                contentPadding = PaddingValues(
//                    start = 12.dp,
//                    end = 12.dp,
//                    bottom = 16.dp
//                )
            ) {

//                item(
//                    span = {
//                        GridItemSpan(
//                            maxLineSpan
//                        )
//                    }
//                ) {
//                    SearchField(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        searchInput = "",
//                        onSearchInputChanged = {},
//                        onSearchClicked = {
//                            Toast.makeText(
//                                context,
//                                "Search clicked.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    )
//                }

                itemsIndexed(movies) { index, movie ->

                    MoviePoster(
                        movie = movie,
                        onClick = {
//                            navigationActions?.navigateToMovieDetailsScreen(movie.imdbID) // Works
                            navigationActions?.navigateToMovieDetailsScreen(it)

//                            Toast.makeText(
//                                context,
//                                it, // Or movie.imdbId
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }
                    )
                }

            }

        }

    }
}
