package mapan.developer.macakomik.domain.repository

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.ComicList

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
interface ShinigamiRepository {
    suspend fun getHome(): Flow<ComicHome>

    suspend fun getComicList(page: Int): Flow<ComicList>

    suspend fun getComicListSearch(keyword: String,page: Int): Flow<ComicList>

    suspend fun getComicListProject(page: Int): Flow<ComicList>

    suspend fun getComicDetail(url: String): Flow<ComicDetail>

    suspend fun getComicChapter(url: String, urlDetail: String): Flow<ComicChapterPageList>
}