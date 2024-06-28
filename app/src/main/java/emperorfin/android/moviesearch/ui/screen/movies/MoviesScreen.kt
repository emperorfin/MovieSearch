package emperorfin.android.moviesearch.ui.screen.movies

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
//import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.statusBarsPadding
import emperorfin.android.moviesearch.ui.components.AppBar
import emperorfin.android.moviesearch.ui.components.MoviePoster
import emperorfin.android.moviesearch.ui.navigation.NavigationActions
import emperorfin.android.moviesearch.temp.SampleMovies


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
    modifier: Modifier
) {
    val movies = SampleMovies.getMovies()

    LazyVerticalGrid(
//        cells = GridCells.Fixed(2),
        columns = GridCells.Fixed(2),
        state = rememberLazyGridState(),
        modifier = modifier
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        itemsIndexed(movies) { index, movie ->

            MoviePoster(
                movie = movie,
//                selectPoster = selectPoster
            )
        }

    }
}
