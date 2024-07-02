package emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.COLUMN_INFO_IMDB_ID
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.MovieEntity.Companion.TABLE_NAME
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.embedded.Rating
import emperorfin.android.moviesearch.data.datasource.framework.local.room.entity.movie.typeconverters.RatingsTypeConverter
import emperorfin.android.moviesearch.domain.uilayer.event.output.movie.MovieEntityParams


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


@Entity(
    tableName = TABLE_NAME,
    primaryKeys = [COLUMN_INFO_IMDB_ID]
)
data class MovieEntity(
    @ColumnInfo(name = COLUMN_INFO_IMDB_ID)
    override val imdbId: String,
    @ColumnInfo(name = COLUMN_INFO_TITLE)
    override val title: String,
    @ColumnInfo(name = COLUMN_INFO_YEAR)
    override val year: String,
    @ColumnInfo(name = COLUMN_INFO_RATED)
    override val rated: String,
    @ColumnInfo(name = COLUMN_INFO_RELEASED)
    override val released: String,
    @ColumnInfo(name = COLUMN_INFO_RUNTIME)
    override val runtime: String,
    @ColumnInfo(name = COLUMN_INFO_GENRE)
    override val genre: String,
    @ColumnInfo(name = COLUMN_INFO_DIRECTOR)
    override val director: String,
    @ColumnInfo(name = COLUMN_INFO_WRITER)
    override val writer: String,
    @ColumnInfo(name = COLUMN_INFO_ACTORS)
    override val actors: String,
    @ColumnInfo(name = COLUMN_INFO_PLOT)
    override val plot: String,
    @ColumnInfo(name = COLUMN_INFO_LANGUAGE)
    override val language: String,
    @ColumnInfo(name = COLUMN_INFO_COUNTRY)
    override val country: String,
    @ColumnInfo(name = COLUMN_INFO_AWARDS)
    override val awards: String,
    @ColumnInfo(name = COLUMN_INFO_POSTER)
    override val poster: String,
    @ColumnInfo(name = COLUMN_INFO_METASCORE)
    override val metascore: String,
    @ColumnInfo(name = COLUMN_INFO_IMDB_RATING)
    override val imdbRating: String,
    @ColumnInfo(name = COLUMN_INFO_IMDB_VOTES)
    override val imdbVotes: String,
    @ColumnInfo(name = COLUMN_INFO_TYPE)
    override val type: String,
    @ColumnInfo(name = COLUMN_INFO_DVD)
    override val dvd: String,
    @ColumnInfo(name = COLUMN_INFO_BOX_OFFICE)
    override val boxOffice: String,
    @ColumnInfo(name = COLUMN_INFO_PRODUCTION)
    override val production: String,
    @ColumnInfo(name = COLUMN_INFO_WEBSITE)
    override val website: String,
    @ColumnInfo(name = COLUMN_INFO_RESPONSE)
    override val response: String,
    @ColumnInfo(name = COLUMN_INFO_RATINGS)
    override val ratings: List<Rating> = emptyList()
) : MovieEntityParams {

    companion object {

        const val TABLE_NAME = "table_movies"

        const val COLUMN_INFO_IMDB_ID = "imdb_id"
        const val COLUMN_INFO_TITLE = "title"
        const val COLUMN_INFO_YEAR = "year"
        const val COLUMN_INFO_RATED = "rated"
        const val COLUMN_INFO_RELEASED = "released"
        const val COLUMN_INFO_RUNTIME = "runtime"
        const val COLUMN_INFO_GENRE = "genre"
        const val COLUMN_INFO_DIRECTOR = "director"
        const val COLUMN_INFO_WRITER = "writer"
        const val COLUMN_INFO_ACTORS = "actors"
        const val COLUMN_INFO_PLOT = "plot"
        const val COLUMN_INFO_LANGUAGE = "language"
        const val COLUMN_INFO_COUNTRY = "country"
        const val COLUMN_INFO_AWARDS = "awards"
        const val COLUMN_INFO_POSTER = "poster"
        const val COLUMN_INFO_METASCORE = "metascore"
        const val COLUMN_INFO_IMDB_RATING = "imdb_rating"
        const val COLUMN_INFO_IMDB_VOTES = "imdb_votes"
        const val COLUMN_INFO_TYPE = "type"
        const val COLUMN_INFO_DVD = "dvd"
        const val COLUMN_INFO_BOX_OFFICE = "box_office"
        const val COLUMN_INFO_PRODUCTION = "production"
        const val COLUMN_INFO_WEBSITE = "website"
        const val COLUMN_INFO_RESPONSE = "response"
        const val COLUMN_INFO_RATINGS = "ratings"

        fun newInstance(
            imdbId: String,
            title: String,
            year: String,
            rated: String,
            released: String,
            runtime: String,
            genre: String,
            director: String,
            writer: String,
            actors: String,
            plot: String,
            language: String,
            country: String,
            awards: String,
            poster: String,
            metascore: String,
            imdbRating: String,
            imdbVotes: String,
            type: String,
            dvd: String,
            boxOffice: String,
            production: String,
            website: String,
            response: String,
            ratings: List<Rating>
        ): MovieEntity {
            return MovieEntity(
                imdbId = imdbId,
                title = title,
                year = year,
                rated = rated,
                released = released,
                runtime = runtime,
                genre = genre,
                director = director,
                writer = writer,
                actors = actors,
                plot = plot,
                language = language,
                country = country,
                awards = awards,
                poster = poster,
                metascore = metascore,
                imdbRating = imdbRating,
                imdbVotes = imdbVotes,
                type = type,
                dvd = dvd,
                boxOffice = boxOffice,
                production = production,
                website = website,
                response = response,
                ratings = ratings
            )
        }

    }

}
