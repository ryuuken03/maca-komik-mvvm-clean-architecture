package mapan.developer.macakomik.domain.usecase.setting

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class RestoreDataDB @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<Boolean, Flow<Int>>() {
    override suspend fun execute(params: Boolean):Flow<Int> {
        return databaseRepository.restoreDataDB(params)
    }
}