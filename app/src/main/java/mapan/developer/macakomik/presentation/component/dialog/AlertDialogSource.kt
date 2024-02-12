package mapan.developer.macakomik.presentation.component.dialog
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
import mapan.developer.macakomik.presentation.component.NoRippleInteractionSource

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@Composable
fun AlertDialogSource(
    showDialog: MutableState<Boolean>,
    selectedIndex: Int,
    setAction: (Int) -> Unit) {
    val index =  remember { mutableStateOf(selectedIndex) }

    Dialog(onDismissRequest = { showDialog.value = false }) {
        val context = LocalContext.current
        var sourceTitles = context.resources.getStringArray(R.array.source_website_title)

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
                    for(i in 0 .. sourceTitles.size-1){
//                        if(index.value != i){
                            var icon = R.drawable.ic_src_komikcast
                            when(i){
                                0 -> { icon = R.drawable.ic_src_komikcast}
                                1 -> { icon = R.drawable.ic_src_westmanga}
                                2 -> { icon = R.drawable.ic_src_ngomik}
                                3 -> { icon = R.drawable.ic_src_shinigami}
                            }
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
                                        setAction(i)
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
                                        text = sourceTitles[i],
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
                                                setAction(i)
                                                index.value = i
                                                showDialog.value = false
                                            }
                                        },
                                    )
                                }
//                            }
                        }
                    }
                }
            }
        }
    }
}