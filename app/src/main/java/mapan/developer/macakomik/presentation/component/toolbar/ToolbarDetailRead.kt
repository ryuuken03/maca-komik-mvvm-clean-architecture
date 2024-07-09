package mapan.developer.macakomik.presentation.component.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.ui.theme.GrayDarker

/***
 * Created By Mohammad Toriq on 18/01/2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarDetailRead (
    title : String,
    titleFontSize : TextUnit,
    iconAction : Int,
    colorIconAction : Color,
    onClickAction : ()-> Unit,
    textPage : String?,
    textPageFontSize : TextUnit?,
    onClickPage : ()-> Unit,
    navigateBack : ()-> Unit,

){
    Surface (shadowElevation = 2.dp){
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                titleContentColor = GrayDarker,
            ),
            navigationIcon = {
                IconButton(
                    onClick = navigateBack
                ) {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        colorFilter = ColorFilter.tint(GrayDarker),
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                if(textPage!=null){
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint =  Color.Black,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .noRippleClickable {
                                onClickPage()
                            },
                    )
                    Text(
                        text = textPage,
                        fontSize = textPageFontSize!!,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                            .noRippleClickable {
                                onClickPage()
                            }
                    )
                }
                Icon(
                    painter = painterResource(iconAction),
                    contentDescription = "action",
                    tint = colorIconAction,
                    modifier = Modifier
                        .padding(5.dp)
                        .noRippleClickable {
                            onClickAction()
                        }
                )
            },
        )
    }
}