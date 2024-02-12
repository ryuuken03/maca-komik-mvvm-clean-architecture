package mapan.developer.macakomik.presentation.history.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.history.HistoryViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.ThumbnailSaveHistory
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 11/01/2024
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryContent(
    modifier: Modifier,
    list :MutableList<ComicHistoryEntity>,
    viewModel: HistoryViewModel,
    navigateToChapter: (String,String,String,String,String) -> Unit,
) {
    val listState = rememberLazyListState()
    ContentScrollUpButton(
        modifier = Modifier,
        listState = listState,
        content = {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize(),
                content = {
                    item {
                        InputTextSearch(
                            search = viewModel.search,
                            onClear = {
                                viewModel.getData()
                            },
                            onSearch = { search ->
                                viewModel.getData(search)
                            }
                        )
                    }
                    if (list != null) {
                        if (list.size > 0) {
                            items(list.size) { index ->
                                var data = list[index]
                                ThumbnailSaveHistory(
                                    modifier = modifier,
                                    save = null,
                                    history = data,
                                    onClick = {
                                        var url = URLEncoder.encode(data.urlChapter!!, "UTF-8")
                                        var urlDetailChange = URLEncoder.encode(data.urlDetail, "UTF-8")
                                        var imageChange = "-"
                                        if (data.imgSrc != null) {
                                            imageChange = URLEncoder.encode(data.imgSrc, "UTF-8")
                                        }
                                        navigateToChapter(
                                            data.title!!,
                                            imageChange,
                                            data.chapter!!,
                                            url,
                                            urlDetailChange
                                        )
                                    }
                                )
                            }
                        } else {
                            item {
                                EmptyData(stringResource(R.string.text_data_not_found))
                            }
                        }
                    } else {
                        item {
                            EmptyData(stringResource(R.string.text_data_not_found))
                        }
                    }
                }
            )
        }
    )
}