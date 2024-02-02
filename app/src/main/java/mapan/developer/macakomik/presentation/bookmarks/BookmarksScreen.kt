package mapan.developer.macakomik.presentation.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.presentation.bookmarks.section.BookmarksContent
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ProgressLoading
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarCenter
import mapan.developer.macakomik.presentation.history.HistoryViewModel
import mapan.developer.macakomik.presentation.history.section.HistoryContent
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 12/01/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen (
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigateToDetail: (String,String) -> Unit,
    navigateToChapter: (String,String,String,String,String) -> Unit,
) {
    Scaffold(
        topBar ={
            ToolbarCenter(
                title = "Daftar Ditandai",
                fontSize = 16.sp
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
                viewModel.uiState.collectAsState(initial = UiState.Loading()).value.let { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            ProgressLoading()
                            viewModel.getList()
                        }

                        is UiState.Success -> {
                            if(uiState.data == null){
                                EmptyData(message = stringResource(R.string.text_data_not_found))
                            }else{
                                BookmarksContent(
                                    modifier = Modifier,
                                    list = uiState.data,
                                    viewModel = viewModel,
                                    navigateToDetail = navigateToDetail,
                                    navigateToChapter = navigateToChapter,
                                )
                            }
                        }

                        is UiState.Error -> {
                            EmptyData(message = stringResource(R.string.text_error_data))
                        }
                    }
                }
            }
        }
    )
}