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
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/***
 * Created By Mohammad Toriq on 19/01/2024
 */
@Composable
fun InputText (
    modifier: Modifier? = null,
    textColor: Color = Color.Black,
    placeholder : String,
    currentText: MutableState<String?>,
    withTop : Boolean = true
){
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember { mutableStateOf(TextFieldValue(currentText.value?:"")) }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Gray,
        backgroundColor = Color.LightGray
    )
    CompositionLocalProvider (
        LocalTextSelectionColors provides customTextSelectionColors,
    ){
        var mod : Modifier = Modifier
            .padding(10.dp)
        if(!withTop){
            mod = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        }
        mod = mod
            .background(
                color = Color.White,
                shape = RoundedCornerShape(6.dp)
            )
        if(modifier != null){
            mod = modifier
        }
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                currentText.value = text.text
                if(newText.text.length == 0){
//                    onClear()
                    keyboardController?.hide()
                }else{
                }
            },
            textStyle = LocalTextStyle.current.copy(color = textColor),
            cursorBrush = SolidColor(textColor),
            singleLine = true,
            modifier = mod,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
//            keyboardActions = KeyboardActions(
//                onSearch = {
//                    keyboardController?.hide()
//                }
//            ),
            decorationBox = { innerTextField ->
                var modif = Modifier
                    .border(
                        width = 1.dp,
                        color = textColor,
                        shape = RoundedCornerShape(6.dp)
                    )
                Box(
                    modifier = modif
                ) {
                    var modif2 = Modifier
                        .padding(10.dp)
                    Row(
                        modifier = modif2,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 5.dp)){
                            if(text.text.isEmpty())
                                Text(text = placeholder,
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
                                    currentText.value = text.text
                                },
                                tint = textColor
                            )
                        }
                    }
                }
            }
        )
    }
}