package mapan.developer.macakomik.util

import android.util.Log
import io.grpc.android.BuildConfig

//import mapan.developer.macakomik.BuildConfig
/***
 * Created By Mohammad Toriq on 20/02/2024
 */
class Constants {
   companion object{

       val IS_PRODUCTION: Boolean = getIsProduction(false)

       fun getIsProduction(status: Boolean): Boolean {
//            return false
//           Log.d("OkChek BuildConfig",BuildConfig.DEBUG.toString())
//           Log.d("OkChek BuildConfig",BuildConfig.BUILD_TYPE)
           return if (!BuildConfig.DEBUG) {
               true
           } else {
               return status
           }
       }
       fun getGenres(index :Int) : ArrayList<String>{
           when(index){
               0 -> { return ArrayList<String>()}
               1 -> { return ArrayList<String>()}
               2 -> { return ArrayList<String>()}
               3 -> { return ArrayList<String>()}
               4 -> { return getGenresKomikIndo()}
               else -> { return ArrayList<String>()}
           }
       }

       fun getGenresKomikIndo() : ArrayList<String>{
           var list = ArrayList<String>()
           list.add("Action")
           list.add("Adventure")
           list.add("Comedy")
           list.add("Crime")
           list.add("Drama")
           list.add("Fantasy")
           list.add("Harem")
           list.add("Historical")
           list.add("Horror")
           list.add("Isekai")
           list.add("Magical Girls")
           list.add("Mecha")
           list.add("Medical")
           list.add("Music")
           list.add("Mystery")
           list.add("Philosophical")
           list.add("Psychological")
           list.add("Sci-Fi")
           list.add("Slice of Life")
           list.add("Sports")
           list.add("Superhero")
           list.add("Sports")
           list.add("Thriller")
           list.add("Tragedy")
           list.add("Wuxia")

           return list
       }
       fun getThemes(index :Int) : ArrayList<String>{
           when(index){
               0 -> { return getThemesKomikIndo()}
               1 -> { return ArrayList<String>()}
               2 -> { return ArrayList<String>()}
               3 -> { return ArrayList<String>()}
               4 -> { return ArrayList<String>()}
               else -> { return ArrayList<String>()}
           }
       }

       fun getThemesKomikIndo() : ArrayList<String>{
           var list = ArrayList<String>()
           list.add("Aliens")
           list.add("Animals")
           list.add("Cooking")
           list.add("Crossdressing")
           list.add("Delinquents")
           list.add("Demons")
           list.add("Ecchi")
           list.add("Ghosts")
           list.add("Gore")
           list.add("Gyaru")
           list.add("Harem")
           list.add("Incest")
           list.add("Loli")
           list.add("Mafia")
           list.add("Magic")
           list.add("Martial Arts")
           list.add("Military")
           list.add("Monster Girls")
           list.add("Monsters")
           list.add("Music")
           list.add("Monsters")
           list.add("Ninja")
           list.add("Office Workers")
           list.add("Police")
           list.add("Post-Apocalyptic")
           list.add("Reincarnation")
           list.add("Reverse Harem")
           list.add("Samurai")
           list.add("School Life")
           list.add("Shota")
           list.add("Smut")
           list.add("Supernatural")
           list.add("Survival")
           list.add("Time Travel")
           list.add("Traditional Games")
           list.add("Vampires")
           list.add("Video Games")
           list.add("Villainess")
           list.add("Virtual Reality")
           list.add("Zombies")

           return list
       }

       fun isContent(text:String):Boolean{
           if(text.lowercase().equals("smut")){
               return true
           }else if(text.lowercase().equals("gore")){
               return true
           }else if(text.lowercase().equals("ecchi")){
               return true
           }
           return false
       }

       fun isAdult(text:String):Boolean{
           if(text.lowercase().contains("smut",false)){
               return true
           }else if(text.lowercase().contains("gore",false)){
               return true
           }else if(text.lowercase().contains("adult",false)){
               return true
           }else if(text.lowercase().contains("thriller",false)){
               return true
           }else if(text.lowercase().contains("tragedy",false)){
               return true
           }else if(text.lowercase().contains("mature",false)){
               return true
           }
           return false
       }
   }
}