package mapan.developer.macakomik.data.repository

import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mapan.developer.macakomik.data.datasource.local.db.AppDatabase
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.data.datasource.local.sharepreference.AppSharePreference
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import javax.inject.Inject
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val sp: SharedPreferences
) : DatabaseRepository{

    override suspend fun getListComicSave(): Flow<MutableList<ComicSaveEntity>> {
        return flowOf(db.appDao().getListComicSave())
    }

    override suspend fun getSearchListComicSave(search: String): Flow<MutableList<ComicSaveEntity>> {
        return flowOf(db.appDao().getSearchListComicSave(search))
    }

    override suspend fun getComicSave(url: String): Flow<ComicSaveEntity> {
        return flowOf(db.appDao().getComicSave(url))
    }

    override suspend fun insertComicSave(comicSave: ComicSaveEntity){
        db.appDao().insertComicSave(comicSave)
    }

    override suspend fun deleteComicSaveUrl(url: String){
        db.appDao().deleteComicSaveUrl(url)
    }

    override suspend fun getListComicHistory(): Flow<MutableList<ComicHistoryEntity>> {
        return flowOf(db.appDao().getListComicHistory())
    }

    override suspend fun getSearchListComicHistory(search: String): Flow<MutableList<ComicHistoryEntity>> {
        return flowOf(db.appDao().getSearchListComicHistory(search))
    }

    override suspend fun getComicHistory(url: String): Flow<ComicHistoryEntity> {
        return flowOf(db.appDao().getComicHistory(url))
    }
    override suspend fun insertComicHistory(comicHistory: ComicHistoryEntity){
        db.appDao().insertComicHistory(comicHistory)
    }

    override suspend fun deleteComicHistoryUrl(url: String){
        db.appDao().deleteComicHistoryUrl(url)
    }

    override suspend fun deleteAllComicHistory(){
        db.appDao().deleteAllComicHistory()
    }

    override suspend fun updateSource(source: Int){
        var editor = sp.edit()
        editor?.putString(AppSharePreference.KEY_ID, "1")
        editor?.putInt(AppSharePreference.KEY_SOURCE_WEBSITE, source)
        editor?.commit()
    }

    override suspend fun getSource():Flow<Int>{
        return flowOf (sp.getInt(AppSharePreference.KEY_SOURCE_WEBSITE, 0))
    }
}