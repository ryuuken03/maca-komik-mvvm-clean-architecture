package mapan.developer.macakomik.data.datasource.remote

import mapan.developer.macakomik.data.datasource.remote.model.Browse
import mapan.developer.macakomik.data.datasource.remote.model.Comic
import mapan.developer.macakomik.data.datasource.remote.model.DetailComic
import mapan.developer.macakomik.data.datasource.remote.model.ImageList
import retrofit2.http.GET
import retrofit2.http.Query

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
interface ShinigamiApiService {
    companion object {
        const val BASE_URL = "http://154.26.133.63:8080/api/"
    }

    @GET("v1/browse")
    suspend fun home(
    ): Browse

    @GET("v1/filter/latest")
    suspend fun loadComicList(
        @Query("page") page : Int,
    ): ArrayList<Comic>

    @GET("v1/search")
    suspend fun loadComicListSearch(
        @Query("keyword") keyword : String,
        @Query("page") page : Int,
    ): ArrayList<Comic>

    @GET("v1/projects")
    suspend fun loadComicListProject(
        @Query("page") page : Int,
    ): ArrayList<Comic>

    @GET("v1/comic")
    suspend fun loadComicDetail(
        @Query("url") url : String,
    ): DetailComic

    @GET("v2/chapter")
    suspend fun loadComicChapter(
        @Query("url") url : String,
        @Query("id") id : Int = 0,
    ): ImageList
}