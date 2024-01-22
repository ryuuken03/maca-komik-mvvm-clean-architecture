package mapan.developer.macakomik.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import mapan.developer.macakomik.data.datasource.local.db.dao.AppDao
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Database(
    entities = [ComicSaveEntity::class,ComicHistoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}