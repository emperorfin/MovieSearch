package emperorfin.android.moviesearch.data.datasource.framework.local.room

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import emperorfin.android.moviesearch.data.datasource.framework.local.room.dao.MovieDao
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_ACTORS
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_AWARDS
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_BOX_OFFICE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_COUNTRY
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_DIRECTOR
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_DVD
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_GENRE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_ID
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_RATING
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_VOTES
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_LANGUAGE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_METASCORE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_PLOT
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_POSTER
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_PRODUCTION
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_RATED
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_RELEASED
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_RESPONSE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_RUNTIME
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_TITLE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_TYPE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_WEBSITE
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_WRITER
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_YEAR
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.TABLE_NAME
import emperorfin.android.moviesearch.data.util.SampleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 01th July, 2024.
 */


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract val mMovieDao: MovieDao

    companion object {

        private const val DATABASE_NAME = "database_app"

        private var isDatabaseAlreadyPopulated: Boolean = false

        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        private val TAG: String = AppRoomDatabase::class.java.simpleName

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase{

            synchronized(this){
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        DATABASE_NAME
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)

                                coroutineScope.launch {
                                    // This is now commented out since real data is being cached
                                    // to the database.
//                                    populateInitialMoviesSampleDataUsingSqliteDatabaseWithCoroutineThread(db, SampleData.getEntityMovies())
                                }
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                            }

                            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                                super.onDestructiveMigration(db)
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }

        }

        private suspend fun populateInitialMoviesSampleDataUsingSqliteDatabaseWithCoroutineThread(db: SupportSQLiteDatabase, movies: List<MovieEntity>) {
            // Unused at the moment.
            if (isDatabaseAlreadyPopulated)
                return

            db.beginTransaction()

            try {
                val initialMovieValues = ContentValues()
                for (movie in movies){
                    initialMovieValues.put(COLUMN_INFO_IMDB_ID, movie.imdbId)
                    initialMovieValues.put(COLUMN_INFO_TITLE, movie.title)
                    initialMovieValues.put(COLUMN_INFO_YEAR, movie.year)
                    initialMovieValues.put(COLUMN_INFO_RATED, movie.rated)
                    initialMovieValues.put(COLUMN_INFO_RELEASED, movie.released)
                    initialMovieValues.put(COLUMN_INFO_RUNTIME, movie.runtime)
                    initialMovieValues.put(COLUMN_INFO_GENRE, movie.genre)
                    initialMovieValues.put(COLUMN_INFO_DIRECTOR, movie.director)
                    initialMovieValues.put(COLUMN_INFO_WRITER, movie.writer)
                    initialMovieValues.put(COLUMN_INFO_ACTORS, movie.actors)
                    initialMovieValues.put(COLUMN_INFO_PLOT, movie.plot)
                    initialMovieValues.put(COLUMN_INFO_LANGUAGE, movie.language)
                    initialMovieValues.put(COLUMN_INFO_COUNTRY, movie.country)
                    initialMovieValues.put(COLUMN_INFO_AWARDS, movie.awards)
                    initialMovieValues.put(COLUMN_INFO_POSTER, movie.poster)
                    initialMovieValues.put(COLUMN_INFO_METASCORE, movie.metascore)
                    initialMovieValues.put(COLUMN_INFO_IMDB_RATING, movie.imdbRating)
                    initialMovieValues.put(COLUMN_INFO_IMDB_VOTES, movie.imdbVotes)
                    initialMovieValues.put(COLUMN_INFO_TYPE, movie.type)
                    initialMovieValues.put(COLUMN_INFO_DVD, movie.dvd)
                    initialMovieValues.put(COLUMN_INFO_BOX_OFFICE, movie.boxOffice)
                    initialMovieValues.put(COLUMN_INFO_PRODUCTION, movie.production)
                    initialMovieValues.put(COLUMN_INFO_WEBSITE, movie.website)
                    initialMovieValues.put(COLUMN_INFO_RESPONSE, movie.response)
                    // TODO: Convert list to JSON string.
//                    initialMovieValues.put(COLUMN_INFO_RATINGS, movie.ratings)

                    db.insert(TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, initialMovieValues)
                }

                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }

    }
}
