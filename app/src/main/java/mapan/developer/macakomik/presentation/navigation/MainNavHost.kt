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
                navigateToGenre = { index->
                    navController.navigate(GeneralScreen.GenreScreen.sendData(index))
                },
                navigateToList = { index, pathUrl->
                    navController.navigate(GeneralScreen.ListScreen.sendData(index,pathUrl))
                },
            )
        }
        composable(BottomBarScreen.History.route) {
            HistoryScreen (
                navigateToChapter = { title, image, chapter, url , urlDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData(title, image, chapter,url,urlDetail))
                },
            )
        }
        composable(BottomBarScreen.Bookmarks.route) {
            BookmarksScreen (
                navigateToDetail = {image, url ->
                    navController.navigate(GeneralScreen.DetailScreen.sendData(image,url))
                },
                navigateToChapter = { title, image, chapter, url , urlDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData(title, image, chapter,url,urlDetail))
                },
            )
        }
        composable(BottomBarScreen.Setting.route) {
            SettingScreen()
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
                navigateToChapter = { title, image, chapter, url , urlDetail, fromDetail->
                    navController.navigate(GeneralScreen.ReadScreen.sendData(title, image, chapter,url,urlDetail,fromDetail))
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
            GenreScreen(
                index = index.toInt(),
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
            var title = it.arguments?.getString("title") ?: ""
            var chapter = it.arguments?.getString("chapter") ?: ""
            var image = it.arguments?.getString("image") ?: ""
            var url = it.arguments?.getString("url") ?: ""
            var urlDetail = it.arguments?.getString("urlDetail") ?: ""
            var fromDetail = it.arguments?.getString("fromDetail") ?: "false"
            ReadScreen(
                title = title,
                image = image,
                chapter = chapter,
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
    }
}