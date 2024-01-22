package mapan.developer.macakomik.presentation.detail.section

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.noRippleClickable
import mapan.developer.macakomik.presentation.component.EmptyData
import mapan.developer.macakomik.presentation.detail.DetailViewModel
import mapan.developer.macakomik.ui.theme.Gray200
import mapan.developer.macakomik.ui.theme.Gray200Transparant
import mapan.developer.macakomik.ui.theme.GrayDarker
import java.net.URLEncoder

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
@Composable
fun DetailContent(
    modifier: Modifier,
    data: ComicDetail,
    urlDetail: String,
    type : Int,
    viewModel: DetailViewModel,
    navigateToChapter: (String,String,String,String,String,Boolean) -> Unit,
) {
    val showSynopsis =  remember { mutableStateOf(false) }
    val showChapter =  remember { mutableStateOf(true) }

    val context = LocalContext.current
    var sourceImageNotFounds = context.resources
        .getStringArray(R.array.source_website_image_not_found)
    var urls = context.resources
        .getStringArray(R.array.source_website_url)
    var isEmpty = false
    var image = data.imgSrc
    if(data.imgSrc.isNullOrBlank() == null){
        isEmpty = true
    }
    if(isEmpty){
        for(i in 0 .. urls.size-1){
            if(urlDetail.contains(urls[i],true)){
                image = sourceImageNotFounds[i]
            }
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
            content = {
                if (data != null) {
                    item{
                        Row (
                            modifier = Modifier.padding(10.dp)
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
                                    .width(140.dp)
                                    .height(200.dp)
                            )
                            Column (
                                modifier = Modifier
                                    .padding(horizontal = 5.dp, vertical = 0.dp)
                                    .fillMaxWidth()
                            ){
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight(),
                                    text = data.title!!,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 5
                                )
                                Spacer(modifier = Modifier.height(25.dp))
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(horizontal = 0.dp, vertical = 3.dp),
                                    text = data.genre!!,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                )
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(horizontal = 0.dp, vertical = 3.dp),
                                    text = data.type!!,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                )
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(horizontal = 0.dp, vertical = 3.dp),
                                    text = data.release!!,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp))
                    }
                    item {
                        if(data.synopsis!=null){
                            if(!data.synopsis.equals("")){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                        .noRippleClickable {
                                            showSynopsis.value = !showSynopsis.value
                                        },
                                ){
                                    Text(
                                        text = "Sinopsis",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                    )
                                    Image(
                                        imageVector = if(showSynopsis.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                        colorFilter = ColorFilter.tint(Color.White),
                                        contentDescription = null
                                    )
                                }
                                Divider(
                                    color = Gray200Transparant,
                                    thickness = 0.dp,
                                    modifier = Modifier
                                        .padding(horizontal = 0.dp, vertical = 5.dp))
                                if(showSynopsis.value){
                                    Text(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .padding(horizontal =10.dp, vertical = 5.dp),
                                        text = data.synopsis!!,
                                        fontSize = 13.sp,
                                        color = Color.White,
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .padding(horizontal = 0.dp, vertical = 5.dp))
                            }
                        }
                    }
                    item{
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .noRippleClickable {
                                    showChapter.value = !showChapter.value
                                },
                        ){
                            Text(
                                text = "Semua Chapter",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            )
                            Image(
                                imageVector = if(showChapter.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = null
                            )
                        }
                        Divider(
                            color = Gray200Transparant,
                            thickness = 0.dp,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp))
                    }

                    if(showChapter.value){
                        items(count = data.list!!.size){ index ->
                            var chapter = data.list!![index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 20.dp)
                                    .noRippleClickable {
                                        var url = URLEncoder.encode(chapter.url!!, "UTF-8")
                                        var urlDetailChange = URLEncoder.encode(urlDetail, "UTF-8")
                                        var imageChange = "-"
                                        if(data.imgSrc!=null){
                                            imageChange = URLEncoder.encode(data.imgSrc, "UTF-8")
                                        }
                                        navigateToChapter(data.title!!,imageChange,chapter.name!!,url,urlDetailChange,true)
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = chapter.name!!,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                )
                                Text(
                                    text = chapter.time!!,
                                    fontSize = 12.sp,
                                    color = Gray200,
                                )
                            }
                            Divider(
                                color = Gray200Transparant,
                                thickness = 0.dp,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 5.dp))
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp))
                    }
                }else{
                    item{
                        EmptyData(stringResource(R.string.text_data_not_found))
                    }
                }
            }
    )

}