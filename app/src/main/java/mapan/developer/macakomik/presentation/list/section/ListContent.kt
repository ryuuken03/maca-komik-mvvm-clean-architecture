package mapan.developer.macakomik.presentation.list.section

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mapan.developer.macakomik.presentation.component.OnBottomReached
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.ThumbnailComic
import mapan.developer.macakomik.presentation.list.ListViewModel
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListContent(
    modifier: Modifier,
    data: ComicList,
    pathUrl: String,
    viewModel: ListViewModel,
    navigateToDetail: (String,String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val isRefreshing =  remember { mutableStateOf(false) }
    val listGridState = rememberLazyGridState()
    ContentScrollUpButton(
        modifier = Modifier,
        listGridState = listGridState,
        content = {
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxWidth(),
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                onRefresh = {
                    isRefreshing.value = true
                    viewModel.resetData()
                    isRefreshing.value = false
                }
            ) {
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize(),
                    state = listGridState,
                    columns = GridCells.Fixed(3),
                    content = {
                        if (pathUrl.equals("-")) {
                            item(span = { GridItemSpan(3) }) {
                                var text by remember {
                                    mutableStateOf(
                                        TextFieldValue(
                                            viewModel.search ?: ""
                                        )
                                    )
                                }
                                val customTextSelectionColors = TextSelectionColors(
                                    handleColor = Color.Gray,
                                    backgroundColor = Color.LightGray
                                )
                                CompositionLocalProvider(
                                    LocalTextSelectionColors provides customTextSelectionColors,
                                ) {
                                    BasicTextField(
                                        value = text,
                                        onValueChange = { newText ->
                                            text = newText
                                            if (newText.text.length == 0) {
                                                viewModel.search = null
                                                viewModel.resetData()
                                            }
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp, vertical = 10.dp)
                                            .background(
                                                color = Color.White,
                                                shape = RoundedCornerShape(6.dp)
                                            ),
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                        keyboardActions = KeyboardActions(
                                            onSearch = {
                                                viewModel.search = text.text
                                                viewModel.resetData()
                                                keyboardController?.hide()
                                            }
                                        ),
                                        decorationBox = { innerTextField ->
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color.Black,
                                                        shape = RoundedCornerShape(6.dp)
                                                    )

                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(10.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = "search",
                                                        tint = Color.Black
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(horizontal = 5.dp)
                                                    ) {
                                                        if (text.text.isEmpty())
                                                            Text(
                                                                text = "Pencarian",
                                                                color = Color.Gray,
                                                                fontSize = 14.sp,
                                                            )
                                                        innerTextField()
                                                    }
                                                    if (text.text.length > 0) {
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "closeIcon",
                                                            modifier = Modifier.clickable {
                                                                var reset by mutableStateOf(
                                                                    TextFieldValue("")
                                                                )
                                                                text = reset
                                                                viewModel.search = null
                                                                viewModel.resetData()
                                                            },
                                                            tint = Color.Black
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        var list = data.list
                        if (list != null) {
                            if (list.size == 0) {
                                item(span = { GridItemSpan(3) }) {
                                    EmptyData(stringResource(R.string.text_data_not_found))
                                }
                            } else {
                                items(count = list.size) { index ->
                                    var thumbnail = list[index]
                                    ThumbnailComic(
                                        data = thumbnail,
                                        onClick = fun() {
                                            var image = "-"
                                            if (thumbnail.imgSrc != null) {
                                                image =
                                                    URLEncoder.encode(thumbnail.imgSrc, "UTF-8")
                                            }
                                            var url =
                                                URLEncoder.encode(thumbnail.url!!, "UTF-8")
                                            navigateToDetail(image, url)
                                        },
                                        modifier = modifier
                                            .fillMaxWidth()
                                            .animateItemPlacement(tween(durationMillis = 100))
                                    )
                                }
                            }
                        } else {
                            item(span = { GridItemSpan(3) }) {
                                EmptyData(stringResource(R.string.text_data_not_found))
                            }
                        }
                    },
                    contentPadding = PaddingValues(8.dp),
                )
            }
            listGridState.OnBottomReached(buffer = 2) {
                if (!viewModel.isMax) {
                    viewModel.loadMore()
                }
            }
        }
    )
}