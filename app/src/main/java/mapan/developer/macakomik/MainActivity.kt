package mapan.developer.macakomik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mapan.developer.macakomik.presentation.component.SetStatusBarColor
import mapan.developer.macakomik.presentation.navigation.BottomNav
import mapan.developer.macakomik.presentation.navigation.screen.BottomBarScreen
import mapan.developer.macakomik.ui.theme.MACAKomikTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        BottomBarScreen.Setting,
    )

    BottomNav(
        modifier = modifier,
        navigationItemContentList = navigationItemContentList,
        navController = navController,
        currentDestination = currentDestination,
    )
}