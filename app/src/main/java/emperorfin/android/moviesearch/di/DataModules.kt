package emperorfin.android.moviesearch.di

import android.content.Context
import coil.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import emperorfin.android.moviesearch.BuildConfig
import emperorfin.android.moviesearch.data.datasource.framework.local.room.AppRoomDatabase
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entitysource.MovieLocalDataSourceRoom
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.dtosource.MovieRemoteDataSourceRetrofit
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.interceptors.RequestInterceptor
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.service.omdb.api.MovieApi
import emperorfin.android.moviesearch.data.repository.MovieRepository
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import emperorfin.android.moviesearch.domain.datalayer.datasource.MovieDataSource
import emperorfin.android.moviesearch.domain.datalayer.repository.IMovieRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MovieLocalDataSource

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MovieRemoteDataSource

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class LocalMovieDao

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class RemoteMovieDao

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    @MovieLocalDataSource
    abstract fun bindMovieLocalDataSourceRoom(dataSource: MovieLocalDataSourceRoom): MovieDataSource

    @Singleton
    @Binds
    @MovieRemoteDataSource
    abstract fun bindMovieRemoteDataSourceRetrofit(dataSource: MovieRemoteDataSourceRetrofit): MovieDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepository): IMovieRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppRoomDatabase {
        return AppRoomDatabase.getInstance(context)
    }

    //    @Provides
//    fun provideMovieDao(database: AppRoomDatabase): MovieDao = database.mMovieDao
    @Provides
    @LocalMovieDao
    fun provideMovieDao(database: AppRoomDatabase): IMovieDao = database.mMovieDao
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideImageLoader(
//        @ApplicationContext context: Context,
//        okHttpClient: OkHttpClient
//    ): ImageLoader {
//        return ImageLoader.Builder(context)
//            .okHttpClient { okHttpClient }
//            .build()
//    }

    @Provides
    @Singleton
    fun provideRetrofit(okhHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okhHttpClient)
//            .baseUrl(Api.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .baseUrl(BuildConfig.OMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @RemoteMovieDao
    fun provideMovieApi(retrofit: Retrofit): IMovieDao { //MovieApi
        return retrofit.create(MovieApi::class.java)
    }
}