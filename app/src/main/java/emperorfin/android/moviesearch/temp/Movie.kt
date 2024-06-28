package emperorfin.android.moviesearch.temp


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */


data class Movie(
    var title: String,
    var year: String,
    var rated: String,
    var released: String,
    var runtime: String,
    var genre: String,
    var director: String,
    var writer: String,
    var actors: String,
    var plot: String,
    var language: String,
    var country: String,
    var awards: String,
    var poster: String,
    var metascore: String,
    var imdbRating: String,
    var imdbVotes: String,
    var imdbID: String,
    var type: String,
    var dvd: String,
    var boxOffice: String,
    var production: String,
    var website: String,
    var response: String,
    var ratings: List<Rating> = ArrayList(),
)

/*


* */
