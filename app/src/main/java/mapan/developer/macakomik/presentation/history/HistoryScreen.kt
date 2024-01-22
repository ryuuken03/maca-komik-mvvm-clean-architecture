package mapan.developer.macakomik.presentation.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.UiState
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.component.ProgressLoading
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogConfirmation
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarCenter
import mapan.developer.macakomik.presentation.history.section.HistoryContent
import mapan.developer.macakomik.presentation.home.section.HomeContent
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    navigateToChapter: (String,String,String,String,String) -> Unit,
) {
    val showDialog =  remember { mutableStateOf(false) }
    val hideAction =  remember { mutableStateOf(false) }
    if(showDialog.value){
        AlertDialogConfirmation(
            title = "Hapus semua riwayat",
            description = "Apakah anda ingin menghapus semua riwayat komik ?",
            showDialog = showDialog,
            setAction = {
                viewModel.deleteAllHistory()
            }
        )
    }
    Scaffold(
        topBar ={
            ToolbarCenter(
                title = "Daftar Riwayat",
                fontSize = 16.sp,
                actionIcons = if(hideAction.value) listOf() else listOf(R.drawable.baseline_auto_delete_24),
                actionClicks =
                    if(hideAction.value)
                        listOf()
                    else
                        listOf({ showDialog.value = true })
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
                            viewModel.getData()
                        }

                        is UiState.Success -> {
                            viewModel.stillLoad = false
                            if(uiState.data == null){
                                hideAction.value = true
                                EmptyData(message = stringResource(R.string.text_data_not_found))
                            }else{
                                if(uiState.data.isEmpty()){
                                    hideAction.value = true
                                }
                                HistoryContent(
                                    modifier = Modifier,
                                    list = uiState.data,
                                    viewModel = viewModel,
                                    navigateToChapter = navigateToChapter
                                )
                            }
                        }

                        is UiState.Error -> {
                            viewModel.stillLoad = false
                            hideAction.value = true
                            EmptyData(message = stringResource(R.string.text_error_data))
                        }
                    }
                }
            }
        }
    )
}