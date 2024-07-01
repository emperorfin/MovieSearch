package emperorfin.android.moviesearch.domain.exception

import emperorfin.android.moviesearch.domain.exception.Failure.FeatureFailure
import androidx.annotation.StringRes
import emperorfin.android.moviesearch.R


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


sealed class MovieFailure(
    @StringRes open val message: Int,
    open val cause: Throwable?
) : FeatureFailure() {

    class MovieListNotAvailableMemoryError(
        @StringRes override val message: Int = R.string.error_memory_movie_list_not_available,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class MovieListNotAvailableLocalError(
        @StringRes override val message: Int = R.string.error_local_movie_list_not_available,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class MovieListNotAvailableRemoteError(
        @StringRes override val message: Int = R.string.error_remote_movie_list_not_available,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class MovieMemoryError(
        @StringRes override val message: Int = R.string.error_memory_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class MovieLocalError(
        @StringRes override val message: Int = R.string.error_local_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class MovieRemoteError(
        @StringRes override val message: Int = R.string.error_remote_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class GetMovieMemoryError(
        @StringRes override val message: Int = R.string.error_memory_get_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class GetMovieLocalError(
        @StringRes override val message: Int = R.string.error_local_get_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class GetMovieRemoteError(
        @StringRes override val message: Int = R.string.error_remote_get_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class InsertMovieMemoryError(
        @StringRes override val message: Int = R.string.error_memory_insert_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class InsertMovieLocalError(
        @StringRes override val message: Int = R.string.error_local_insert_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class InsertMovieRemoteError(
        @StringRes override val message: Int = R.string.error_remote_insert_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class UpdateMovieMemoryError(
        @StringRes override val message: Int = R.string.error_memory_update_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class UpdateMovieLocalError(
        @StringRes override val message: Int = R.string.error_local_update_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class UpdateMovieRemoteError(
        @StringRes override val message: Int = R.string.error_remote_update_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class DeleteMovieMemoryError(
        @StringRes override val message: Int = R.string.error_memory_delete_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class DeleteMovieLocalError(
        @StringRes override val message: Int = R.string.error_local_delete_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class DeleteMovieRemoteError(
        @StringRes override val message: Int = R.string.error_remote_delete_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class NonExistentMovieDataMemoryError(
        @StringRes override val message: Int = R.string.error_memory_non_existent_movie_data,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class NonExistentMovieDataLocalError(
        @StringRes override val message: Int = R.string.error_local_non_existent_movie_data,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class NonExistentMovieDataRemoteError(
        @StringRes override val message: Int = R.string.error_remote_non_existent_movie_data,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    // For Repositories
    class GetMovieRepositoryError(
        @StringRes override val message: Int = R.string.error_repository_get_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class InsertMovieRepositoryError(
        @StringRes override val message: Int = R.string.error_repository_insert_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)

    class DeleteMovieRepositoryError(
        @StringRes override val message: Int = R.string.error_repository_delete_movie,
        override val cause: Throwable? = null
    ) : MovieFailure(message = message, cause = cause)
}
