package mapan.developer.macakomik.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.presentation.list.section.ListContent

/***
 * Created By Mohammad Toriq on 09/02/2024
 */
@Composable
fun ContentSwipeRefresh (
    modifier :Modifier,
    onRefresh : () -> Unit,
    content : @Composable () ->Unit,
){
    val isRefreshing =  remember { mutableStateOf(false) }
    SwipeRefresh(
        modifier = modifier
            .fillMaxWidth(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            onRefresh()
            isRefreshing.value = false
        }
    ) {
       content()
    }
}