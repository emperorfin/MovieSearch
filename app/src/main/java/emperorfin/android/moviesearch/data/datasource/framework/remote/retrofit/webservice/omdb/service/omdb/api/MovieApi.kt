package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.service.omdb.api

import emperorfin.android.moviesearch.BuildConfig
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieDetailsResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieSearchResponse
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


interface MovieApi : IMovieDao {

//    companion object {
//
//        private val retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl(BuildConfig.OMDB_BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build()
//        }
//
//        val INSTANCE: MovieApi by lazy { retrofit.create(MovieApi::class.java) }
//    }

    @GET("?&")
    override suspend fun getRemoteMovies(@Query("s") search: String): Response<MovieSearchResponse>

    @GET("?&")
    override suspend fun getRemoteMovie(@Query("i") imdbId: String): Response<MovieDetailsResponse>

    override suspend fun getMovie(imdbId: String): MovieEntity {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun getMovies(search: String): List<MovieEntity> {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun getMovies(): List<MovieEntity> {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun insertMovie(movie: MovieEntity): Long {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun deleteMovie(imdbId: String): Int {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun countAllMovies(): Int {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

}