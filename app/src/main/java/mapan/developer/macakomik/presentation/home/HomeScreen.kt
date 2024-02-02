package mapan.developer.macakomik.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ProgressLoading
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogSource
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarHome
import mapan.developer.macakomik.presentation.home.section.HomeContent
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String,String) -> Unit,
    navigateToGenre: (Int) -> Unit,
    navigateToList: (Int,String) -> Unit,
) {
    val context = LocalContext.current
    val showDialog =  remember { mutableStateOf(false) }
    val title by viewModel.title.collectAsStateWithLifecycle()
    val canBrowse by viewModel.canBrowse.collectAsStateWithLifecycle()
    if(showDialog.value){
        AlertDialogSource(
            showDialog = showDialog,
            selectedIndex = viewModel.index,
            setAction = { index ->
                if(canBrowse){
                    viewModel.setIndexSource(index)
                }else{
                    Toast.makeText(context,"Tunggu Sebentar...",Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    Scaffold(
        topBar ={
            ToolbarHome(
                title = title,
                icon = viewModel.getIcon(),
                onChangeSource = {
                    showDialog.value = true
                })
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
                            viewModel.getBrowse()
                        }

                        is UiState.Success -> {
                            if(uiState.data == null){
                                EmptyData(message = stringResource(R.string.text_data_not_found))
                            }else{
                                viewModel.uiGenreState
                                    .collectAsState(initial = UiState.Loading())
                                    .value.let{ uiGenreState ->
                                        var genres = ArrayList<ComicFilter>()
                                        when (uiState) {
                                            is UiState.Loading -> {
                                            }
                                            is UiState.Success -> {
                                                uiGenreState.data?.forEach {
                                                    genres.add(it)
                                                }
                                            }
                                            is UiState.Error -> {
                                            }
                                        }
                                        HomeContent(
                                            modifier = Modifier,
                                            data = uiState.data,
                                            genres = genres,
                                            viewModel = viewModel,
                                            navigateToDetail = navigateToDetail,
                                            navigateToGenre = navigateToGenre,
                                            navigateToList = navigateToList
                                        )
                                    }
                            }
                        }

                        is UiState.Error -> {
                            EmptyData(message = uiState.message!!)
                        }
                    }
                }
            }
        }
    )
}