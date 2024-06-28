package emperorfin.android.moviesearch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import emperorfin.android.moviesearch.ui.theme.Purple40


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 28th June, 2024.
 */



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithArrow(
    title: String?,
    onBackPress: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.height(58.dp),
        title = {
            Box {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
//                        .align(Alignment.CenterVertically),
                        .align(Alignment.TopCenter),
                    text = title ?: "",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackPress() }) {
                Box {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
                            .align(Alignment.TopCenter)
//                            .clickable {
//                                onBackPress()
//                            }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple40
        )
    )
}