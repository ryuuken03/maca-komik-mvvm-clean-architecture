package mapan.developer.macakomik.domain.usecase.history

import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class DeleteAllComicHistory @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun execute(params : Unit) {
        return databaseRepository.deleteAllComicHistory()
    }
}