package mapan.developer.macakomik.presentation.read.section

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.size.Size
import coil.util.DebugLogger
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogScrollToPage
import mapan.developer.macakomik.presentation.read.ReadViewModel
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import okhttp3.OkHttpClient
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadContent(
    modifier: Modifier,
    data: ComicChapterPageList,
    image: String,
    urlDetail: String,
    fromDetail :Boolean,
    showDialog: MutableState<Boolean>,
    viewModel: ReadViewModel,
    navigateToDetail: (String,String,Boolean) -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val showInfo =  remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                showInfo.value = false
                return super.onPreScroll(available, source)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                viewModel.setCurrentPageText((listState.firstVisibleItemIndex+1).toString())
                return super.onPostScroll(consumed, available, source)
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val maxPage by viewModel.maxPage.collectAsStateWithLifecycle()
    if(showDialog.value){
        AlertDialogScrollToPage(
            currentPage = currentPage,
            maxPage = maxPage,
            showDialog = showDialog,
            setAction = { page ->
                coroutineScope.launch {
                    listState.scrollToItem(index = page.toInt()-1)
                    viewModel.setCurrentPageText(page)
                }
            }
        )
    }

    val isRefreshing =  remember { mutableStateOf(false) }
    SwipeRefresh(
        modifier = Modifier
            .fillMaxWidth(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            viewModel.setLoading()
            isRefreshing.value = false
        }
    ) {
        Box{
            LazyColumn(
                state = listState,
                modifier = modifier
                    .nestedScroll(nestedScrollConnection)
                    .fillMaxSize(),
                content = {
                    if (data != null) {
                        if (data.list != null) {
                            if(data.list!!.size == 0){
                                item{
                                    EmptyData(stringResource(R.string.text_data_not_found))
                                }
                            }else{
                                items (count = data.list!!.size){ index ->
                                    var page = data.list!![index]
                                    Box{
                                        SubcomposeAsyncImage(
                                            model = page.imgSrc,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            val state = painter.state
                                            if (state is AsyncImagePainter.State.Loading) {
                                                LinearProgressIndicator(
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                )
                                            }else {
                                                SubcomposeAsyncImageContent()
                                            }
                                        }
                                        Box (modifier = Modifier
                                            .padding(5.dp)){
                                            var color = md_theme_light_primary
                                            Text(
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .drawBehind {
                                                        drawCircle(
                                                            color = color,
                                                            radius = this.size.maxDimension
                                                        )
                                                    },
                                                text = (index+1).toString(),
                                                fontSize = 8.sp
                                            )
                                        }
                                    }
                                }
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
                },
            )
            if (data != null) {
                if (data.title != null) {
                    Column(
                        modifier = modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        if(showInfo.value){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(GrayDarker)
                            ){
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 0.dp,
                                            start = 10.dp,
                                            end = 10.dp
                                        )
                                        .fillMaxWidth(),
                                    text = data.title!!,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )

                                Row (modifier = Modifier
                                    .padding(5.dp)
                                ){
                                    if(data.pagePrev == null){
                                        Spacer(modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f, false)
                                        )
                                    }else{
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp)
                                                .weight(1f, false),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = md_theme_light_primary
                                            ),
                                            shape = RoundedCornerShape(10),
                                            contentPadding = PaddingValues(),
                                            onClick = {
                                                var url = data.pagePrev!!
                                                viewModel.changeUrl(url,"","")
                                            }
                                        ) {
                                            Text(
                                                text = "Prev",
                                                fontSize = 12.sp,
                                                color = GrayDarker,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp)
                                            .weight(2f, false),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(10),
                                        contentPadding = PaddingValues(),
                                        onClick = {
                                            if(fromDetail){
                                                navigateBack()
                                            }else{
                                                var image = image
                                                if(!image.equals("")){
                                                    image = URLEncoder.encode(image, "UTF-8")
                                                }
                                                var url = URLEncoder.encode(urlDetail, "UTF-8")
                                                navigateToDetail(image,url,true)
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "Semua Chapter",
                                            fontSize = 12.sp,
                                            color = GrayDarker,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    if(data.pageNext == null){
                                        Spacer(modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f, false)
                                        )
                                    }else{
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(5.dp)
                                                .weight(1f, false),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = md_theme_light_primary
                                            ),
                                            shape = RoundedCornerShape(10),
                                            contentPadding = PaddingValues(),
                                            onClick = {
                                                var url = data.pageNext!!
                                                viewModel.changeUrl(url,"","")
                                            }
                                        ) {
                                            Text(
                                                text = "Next",
                                                fontSize = 12.sp,
                                                color = GrayDarker,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }else{
                            Row(
                                modifier =  Modifier
                                    .padding(horizontal = 10.dp, vertical = 30.dp)
                                    .fillMaxWidth()
                                    .noRippleClickable {
                                        coroutineScope.launch {
                                            listState.scrollToItem(index = 0)
                                        }
                                    },
                                horizontalArrangement = Arrangement.End
                            ){

                                Box(
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .background(
                                            color = md_theme_light_primary,
                                            shape = RoundedCornerShape(100),
                                        )
                                    ,
                                    contentAlignment = Alignment.Center
                                ){
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
                            Row(
                                modifier =  Modifier
                                    .padding(horizontal = 15.dp, vertical = 0.dp)
                                    .fillMaxWidth()
                                    .noRippleClickable {
                                        showInfo.value = true
                                    },
                                horizontalArrangement = Arrangement.End
                            ){
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .background(
                                            color = md_theme_light_primary,
                                            shape = RoundedCornerShape(4.dp),
                                        )
                                    ,
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = "showInfo",
                                        tint = GrayDarker,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
