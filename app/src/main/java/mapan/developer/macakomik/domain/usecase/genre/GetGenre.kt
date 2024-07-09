package mapan.developer.macakomik.domain.usecase.genre

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import mapan.developer.macakomik.util.Constants
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
class GetGenre @Inject constructor(
    private val shinigamiRepository: ShinigamiRepository,
    private val webScrappingRepository: WebScrappingRepository
) : BaseUseCase<String, Flow<ArrayList<ComicFilter>> >() {
    override suspend fun execute(params: String): Flow<ArrayList<ComicFilter>> {
        if(params.contains("shinigami")){
            var list = ArrayList<ComicFilter>()
            return flowOf(list)
        }else if(params.contains("komikindo")){
            var list = ArrayList<ComicFilter>()
            var genres = Constants.getGenres(4)
            genres.forEach {
                var filter = ComicFilter()
                filter.name = it
                filter.value = it.lowercase().replace(" ","-")
                list.add(filter)
            }
            return flowOf(list)
        }else{
            return webScrappingRepository.getGenreList(params)
        }
    }
}