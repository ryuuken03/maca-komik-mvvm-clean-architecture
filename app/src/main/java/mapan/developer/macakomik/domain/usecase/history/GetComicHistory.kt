package mapan.developer.macakomik.domain.usecase.history

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicSaveEntity
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class GetComicHistory @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<String, Flow<ComicHistoryEntity>>() {
    override suspend fun execute(params: String) :Flow<ComicHistoryEntity> {
        return databaseRepository.getComicHistory(params)
    }
}