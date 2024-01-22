package mapan.developer.macakomik.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mapan.developer.macakomik.R
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 18/01/2024
 */
@Composable
fun OutlinedButtonPrimary(
    title : String,
    fontSize : TextUnit,
    onClick : () -> Unit,
){
    OutlinedButton(
        modifier = Modifier
            .padding(5.dp),
        shape = RoundedCornerShape(10),
        border = BorderStroke(1.dp, md_theme_light_primary),
        onClick = onClick,
        contentPadding = PaddingValues()
    ) {
        Text(
            modifier = Modifier
                .padding(15.dp),
            text = title,
            fontSize = fontSize,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}