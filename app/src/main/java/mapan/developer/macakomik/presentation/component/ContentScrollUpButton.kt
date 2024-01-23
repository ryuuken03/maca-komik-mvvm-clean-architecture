package mapan.developer.macakomik.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary

/***
 * Created By Mohammad Toriq on 23/01/2024
 */
@Composable
fun ContentScrollUpButton(
    modifier: Modifier,
    listState : LazyListState? = null,
    listGridState : LazyGridState? = null,
    content : @Composable () ->Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Box {
        content()
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 30.dp)
                    .fillMaxWidth()
                    .noRippleClickable {
                        coroutineScope.launch {
                            if(listGridState!=null){
                                listGridState.scrollToItem(index = 0)
                            }
                            if(listState!=null){
                                listState.scrollToItem(index = 0)
                            }
                        }
                    },
                horizontalArrangement = Arrangement.End
            ) {

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(
                            color = md_theme_light_primary,
                            shape = RoundedCornerShape(100),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "scrollUp",
                        tint = GrayDarker,
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .padding(5.dp),
                    )
                }
            }
        }
    }
}