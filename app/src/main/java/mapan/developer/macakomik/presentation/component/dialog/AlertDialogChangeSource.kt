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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
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
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.presentation.component.NoRippleInteractionSource
import mapan.developer.macakomik.presentation.component.dropdown.DropdownFliter
import mapan.developer.macakomik.ui.theme.WhiteBackground
import mapan.developer.macakomik.ui.theme.md_theme_light_primary

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@ExperimentalMaterial3Api
@Composable
fun AlertDialogFilter(
    showDialog: MutableState<Boolean>,
    genres : ArrayList<ComicFilter>,
    types : ArrayList<ComicFilter>,
    orderbys : ArrayList<ComicFilter>,
    genresSelected : MutableState<List<Int>>,
    typeSelected : MutableState<List<Int>>,
    orderbySelected : MutableState<List<Int>>,
    setAction: (String,String,String) -> Unit
) {
//    val genresSelected = remember { mutableStateOf(listOf(-1)) }
//    val typeSelected = remember { mutableStateOf(listOf(0)) }
//    val orderbySelected = remember { mutableStateOf(listOf(2)) }

    Dialog(onDismissRequest = { showDialog.value = false }) {
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
                            text = "Filter "+ stringResource(id = R.string.app_name2),
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

                    Spacer(modifier = Modifier.height(15.dp))
                    var listGenre = ArrayList<String>()
                    genres.forEach{
                        listGenre.add(it.name!!)
                    }
                    var listType = ArrayList<String>()
                    types.forEach{
                        listType.add(it.name!!)
                    }
                    var listOrderBy = ArrayList<String>()
                    orderbys.forEach{
                        listOrderBy.add(it.name!!)
                    }
                    DropdownFliter(
                        title = "Jenis Komik",
                        listType,
                        indexSelected = typeSelected,
                    )
                    DropdownFliter(
                        title = "Urutkan Komik",
                        listOrderBy,
                        indexSelected = orderbySelected,
                    )
                    DropdownFliter(
                        title = "Genre Komik",
                        listGenre,
                        indexSelected = genresSelected,
                        isCheckBox = true,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Button(
                            onClick = {
                                showDialog.value = false
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults
                                .buttonColors(containerColor = md_theme_light_primary),
                            modifier = Modifier
                                .padding(5.dp),
                        ) {
                            Text(text = "Batal", color = GrayDarker)
                        }
                        Button(
                            onClick = {
                                var genre = ""
                                var i = 0
                                genresSelected.value.forEach {
                                    if(it > -1){
                                        genre+="genre%5B"+i.toString()+"%5D="+genres[it].value+"&"
                                    }
                                }
                                var type = ""
                                typeSelected.value.forEach {
                                    if(it > -1){
                                        type = types[it].value!!
                                    }
                                }
                                var orderby = ""
                                orderbySelected.value.forEach {
                                    if(it > -1){
                                        orderby = orderbys[it].value!!
                                    }
                                }
                                setAction(type, orderby, genre)
                                showDialog.value = false
                            },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults
                                .buttonColors(containerColor = md_theme_light_primary),
                            modifier = Modifier
                                    .padding(5.dp),
                        ) {
                            Text(text = "Filter", color = GrayDarker)
                        }
                    }
                }
            }
        }
    }
}
