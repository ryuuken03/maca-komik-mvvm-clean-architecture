package mapan.developer.macakomik.presentation.component.dialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.datasource.remote.model.SourceFB
import mapan.developer.macakomik.presentation.component.NoRippleInteractionSource

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@Composable
fun AlertDialogSource(
    showDialog: MutableState<Boolean>,
    selectedIndex: Int,
    sources:List<SourceFB>,
//    setAction: (Int) -> Unit) {
    setAction: (SourceFB) -> Unit) {
    val index =  remember { mutableStateOf(selectedIndex) }

    Dialog(onDismissRequest = { showDialog.value = false }) {
        val context = LocalContext.current
//        var sourceTitles = context.resources.getStringArray(R.array.source_website_title)

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sumber Website "+ stringResource(id = R.string.app_name2),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                                .clickable { showDialog.value = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    for(i in 0 .. sources.size-1){
//                        if(index.value != i){
                            var title = sources[i].title!!
                            var icon = R.drawable.ic_src_komikcast
//                            Log.d("OkCheck title",title+":"+"komikcast:"+title.equals("komikcast",true))
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
//                            when(i){
//                                0 -> { icon = R.drawable.ic_src_komikindo}
//                                1 -> { icon = R.drawable.ic_src_westmanga}
//                                2 -> { icon = R.drawable.ic_src_ngomik}
//                                3 -> { icon = R.drawable.ic_src_shinigami}
//                                4 -> { icon = R.drawable.ic_src_komikcast}
//                            }
                            OutlinedButton(
                                modifier = Modifier
                                    .padding(all = 5.dp),
                                interactionSource = remember { NoRippleInteractionSource() },
                                contentPadding = PaddingValues(),
                                border = BorderStroke(1.dp, GrayDarker),
                                shape = RoundedCornerShape(4),
                                onClick = {
                                    if(index.value == i){
                                    }else{
                                        setAction(sources[i])
                                        index.value = i
                                        showDialog.value = false
                                    }
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
                                    Text(
                                        modifier = Modifier
                                            .weight(2f,true),
                                        text = title,
                                        color = GrayDarker,
                                        textAlign = TextAlign.Start
                                    )
                                    RadioButton(
                                        modifier = Modifier
                                            .weight(1f,true),
                                        selected = if(index.value == i) true else false,
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = GrayDarker,
                                            unselectedColor = Color.Gray,
                                        ),
                                        onClick = {
                                            if(index.value == i){
                                            }else{
                                                setAction(sources[i])
                                                index.value = i
                                                showDialog.value = false
                                            }
                                        },
                                    )
                                }
                            }
                    }
                }
            }
        }
    }
}