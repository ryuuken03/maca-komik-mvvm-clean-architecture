package mapan.developer.macakomik.domain.usecase

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
abstract class BaseUseCase<in Params, out T> {
    abstract suspend fun execute(params: Params): T
}