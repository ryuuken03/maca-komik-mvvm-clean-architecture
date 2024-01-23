package mapan.developer.macakomik.presentation.bookmarks.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.presentation.bookmarks.BookmarksViewModel
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogConfirmation
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ThumbnailSaveHistory
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
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