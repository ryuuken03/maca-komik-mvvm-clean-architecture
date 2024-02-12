package mapan.developer.macakomik.presentation.read

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.presentation.component.ContentSwipeRefresh
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ProgressLoading
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarDetailRead
import mapan.developer.macakomik.presentation.read.section.ReadContent
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import java.net.URLDecoder

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadScreen(
    title :String,
    image :String,
    chapter :String,
    url :String,
    urlDetail :String,
    fromDetail :Boolean,
    viewModel: ReadViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToDetail: (String,String,Boolean) -> Unit,
) {
    val context = LocalContext.current
    val isAdded by viewModel.isAddedBookmark.collectAsStateWithLifecycle()
    val isDetailAdded by viewModel.isDetailAddedBookmark.collectAsStateWithLifecycle()
    val title by viewModel.currentChapter.collectAsStateWithLifecycle()
    val cPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val mPage by viewModel.maxPage.collectAsStateWithLifecycle()

    val showSearch =  remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            var icon =
                if(!isDetailAdded){
                    R.drawable.baseline_add_24
                } else {
                    if(!isAdded){
                        R.drawable.baseline_bookmarks_24
                    }else{
                        R.drawable.baseline_bookmark_24
                    }
                }

            if(isAdded){
                icon = R.drawable.baseline_bookmark_24
            }
            var color = GrayDarker
            if(isAdded){
                color = md_theme_light_primary
            }
            ToolbarDetailRead(
                title = if(title.equals("")) "Memuat..." else title,
                titleFontSize = 16.sp,
                iconAction = icon,
                colorIconAction = color,
                onClickAction = {
                    if(!viewModel.currentPage.value.equals("-")){
                        if (color != md_theme_light_primary) {
                            viewModel.getDetail(false)
                            Toast.makeText(context,"Komik Chapter ini telah disimpan", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                textPage = cPage+"/"+mPage,
                textPageFontSize = 14.sp,
                onClickPage = {
                    if(!viewModel.currentPage.value.equals("-")){
                        showSearch.value = true
                    }
                },
                navigateBack = navigateBack,
            )
        },

        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayDarker)
                    .padding(it)
            ) {
                ContentSwipeRefresh(
                    modifier = Modifier,
                    onRefresh = {
                        viewModel.setLoading()
                    },
                    content = {
                        viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
                            when (uiState) {
                                is UiState.Loading -> {
                                    ProgressLoading()
                                    var urlChange = URLDecoder.decode(url, "UTF-8")
                                    var urlDetailChange = URLDecoder.decode(urlDetail, "UTF-8")
                                    if(!viewModel.init){
                                        viewModel.changeUrl(urlChange,urlDetailChange,image)
                                    }
                                    viewModel.getRead()
                                }

                                is UiState.Success -> {
                                    if(uiState.data == null){
                                        EmptyData(message = stringResource(R.string.text_data_not_found))
                                    }else{
                                        ReadContent(
                                            modifier = Modifier,
                                            data = uiState.data,
                                            image = image,
                                            urlDetail = urlDetail,
                                            fromDetail = fromDetail,
                                            showSearch = showSearch,
                                            viewModel = viewModel,
                                            navigateToDetail = navigateToDetail,
                                            navigateBack = navigateBack,
                                        )
                                    }
                                }

                                is UiState.Error -> {
                                    EmptyData(message = stringResource(R.string.text_error_data))
                                }
                            }
                        }
                    }
                )
            }
        }
    )
}