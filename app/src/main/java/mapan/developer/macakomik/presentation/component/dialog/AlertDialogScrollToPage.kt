package mapan.developer.macakomik.presentation.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@Composable
fun AlertDialogScrollToPage(
    currentPage: String,
    maxPage: String,
    showDialog: MutableState<Boolean>,
    setAction: (String) -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current
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
                            text = "Lompat Ke",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
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

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Halaman sekarang : "+currentPage+"/"+maxPage,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    var search by remember { mutableStateOf(TextFieldValue("")) }
                    val customTextSelectionColors = TextSelectionColors(
                        handleColor = Color.Gray,
                        backgroundColor = Color.LightGray
                    )
                    CompositionLocalProvider (
                        LocalTextSelectionColors provides customTextSelectionColors,
                    ){
                        BasicTextField(
                            value = search,
                            onValueChange = { newText ->
                                try {
                                    if(newText.text.toInt() > maxPage.toInt()){
                                        var max by mutableStateOf(TextFieldValue(maxPage))
                                        search = max
                                    }else if(newText.text.toInt() < 1){
                                        var max by mutableStateOf(TextFieldValue(""))
                                        search = max
                                    }else{
                                        search = newText
                                    }
                                }catch (e:Exception){
                                    var max by mutableStateOf(TextFieldValue(""))
                                    search = max
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
//                                .padding(5.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(6.dp)
                                ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    setAction(search.text)
                                    showDialog.value = false
                                    keyboardController?.hide()
                                }
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(6.dp)
                                        )

                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(
                                            modifier = Modifier,
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "search",
                                            tint =  Color.Black
                                        )
                                        Box(modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 5.dp)){
                                            if(search.text.isEmpty()){
                                                    Text(text = "Halaman Ke",
                                                        color = Color.Gray,
                                                        fontSize = 14.sp,)
                                            }
                                            innerTextField()
                                        }
                                        if(search.text.length > 0){
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "closeIcon",
                                                modifier = Modifier
                                                    .clickable {
                                                        var reset by mutableStateOf(TextFieldValue(""))
                                                        search = reset
                                                        keyboardController?.hide()
                                                   },
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun prevAlert(){
//    val showDialog =  remember { mutableStateOf(false) }
//    AlertDialogConfirmation(
//        title = "Hapus Komik yang ditandai",
//        description = "Apakah anda ingin menghapus komik ditandai ini?",
//        showDialog = showDialog,
//        setAction = {
//        }
//    )
//}