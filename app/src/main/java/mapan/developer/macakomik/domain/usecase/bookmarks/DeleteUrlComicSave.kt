package mapan.developer.macakomik.domain.usecase.bookmarks

import mapan.developer.macakomik.domain.repository.DatabaseRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 09/01/2024
 */
class DeleteUrlComicSave @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : BaseUseCase<String, Unit>() {
    override suspend fun execute(params: String) {
        return databaseRepository.deleteComicSaveUrl(params)
    }
}