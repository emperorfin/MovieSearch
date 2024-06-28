package emperorfin.android.moviesearch.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import emperorfin.android.moviesearch.ui.navigation.Destinations.ROUTE_MOVIES
import emperorfin.android.moviesearch.ui.navigation.Destinations.ROUTE_MOVIE_DETAILS
import emperorfin.android.moviesearch.ui.navigation.ScreenArgs.SCREEN_MOVIE_DETAILS_IMDBID
import emperorfin.android.moviesearch.ui.screen.moviedetails.MovieDetailsScreen
import emperorfin.android.moviesearch.ui.screen.movies.MoviesScreen


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */


@Composable
fun NavGraph(
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_MOVIES,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {

    val movieDetailsRouteWithArgs = "${ROUTE_MOVIE_DETAILS}/{$SCREEN_MOVIE_DETAILS_IMDBID}"

    ProvideWindowInsets {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination,
        ) {

            composable(ROUTE_MOVIES) {
                MoviesScreen(navigationActions = navActions)
            }

            composable(
                movieDetailsRouteWithArgs,
                arguments = listOf(                                         // declaring argument type
                    navArgument(SCREEN_MOVIE_DETAILS_IMDBID) { type = NavType.StringType },
                )
            ) { backStackEntry ->

                val imdbId: String = backStackEntry.arguments?.getString(SCREEN_MOVIE_DETAILS_IMDBID)!!

                MovieDetailsScreen(
                    navigationActions = navActions,
                    imdbId = imdbId,
                )
            }
        }
    }

}