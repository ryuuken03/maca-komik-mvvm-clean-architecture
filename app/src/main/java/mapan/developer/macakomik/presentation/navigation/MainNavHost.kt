package mapan.developer.macakomik.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mapan.developer.macakomik.presentation.bookmarks.BookmarksScreen
import mapan.developer.macakomik.presentation.detail.DetailScreen
import mapan.developer.macakomik.presentation.genre.GenreScreen
import mapan.developer.macakomik.presentation.history.HistoryScreen
import mapan.developer.macakomik.presentation.home.HomeScreen
import mapan.developer.macakomik.presentation.list.ListScreen
import mapan.developer.macakomik.presentation.navigation.screen.BottomBarScreen
import mapan.developer.macakomik.presentation.navigation.screen.GeneralScreen
import mapan.developer.macakomik.presentation.read.ReadScreen
import mapan.developer.macakomik.presentation.setting.SettingScreen
import mapan.developer.macakomik.presentation.source.SourceScreen

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Composable
fun MainNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(BottomBarScreen.Home.route) {
            HomeScreen (
                navigateToDetail = {image, url ->
                    navController.navigate(GeneralScreen.DetailScreen.sendData(image,url))
                },
                navigateToGenre = { index, isTheme->
                    navController.navigate(GeneralScreen.GenreScreen.sendData(index,isTheme))
                },
                navigateToList = { index, pathUrl->
                    navController.navigate(GeneralScreen.ListScreen.sendData(index,pathUrl))
                },
            )
        }
        composable(BottomBarScreen.History.route) {
            HistoryScreen (
                navigateToChapter = { image, url , urlDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData( image,url,urlDetail))
                },
            )
        }
        composable(BottomBarScreen.Bookmarks.route) {
            BookmarksScreen (
                navigateToDetail = {image, url ->
                    navController.navigate(GeneralScreen.DetailScreen.sendData(image,url))
                },
                navigateToChapter = { image, url , urlDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData( image,url,urlDetail))
                },
                navigateToSetting = {
                    navController.navigate(GeneralScreen.SettingScreen.route)
                },
            )
        }
//        composable(BottomBarScreen.Setting.route) {
        composable(GeneralScreen.SettingScreen.route) {
            SettingScreen(
                navigateSource = {  ->
                    navController.navigate(GeneralScreen.SourceScreen.route)
                },
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }

        composable(
            route = GeneralScreen.DetailScreen.route,
        ) {
            var image = it.arguments?.getString("image") ?: ""
            var url = it.arguments?.getString("url") ?: ""
            DetailScreen(
                image = image,
                url = url,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToChapter = { image, url , urlDetail, fromDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData( image,url,urlDetail,fromDetail))
                },
            )
        }
        composable(
            route = GeneralScreen.ListScreen.route,
        ) {
            var index = it.arguments?.getString("index") ?: "0"
            var pathUrl = it.arguments?.getString("pathUrl") ?: "-"
            ListScreen(
                index = index.toInt(),
                pathUrl = pathUrl,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToDetail = {image, url ->
                    navController.navigate(GeneralScreen.DetailScreen.sendData(image,url))
                },
            )
        }
        composable(
            route = GeneralScreen.GenreScreen.route,
        ) {
            var index = it.arguments?.getString("index") ?: "0"
            var isTheme = it.arguments?.getString("isTheme") ?: "false"
            GenreScreen(
                index = index.toInt(),
                isTheme = isTheme.toBoolean(),
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToList = { index, pathUrl->
                    navController.navigate(GeneralScreen.ListScreen.sendData(index,pathUrl))
                },
            )
        }
        composable(
            route = GeneralScreen.ReadScreen.route,
        ) {
            var image = it.arguments?.getString("image") ?: ""
            var url = it.arguments?.getString("url") ?: ""
            var urlDetail = it.arguments?.getString("urlDetail") ?: ""
            var fromDetail = it.arguments?.getString("fromDetail") ?: "false"
            ReadScreen(
                image = image,
                url = url,
                urlDetail = urlDetail,
                fromDetail = fromDetail.toBoolean(),
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToDetail = { image, url, finish ->
                    navController.navigate(GeneralScreen.DetailScreen.sendData(image,url)){
                        if(finish){
                            navController.popBackStack()
                        }
                    }
                },
            )
        }

        composable(GeneralScreen.SourceScreen.route) {
            SourceScreen(
                navigateBack = {
                    navController.navigateUp()
                },)
        }
    }
}