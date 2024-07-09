package mapan.developer.macakomik.data.repository

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mapan.developer.macakomik.data.datasource.local.db.AppDatabase
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.data.datasource.local.sharepreference.AppSharePreference
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    @ApplicationContext private val mContext: Context,
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
    override suspend fun backupDataDB() : Flow<Int>{
        val dbFile = mContext.getDatabasePath(AppDatabase.DB_NAME)
        val dbWalFile = File(dbFile.path + AppDatabase.SQLITE_WALFILE_SUFFIX)
        val dbShmFile = File(dbFile.path + AppDatabase.SQLITE_SHMFILE_SUFFIX)
        var pathOut = mContext.getExternalFilesDir("MACA")
        var fileOut = File(pathOut,AppDatabase.DB_NAME+AppDatabase.DB_BACKUP_SUFFIX)
        var fileWalOut = File(fileOut.path+AppDatabase.SQLITE_WALFILE_SUFFIX)
        var fileShmOut = File(fileOut.path+AppDatabase.SQLITE_SHMFILE_SUFFIX)
        if (fileOut.exists()) fileOut.delete()
        if (fileWalOut.exists()) fileWalOut.delete()
        if (fileShmOut.exists()) fileShmOut.delete()
        checkpoint()
        var result = 0
        try {
            dbFile.copyTo(fileOut,true)
            if (dbWalFile.exists()) dbWalFile.copyTo(fileWalOut,true)
            if (dbShmFile.exists()) dbShmFile.copyTo(fileShmOut, true)
            result = 1
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e :Exception){
            e.printStackTrace()
        }
        return flowOf(result)
    }

    override suspend fun restoreDataDB(restart:Boolean) : Flow<Int>{
        var pathOut = mContext.getExternalFilesDir("MACA")
        var fileOut = File(pathOut,AppDatabase.DB_NAME+AppDatabase.DB_BACKUP_SUFFIX)
        if(!fileOut.exists()) {
            return flowOf(-1)
        }
        val dbpath = db.openHelper.readableDatabase.path
        val dbFile = File(dbpath)
        val dbWalFile = File(dbFile.path + AppDatabase.SQLITE_WALFILE_SUFFIX)
        val dbShmFile = File(dbFile.path + AppDatabase.SQLITE_SHMFILE_SUFFIX)

        var fileWalOut = File(fileOut.path+AppDatabase.SQLITE_WALFILE_SUFFIX)
        var fileShmOut = File(fileOut.path+AppDatabase.SQLITE_SHMFILE_SUFFIX)
        try {
            fileOut.copyTo(dbFile, true)
            if (fileWalOut.exists()) fileWalOut.copyTo(dbWalFile, true)
            if (fileShmOut.exists()) fileShmOut.copyTo(dbShmFile,true)
            checkpoint()
        } catch (e: IOException) {
            e.printStackTrace()
            return flowOf(0)
        } catch (e: Exception) {
            e.printStackTrace()
            return flowOf(0)
        }
        if (restart) {
            val i = mContext.packageManager.getLaunchIntentForPackage(mContext.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mContext.startActivity(i)
            System.exit(0)
        }
        return flowOf(1)
    }
    private fun checkpoint() {
        var db1 = db.openHelper.writableDatabase
        db1.query("PRAGMA wal_checkpoint(FULL);")
        db1.query("PRAGMA wal_checkpoint(TRUNCATE);")
    }

//    override suspend fun getListSourceFB(): Flow<MutableList<SourceEntity>>{
//        return flowOf(db.appDao().getListSourceFB())
//    }
//
//    override suspend fun insertSourceFB(source: SourceEntity){
//        db.appDao().insertSourceFB(source)
//    }
}