package mapan.developer.macakomik.domain.repository

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
interface DatabaseRepository {
    suspend fun getListComicSave(): Flow<MutableList<ComicSaveEntity>>
    suspend fun getSearchListComicSave(search : String): Flow<MutableList<ComicSaveEntity>>
    suspend fun getComicSave(url : String): Flow<ComicSaveEntity>
    suspend fun insertComicSave(comicHistory: ComicSaveEntity)
    suspend fun deleteComicSaveUrl(url: String)
    suspend fun getListComicHistory(): Flow<MutableList<ComicHistoryEntity>>
    suspend fun getSearchListComicHistory(search : String): Flow<MutableList<ComicHistoryEntity>>
    suspend fun getComicHistory(url : String): Flow<ComicHistoryEntity>
    suspend fun insertComicHistory(comicHistory: ComicHistoryEntity)
    suspend fun deleteComicHistoryUrl(url: String)
    suspend fun deleteAllComicHistory()
    suspend fun updateSource(index: Int)
    suspend fun getSource() : Flow<Int>
    suspend fun backupDataDB(): Flow<Int>
    suspend fun restoreDataDB(restart:Boolean) : Flow<Int>
//    suspend fun getListSourceFB() : Flow<MutableList<SourceEntity>>
//    suspend fun insertSourceFB(source: SourceEntity)
}