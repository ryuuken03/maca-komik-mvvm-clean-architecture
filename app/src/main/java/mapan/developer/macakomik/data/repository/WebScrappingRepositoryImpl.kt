package mapan.developer.macakomik.data.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zhkrb.cloudflare_scrape_webview.CfCallback
import com.zhkrb.cloudflare_scrape_webview.Cloudflare
import com.zhkrb.cloudflare_scrape_webview.util.ConvertUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import mapan.developer.macakomik.R
import mapan.developer.macakomik.data.model.ComicChapter
import mapan.developer.macakomik.data.model.ComicChapterPage
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.data.model.mapper.Scrapper
import mapan.developer.macakomik.domain.repository.WebScrappingRepository
import org.jsoup.Jsoup
import java.net.HttpCookie
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
    val ua =
        "Mozilla/5.0 (Linux; Android 11; SM-A205U; SM-A102U; SM-G960U; SM-N960U; LM-Q720; LM-X420; LM-Q710(FGN)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Mobile Safari/537.36"

    var cookiesz: HashMap<String, String> = object : HashMap<String, String>() {
        override fun get(key: String): String? {
            if (!containsKey(key)) {
                return ""
            }
            return super.get(key)
        }
    }
    fun checkConnection(url:String, function : () ->Unit){
        try{
            Log.d("OkCheck","check checkConnection")
            val cloudflare = Cloudflare(appContext, url)
            cloudflare.user_agent = ua
            Log.d("OkCheck","check checkConnection user_agent")
            cloudflare.setCfCallback(object : CfCallback {
                override fun onSuccess(
                    cookieList: List<HttpCookie>,
                    hasNewUrl: Boolean,
                    newUrl: String
                ) {
                    Log.d("OkCheck","check checkConnection onSuccess")
                    cookiesz = ConvertUtil.List2Map(cookieList) as HashMap<String, String>
                    function()
                }

                override fun onFail(code: Int, msg: String) {
                    Log.d("OkCheck","check checkConnection onFail")
                    cloudflare.cancel()
                }
            })
            Log.d("OkCheck","check checkConnection setCfCallback")
            cloudflare.getCookies()
            Log.d("OkCheck","check checkConnection getCookies")
        }catch (e:Exception){
            Log.d("OkCheck","check error getCookies ${e.message}")
        }

    }
    override suspend fun getHome(url:String): Flow<ComicHome> {
        return flow {
                var result = ComicHome()
//                try{
                    var doc = Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                    var sources = appContext.resources.getStringArray(R.array.source_website_url)
                    var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                    var index = -1
                    for(i in 0 .. sources.size-1){
                        if(url.contains(sources[i])){
                            index = i
                        }
                        if(url.contains(sourceTitles[i])){
                            index = i
                        }
                    }
                    when(index){
                        4->{
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
                        0->{
                            result = Scrapper.parsingKomikIndoHome(doc)
                        }
                    }
//                }
//                catch (e:Exception){
//                }
                emit(result)
            }
    }

    override suspend fun getComicList(url:String): Flow<ComicList> {
        return flow {
            var result = ComicList()
            Log.d("OKCheck","check getComicList url:$url")
            try{
                var doc =
                    if(cookiesz.isEmpty()){
                        Log.d("OkCheck","check getComicList cookiesz.isEmpty()")
                        Jsoup.parse(URL(url).openStream(),  "UTF-8", url)
                    }else {
                        Log.d("OkCheck","check getComicList cookiesz")
                        Jsoup.connect(url).timeout(60 * 1000)
                            .userAgent(ua)
                            .cookies(cookiesz)
                            .execute()
                            .parse()
                    }
                var sources = appContext.resources.getStringArray(R.array.source_website_url)
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                when(index){
                    4->{
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
                    0->{
                        result = Scrapper.parsingKomikIndoList(doc)
                    }
                }
            }
            catch (e:Exception){
                Log.d("OKCheck","check getComicList error url:$url")
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
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                when(index){
                    4->{
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
                    0->{
                        result = Scrapper.parsingKomikIndoList(doc)
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
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                when(index){
                    4->{
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
                    0->{
                        result = Scrapper.parsingKomikIndoList(doc)
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
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                var result = ComicList()
                when(index){
                    4->{
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
                    0->{
                        result = Scrapper.parsingKomikIndoList(doc)
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
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                Log.d("OkCheck url;"+index.toString(),url)
                when(index){
                    4->{
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
                    0->{
                            result = Scrapper.parsingKomikIndoDetail(doc)
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
                var sourceTitles = appContext.resources.getStringArray(R.array.source_website_title)
                var index = -1
                for(i in 0 .. sources.size-1){
                    if(url.contains(sources[i])){
                        index = i
                    }
                    if(url.contains(sourceTitles[i])){
                        index = i
                    }
                }
                when(index){
                    4->{
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
                    0->{
                        result = Scrapper.parsingKomikIndoChapter(doc,url)
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