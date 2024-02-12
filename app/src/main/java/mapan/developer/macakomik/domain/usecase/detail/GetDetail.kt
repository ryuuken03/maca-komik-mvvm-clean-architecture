package mapan.developer.macakomik.domain.usecase.detail

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
class GetDetail @Inject constructor(
    private val shinigamiRepository: ShinigamiRepository,
    private val webScrappingRepository: WebScrappingRepository
) : BaseUseCase<String, Flow<ComicDetail>>() {
    override suspend fun execute(params: String): Flow<ComicDetail> {
        if(params.contains("shinigami")){
            return shinigamiRepository.getComicDetail(params)
        }else{
            return webScrappingRepository.getComicDetail(params)
        }
    }
}