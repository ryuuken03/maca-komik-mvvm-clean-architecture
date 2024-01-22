package mapan.developer.macakomik.presentation.component.inputtextfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/***
 * Created By Mohammad Toriq on 19/01/2024
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InputTextSearch (
    search : String?,
    onClear :()-> Unit,
    onSearch :(String)-> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf(TextFieldValue(search?:"")) }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Gray,
        backgroundColor = Color.LightGray
    )
    CompositionLocalProvider (
        LocalTextSelectionColors provides customTextSelectionColors,
    ){
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                if(newText.text.length == 0){
                    onClear()
                    keyboardController?.hide()
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(6.dp)
                ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text.text)
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
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint =  Color.Black
                        )
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 5.dp)){
                            if(text.text.isEmpty())
                                Text(text = "Pencarian",
                                    color = Color.Gray,
                                    fontSize = 14.sp,)
                            innerTextField()
                        }
                        if(text.text.length > 0){
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "closeIcon",
                                modifier = Modifier.clickable {
                                    var reset by mutableStateOf(TextFieldValue(""))
                                    text = reset
                                    onClear()
                                },
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        )
    }
}