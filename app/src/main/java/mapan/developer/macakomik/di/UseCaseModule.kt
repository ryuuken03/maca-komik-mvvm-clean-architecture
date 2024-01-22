package mapan.developer.macakomik.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.bookmarks.DeleteUrlComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetListComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.GetSearchListComicSave
import mapan.developer.macakomik.domain.usecase.bookmarks.InsertComicSave
import mapan.developer.macakomik.domain.usecase.detail.GetDetail
import mapan.developer.macakomik.domain.usecase.history.DeleteUrlComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetListComicHistory
import mapan.developer.macakomik.domain.usecase.history.GetSearchListComicHistory
import mapan.developer.macakomik.domain.usecase.history.InsertComicHistory
import mapan.developer.macakomik.domain.usecase.home.GetHome
import mapan.developer.macakomik.domain.usecase.read.GetRead

/***
 * Created By Mohammad Toriq on 11/01/2024
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHome(
        shinigamiRepository: ShinigamiRepository,
        webScrappingRepository: WebScrappingRepository): GetHome {
        return GetHome(shinigamiRepository,webScrappingRepository)
    }

    @Provides
    fun provideGetDetai(
        shinigamiRepository: ShinigamiRepository,
        webScrappingRepository: WebScrappingRepository): GetDetail {
        return GetDetail(shinigamiRepository,webScrappingRepository)
    }

    @Provides
    fun provideGetRead(
        shinigamiRepository: ShinigamiRepository,
        webScrappingRepository: WebScrappingRepository): GetRead {
        return GetRead(shinigamiRepository,webScrappingRepository)
    }

    @Provides
    fun provideInsertComicHistory(databaseRepository: DatabaseRepository): InsertComicHistory {
        return InsertComicHistory(databaseRepository)
    }

    @Provides
    fun provideGetComicHistory(databaseRepository: DatabaseRepository): GetComicHistory {
        return GetComicHistory(databaseRepository)
    }

    @Provides
    fun provideGetListComicHistory(databaseRepository: DatabaseRepository): GetListComicHistory {
        return GetListComicHistory(databaseRepository)
    }

    @Provides
    fun provideGetSearchListComicHistory(databaseRepository: DatabaseRepository): GetSearchListComicHistory {
        return GetSearchListComicHistory(databaseRepository)
    }

    @Provides
    fun provideDeleteUrlComicHistory(databaseRepository: DatabaseRepository): DeleteUrlComicHistory {
        return DeleteUrlComicHistory(databaseRepository)
    }

    @Provides
    fun provideInsertComicSave(databaseRepository: DatabaseRepository): InsertComicSave {
        return InsertComicSave(databaseRepository)
    }

    @Provides
    fun provideGetComicSave(databaseRepository: DatabaseRepository): GetComicSave {
        return GetComicSave(databaseRepository)
    }

    @Provides
    fun provideGetListComicSave(databaseRepository: DatabaseRepository): GetListComicSave {
        return GetListComicSave(databaseRepository)
    }

    @Provides
    fun provideGetSearchListComicSave(databaseRepository: DatabaseRepository): GetSearchListComicSave {
        return GetSearchListComicSave(databaseRepository)
    }

    @Provides
    fun provideDeleteUrlComicSave(databaseRepository: DatabaseRepository): DeleteUrlComicSave {
        return DeleteUrlComicSave(databaseRepository)
    }
}