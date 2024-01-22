package mapan.developer.macakomik.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mapan.developer.macakomik.data.datasource.local.db.AppDatabase
import mapan.developer.macakomik.data.datasource.local.db.dao.AppDao
import mapan.developer.macakomik.data.datasource.local.sharepreference.AppSharePreference
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "m_maca.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideAppsharePreferece() : AppSharePreference {
        return AppSharePreference()
    }

    @Provides
    @Singleton
    fun provideSharePrefereces(@ApplicationContext context: Context) : SharedPreferences {
        return AppSharePreference().getSharePreferences(context)
    }
}