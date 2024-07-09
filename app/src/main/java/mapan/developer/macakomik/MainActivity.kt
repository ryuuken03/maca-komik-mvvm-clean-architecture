package mapan.developer.macakomik

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.zhkrb.cloudflare_scrape_webview.Cloudflare
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mapan.developer.macakomik.presentation.component.SetStatusBarColor
import mapan.developer.macakomik.presentation.home.HomeViewModel
import mapan.developer.macakomik.presentation.navigation.BottomNav
import mapan.developer.macakomik.presentation.navigation.screen.BottomBarScreen
import mapan.developer.macakomik.ui.theme.GrayDarker
import mapan.developer.macakomik.ui.theme.MACAKomikTheme
import mapan.developer.macakomik.ui.theme.md_theme_light_primary

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var keep = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("260671CC026401AD380FF05EFD001A5E")).build()
        )
        installSplashScreen().setKeepOnScreenCondition{
            keep
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            keep = false
        }

        setContent {
            MACAKomikTheme {
                // A surface container using the 'background' color from the theme
                SetStatusBarColor(color = MaterialTheme.colorScheme.primary)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigationItemContentList = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.History,
        BottomBarScreen.Bookmarks,
//        BottomBarScreen.Setting,
    )

    BottomNav(
        modifier = modifier,
        navigationItemContentList = navigationItemContentList,
        navController = navController,
        currentDestination = currentDestination,
    )
}