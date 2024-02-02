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
    companion object {
        const val DB_NAME = "m_maca.db"
        const val DB_BACKUP_SUFFIX = "-bkp"
        const val SQLITE_WALFILE_SUFFIX = "-wal"
        const val SQLITE_SHMFILE_SUFFIX = "-shm"
    }
    abstract fun appDao(): AppDao
}