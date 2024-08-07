package emperorfin.android.moviesearch.ui.screen.moviedetails

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.palette.BitmapPalette
import com.skydoves.whatif.whatIfNotNullOrEmpty
import emperorfin.android.moviesearch.R
import emperorfin.android.moviesearch.domain.uilayer.event.input.movie.MovieParams
import emperorfin.android.moviesearch.temp.Movie
//import emperorfin.android.moviesearch.temp.Rating
import emperorfin.android.moviesearch.temp.SampleMovies
import emperorfin.android.moviesearch.ui.components.AppBarWithArrow
import emperorfin.android.moviesearch.ui.components.EmptyContent
import emperorfin.android.moviesearch.ui.components.LoadingContent
import emperorfin.android.moviesearch.ui.components.LoadingIndicator
import emperorfin.android.moviesearch.ui.components.NetworkImage
import emperorfin.android.moviesearch.ui.components.RatingBar
import emperorfin.android.moviesearch.ui.model.movie.MovieUiModel
import emperorfin.android.moviesearch.ui.model.movie.embedded.Rating
import emperorfin.android.moviesearch.ui.navigation.NavigationActions
import emperorfin.android.moviesearch.ui.screen.moviedetails.stateholders.MovieDetailsUiState
import emperorfin.android.moviesearch.ui.screen.moviedetails.stateholders.MovieDetailsViewModel
import emperorfin.android.moviesearch.ui.screen.movies.stateholders.MoviesViewModel
import emperorfin.android.moviesearch.ui.theme.Purple40
import emperorfin.android.moviesearch.ui.theme.background


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */



@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    navigationActions: NavigationActions?,
    imdbId: String,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

//    val movie: Movie = SampleMovies.getMovies()[1]

    val uiState by viewModel.uiState.collectAsState()

    val movie = uiState.movie

    LaunchedEffect(key1 = imdbId) {
        viewModel.loadMovie(
            params = MovieParams(imdbId = imdbId),
            isRefresh = false
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppBarWithArrow(
                movie?.title,
                onBackPress = {
                    navigationActions?.navigateBack()
                }
            )
         },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->

        Content(
            modifier = Modifier.padding(paddingValues),
            imdbId = imdbId,
            movie = movie,
            viewModel = viewModel,
            uiState = uiState
        )

        // Check for SnackBar messages to display on the screen
        uiState.messageSnackBar?.let { message ->
            val snackBarText = stringResource(message)
            LaunchedEffect(snackbarHostState, viewModel, message, snackBarText) {
                snackbarHostState.showSnackbar(message = snackBarText)
                viewModel.snackBarMessageShown()
            }
        }

    }
}

@Composable
private fun Content(
    modifier: Modifier,
    imdbId: String,
    movie: MovieUiModel?,
    viewModel: MovieDetailsViewModel,
    uiState: MovieDetailsUiState
    ) {

    val isLoading = uiState.isLoading
    val errorMessage = uiState.errorMessage

    LoadingContent(
        loading = isLoading,
        empty = movie == null && !isLoading,
        emptyContent = {
            EmptyContent(
                errorLabel = errorMessage ?: R.string.content_description_error_message,
                onRetry = {
                    viewModel.loadMovie(
                        params = MovieParams(imdbId = imdbId),
                        isRefresh = false
                    )
                }
            )
        },
        loadingIndicator = {
            LoadingIndicator(modifier = modifier)
        }
    ) {


        movie?.let {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(background)
                    .fillMaxSize(),
            ) {

                Spacer(modifier = Modifier.height(58.dp))

                MovieDetailsHeader(it)

                MovieDetailsSummary(it)

                MovieDetailsRatings(it.ratings)

                Spacer(modifier = Modifier.height(24.dp))
            }

        }

    }
}

@Composable
private fun MovieDetailsHeader(
//    movie: Movie
    movie: MovieUiModel
) {

    Column {

        var palette by remember { mutableStateOf<Palette?>(null) }
        NetworkImage(
            networkUrl = movie.poster,
            circularReveal = CircularReveal(duration = 300),
            shimmerParams = null,
            bitmapPalette = BitmapPalette {
                palette = it
            },
            modifier = Modifier
                .height(280.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = movie.title,
//            style = MaterialTheme.typography.h5,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Release Date: ${movie.released}",
//            style = MaterialTheme.typography.body1,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        RatingBar(
//            rating = (movie.imdbRating ?: 0f) / 2f,
            rating = movie.imdbRating.toFloat() / 2f,
            color = Color(palette?.vibrantSwatch?.rgb ?: 0),
            modifier = Modifier
                .height(15.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun MovieDetailsSummary(
//    movie: Movie
    movie: MovieUiModel
) {

    val genre: List<String> = movie.genre.split(',')

    Column {

        Spacer(modifier = Modifier.height(23.dp))

        Text(
            text = stringResource(R.string.summary),
//            style = MaterialTheme.typography.h6,
//            style = MaterialTheme.typography.headlineLarge,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.plot,
//            style = MaterialTheme.typography.body1,
//            style = MaterialTheme.typography.bodyLarge,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        FlowRow {

            genre.forEach {

                Keyword(it.trim())
            }
        }
    }
}

@Composable
private fun Keyword(keyword: String) {
    Surface(
        shape = RoundedCornerShape(32.dp),
//        elevation = 8.dp,
        shadowElevation = 8.dp,
        color = Purple40,
        modifier = Modifier.padding(8.dp)
    ) {

        Text(
            text = keyword,
//            style = MaterialTheme.typography.body1,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun MovieDetailsRatings(
//    ratings: List<Rating>
    ratings: List<Rating>
) {

    ratings.whatIfNotNullOrEmpty {

        Column {

            Spacer(modifier = Modifier.height(23.dp))

            Text(
                text = stringResource(R.string.ratings),
//                style = MaterialTheme.typography.h6,
//                style = MaterialTheme.typography.headlineLarge,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Column {
                ratings.forEach {
                    Rating(it)
                }
            }
        }
    }
}

@Composable
private fun Rating(
    rating: Rating
) {

    Column {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = rating.source,
//            style = MaterialTheme.typography.body1,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = rating.value,
//            style = MaterialTheme.typography.body2,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )
    }
}

