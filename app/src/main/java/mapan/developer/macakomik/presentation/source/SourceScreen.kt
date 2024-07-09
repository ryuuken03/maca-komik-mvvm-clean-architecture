package mapan.developer.macakomik.presentation.source

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.remote.model.SourceFB
import mapan.developer.macakomik.presentation.component.NoRippleInteractionSource
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogChangeSource
import mapan.developer.macakomik.presentation.component.dialog.AlertDialogSource
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarCenter
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarDefault
import mapan.developer.macakomik.presentation.home.HomeViewModel
import mapan.developer.macakomik.ui.theme.Gray200Transparant
import mapan.developer.macakomik.ui.theme.GrayDarker


/***
 * Created By Mohammad Toriq on 01/02/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceScreen (
    viewModel: SourceViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
){
    val sources by viewModel.sourceFBState.collectAsStateWithLifecycle()
    val showDialog =  remember { mutableStateOf(false) }
    val index =  remember { mutableStateOf(0) }
    if(showDialog.value){
        AlertDialogChangeSource(
            showDialog = showDialog,
            data = sources[index.value],
            setAction = { data ->
                viewModel.updateSource(data)
            }
        )
    }
    Scaffold(
        topBar ={
            ToolbarDefault(
                title = "Source Website",
                withAds = false,
                navigateBack = navigateBack
            )
        },

        content = {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
//                    .background(GrayDarker)
                    .padding(it)
            ) {
                Column (
                    verticalArrangement = Arrangement.Top,
                ){
                    for(i in 0 .. sources.size-1){
                        var title = sources[i].title!!
                        var icon = R.drawable.ic_src_komikcast
                        if(title.contains("komikcast",true)){
                            icon = R.drawable.ic_src_komikcast
                        }else if(title.contains("westmanga",true)){
                            icon = R.drawable.ic_src_westmanga
                        }else if(title.contains("ngomik",true)){
                            icon = R.drawable.ic_src_ngomik
                        }else if(title.contains("shinigami",true)){
                            icon = R.drawable.ic_src_shinigami
                        }else if(title.contains("komikindo",true)){
                            icon = R.drawable.ic_src_komikindo
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .padding(all = 5.dp),
                            interactionSource = remember { NoRippleInteractionSource() },
                            contentPadding = PaddingValues(),
                            border = BorderStroke(1.dp, GrayDarker),
                            shape = RoundedCornerShape(4),
                            onClick = {
                                index.value = i
                                showDialog.value = true
                            }
                        ){
                            Row (
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ){
                                Image(
                                    modifier = Modifier
                                        .weight(1f,true)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                        .width(25.dp)
                                        .height(25.dp),
                                    painter = painterResource(icon),
                                    contentDescription = "web",
                                    alignment = Alignment.CenterStart
                                )
                                Row (
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(5f,true),
                                ){
                                    Column(){
                                        Text(
                                            text = sources[i].title!!,
                                            color = GrayDarker,
                                            textAlign = TextAlign.Start
                                        )
                                        Text(
                                            text = sources[i].url!!,
                                            color = GrayDarker,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    Image(
                                        imageVector = Icons.Filled.Edit,
                                        colorFilter = ColorFilter.tint(Color.DarkGray),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

//@Preview
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun prevScreen(){
//    SourceScreen(
//        navigateBack = {
////            navController.navigateUp()
//        },)
//}