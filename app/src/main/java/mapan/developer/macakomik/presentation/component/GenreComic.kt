package mapan.developer.macakomik.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicThumbnail
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.ui.theme.md_theme_light_primary
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 16/01/2024
 */

@Composable
fun GenreComic(
    modifier: Modifier,
    data: ComicFilter,
    onClick: () -> Unit = {}){
    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .background(
                color = md_theme_light_primary,
                shape = RoundedCornerShape(4.dp),
            )
            .noRippleClickable {
                onClick()
            },
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            text = data.name!!,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}