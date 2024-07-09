package mapan.developer.macakomik.presentation.list.section

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mapan.developer.macakomik.presentation.component.OnBottomReached
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.ThumbnailComic
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogFilter
import mapan.developer.macakomik.presentation.component.inputtextfield.InputTextSearch
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.presentation.list.ListViewModel
import mapan.developer.macakomik.ui.theme.GrayDarker
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun ListContent(
    modifier: Modifier,
    data: ComicList,
    pathUrl: String,
    viewModel: ListViewModel,
    navigateToDetail: (String,String) -> Unit,
) {
    val showDialog =  remember { mutableStateOf(false) }
    val hasFilter by viewModel.hasFilter.collectAsStateWithLifecycle()
    val listGridState = rememberLazyGridState()

    val genresSelected = remember { mutableStateOf(listOf(-1)) }
    val typeSelected = remember { mutableStateOf(listOf(0)) }
    val orderbySelected = remember { mutableStateOf(listOf(2)) }

    if(viewModel.genre.equals("")){
        genresSelected.value = listOf(-1)
    }else{
        var index = 0
        var list =ArrayList<Int>()
        data.genres!!.forEach {
            var split = viewModel.genre.split("&")
            if(split.size > 1){
                for(i in 0 .. split.size-1){
                    if(!split[i].equals("")){
                        var split2 = split[i].split("%5D=")
                        if(split2.size > 1){
                            if(split2[1].equals(it.value)){
                                list.add(index)
                                break
                            }
                        }
                    }
                }
            }
            index++
        }
        if(list.size > 0){
            genresSelected.value = list.toList()
        }
    }
    if(viewModel.type.equals("")){
        typeSelected.value = listOf(0)
    }else{
        for(i in 0 .. data.types!!.size-1){
            if(viewModel.type.equals(data.types!![i].value)){
                typeSelected.value = listOf(i)
                break
            }
        }
    }
    if(viewModel.orderby.equals("")){
        var selectedIndex = 2
        if(viewModel.index == 0){
            selectedIndex = 2
        }else if(viewModel.index == 1){
            selectedIndex = 3
        }else if(viewModel.index == 2){
            selectedIndex = 3
        }
        orderbySelected.value = listOf(selectedIndex)
    }else{
        for(i in 0 .. data.orderbys!!.size-1){
            if(viewModel.orderby.equals(data.orderbys!![i].value)){
                orderbySelected.value = listOf(i)
                break
            }
        }
    }
    if(showDialog.value){
        AlertDialogFilter(
            genresSelected = genresSelected,
            typeSelected = typeSelected,
            orderbySelected = orderbySelected,
            showDialog = showDialog,
            genres = data.genres!!,
            types = data.types!!,
            orderbys = data.orderbys!!,
            setAction = { type, orderby, genre ->
                viewModel.setFilter(g = genre, t = type, o = orderby, th ="")
            }
        )
    }
    ContentScrollUpButton(
        modifier = Modifier,
        listGridState = listGridState,
        content = {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize(),
                state = listGridState,
                columns = GridCells.Fixed(3),
                content = {
                    if (pathUrl.equals("-")) {
                        item(span = { GridItemSpan(3) }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                InputTextSearch(
                                    modifier = Modifier
                                        .padding(
                                            start = 2.dp,
                                            end = 2.dp,
                                            top = 0.dp,
                                            bottom = 10.dp
                                            )
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .weight(5f, true),
                                    search = viewModel.search,
                                    onClear = {
//                                        viewModel.search = null
//                                        viewModel.resetData()
                                        viewModel.searching(null)
                                    },
                                    onSearch = { search ->
//                                        viewModel.search = search
//                                        viewModel.resetData()
                                        viewModel.searching(search)
                                    }
                                )
                                if(viewModel.canFilter){
                                    Box(
                                        modifier = modifier
                                            .padding(
                                                start = 5.dp,
                                                end = 5.dp,
                                                top = 0.dp,
                                                bottom = 10.dp)
                                            .fillMaxWidth()
                                            .weight(1f, false)
                                            .background(
                                                color = Color.White,
                                                shape = RoundedCornerShape(4.dp),
                                            )
                                            .noRippleClickable {
                                                showDialog.value = true
                                            },
                                    ){
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_filter_list_24),
                                            contentDescription = "filter",
                                            tint = GrayDarker,
                                            modifier = Modifier
                                                .align(alignment = Alignment.Center)
                                                .padding(9.dp)
                                        )
                                        if(hasFilter){
                                            Canvas(
                                                modifier = Modifier
                                                    .padding(
                                                        start = 10.dp,
                                                        end = 10.dp,
                                                        top = 10.dp
                                                    )
                                                    .size(8.dp)
                                                    .align(alignment = Alignment.TopEnd)
                                            ){
                                                drawCircle(Color.Red)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    var list = data.list
                    if(!viewModel.genre.equals("")){
                        var genreSearch = ""
                        genresSelected.value.forEach{index ->
                            if(index > -1){
                                if(!genreSearch.equals("")){
                                    genreSearch += ", "
                                }
                                genreSearch += data.genres!![index].name
                            }
                        }
                        if(!genreSearch.equals("")){
                            item(span = { GridItemSpan(3) }) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 5.dp,
                                            bottom = 10.dp
                                        ),
                                    text = "Genre yang dicari : ${genreSearch}",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                    if (list != null) {
                        if (list.size == 0) {
                            item(span = { GridItemSpan(3) }) {
                                EmptyData(stringResource(R.string.text_data_not_found),false)
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
                            EmptyData(stringResource(R.string.text_data_not_found),false)
                        }
                    }
                },
                contentPadding = PaddingValues(8.dp),
            )
            listGridState.OnBottomReached(buffer = 2) {
                if (!viewModel.isMax) {
                    viewModel.loadMore()
                }
            }
        }
    )
}