package emperorfin.android.moviesearch.data.datasource.framework.local.room.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_ID
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_TITLE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.TABLE_NAME
import emperorfin.android.moviesearch.domain.datalayer.dao.IMovieDao
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieEntityParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 30th June, 2024.
 */


interface MovieDao : IMovieDao {

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    override suspend fun countAllMovies(): Int

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_INFO_TITLE ASC")
    override suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_TITLE LIKE '%' || :search || '%' ORDER BY $COLUMN_INFO_TITLE ASC")
    override suspend fun getMovies(search: String): List<MovieEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_INFO_IMDB_ID = :omdbId")
    override suspend fun getMovie(omdbId: String): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertMovie(movie: MovieEntityParams): Long

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_INFO_IMDB_ID = :omdbId")
    override suspend fun deleteMovie(omdbId: String): Int

}