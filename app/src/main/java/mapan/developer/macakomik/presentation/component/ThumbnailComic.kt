package mapan.developer.macakomik.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicThumbnail
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.md_theme_light_primary

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Composable
fun ThumbnailComic(
    modifier: Modifier,
    data: ComicThumbnail,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier = modifier
            .padding(all = 3.dp),
        interactionSource = remember { NoRippleInteractionSource() },
        contentPadding = PaddingValues(),
        border = BorderStroke(0.dp, GrayDarker),
        shape = RoundedCornerShape(4),
        onClick = onClick
    ){

        val context = LocalContext.current
        var sourceImageNotFounds = context.resources
            .getStringArray(R.array.source_website_image_not_found)
        var urls = context.resources
            .getStringArray(R.array.source_website_url)
        var image = data.imgSrc
        var isEmpty = false
        if(data.imgSrc == null){
            isEmpty = true
        }else{
            if(data.imgSrc.equals("")){
                isEmpty = true
            }
        }
        if(isEmpty){
            for(i in 0 .. urls.size-1){
                if(data.url!!.contains(urls[i],true)){
                    if(sourceImageNotFounds[i].contains("http")){
                        image = sourceImageNotFounds[i]
                    }else{
                        image = urls[i]+sourceImageNotFounds[i]
                    }
                }
            }
        }
        Column(
            modifier = modifier
        ){
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale =
                    if(isEmpty)
                        ContentScale.Inside
                    else
                        ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 10.dp),
                text = data.title!!,
                fontSize = 13.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if(!data.lastChap.isNullOrBlank()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 10.dp),
                    text = data.lastChap!!,
                    fontSize = 12.sp,
                    color = Color.White,
                )
            }
        }
    }
}