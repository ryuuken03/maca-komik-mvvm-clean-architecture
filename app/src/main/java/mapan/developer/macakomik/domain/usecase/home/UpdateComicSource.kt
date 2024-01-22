package mapan.developer.macakomik.domain.usecase.home

import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class UpdateComicSource @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<Int, Unit>() {
    override suspend fun execute(params: Int) {
        return databaseRepository.updateSource(params)
    }
}