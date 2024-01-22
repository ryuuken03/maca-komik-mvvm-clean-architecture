package mapan.developer.macakomik.data.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Dao
interface AppDao {

    @Query("SELECT * FROM ComicSaveEntity ORDER By id DESC")
    fun getListComicSave(): MutableList<ComicSaveEntity>

    @Query("SELECT * FROM ComicSaveEntity WHERE urlDetail LIKE '%' || :url || '%'")
    fun getComicSave(url : String): ComicSaveEntity

    @Query("SELECT * FROM ComicSaveEntity WHERE title LIKE '%' || :search || '%'")
    fun getSearchListComicSave(search : String): MutableList<ComicSaveEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComicSave(comicSave: ComicSaveEntity)

    @Query("DELETE FROM ComicSaveEntity WHERE urlDetail = :url")
    fun deleteComicSaveUrl(url: String)

    @Query("SELECT * FROM ComicHistoryEntity ORDER By id DESC")
    fun getListComicHistory(): MutableList<ComicHistoryEntity>

    @Query("SELECT * FROM ComicHistoryEntity WHERE urlDetail LIKE '%' || :url || '%' ")
    fun getComicHistory(url : String): ComicHistoryEntity

    @Query("SELECT * FROM ComicHistoryEntity WHERE title LIKE '%' || :search || '%'")
    fun getSearchListComicHistory(search : String): MutableList<ComicHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComicHistory(comicHistory: ComicHistoryEntity)

    @Query("DELETE FROM ComicHistoryEntity WHERE urlDetail = :url")
    fun deleteComicHistoryUrl(url: String)

    @Query("DELETE FROM ComicHistoryEntity")
    fun deleteAllComicHistory()
}