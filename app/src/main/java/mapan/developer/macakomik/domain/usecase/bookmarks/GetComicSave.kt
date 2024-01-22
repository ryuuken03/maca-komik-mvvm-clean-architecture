package mapan.developer.macakomik.domain.usecase.bookmarks

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class GetComicSave @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<String, Flow<ComicSaveEntity>>() {
    override suspend fun execute(params: String) :Flow<ComicSaveEntity> {
        return databaseRepository.getComicSave(params)
    }
}