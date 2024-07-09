package mapan.developer.macakomik.presentation.component.dropdown

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import mapan.developer.macakomik.presentation.component.noRippleClickable

/***
 * Created By Mohammad Toriq on 13/02/2024
 */

fun getSelectedText(
    title:String,
    indexSelected : List<Int>,
    options : List<String>
) : String{
    var text = ""
    var count = 0
    var diff = 0
    indexSelected.forEach {
        if(it > -1){
            if(count < 2) {
                if (!text.equals("")) {
                    text += ", "
                }
                text += options[it].replaceFirstChar(Char::titlecase)
                count++
            }else{
                diff++
            }
        }
    }
    if(text.equals("")){
        text = "Pilih "+title
    }else{
        if(diff > 0){
            text += ", +${diff} Lainnya"
        }
    }
    return text
}
@ExperimentalMaterial3Api
@Composable
fun DropdownFliter(
    title : String?,
    options : List<String>,
    isCheckBox : Boolean = false,
    indexSelected : MutableState<List<Int>>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember {
        mutableStateOf(
            getSelectedText(
                title = title!!,
                indexSelected = indexSelected.value,
                options = options)
        )
    }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    if(title !=null){
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = selectedOptionText,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .background(
                    color = Color.White,
                )
                .noRippleClickable {

                }
            ,
            interactionSource = interactionSource,
            readOnly = true,
        ){ innerTextField ->
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
                    innerTextField.invoke()
                    Icon(
                        icon,
                        contentDescription = "",
                    )
                }
            }
        }
        DropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(),
            expanded = expanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed  {index, select ->
                var checked by remember { mutableStateOf(false) }
                indexSelected.value.forEach {
                    if(it > -1){
                        if(it == index){
                            checked = true
                        }
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        if(isCheckBox){
                            checked = !checked

                            var list = ArrayList<Int>()
                            indexSelected.value.forEach {
                                if(it > -1){
                                    list.add(it)
                                }
                            }
                            if(checked){
                                Log.d("OkCheck","DF : add index :$index -> $select")
                                list.add(index)
                            }else{
                                Log.d("OkCheck","DF : remove index :$index -> $select")
                                list.remove(index)
                            }
                            if(list.isEmpty()){
                                list.add(-1)
                            }
                            indexSelected.value = list.toList()
                        }else{
                            indexSelected.value = listOf(index)
                            expanded = false
                        }
                        selectedOptionText =
                            getSelectedText(
                                title = title!!,
                                indexSelected = indexSelected.value,
                                options = options)
                    },
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(text = select.replaceFirstChar(Char::titlecase))
                            if(isCheckBox){
                                Checkbox(
                                    checked = checked ,
                                    onCheckedChange = {
                                        checked = it
                                        if(isCheckBox){
                                            var list = ArrayList<Int>()
                                            indexSelected.value.forEach {
                                                if(it > -1){
                                                    list.add(it)
                                                }
                                            }
                                            if(checked){
                                                Log.d("OkCheck","DF : add index :$index -> $select")
                                                list.add(index)
                                            }else{
                                                Log.d("OkCheck","DF : remove index :$index -> $select")
                                                list.remove(index)
                                            }
                                            if(list.isEmpty()){
                                                list.add(-1)
                                            }
                                            indexSelected.value = list.toList()
                                        }else{
                                            indexSelected.value = listOf(index)
                                            expanded = false
                                        }
                                        selectedOptionText =
                                            getSelectedText(
                                                title = title!!,
                                                indexSelected = indexSelected.value,
                                                options = options)
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}