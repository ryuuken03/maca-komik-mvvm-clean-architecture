package mapan.developer.macakomik.domain.usecase.home

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class GetComicSource @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<Unit, Flow<Int>>() {
    override suspend fun execute(params: Unit) :Flow<Int> {
        return databaseRepository.getSource()
    }
}