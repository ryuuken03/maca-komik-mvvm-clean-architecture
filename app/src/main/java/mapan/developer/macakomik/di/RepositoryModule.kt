package mapan.developer.macakomik.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mapan.developer.macakomik.data.datasource.local.db.AppDatabase
import mapan.developer.macakomik.data.datasource.local.db.dao.AppDao
import mapan.developer.macakomik.data.datasource.remote.ShinigamiApiService
import mapan.developer.macakomik.data.repository.DatabaseRepositoryImpl
import mapan.developer.macakomik.data.repository.ShinigamiRepositoryImpl
import mapan.developer.macakomik.data.repository.WebScrappingRepositoryImpl
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideShinigamiRepository(shinigamiApiService: ShinigamiApiService): ShinigamiRepository {
        return ShinigamiRepositoryImpl(shinigamiApiService)
    }

    @Provides
    @Singleton
    fun provideWebScrappingRepository(@ApplicationContext appContext: Context): WebScrappingRepository {
        return WebScrappingRepositoryImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(@ApplicationContext appContext: Context,
                                  appDatabase: AppDatabase,
                                  sf : SharedPreferences): DatabaseRepository {
        return DatabaseRepositoryImpl(appContext,appDatabase,sf)
    }
}