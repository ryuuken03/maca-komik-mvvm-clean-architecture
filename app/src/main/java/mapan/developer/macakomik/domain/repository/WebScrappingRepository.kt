package mapan.developer.macakomik.domain.repository

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.ComicList

/***
 * Created By Mohammad Toriq on 08/01/2024
 */
interface WebScrappingRepository {
    suspend fun getHome(url:String): Flow<ComicHome>

    suspend fun getComicList(url:String): Flow<ComicList>

    suspend fun getComicListSearch(url:String,keyword: String): Flow<ComicList>

    suspend fun getComicListProject(url:String): Flow<ComicList>

    suspend fun getGenreList(url:String): Flow<ArrayList<ComicFilter>>

    suspend fun getComicDetail(url: String): Flow<ComicDetail>

    suspend fun getComicChapter(url: String): Flow<ComicChapterPageList>
}