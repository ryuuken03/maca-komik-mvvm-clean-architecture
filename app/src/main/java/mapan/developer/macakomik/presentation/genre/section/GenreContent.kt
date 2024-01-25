package mapan.developer.macakomik.presentation.genre.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.presentation.component.GenreComic
import mapan.developer.macakomik.presentation.component.ThumbnailComic
import mapan.developer.macakomik.presentation.genre.GenreViewModel
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreContent(
    modifier: Modifier,
    data: ArrayList<ComicFilter>,
    viewModel: GenreViewModel,
    navigateToList: (Int,String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        content = {
            if (data.size > 0) {
                items (count = data.size){ index ->
                    var genre = data[index]
                    GenreComic(
                        modifier = Modifier,
                        data = genre,
                        onClick = {
                            var index = viewModel.index
                            var path = "genres/"+genre.name!!
                                .lowercase().replace(" ","-")
                            var pathEncode = URLEncoder.encode(path,"UTF-8")
                            navigateToList(index,pathEncode)
                        }
                    )
                }
            }else{
                item(span = { GridItemSpan(2) }){
                    EmptyData(stringResource(R.string.text_data_not_found))
                }
            }
        },
        contentPadding = PaddingValues(8.dp),
    )
}