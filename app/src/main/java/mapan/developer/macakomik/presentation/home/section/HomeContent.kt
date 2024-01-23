package mapan.developer.macakomik.presentation.home.section

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import mapan.developer.macakomik.presentation.component.ContentScrollUpButton
import mapan.developer.macakomik.presentation.component.GenreComic
import mapan.developer.macakomik.presentation.component.ThumbnailComic
import mapan.developer.macakomik.presentation.component.button.OutlinedButtonPrimary
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
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
    viewModel: HomeViewModel,
    navigateToDetail: (String,String) -> Unit,
    navigateToGenre: (Int) -> Unit,
    navigateToList: (Int,String) -> Unit,
) {
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
                    viewModel.setLoading()
                    isRefreshing.value = false
                }
            ) {
                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize(),
                    state = listGridState,
                    columns = GridCells.Fixed(3),
                    content = {
                        if (data != null) {
                            var isPopularEmpty = true
                            var isGenreEmpty = true
                            var isListEmpty = true
                            if (data.popular != null) {
                                if (data.popular!!.size > 0) {
                                    isPopularEmpty = false
                                    item(span = { GridItemSpan(3) }) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 5.dp, vertical = 10.dp),
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
                                            title = ("Proyek " + sourceTitle).uppercase(),
                                            fontSize = 14.sp,
                                            onClick = {
                                                var projectPath = "project"
                                                if (index == 0) {
                                                    projectPath = "project-list"
                                                } else if (index == 1) {
                                                    projectPath = "project"
                                                } else if (index == 2) {
                                                    projectPath = "pj"
                                                }
                                                var pathEncode =
                                                    URLEncoder.encode(projectPath, "UTF-8")
                                                navigateToList(index, pathEncode)
                                            }
                                        )
                                    }
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
                                var countRowGenre = 3
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
                                            navigateToGenre(viewModel.index)
                                        }
                                    )
                                }
                            }
                            if (data.list != null) {
                                if (data.popular!!.size > 0) {
                                    isListEmpty = false
                                    item(span = { GridItemSpan(3) }) {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 5.dp, vertical = 10.dp),
                                            text = "Komik Terbaru",
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    var count = 15
                                    if (data.list!!.size < count) {
                                        count = data.list!!.size
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

                            if (isPopularEmpty && isListEmpty && isGenreEmpty) {
                                item(span = { GridItemSpan(3) }) {
                                    EmptyData(stringResource(R.string.text_data_not_found))
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
        }
    )
}