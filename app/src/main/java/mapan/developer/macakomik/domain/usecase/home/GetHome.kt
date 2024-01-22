package mapan.developer.macakomik.domain.usecase.home

import android.util.Log
import kotlinx.coroutines.flow.Flow
import mapan.developer.macakomik.data.datasource.remote.ShinigamiApiService
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import mapan.developer.macakomik.domain.usecase.BaseUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
class GetHome @Inject constructor(
    private val shinigamiRepository: ShinigamiRepository,
    private val webScrappingRepository: WebScrappingRepository
) : BaseUseCase<String, Flow<ComicHome> >() {
    override suspend fun execute(params: String): Flow<ComicHome> {
        if(params.contains("shinigami")){
            return shinigamiRepository.getHome()
        }else{
            return webScrappingRepository.getHome(params)
        }
    }
}