package mapan.developer.macakomik.presentation.component.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mapan.developer.macakomik.R
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 18/01/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarDefault(
    title:String,
    icon : Int,
    navigateBack :()-> Unit,
){
    Surface (shadowElevation = 1.dp){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                titleContentColor = GrayDarker,
            ),
            actions = {
                Image(
                    painter = painterResource(icon),
                    contentDescription = "web",
                    modifier = Modifier
                        .padding(5.dp)
                        .width(25.dp)
                        .height(25.dp)
                        .noRippleClickable {

                        }
                )
            },
            navigationIcon = {
                Image(
                    imageVector = Icons.Filled.ArrowBack,
                    colorFilter = ColorFilter.tint(GrayDarker),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(5.dp)
                        .noRippleClickable(navigateBack)
                )
            },
        )
    }
}