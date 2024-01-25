package mapan.developer.macakomik.presentation.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ProgressLoading
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarDefault
import mapan.developer.macakomik.presentation.genre.section.GenreContent
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(
    index:Int,
    viewModel: GenreViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToList: (Int,String) -> Unit,
) {
    viewModel.setInit(index)
    Scaffold(
        topBar ={
            Surface (shadowElevation = 1.dp){

                ToolbarDefault(
                    title = "Genre " + stringArrayResource(id =
                                    R.array.source_website_title)[viewModel.index],
                    icon = viewModel.getIcon(),
                    navigateBack = navigateBack
                )
            }
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
                            if(!viewModel.isInit){
                                viewModel.resetData()
                            }
                        }

                        is UiState.Success -> {
                            if(uiState.data == null){
                                EmptyData(message = stringResource(R.string.text_data_not_found))
                            }else{
                                GenreContent(
                                    modifier = Modifier,
                                    data = uiState.data,
                                    viewModel = viewModel,
                                    navigateToList = navigateToList
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