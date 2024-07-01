package emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.service.omdb.api

import emperorfin.android.moviesearch.BuildConfig
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieDetailsResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieSearchResponse
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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

    @GET
    override suspend fun getMovies(@Query("s") search: String): Response<MovieSearchResponse>

    @GET
    override suspend fun getMovie(@Query("i") omdbId: String): Response<MovieDetailsResponse>

}