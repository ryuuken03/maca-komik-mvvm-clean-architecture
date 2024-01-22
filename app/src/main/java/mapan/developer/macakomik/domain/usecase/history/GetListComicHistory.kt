package mapan.developer.macakomik.domain.usecase.history

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class GetListComicHistory @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<Unit, Flow<MutableList<ComicHistoryEntity>>>() {
    override suspend fun execute(params: Unit) :Flow<MutableList<ComicHistoryEntity>> {
        return databaseRepository.getListComicHistory()
    }
}