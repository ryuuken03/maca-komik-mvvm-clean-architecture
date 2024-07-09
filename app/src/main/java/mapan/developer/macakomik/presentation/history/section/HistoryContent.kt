package mapan.developer.macakomik.presentation.history.section

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
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.history.HistoryViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.presentation.bookmarks.section.bookmarksContentRead
import mapan.developer.macakomik.presentation.component.AdmobBanner
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.ThumbnailSaveHistory
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogConfirmation
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import mapan.developer.macakomik.util.Constants
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
    navigateToChapter: (String,String,String) -> Unit,
) {
    val listState = rememberLazyListState()

    val showDialogAlert =  remember { mutableStateOf(false) }
    val comicSelect: MutableState<ComicHistoryEntity?> = remember { mutableStateOf(null) }
    if(showDialogAlert.value){
        AlertDialogConfirmation(
            title = "Peringatan",
            description = "Peringatan, komik berjudul \"${comicSelect.value?.title}\" ini di dalamnya mungkin terdapat konten kekerasan, berdarah, atau seksual yang tidak sesuai dengan pembaca di bawah umur.",
            showDialog = showDialogAlert,
            setAction = {
                historyContentRead(comicSelect.value!!, navigateToChapter)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        ContentScrollUpButton(
            modifier = Modifier.padding(top = 0.dp),
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
                                withTop = false,
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
                                            comicSelect.value = data
                                            if(Constants.isAdult(data.genre!!)){
                                                showDialogAlert.value = true
                                            }else{
                                                historyContentRead(comicSelect.value!!, navigateToChapter)
                                            }
                                        }
                                    )
                                }
                            } else {
                                item {
                                    EmptyData(stringResource(R.string.text_data_not_found),false)
                                }
                            }
                        } else {
                            item {
                                EmptyData(stringResource(R.string.text_data_not_found),false)
                            }
                        }
                    }
                )
            }
        )
    }
}
fun historyContentRead(
    data: ComicHistoryEntity,
    navigateToChapter: (String,String,String) -> Unit){
    var url = URLEncoder.encode(data.urlChapter!!, "UTF-8")
    var urlDetailChange = URLEncoder.encode(data.urlDetail, "UTF-8")
    var imageChange = "-"
    if(data.imgSrc!=null){
        imageChange = URLEncoder.encode(data.imgSrc, "UTF-8")
    }
    navigateToChapter(imageChange, url, urlDetailChange)
}