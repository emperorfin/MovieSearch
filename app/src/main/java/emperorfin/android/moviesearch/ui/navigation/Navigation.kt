package emperorfin.android.moviesearch.ui.navigation

import androidx.navigation.NavHostController
import emperorfin.android.moviesearch.ui.navigation.Destinations.ROUTE_MOVIES
import emperorfin.android.moviesearch.ui.navigation.Destinations.ROUTE_MOVIE_DETAILS
import emperorfin.android.moviesearch.ui.navigation.Screens.SCREEN_MOVIES
import emperorfin.android.moviesearch.ui.navigation.Screens.SCREEN_MOVIE_DETAILS


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */


/**
 * Screens used in [Destinations]
 */
private object Screens {
    const val SCREEN_MOVIES: String = "movies"
    const val SCREEN_MOVIE_DETAILS: String = "moviedetails"
}

/**
 * Destinations used in the [MainActivity]
 */
object Destinations {
    const val ROUTE_MOVIES: String = SCREEN_MOVIES
    const val ROUTE_MOVIE_DETAILS: String = SCREEN_MOVIE_DETAILS
}

object ScreenArgs {
    const val SCREEN_MOVIE_DETAILS_IMDBID: String = "imdbId"
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {

    fun navigateToMoviesScreen() {
        navController.navigate(ROUTE_MOVIES)
    }

    /**
     * args: "{imdbID}"
     */
    fun navigateToMovieDetailsScreen(args: String) {

        val routeWithArgs = "${ROUTE_MOVIE_DETAILS}/$args"

        navController.navigate(routeWithArgs)
    }

    fun navigateBack() {
//        navController.popBackStack() // Still works
        navController.navigateUp()
    }

}