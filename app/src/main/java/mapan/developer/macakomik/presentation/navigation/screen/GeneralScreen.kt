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

    object GenreScreen: GeneralScreen("genre_screen/{index}"){
        fun sendData(index: Int) = "genre_screen/$index"
    }

    object ReadScreen: GeneralScreen("read_screen/{title}/{image}/{chapter}/{url}/{urlDetail}/{fromDetail}"){
        fun sendData(title: String,
                     image: String,
                     chapter: String,
                     url: String,
                     urlDetail: String,
                     fromDetail:Boolean = false) =
            "read_screen/$title/$image/$chapter/$url/$urlDetail/$fromDetail"
    }

}