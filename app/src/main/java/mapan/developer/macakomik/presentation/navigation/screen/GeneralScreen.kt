package mapan.developer.macakomik.presentation.navigation.screen

import android.util.Log

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
sealed class GeneralScreen(val route: String) {
    object SplashScreen: GeneralScreen("splash_screen")

    object DetailScreen: GeneralScreen("detail_screen/{image}/{url}/{finish}"){
        fun sendData(image: String,
                     url: String,
                     finish:Boolean = false):String{
            var imageChange = "-"
            if(!image.equals("")){
                imageChange = image
            }
            return "detail_screen/$imageChange/$url/$finish"
        }
    }

    object ListScreen: GeneralScreen("list_screen/{index}/{pathUrl}"){
        fun sendData(index: Int,pathUrl:String) = "list_screen/$index/$pathUrl"
    }

    object GenreScreen: GeneralScreen("genre_screen/{index}/{isTheme}"){
        fun sendData(index: Int,isTheme:Boolean = false) = "genre_screen/$index/$isTheme"
    }

    object ReadScreen: GeneralScreen("read_screen/{image}/{url}/{urlDetail}/{fromDetail}"){
        fun sendData(image: String,
                     url: String,
                     urlDetail: String,
                     fromDetail:Boolean = false) =
            "read_screen/$image/$url/$urlDetail/$fromDetail"
    }
    object SettingScreen: GeneralScreen("setting_screen"){
        fun sendData() ="setting_screen"
    }
    object SourceScreen: GeneralScreen("source_screen"){
        fun sendData() ="source_screen"
    }

}