package mapan.developer.macakomik.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mapan.developer.macakomik.R
import mapan.developer.macakomik.ui.theme.md_theme_light_primary

/***
 * Created By Mohammad Toriq on 6/30/2024
 */
@Composable
fun ImageError (
    disableClick : Boolean,
    onClick: () -> Unit,
){
    var mod = Modifier.fillMaxWidth()
        .padding(horizontal = 5.dp, vertical = 10.dp)
//    if(!disableClick){
//        mod = mod.clickable {
//            onClick()
//        }
//    }
    Column(
        modifier = mod.height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            painter = painterResource(
                id = R.drawable.baseline_hide_image_24),
            contentDescription = null,
            tint = Color.White)
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp),
            text = "Image is not found",
            fontSize = 14.sp,
            color = Color.White
        )
        if(!disableClick){
            OutlinedButton(
                modifier = Modifier
                    .padding(10.dp),
                shape = RoundedCornerShape(10),
                border = BorderStroke(1.dp, Color.White),
                onClick = onClick,
                contentPadding = PaddingValues()
            ){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 0.dp),
                        text = "Retry",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,)

                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null, // contentDescription is mandatory for accessibility
                        tint = Color.White, // Optionally specify icon tint color
                        modifier = Modifier.width(20.dp).height(20.dp)
//                modifier = Modifier.padding(end = 8.dp) // Add padding between icon and text
                    )
                }
            }
        }
    }
}