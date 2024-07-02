package emperorfin.android.moviesearch.data.datasource.framework.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import emperorfin.android.moviesearch.data.constant.StringConstants.ERROR_MESSAGE_NOT_YET_IMPLEMENTED
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_ID
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_TITLE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.TABLE_NAME
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieDetailsResponse
import emperorfin.android.moviesearch.data.datasource.framework.remote.retrofit.webservice.omdb.endpoint.MovieSearchResponse
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieEntityParams
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.Params
import retrofit2.Response


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


@Dao
interface MovieDao : IMovieDao {

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    override suspend fun countAllMovies(): Int

//    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_INFO_TITLE ASC")
//    override suspend fun getMovies(): Any
    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_INFO_TITLE ASC")
    override suspend fun getMovies(): List<MovieEntity>

//    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_TITLE LIKE '%' || :search || '%' ORDER BY $COLUMN_INFO_TITLE ASC")
//    override suspend fun getMovies(search: String): Any
    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_TITLE LIKE '%' || :search || '%' ORDER BY $COLUMN_INFO_TITLE ASC")
    override suspend fun getMovies(search: String): List<MovieEntity>

//    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_IMDB_ID = :imdbId")
//    override suspend fun getMovie(imdbId: String): Any
    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_IMDB_ID = :imdbId")
    override suspend fun getMovie(imdbId: String): MovieEntity

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    override suspend fun insertMovie(movie: Params): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertMovie(movie: MovieEntity): Long

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_INFO_IMDB_ID = :imdbId")
    override suspend fun deleteMovie(imdbId: String): Int

    override suspend fun getRemoteMovie(imdbId: String): Response<MovieDetailsResponse> {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

    override suspend fun getRemoteMovies(search: String): Response<MovieSearchResponse> {
        throw IllegalStateException(
            ERROR_MESSAGE_NOT_YET_IMPLEMENTED
        )
    }

}