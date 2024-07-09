package mapan.developer.macakomik.presentation.setting

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mapan.developer.macakomik.R
import mapan.developer.macakomik.presentation.component.noRippleClickable
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarCenter
import mapan.developer.macakomik.presentation.component.toolbar.ToolbarDefault
import mapan.developer.macakomik.ui.theme.Gray200Transparant
import mapan.developer.macakomik.ui.theme.GrayDarker


/***
 * Created By Mohammad Toriq on 01/02/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen (
    viewModel: SettingViewModel = hiltViewModel(),
    navigateSource: () -> Unit,
    navigateBack: () -> Unit,
){
    val context = LocalContext.current
    val versionName by viewModel.versionName.collectAsStateWithLifecycle()
    val stillBackup by viewModel.stillBackup.collectAsStateWithLifecycle()
    val stillRestore by viewModel.stillRestore.collectAsStateWithLifecycle()
    val messageToast by viewModel.messageToast.collectAsStateWithLifecycle()
    if(!messageToast.equals("")){
        Toast.makeText(context,messageToast, Toast.LENGTH_SHORT).show()
        viewModel.resetMessageToast()
    }
    Scaffold(
        topBar ={
//            ToolbarCenter(
//                title = "Pengaturan",
//                fontSize = 16.sp,
//                withAds = false
//            )
            ToolbarDefault(
                title = "Pengaturan",
                withAds = false,
                navigateBack = navigateBack
            )
        },
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayDarker)
                    .padding(it)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 5.dp),
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp,
                                start = 10.dp,
                                bottom = 10.dp,
                                end = 10.dp
                            ),
                        text = "Akun",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                    )
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))

//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .padding(all = 5.dp)
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ){
//                        if(user.avatarUrl!=null){
//                            AsyncImage(
//                                model = user.avatarUrl!!,
//                                contentDescription = null,
//                                contentScale = ContentScale.Crop,
//                                alignment = Alignment.Center,
//                                modifier = Modifier
//                                    .padding(5.dp)
//                                    .clip(CircleShape)
//                                    .size(35.dp)
//                            )
//                        }else{
//                            var color = Color.LightGray
//                            Text(
//                                modifier = Modifier
//                                    .weight(1f,false)
//                                    .padding(15.dp)
//                                    .drawBehind {
//                                        drawCircle(
//                                            color = color,
//                                            radius = this.size.maxDimension
//                                        )
//                                    },
//                                text = "A1",
//                                text = user.username!!.substring(0,1),
//                            )
//                        }
//                        Column (
//                            modifier = Modifier.weight(2f)
//                        ){
//                            Text(
//                                modifier = Modifier
//                                    .padding(
//                                        top = 5.dp,
//                                        start = 5.dp,
//                                        end = 5.dp
//                                    ),
////                            text = user.username!!,
//                                text = "Akun Name",
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White,
//                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(
//                                        top = 5.dp,
//                                        start = 5.dp,
//                                        end = 5.dp
//                                    ),
//                            text = user.email!!,
//                                text = "akun@mail.com",
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White,
//                            )
//                        }
//                        Row(
//                            modifier = Modifier
//                                .weight(1f,false)
//                                .padding(
//                                    top = 5.dp,
//                                    start = 5.dp,
//                                    bottom = 5.dp,
//                                    end = 5.dp
//                                )
//                                .background(
//                                    color = Color.Red,
//                                    shape = RoundedCornerShape(4.dp),
//                                )
//                                .noRippleClickable {
//
//                                },
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
//                        ){
//                            Text(
//                                modifier = Modifier
//                                    .padding(
//                                        top = 10.dp,
//                                        start = 10.dp,
//                                        bottom = 10.dp,
//                                        end = 10.dp
//                                    ),
//                                text = "Logout",
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.SemiBold,
//                                color = Color.White,
//                            )
//                        }
//                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp,
                                start = 15.dp,
                                bottom = 5.dp,
                                end = 15.dp
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .noRippleClickable {

                            },
                        contentAlignment = Alignment.Center
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(
                                        top = 10.dp,
                                        start = 10.dp,
                                        bottom = 10.dp,
                                        end = 5.dp
                                    )
                                    .clip(CircleShape)
                                    .size(20.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(
                                        top = 10.dp,
                                        start = 5.dp,
                                        bottom = 10.dp,
                                        end = 10.dp
                                    ),
                                text = "Login/Daftar",
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                            )
                        }
                    }
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 10.dp,
                                start = 10.dp,
                                bottom = 10.dp,
                                end = 10.dp
                            ),
                        text = "Data",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                    )
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 10.dp,
                                start = 20.dp,
                                bottom = 10.dp,
                                end = 20.dp
                            ).noRippleClickable {
                                if(!stillBackup){
//                                    viewModel.backupOffline()
                                }else{
                                    var message = context.getString(R.string.text_please_waiting)
                                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                                }
                            },
                        text = "Backup Data",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 10.dp,
                                start = 20.dp,
                                bottom = 10.dp,
                                end = 20.dp
                            ).noRippleClickable {
                                if(!stillRestore){
//                                    viewModel.restoreOffline()
                                }else{
                                    var message = context.getString(R.string.text_please_waiting)
                                    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
                                }
                            },
                        text = "Restore Data",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 10.dp,
                                start = 10.dp,
                                bottom = 10.dp,
                                end = 10.dp
                            ),
                        text = "App",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                    )
                    Divider(
                        color = Gray200Transparant,
                        thickness = 0.dp,
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 5.dp))
                    if(!versionName.equals("")){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 10.dp,
                                    start = 20.dp,
                                    bottom = 10.dp,
                                    end = 20.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Version",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.weight(1f,false)
                            )
                            Text(
                                text = versionName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.weight(1f,false)
                            )
                        }
                        Divider(
                            color = Gray200Transparant,
                            thickness = 0.dp,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp))
                    }
//                    if(!versionName.equals("")){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 10.dp,
                                    start = 20.dp,
                                    bottom = 10.dp,
                                    end = 20.dp
                                )
                                .noRippleClickable {
                                   navigateSource()
                                },
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Source Website",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.weight(1f,false)
                            )
                        }
                        Divider(
                            color = Gray200Transparant,
                            thickness = 0.dp,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 5.dp))
                    }
//                }
            }
        }
    )
}