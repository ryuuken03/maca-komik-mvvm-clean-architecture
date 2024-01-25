package mapan.developer.macakomik.presentation.read.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.presentation.component.noRippleCombileClickable
import mapan.developer.macakomik.presentation.read.ReadViewModel
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import java.net.URLEncoder

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
    showSearch: MutableState<Boolean>,
    viewModel: ReadViewModel,
    navigateToDetail: (String,String,Boolean) -> Unit,
    navigateBack: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val showInfo =  remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val canZoom =  remember { mutableStateOf(false) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                showInfo.value = false
                showSearch.value = false
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
                userScrollEnabled  = !canZoom.value,
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
                                    val scale = remember { mutableStateOf(1f) }
                                    val offset = remember { mutableStateOf(Offset.Zero) }
                                    BoxWithConstraints(
                                        modifier = Modifier
                                    ){
                                        val state = rememberTransformableState{ zoomChange, panChange, rotation ->
                                            val eWidth = (scale.value - 1) * constraints.maxWidth
                                            val eHeight = (scale.value - 1) * constraints.maxHeight

                                            val maxX = eWidth / 2
                                            val maxY = eHeight / 2
                                            offset.value = Offset(
                                                x = (offset.value.x +panChange.x).coerceIn(-maxX,maxX),
                                                y = (offset.value.y +panChange.y).coerceIn(-maxY,maxY)
                                            )
                                        }
                                        if(!canZoom.value){
                                            scale.value = 1f
                                            offset.value = Offset.Zero
                                        }
                                        SubcomposeAsyncImage(
                                            model = page.imgSrc,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .graphicsLayer(
                                                    scaleX = scale.value,
                                                    scaleY = scale.value,
                                                    translationX = offset.value.x,
                                                    translationY = offset.value.y
                                                )
                                                .transformable(state, enabled = canZoom.value)
                                                .noRippleCombileClickable(
                                                    onClick = {},
                                                    onDoubleClick = {
                                                        canZoom.value = !canZoom.value
                                                        if(canZoom.value){
                                                            scale.value = (scale.value * 2f)
                                                            val eWidth = (scale.value - 1) * constraints.maxWidth
                                                            val eHeight = (scale.value - 1) * constraints.maxHeight

                                                            val maxX = eWidth / 2
                                                            val maxY = eHeight / 2
                                                            offset.value = Offset(
                                                                x = (offset.value.x).coerceIn(-maxX,maxX),
                                                                y = (offset.value.y).coerceIn(-maxY,maxY)
                                                            )
                                                        }
                                                    }
                                                )
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
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(showSearch.value){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ){
                                var text = ""
                                if(!currentPage.equals("-")){
                                    if(currentPage.toInt() > 1){
                                        text = currentPage
                                    }
                                }
                                var search by remember { mutableStateOf(TextFieldValue(text)) }
                                val customTextSelectionColors = TextSelectionColors(
                                    handleColor = Color.Gray,
                                    backgroundColor = Color.LightGray
                                )
                                CompositionLocalProvider (
                                    LocalTextSelectionColors provides customTextSelectionColors,
                                ){
                                    BasicTextField(
                                        value = search,
                                        onValueChange = { newText ->
                                            try {
                                                if(newText.text.toInt() > maxPage.toInt()){
                                                    var max by mutableStateOf(TextFieldValue(maxPage))
                                                    search = max
                                                }else if(newText.text.toInt() < 1){
                                                    var max by mutableStateOf(TextFieldValue(""))
                                                    search = max
                                                }else{
                                                    search = newText
                                                }
                                            }catch (e:Exception){
                                                var max by mutableStateOf(TextFieldValue(""))
                                                search = max
                                            }
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .background(
                                                color = Color.White,
                                                shape = RoundedCornerShape(6.dp)
                                            ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Search
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onSearch = {
                                                coroutineScope.launch {
                                                    listState.scrollToItem(index = search.text.toInt()-1)
                                                    viewModel.setCurrentPageText(search.text)
                                                }
                                                showSearch.value = false
                                                keyboardController?.hide()
                                            }
                                        ),
                                        decorationBox = { innerTextField ->
                                            Box(
                                                modifier = Modifier
                                                    .width(130.dp)
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color.Black,
                                                        shape = RoundedCornerShape(6.dp)
                                                    )

                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .width(130.dp)
                                                        .padding(10.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Icon(
                                                        modifier = Modifier,
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = "search",
                                                        tint =  Color.Black
                                                    )
                                                    Box(modifier = Modifier
                                                        .weight(1f)
                                                        .padding(horizontal = 5.dp)){
                                                        if(search.text.isEmpty()){
                                                            Text(text = currentPage+"/"+maxPage,
                                                                color = Color.Gray,
                                                                fontSize = 14.sp,)
                                                        }
                                                        innerTextField()
                                                    }
                                                    if(search.text.length > 0){
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "closeIcon",
                                                            modifier = Modifier
                                                                .clickable {
                                                                    var reset by mutableStateOf(
                                                                        TextFieldValue("")
                                                                    )
                                                                    search = reset
                                                                    keyboardController?.hide()
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
                        }else{
                            Spacer(modifier = Modifier.height(10.dp))
                        }
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
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 0.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ){
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Box(
                                        modifier = Modifier
                                            .width(50.dp)
                                            .height(50.dp)
                                            .background(
                                                color = md_theme_light_primary,
                                                shape = RoundedCornerShape(100),
                                            )
                                            .noRippleClickable {
                                                coroutineScope.launch {
                                                    listState.scrollToItem(index = 0)
                                                }
                                            },
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
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Box(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .background(
                                                color = md_theme_light_primary,
                                                shape = RoundedCornerShape(4.dp),
                                            )
                                            .noRippleClickable {
                                                showInfo.value = true
                                            }
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
}
