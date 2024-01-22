package mapan.developer.macakomik.domain.usecase.history

import mapan.developer.macakomik.data.datasource.local.db.entity.ComicHistoryEntity
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class InsertComicHistory @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<ComicHistoryEntity, Unit>() {
    override suspend fun execute(params: ComicHistoryEntity) {
        return databaseRepository.insertComicHistory(params)
    }
}