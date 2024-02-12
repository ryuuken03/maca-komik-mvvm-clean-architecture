package mapan.developer.macakomik.domain.usecase.list

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
class GetListSearch @Inject constructor(
    private val shinigamiRepository: ShinigamiRepository,
    private val webScrappingRepository: WebScrappingRepository
) : BaseUseCase<List<String>, Flow<ComicList> >() {
    override suspend fun execute(params: List<String>): Flow<ComicList> {
        if(params[0].contains("shinigami")){
            return shinigamiRepository.getComicListSearch(params[1],params[2].toInt())
        }else{
            return webScrappingRepository.getComicListSearch(params[0],params[1])
        }
    }
}