package emperorfin.android.moviesearch.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 29th June, 2024.
 */



@Composable
fun LoadingContent(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loadingIndicator: @Composable () -> Unit,
    content: @Composable () -> Unit = {}
) {
    if (empty) {
        emptyContent()
    } else if (loading) {
        loadingIndicator()
    } else {

        Box {

            content()

        }
    }

}