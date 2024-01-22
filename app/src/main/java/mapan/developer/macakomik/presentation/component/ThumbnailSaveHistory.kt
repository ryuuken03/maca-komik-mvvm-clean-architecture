package mapan.developer.macakomik.presentation.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.noRippleClickable

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@Composable
fun ThumbnailSaveHistory(
    modifier: Modifier,
    save: ComicSaveEntity?,
    history: ComicHistoryEntity?,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    var image :String? = null
    var title = ""
    var chapter : String? = null
    var genre = ""
    var type = ""
    var url = ""
    if(save != null){
        image = save.imgSrc
        title = save.title!!
        chapter = save.chapter
        genre = save.genre!!
        type = save.type!!
        url = save.urlDetail!!
    }
    if(history != null){
        image = history.imgSrc
        title = history.title!!
        chapter = history.chapter
        genre = history.genre!!
        type = history.type!!
        url = history.urlDetail!!
    }
    val context = LocalContext.current
    var sourceImageNotFounds = context.resources
        .getStringArray(R.array.source_website_image_not_found)
    var urls = context.resources
        .getStringArray(R.array.source_website_url)
    var isEmpty = false
    if(image == null){
        isEmpty = true
    }else{
        if(image.equals("")){
            isEmpty = true
        }
    }
    if(isEmpty){
        for(i in 0 .. urls.size-1){
            if(url.contains(urls[i],true)){
                image = sourceImageNotFounds[i]
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .noRippleClickable(onClick),
        ){
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale =
                    if(isEmpty)
                        ContentScale.Inside
                    else
                        ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
            )
            Column (
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 0.dp)
                    .fillMaxWidth(),
            ){
                Text(
                    modifier = Modifier,
                    text = title,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 3.dp),
                    text = genre,
                    fontSize = 12.sp,
                    color = Color.White,
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 3.dp),
                    text = type,
                    fontSize = 12.sp,
                    color = Color.White,
                )
                if(chapter!=null){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 10.dp),
                        text = chapter,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                }
                var titles = stringArrayResource(id = R.array.source_website_title)
                var title = ""
                titles.forEach {
                    if(url.contains(it,true)){
                        title = it
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 3.dp),
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            }
        }
        if(save !=null){
            Icon(
                painter = painterResource(R.drawable.baseline_delete_24),
                contentDescription = "delete",
                tint = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .noRippleClickable(onDelete)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}