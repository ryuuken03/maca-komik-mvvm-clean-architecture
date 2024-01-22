package mapan.developer.macakomik.presentation.component.dialog

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.WhiteBackground

/***
 * Created By Mohammad Toriq on 13/01/2024
 */

@Composable
fun AlertDialogConfirmation(
    title: String,
    description:String,
    showDialog: MutableState<Boolean>,
    setAction: () -> Unit) {
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
                            text = title,
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

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier
                        .padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                    ){
                        Button(
                            onClick = {
                                showDialog.value = false
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f,false)
                                .padding(5.dp)
                                .height(30.dp)
                            ,
                            colors = ButtonDefaults.buttonColors(containerColor = WhiteBackground)
                        ) {
                            Text(text = "Batal", color = GrayDarker)
                        }
                        Button(
                            onClick = {
                                setAction()
                                showDialog.value = false
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f,false)
                                .padding(5.dp)
                                .height(30.dp)
                            ,
                            colors = ButtonDefaults.buttonColors(containerColor = WhiteBackground)
                        ) {
                            Text(text = "Ok", color = GrayDarker)
                        }
                    }
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