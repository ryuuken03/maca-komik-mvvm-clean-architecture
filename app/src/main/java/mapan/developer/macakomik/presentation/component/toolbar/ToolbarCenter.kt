package mapan.developer.macakomik.presentation.component.toolbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mapan.developer.macakomik.R
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 18/01/2024
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ToolbarCenter(
    title : String,
    fontSize : TextUnit,
    actionIcons :List<Int> = listOf(),
    actionClicks :List<()->Unit> = listOf(),
){
    Surface (shadowElevation = 1.dp){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                titleContentColor = GrayDarker,
            ),
            actions = {
                if(actionIcons.size > 0){
                    for(i in 0 .. actionIcons.size-1) {
                        Icon(
                            painter = painterResource(actionIcons[i]),
                            contentDescription = null,
                            tint = GrayDarker,
                            modifier = Modifier
                                .padding(5.dp)
                                .noRippleClickable {
                                    actionClicks[i]()
                                }
                        )
                    }
                }
            },
        )
    }
}