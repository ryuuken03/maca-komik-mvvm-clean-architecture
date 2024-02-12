package mapan.developer.macakomik.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mapan.developer.macakomik.data.datasource.remote.ShinigamiApiService
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.mapper.Mapper
import mapan.developer.macakomik.domain.repository.ShinigamiRepository
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicList
import javax.inject.Inject
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Singleton
class ShinigamiRepositoryImpl @Inject constructor(
    private val shinigamiApiService: ShinigamiApiService
) : ShinigamiRepository  {
    override suspend fun getHome(): Flow<ComicHome> {
        return flow {
            var browse = shinigamiApiService.home()
            emit(Mapper.mapFromBrowseAPIToComicHome(browse))

        }
    }

    override suspend fun getComicList(page: Int): Flow<ComicList> {
        return flow {
            var list = shinigamiApiService.loadComicList(page)
            emit(Mapper.mapFromArrayListComicToComicList(list))
        }
    }

    override suspend fun getComicListSearch(keyword: String, page: Int): Flow<ComicList> {
        return flow {
            var list = shinigamiApiService.loadComicListSearch(keyword, page)
            emit(Mapper.mapFromArrayListComicToComicList(list))
        }
    }

    override suspend fun getComicListProject(page: Int): Flow<ComicList> {
        return flow {
            var list = shinigamiApiService.loadComicListProject(page)
            emit(Mapper.mapFromArrayListComicToComicList(list))
        }
    }

    override suspend fun getComicDetail(url: String): Flow<ComicDetail> {
        return flow {
            var detailComic = shinigamiApiService.loadComicDetail(url)
            emit(Mapper.mapFromDetailComicAPIToComicDetail(detailComic))
        }
    }

    override suspend fun getComicChapter(url: String, urlDetail: String): Flow<ComicChapterPageList> {
        return flow {
            var detailComic = shinigamiApiService.loadComicDetail(urlDetail)
            var chapterPages = shinigamiApiService.loadComicChapter(url,0)
            emit(
                Mapper.mapFromImageListAPIToComicChapterPageList(
                    detailComic,
                    chapterPages,
                    url,
                    urlDetail
                )
            )
        }
    }

}