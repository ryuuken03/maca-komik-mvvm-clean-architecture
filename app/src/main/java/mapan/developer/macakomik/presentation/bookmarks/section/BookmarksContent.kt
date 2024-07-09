package mapan.developer.macakomik.presentation.bookmarks.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.data.model.ComicChapter
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.presentation.bookmarks.BookmarksViewModel
import mapan.developer.macakomik.presentation.component.AdmobBanner
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogConfirmation
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ThumbnailSaveHistory
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import mapan.developer.macakomik.presentation.detail.section.detailContentRead
import mapan.developer.macakomik.util.Constants
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 12/01/2024
 */
@Composable
fun BookmarksContent (
    modifier: Modifier,
    list :MutableList<ComicSaveEntity>,
    viewModel: BookmarksViewModel,
    navigateToDetail: (String,String) -> Unit,
    navigateToChapter: (String,String,String) -> Unit,
) {

    val showDialog =  remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    val showDialogAlert =  remember { mutableStateOf(false) }
    val comicSelect: MutableState<ComicSaveEntity?> = remember { mutableStateOf(null) }
    if(showDialogAlert.value){
        AlertDialogConfirmation(
            title = "Peringatan",
            description = "Peringatan, komik berjudul \"${comicSelect.value?.title}\" ini di dalamnya mungkin terdapat konten kekerasan, berdarah, atau seksual yang tidak sesuai dengan pembaca di bawah umur.",
            showDialog = showDialogAlert,
            setAction = {
                bookmarksContentRead(comicSelect.value!!, navigateToChapter)
            }
        )
    }

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
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        var top = 0.dp
//        if(Constants.IS_PRODUCTION){
//            top = 10.dp
//            AdmobBanner(
//                modifier = Modifier.fillMaxWidth())
//        }
        ContentScrollUpButton(
            modifier = Modifier.padding(top = top),
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
                                withTop = false,
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
                                                comicSelect.value = data
                                                if(Constants.isAdult(data.genre!!)){
                                                    showDialogAlert.value = true
                                                }else{
                                                    bookmarksContentRead(comicSelect.value!!, navigateToChapter)
                                                }
                                            }
                                        },
                                        onDelete = {
                                            viewModel.deleteUrl = data.urlDetail
                                            showDialog.value = true
                                        }
                                    )
                                }
                            }else{
                                item{
                                    EmptyData(stringResource(R.string.text_data_not_found),false)
                                }
                            }
                        }else{
                            item{
                                EmptyData(stringResource(R.string.text_data_not_found),false)
                            }
                        }
                    }
                )
            }
        )
    }
}
fun bookmarksContentRead(
    data: ComicSaveEntity,
    navigateToChapter: (String,String,String) -> Unit){
    var url = URLEncoder.encode(data.urlChapter!!, "UTF-8")
    var urlDetailChange = URLEncoder.encode(data.urlDetail, "UTF-8")
    var imageChange = "-"
    if(data.imgSrc!=null){
        imageChange = URLEncoder.encode(data.imgSrc, "UTF-8")
    }
    navigateToChapter(imageChange, url, urlDetailChange)
}