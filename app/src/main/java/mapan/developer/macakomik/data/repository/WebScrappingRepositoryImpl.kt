package mapan.developer.macakomik.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicChapter
import mapan.developer.macakomik.data.model.ComicChapterPage
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.data.model.mapper.Scrapper
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import org.jsoup.Jsoup
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Singleton
class WebScrappingRepositoryImpl @Inject constructor(
    private val appContext: Context
) : WebScrappingRepository {

    override suspend fun getHome(url:String): Flow<ComicHome> {
        return flow {
                var result = ComicHome()
                try{
                    var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                    var sources = appContext.resources.getStringArray(R.array.source_website_url)
                    var index = 0
                    for(i in 0 .. sources.size-1){
                        if(url.contains(sources[i])){
                            index = i
                        }
                    }
                    when(index){
                        0->{
                            result = Scrapper.parsingKomikcastHome(doc)
                        }
                        1->{
                            result = Scrapper.parsingWestmangaHome(doc)
                        }
                        2->{
                            result = Scrapper.parsingNgomikHome(doc)
                        }
//                        3->{
////                            result = parsingShinigamiId(doc)
//                        }
                    }
                }
                catch (e:Exception){
                }
                emit(result)
            }
    }

    override suspend fun getComicList(url:String): Flow<ComicList> {
        return flow {
            var result = ComicList()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastList(doc)
                    }
                    1->{
                            result = Scrapper.parsingWestmangaList(doc)
                    }
                    2->{
                            result = Scrapper.parsingNgomikList(doc)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
            }
            catch (e:Exception){
            }
            emit(result)
        }
    }

    override suspend fun getComicListSearch(url:String,keyword: String): Flow<ComicList> {
        return flow {
            var result = ComicList()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastList(doc)
                    }
                    1->{
                        result = Scrapper.parsingWestmangaList(doc)
                    }
                    2->{
                        result = Scrapper.parsingNgomikList(doc)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
            }
            catch (e:Exception){
            }
            emit(result)
        }
    }

    override suspend fun getComicListProject(url:String): Flow<ComicList> {
        return flow {
            var result = ComicList()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastList(doc)
                    }
                    1->{
                        result = Scrapper.parsingWestmangaList(doc)
                    }
                    2->{
                        result = Scrapper.parsingNgomikList(doc)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
            }
            catch (e:Exception){
            }
            emit(result)
        }
    }

    override suspend fun getGenreList(url: String): Flow<ArrayList<ComicFilter>> {
        return flow {
            var genres = ArrayList<ComicFilter>()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                var result = ComicList()
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastList(doc)
                    }
                    1->{
                        result = Scrapper.parsingWestmangaList(doc)
                    }
                    2->{
                        result = Scrapper.parsingNgomikList(doc)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
                result.genres?.forEach {
                    genres.add(it)
                }
            }
            catch (e:Exception){
            }
            emit(genres)
        }
    }

    override suspend fun getComicDetail(url: String): Flow<ComicDetail> {
        return flow {
            var result = ComicDetail()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastDetail(doc)
                    }
                    1->{
                        result = Scrapper.parsingWestmangaDetail(doc)
                    }
                    2->{
                        result = Scrapper.parsingNgomikDetail(doc)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
            }
            catch (e:Exception){
                var detail = ComicDetail()
                var list = ArrayList<ComicChapter> ()
                detail.list = list

                result = detail
            }
            emit(result)
        }
    }

    override suspend fun getComicChapter(url: String): Flow<ComicChapterPageList> {
        return flow {
            var result = ComicChapterPageList()
            try{
                var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
//                Log.d("OkCheck","check url "+url)
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var index = 0
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                }
                when(index){
                    0->{
                        result = Scrapper.parsingKomikcastChapter(doc,url)
                    }
                    1->{
                        result = Scrapper.parsingWestmangaChapter(doc,url)
                    }
                    2->{
                        result = Scrapper.parsingNgomikChapter(doc,url)
                    }
                    3->{
//                            result = parsingShinigamiId(doc)
                    }
                }
            }
            catch (e:Exception){
                var detail = ComicChapterPageList()
                var list = ArrayList<ComicChapterPage> ()
                detail.list = list

                result = detail
            }
            emit(result)
        }
    }
}