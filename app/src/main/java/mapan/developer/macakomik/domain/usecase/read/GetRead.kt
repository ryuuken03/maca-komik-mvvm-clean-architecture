package mapan.developer.macakomik.domain.usecase.read

import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 04/01/2024
 */
class GetRead @Inject constructor(
    private val shinigamiRepository: ShinigamiRepository,
    private val webScrappingRepository: WebScrappingRepository
) : BaseUseCase<List<String>, Flow<ComicChapterPageList>>() {
    override suspend fun execute(params: List<String>): Flow<ComicChapterPageList> {
        if(params[0].contains("shinigami")){
            return shinigamiRepository.getComicChapter(params[0],params[1])
        }else{
            return webScrappingRepository.getComicChapter(params[0])
        }
    }
}