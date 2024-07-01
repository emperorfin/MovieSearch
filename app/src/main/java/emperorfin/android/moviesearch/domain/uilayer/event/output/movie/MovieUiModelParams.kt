package emperorfin.android.moviesearch.domain.uilayer.event.output.movie


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */


interface MovieUiModelParams : MovieModelParams {
    val keywords: List<String>?
}