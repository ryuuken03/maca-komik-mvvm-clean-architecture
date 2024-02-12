package mapan.developer.macakomik.presentation.bookmarks.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.presentation.bookmarks.BookmarksViewModel
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogConfirmation
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ThumbnailSaveHistory
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 12/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarksContent (
    modifier: Modifier,
    list :MutableList<ComicSaveEntity>,
    viewModel: BookmarksViewModel,
    navigateToDetail: (String,String) -> Unit,
    navigateToChapter: (String,String,String,String,String) -> Unit,
) {

    val showDialog =  remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    if(showDialog.value){
        AlertDialogConfirmation(
            title = "Hapus komik yang ditandai",
            description = "Apakah anda ingin menghapus komik ditandai ini?",
            showDialog = showDialog,
            setAction = {
                viewModel.deleteSave()
            }
        )
    }
    ContentScrollUpButton(
        modifier = Modifier,
        listState = listState,
        content = {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize(),
                content = {
                    item{
                        InputTextSearch(
                            search = viewModel.search,
                            onClear = {
                                viewModel.getList()
                            },
                            onSearch = { search ->
                                viewModel.getSearchList(search)
                            }
                        )
                    }
                    if(list != null){
                        if(list.size > 0){
                            items(list.size) { index ->
                                var data = list[index]

                                ThumbnailSaveHistory(
                                    modifier = modifier,
                                    save = data,
                                    history = null,
                                    onClick = {
                                        if(data.urlChapter == null){
                                            var urlDetailChange = URLEncoder.encode(data.urlDetail, "UTF-8")
                                            var imageChange = "-"
                                            if(data.imgSrc!=null){
                                                imageChange = URLEncoder.encode(data.imgSrc, "UTF-8")
                                            }
                                            navigateToDetail(imageChange,urlDetailChange)
                                        }else{
                                            var url = URLEncoder.encode(data.urlChapter!!, "UTF-8")
                                            var urlDetailChange = URLEncoder.encode(data.urlDetail, "UTF-8")
                                            var imageChange = "-"
                                            if(data.imgSrc!=null){
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
                                    },
                                    onDelete = {
    //                                    viewModel.deleteId = data.id!!
                                        viewModel.deleteUrl = data.urlDetail
                                        showDialog.value = true
                                    }
                                )
                            }
                        }else{
                            item{
                                EmptyData(stringResource(R.string.text_data_not_found))
                            }
                        }
                    }else{
                        item{
                            EmptyData(stringResource(R.string.text_data_not_found))
                        }
                    }
                }
            )
        }
    )
}