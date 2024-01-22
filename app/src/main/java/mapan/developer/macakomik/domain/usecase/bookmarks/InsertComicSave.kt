package mapan.developer.macakomik.domain.usecase.bookmarks

import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class InsertComicSave @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<ComicSaveEntity, Unit>() {
    override suspend fun execute(params: ComicSaveEntity) {
        return databaseRepository.insertComicSave(params)
    }
}