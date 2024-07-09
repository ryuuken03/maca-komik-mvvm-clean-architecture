package mapan.developer.macakomik.presentation.home.section

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.home.HomeViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.presentation.component.AdmobBanner
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.GenreComic
import mapan.developer.macakomik.presentation.component.ThumbnailComic
import mapan.developer.macakomik.presentation.component.button.OutlinedButtonPrimary
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import mapan.developer.macakomik.util.Constants
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier,
    data: ComicHome,
    genres: ArrayList<ComicFilter>,
    themes: ArrayList<ComicFilter>,
    viewModel: HomeViewModel,
    navigateToDetail: (String,String) -> Unit,
    navigateToGenre: (Int,Boolean) -> Unit,
    navigateToList: (Int,String) -> Unit,
) {
    val listGridState = rememberLazyGridState()
    val indexPosition =  remember { mutableStateOf(0) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                indexPosition.value = listGridState.firstVisibleItemIndex
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    ContentScrollUpButton(
        modifier = Modifier,
        listGridState = listGridState,
        indexPosition = indexPosition.value,
        content = {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection),
                state = listGridState,
                columns = GridCells.Fixed(3),
                content = {
                    if (data != null) {
                        var isPopularEmpty = true
                        var isGenreEmpty = true
                        var isThemeEmpty = true
                        var isListEmpty = true
                        if (data.popular != null) {
                            if (data.popular!!.size > 0) {
                                isPopularEmpty = false
                                item(span = { GridItemSpan(3) }) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                                        text = "Populer Hari Ini",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                item(span = { GridItemSpan(3) }) {
                                    LazyRow(
                                        content = {
                                            items(count = data.popular!!.size) { index ->
                                                var thumbnail = data.popular!![index]
                                                ThumbnailComic(
                                                    data = thumbnail,
                                                    onClick = fun() {
                                                        var image = "-"
                                                        if (thumbnail.imgSrc != null) {
                                                            image = URLEncoder.encode(
                                                                thumbnail.imgSrc,
                                                                "UTF-8"
                                                            )
                                                        }
                                                        var url = URLEncoder.encode(
                                                            thumbnail.url!!,
                                                            "UTF-8"
                                                        )
                                                        navigateToDetail(image, url)
                                                    },
                                                    modifier = modifier
                                                        .width(110.dp)
                                                        .animateItemPlacement(
                                                            tween(
                                                                durationMillis = 100
                                                            )
                                                        )
                                                )
                                            }
                                        }
                                    )
                                }
                                item(span = { GridItemSpan(3) }) {
                                    var index = viewModel.index
                                    var sourceTitle =
                                        stringArrayResource(
                                            id =
                                            R.array.source_website_title
                                        )[index]
                                    OutlinedButtonPrimary(
                                        title = (if (index != 4) {"Proyek "} else {"Populer "} + sourceTitle).uppercase(),
                                        fontSize = 14.sp,
                                        onClick = {
                                            var projectPath = "project"
                                            if (index == 4) {
                                                projectPath = "project-list"
                                            } else if (index == 1) {
                                                projectPath = "project"
                                            } else if (index == 2) {
                                                projectPath = "pj"
                                            }else if (index == 0) {
                                                projectPath = "komik-populer"
                                            }
                                            var pathEncode =
                                                URLEncoder.encode(projectPath, "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )
                                }
//                                item(span = { GridItemSpan(3) }){
//                                    AdmobBanner(modifier = Modifier.fillMaxWidth())
//                                }
                            }
                        }
                        if (genres.size > 0) {
                            isGenreEmpty = false
                            item(span = { GridItemSpan(3) }) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp, vertical = 10.dp),
                                    text = "Genre Komik",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            var countRowGenre = 2
                            items(
                                count = countRowGenre, span = { GridItemSpan(3) }
                            ) { index ->
                                var first = index * 2
                                var second = first + 1
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    GenreComic(
                                        modifier = Modifier
                                            .weight(1f, false),
                                        data = genres[first],
                                        onClick = {
                                            var index = viewModel.index
                                            var path = "genres/" + genres[first].name!!
                                                .lowercase().replace(" ", "-")
                                            var pathEncode = URLEncoder.encode(path, "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )

                                    GenreComic(
                                        modifier = Modifier
                                            .weight(1f, false),
                                        data = genres[second],
                                        onClick = {
                                            var index = viewModel.index
                                            var path = "genres/" + genres[second].name!!
                                                .lowercase().replace(" ", "-")
                                            var pathEncode = URLEncoder.encode(path, "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )
                                }
                            }
                            item(span = { GridItemSpan(3) }) {
                                OutlinedButtonPrimary(
                                    title = "Semua Genre".uppercase(),
                                    fontSize = 14.sp,
                                    onClick = {
                                        navigateToGenre(viewModel.index,false)
                                    }
                                )
                            }
                        }
                        if (themes.size > 0) {
                            isThemeEmpty = false
                            item(span = { GridItemSpan(3) }) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp, vertical = 10.dp),
                                    text = "Tema Komik",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            var countRowTheme = 2
                            items(
                                count = countRowTheme, span = { GridItemSpan(3) }
                            ) { index ->
                                var first = index * 2
                                var second = first + 1
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    var firstTheme = themes[first].name!!
                                    var root = "tema/"
                                    if(Constants.isContent(firstTheme)){
                                        root = "konten/"
                                    }
                                    GenreComic(
                                        modifier = Modifier
                                            .weight(1f, false),
                                        data = themes[first],
                                        onClick = {
                                            var index = viewModel.index
                                            var path = root + firstTheme
                                                .lowercase().replace(" ", "-")
                                            var pathEncode = URLEncoder.encode(path, "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )
                                    var secondTheme = themes[second].name!!
                                    var root2 = "tema/"
                                    if(Constants.isContent(secondTheme)){
                                        root2 = "konten/"
                                    }

                                    GenreComic(
                                        modifier = Modifier
                                            .weight(1f, false),
                                        data = themes[second],
                                        onClick = {
                                            var index = viewModel.index
                                            var path = root2 + secondTheme
                                                .lowercase().replace(" ", "-")
                                            var pathEncode = URLEncoder.encode(path, "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )
                                }
                            }
                            item(span = { GridItemSpan(3) }) {
                                OutlinedButtonPrimary(
                                    title = "Semua Tema".uppercase(),
                                    fontSize = 14.sp,
                                    onClick = {
                                        navigateToGenre(viewModel.index,true)
                                    }
                                )
                            }
                        }
                        if (data.list != null) {
                            if (data.popular!!.size > 0) {
                                isListEmpty = false
                                item(span = { GridItemSpan(3) }) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 5.dp, vertical = 10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){

                                        Text(
                                            modifier = Modifier,
                                            text = "Komik Terbaru",
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Text(
                                            modifier = Modifier
                                                .noRippleClickable {
                                                    var index = viewModel.index
                                                    var pathEncode = URLEncoder.encode("-", "UTF-8")
                                                    navigateToList(index, pathEncode)
                                                },
                                            text = "Lainnya >>",
                                            fontSize = 14.sp,
                                            color = md_theme_light_primary,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
//                                var count = 9
                                var count = data.list!!.size
                                var dis = count % 3
                                if(dis != 0){
                                    count -= dis
                                }
                                items(count = count) { index ->
                                    var thumbnail = data.list!![index]
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
                                item(span = { GridItemSpan(3) }) {
                                    OutlinedButtonPrimary(
                                        title = "Lihat Semua".uppercase(),
                                        fontSize = 14.sp,
                                        onClick = {
                                            var index = viewModel.index
                                            var pathEncode = URLEncoder.encode("-", "UTF-8")
                                            navigateToList(index, pathEncode)
                                        }
                                    )
                                }
                            }
                        }

                        if (isPopularEmpty && isListEmpty && isGenreEmpty && isThemeEmpty) {
                            item(span = { GridItemSpan(3) }) {
                                EmptyData(stringResource(R.string.text_data_not_found),false)
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
        }
    )
}