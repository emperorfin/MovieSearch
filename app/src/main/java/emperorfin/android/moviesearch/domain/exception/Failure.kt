package emperorfin.android.moviesearch.domain.exception


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


/**
 * Base class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()

    /**
     * Extend this class for feature specific failures.
     */
    abstract class FeatureFailure : Failure()
}
