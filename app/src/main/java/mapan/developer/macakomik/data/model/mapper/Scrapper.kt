package mapan.developer.macakomik.data.model.mapper

import android.R.menu
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.zhkrb.cloudflare_scrape_webview.CfCallback
import com.zhkrb.cloudflare_scrape_webview.Cloudflare
import com.zhkrb.cloudflare_scrape_webview.util.ConvertUtil
import mapan.developer.macakomik.data.model.ComicChapter
import mapan.developer.macakomik.data.model.ComicChapterPage
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.data.model.ComicThumbnail
import org.json.JSONObject
import org.jsoup.nodes.Document
import java.net.HttpCookie


/***
 * Created By Mohammad Toriq on 14/01/2024
 */
object Scrapper {
    fun parsingKomikcastHome(doc: Document): ComicHome {
        doc.charset()
        var listThumb = ArrayList<ComicThumbnail>()
        var listThumbPopular = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".listupd")
//        Log.d("OkCheck listupds",listupds[0].text())
        var listUpdatePopular = listupds[0].getElementsByClass("swiper-slide")
//        Log.d("OkCheck listUpdatePopular",listUpdatePopular.text())
        for(utao in listUpdatePopular){
//            Log.d("OkCheck utao",utao.text())
            var slideImage = utao.getElementsByClass("splide__slide-image")
//            Log.d("OkCheck slideImage",slideImage.text())
            var span = slideImage[0].getElementsByTag("span")
//            Log.d("OkCheck span",span.text())
            var type = ""
            if(span.size > 0){
                for(it in span){
                    if(it.className().contains("type")){
                        type = it.text()
                        break
                    }
                }
            }
//            Log.d("OkCheck type",type)
            var imgSrc = slideImage[0].getElementsByTag("img")[0].attr("src")
//            Log.d("OkCheck img",slideImage[0].getElementsByTag("img").text())
//            if(imgSrc.equals("") || imgSrc.contains("data:image")){
//                var noScript = slideImage[0].getElementsByTag("noscript")[0]
//                imgSrc = noScript.getElementsByTag("img")[0].attr("src")
//            }
            if(imgSrc.equals("")){
                imgSrc = slideImage[0].getElementsByTag("img")[0].attr("data-src")
            }
            var info = utao.getElementsByClass("splide__slide-info")[0]
            var title = info.getElementsByClass("title")[0].text()
            var other = info.getElementsByClass("other")[0]
            var chapterClass = other.getElementsByClass("chapter")[0]
            var chapter = chapterClass.text()
            var chapterLink = chapterClass.attr("href")
            var url = "-"
            utao.getElementsByTag("a").forEach {
                if(url.equals("-")){
                    if(it.hasAttr("href")){
                        url = it.attr("href")
                    }
                }
            }

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = chapterLink
            listThumbPopular.add(com)
        }
        var listUpdateItem = listupds[listupds.size-1].getElementsByClass("utao")
        for(utao in listUpdateItem){
            var uta = utao.getElementsByClass("uta")[0]
            var tooltip = uta.getElementsByClass("data-tooltip")
            var series = tooltip[0].allElements
            var imgSrc = series[0].getElementsByTag("img")[0].attr("src")
//            if(imgSrc.equals("") || imgSrc.contains("data:image")){
//                var noScript = series[0].getElementsByTag("noscript")[0]
//                imgSrc = noScript.getElementsByTag("img")[0].attr("src")
//            }
            if(imgSrc.equals("")){
                imgSrc = series[0].getElementsByTag("img")[0].attr("data-src")
            }
            var luf = uta.getElementsByClass("luf")[0]
            var ul = luf.getElementsByTag("ul")
            var type = ul[0].className()
            var title = luf.getElementsByTag("h3")[0].text()
            var manga = luf.getElementsByTag("ul")[0].getElementsByTag("li")
            var chapter = ""
            var chapterLink = ""
            var url = ""
            if(manga.size > 0){
                var aLink = manga[0].getElementsByTag("a")[0]
                chapter = aLink.text()
                chapterLink = aLink.attr("href")
                url = uta.getElementsByClass("data-tooltip")[0].getElementsByTag("a")[0].attr("href")
            }


            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = chapterLink
            listThumb.add(com)
        }

        var homePage = ComicHome()
        homePage.popular = listThumbPopular
        homePage.list = listThumb
        return homePage
    }

    fun parsingWestmangaHome(doc: Document): ComicHome{
        var listThumb = ArrayList<ComicThumbnail>()
        var listThumbPopular = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".listupd")
        var listUpdateItemProject = listupds[0].getElementsByClass("bs")
        var i = 0
        for(uta in listUpdateItemProject){
            var links = uta.getElementsByTag("a")
            var limit = links[0].getElementsByClass("limit")
            var type = ""
            if(limit.size > 0){
                var span = limit[0].getElementsByTag("span")
                if(span.size > 0){
                    for(it in span){
                        if(it.className().contains("type")){
                            type = it.className().replace("type ","")
                            break
                        }
                    }
                }
            }
            var imgSrc = links[0].getElementsByTag("img")[0].attr("src")
            var title = links[0].attr("title")
            var chapter = uta.getElementsByClass("epxs")[0].text()
            var url = links[0].attr("href")

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = ""
            listThumbPopular.add(com)
            i++
        }
        var listUpdateItem = listupds[2].getElementsByClass("bs")
        var j = 0
        for(uta in listUpdateItem){
            var links = uta.getElementsByTag("a")
            var limit = links[0].getElementsByClass("limit")
            var type = ""
            if(limit.size > 0){
                var span = limit[0].getElementsByTag("span")
                if(span.size > 0){
                    for(it in span){
//                        Log.d("OkCheck","class:"+it.className())
                        if(it.className().contains("type")){
                            type = it.className().replace("type ","")
                            break
                        }
                    }
                }
            }
            var imgSrc = links[0].getElementsByTag("img")[0].attr("src")
            var title = links[0].attr("title")
            var chapter = uta.getElementsByClass("epxs")[0].text()
            var url = links[0].attr("href")

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = ""
            listThumb.add(com)
            j++
        }

        var homePage = ComicHome()
        homePage.popular = listThumbPopular
        homePage.list = listThumb
        return homePage
    }

    fun parsingNgomikHome(doc: Document): ComicHome{
        var listThumb = ArrayList<ComicThumbnail>()
        var listThumbPopular = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".listupd")
        for(i in 0 .. listupds.size-1){

            var listUpdateItem = listupds[i].getElementsByClass("utao")
            for(utao in listUpdateItem){
                var uta = utao.getElementsByClass("uta")[0]

                var imgu = uta.getElementsByClass("imgu")[0]
                var links = imgu.getElementsByTag("a")
                var imgSrc = links[0].getElementsByTag("img")[0].attr("src")

                var luf = uta.getElementsByClass("luf")[0]
                var linkUrl = luf.getElementsByTag("a")[0]
                var title = linkUrl.attr("title")
                if(title.equals("")){
                    title = linkUrl.getElementsByTag("h4")[0].text()
                }

                Log.d("OkCheck",title)
                Log.d("OkCheck",imgSrc)
                var url = links[0].attr("href")
                if(url.equals("")){
                    url = linkUrl.attr("href")
                }
                Log.d("OkCheck",url)

                var ul = luf.getElementsByTag("ul")
                var type = ""
                var chapter = ""
                var urlLastChap = ""
                if(ul.size > 0){
                    type = ul[0].className()
                    Log.d("OkCheck",type)
                    var li = ul[0].getElementsByTag("li")
                    if(li.size > 0){
                        var chapLink = li[0].getElementsByTag("a")
                        chapter = chapLink[0].text()
                        Log.d("OkCheck",chapter)
                        urlLastChap = chapLink[0].attr("href")
                        Log.d("OkCheck",urlLastChap)
                    }
                }

                var com = ComicThumbnail()
                com.title = title
                com.url = url
                com.type = type
                com.imgSrc = imgSrc
                com.lastChap = chapter
                com.urlLastChap = urlLastChap
                if(i == 0){
                    listThumbPopular.add(com)
                }else if(i == listupds.size-1){
                    listThumb.add(com)
                }
            }
        }

        var homePage = ComicHome()
        homePage.popular = listThumbPopular
        homePage.list = listThumb
        return homePage
    }
    fun parsingKomikIndoHome(doc: Document): ComicHome {
        doc.charset()
        var listThumb = ArrayList<ComicThumbnail>()
        var listThumbPopular = ArrayList<ComicThumbnail>()
        val content = doc.select(".content")
        var slide = content[0].getElementsByClass("odadingslider")
        var postShow = content[0].getElementsByClass("post-show")
        content.forEach {
            var post = it.getElementsByClass("post-show")
            if(post.hasClass("chapterbaru")){
                postShow = post
            }
        }

        var animeposts = slide[0].getElementsByClass("animepost")
        for(animepost in animeposts){
            var animposx = animepost.getElementsByClass("animposx")[0]
            var a = animposx.getElementsByTag("a")
            var span = animposx.getElementsByTag("span")
            var type = ""
            span.forEach{
                if(it.className().contains("typeflag")){
                    type = it.className().replace("typeflag ","")
                }
            }
            var url = a.attr("href")
            var limit  = a[0].getElementsByClass("limit")
            var img = limit[0].getElementsByTag("img")
            var imgSrc = img.attr("src")
            var title = a.attr("title").replace("Komik ","")

            var bigor = animposx.getElementsByClass("bigor")
            var adds = bigor[0].getElementsByClass("adds")
            var lsch = adds[0].getElementsByClass("lsch")
            var chapterClass = lsch[0].getElementsByTag("a")
            var chapter = chapterClass.text()
            if(chapter.contains("Ch.")){
                chapter = chapter.replace("Ch. ","Chapter ")
            }
            var chapterLink = chapterClass.attr("href")

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = chapterLink
            listThumbPopular.add(com)
        }

        postShow.forEach {
            var listupd = it.getElementsByClass("listupd")[0]
            var animeposts2 = listupd.getElementsByClass("animepost")
            for(animepost in animeposts2){
                var animposx = animepost.getElementsByClass("animposx")
                var animepostxxTop = animposx[0].getElementsByClass("animepostxx-top")

                var animepostxxBottom = animposx[0].getElementsByClass("animepostxx-bottom")

                var img = animepostxxTop[0].getElementsByTag("img")
                var imgSrc = img[0].attr("src")
                for(i in 9 downTo 0){
                    if(imgSrc.contains("-7"+i+"x")){
                        var split = imgSrc.split("-7"+i+"x")
                        if(split.size > 1){
                            var ext = split[1].split(".")
                            if(ext.size > 1){
                                imgSrc = split[0]+"."+ext[1]
                                break
                            }
                        }
                    }
                }
//                Log.d("OkCheck","check Scrapper.parsingKomikIndoHome imgSrc:${imgSrc}")

                var a = animepostxxTop[0].getElementsByTag("a")
                var type = ""
                var animepostxxTopBottom = animepostxxTop[0].getElementsByClass("animepostxx-top-bottom")
                var infoSkroep = animepostxxTopBottom[0].getElementsByClass("info-skroep")
                var nginfo = infoSkroep[0].getElementsByClass("flex-skroep nginfo-skroep")
                nginfo.forEach {
                    if(it.className().contains("typeflag")){
                        type = it.className().replace("typeflag ","")
                    }
                }
                var title = a.attr("title").replace("Komik ","")
                var url = a.attr("href")

                var chapter = ""
                var chapterLink = ""
                var nginfo2 = animepostxxBottom[0].getElementsByClass("flex-skroep nginfo-skroep list-ch-skroep")
                var lsch = nginfo2[0].getElementsByClass("lsch")
                if(lsch.size > 0){
                    var aLink = lsch[0].getElementsByTag("a")[0]
                    chapter = aLink.text()
                    if(chapter.contains("Ch.")){
                        chapter = chapter.replace("Ch. ","Chapter ")
                    }
                    chapterLink = aLink.attr("href")
                }

                var com = ComicThumbnail()
                com.title = title
                com.url = url
                com.type = type
                com.imgSrc = imgSrc
                com.lastChap = chapter
                com.urlLastChap = chapterLink
                listThumb.add(com)
            }
        }

        var homePage = ComicHome()
        homePage.popular = listThumbPopular
        homePage.list = listThumb
        return homePage
    }

    fun parsingKomikcastList(doc:Document) : ComicList {
        var listThumb = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".list-update_items-wrapper")
        var listUpdateItem = listupds[0].getElementsByClass("list-update_item")
        for(uta in listUpdateItem){
            var links = uta.getElementsByClass("data-tooltip")
            for (i in 0 .. 0) {
                var imgSrc = uta.getElementsByTag("img")[0].attr("src")
                if(imgSrc.equals("") || imgSrc.contains("data:image")){
                    var noScript = uta.getElementsByTag("noscript")
                    if(noScript.size > 0){
                        imgSrc = noScript[0].getElementsByTag("img")[0].attr("src")
                    }
                }
                var type = ""
                var span = uta.getElementsByTag("span")
                if(span.size > 0){
                    for(it in span){
                        if(it.className().contains("type")){
                            type = it.text()
                            break
                        }
                    }
                }
                var title = uta.getElementsByTag("h3")[0].text()
                var chapter = uta.getElementsByClass("chapter")[0].text()
                var chapterLink = uta.getElementsByClass("chapter")[0].attr("href")
                var url = links[i].attr("href")

                var com = ComicThumbnail()
                com.title = title
                com.url = url
                com.type = type
                com.imgSrc = imgSrc
                com.lastChap = chapter
                com.urlLastChap = chapterLink
                listThumb.add(com)
            }
        }
        var filters = doc.select(".komiklist_filter-dropdown")
        var genres = ArrayList<ComicFilter>()
        var types = ArrayList<ComicFilter>()
        var orderbys = ArrayList<ComicFilter>()
        for(filter in filters){
            var ulGenre = filter.getElementsByClass("komiklist_dropdown-menu c4 genrez")
            if(ulGenre.size > 0){
                var lis = ulGenre[0].getElementsByTag("li")
                for(li in lis){
                    var value = li.getElementsByTag("input").attr("value")
                    var name = li.getElementsByTag("label").text()
                    var filComic = ComicFilter()
                    filComic.id = (genres.size+1).toLong()
                    filComic.value = value
                    filComic.name = name
                    genres.add(filComic)
                }
            }
            var ulType = filter.getElementsByClass("komiklist_dropdown-menu type")
            if(ulType.size > 0){
                var lis = ulType[0].getElementsByTag("li")
                for(li in lis){
                    var value = li.getElementsByTag("input").attr("value")
                    var name = li.getElementsByTag("label").text()
                    var filComic = ComicFilter()
                    filComic.id = (types.size+1).toLong()
                    filComic.value = value
                    filComic.name = name
                    types.add(filComic)
                }
            }
            var ulOrderBy = filter.getElementsByClass("komiklist_dropdown-menu sort_by")
            if(ulOrderBy.size > 0){
                var lis = ulOrderBy[0].getElementsByTag("li")
                for(li in lis){
                    var value = li.getElementsByTag("input").attr("value")
                    var name = li.getElementsByTag("label").text()
                    var filComic = ComicFilter()
                    filComic.id = (orderbys.size+1).toLong()
                    filComic.value = value
                    filComic.name = name
                    orderbys.add(filComic)
                }
            }
        }

        var pagination = doc.select(".pagination")
        var pageNextUrl : String?= null
        if(pagination.size > 0){
            var pages = pagination[0].allElements
            pages.forEach {
                if(it.className().contains("next")){
                    pageNextUrl = it.attr("href")
                }
            }
        }

        var result = ComicList()
        result.pageNextUrl = pageNextUrl
        result.list = listThumb
        result.genres = genres
        result.types = types
        result.orderbys = orderbys

        return result
    }

    fun parsingWestmangaList(doc: Document): ComicList{
        var listThumb = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".listupd")
        var listUpdateItem = listupds[0].getElementsByClass("bs")
        var i = 0
        for(uta in listUpdateItem){
            var links = uta.getElementsByTag("a")
            var limit = links[0].getElementsByClass("limit")
            var type = ""
            if(limit.size > 0){
                var span = limit[0].getElementsByTag("span")
                if(span.size > 0){
                    for(it in span){
                        if(it.className().contains("type")){
                            type = it.className().replace("type ","")
                            break
                        }
                    }
                }
            }
            var imgSrc = links[0].getElementsByTag("img")[0].attr("src")
            var title = links[0].attr("title")
            var chapter = uta.getElementsByClass("epxs")[0].text()
            var url = links[0].attr("href")

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = ""
            listThumb.add(com)
            i++
        }
        var qFilter = doc.select(".quickfilter")
        var genres = ArrayList<ComicFilter>()
        var types = ArrayList<ComicFilter>()
        var orderbys = ArrayList<ComicFilter>()
        if(qFilter.size > 0){
            var filters = qFilter[0].getElementsByClass("filter dropdown")
            for(filter in filters){
                var ulGenre = filter.getElementsByClass("dropdown-menu c4 genrez")
                if(ulGenre.size > 0){
                    var lis = ulGenre[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (genres.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        genres.add(filComic)
                    }
                }
                var typeText = filter.getElementsByTag("button")[0].text()
                if(typeText.contains("Type",true)){
                    var ulType = filter.getElementsByClass("dropdown-menu c1")
                    if(ulType.size > 0){
                        var lis = ulType[0].getElementsByTag("li")
                        for(li in lis){
                            var value = li.getElementsByTag("input").attr("value")
                            var name = li.getElementsByTag("label").text()
                            var filComic = ComicFilter()
                            filComic.id = (types.size+1).toLong()
                            filComic.value = value
                            filComic.name = name
                            types.add(filComic)
                        }
                    }
                }
                if(typeText.contains("Order",true)){
                    var ulOrderBy = filter.getElementsByClass("dropdown-menu c1")
                    var lis = ulOrderBy[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (orderbys.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        orderbys.add(filComic)
                    }
                }
            }
        }
        var pageNextUrl = ""
        var mrgn = doc.select(".mrgn")
        if(mrgn.size > 0){
//            Log.d("OKCheck","nextUrl : mrgn"+mrgn.size.toString())
            var hpage = mrgn[0].getElementsByClass("hpage")
            if(hpage.size > 0){
//                Log.d("OKCheck","nextUrl : hpage"+hpage.size.toString())
                var checkPage = hpage[0].getElementsByTag("a")
                for(page in checkPage){
                    if(page.text().contains("Next",true)){
                        pageNextUrl = page.attr("href")
//                        Log.d("OKCheck","nextUrl : page.attr "+page.attr("href"))
                    }
                }
            }
        }

        var homePage = ComicList()
        homePage.pageNextUrl = pageNextUrl
        homePage.list = listThumb
        homePage.genres = genres
        homePage.types = types
        homePage.orderbys = orderbys
        return homePage
    }

    fun parsingNgomikList(doc: Document): ComicList{
        var listThumb = ArrayList<ComicThumbnail>()
        val listupds = doc.select(".listupd")
        var listUpdateItem = listupds[0].getElementsByClass("bs")
        var i = 0
        for(uta in listUpdateItem){
            var links = uta.getElementsByTag("a")
            var limit = links[0].getElementsByClass("limit")
            var type = ""
            if(limit.size > 0){
                var span = limit[0].getElementsByTag("span")
                if(span.size > 0){
                    for(it in span){
                        if(it.className().contains("type")){
                            type = it.className().replace("type ","")
                            break
                        }
                    }
                }
            }
            var imgSrc = links[0].getElementsByTag("img")[0].attr("src")
            var title = links[0].attr("title")
            var chapter = uta.getElementsByClass("epxs")[0].text()
            var url = links[0].attr("href")

            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = ""
            listThumb.add(com)
            i++
        }
        var qFilter = doc.select(".quickfilter")
        var genres = ArrayList<ComicFilter>()
        var types = ArrayList<ComicFilter>()
        var orderbys = ArrayList<ComicFilter>()
        if(qFilter.size > 0){
            var filters = qFilter[0].getElementsByClass("filter dropdown")
            for(filter in filters){
                var ulGenre = filter.getElementsByClass("dropdown-menu c4 genrez")
                if(ulGenre.size > 0){
                    var lis = ulGenre[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (genres.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        genres.add(filComic)
                    }
                }
                var typeText = filter.getElementsByTag("button")[0].text()
                if(typeText.contains("Type",true)){
                    var ulType = filter.getElementsByClass("dropdown-menu c1")
                    if(ulType.size > 0){
                        var lis = ulType[0].getElementsByTag("li")
                        for(li in lis){
                            var value = li.getElementsByTag("input").attr("value")
                            var name = li.getElementsByTag("label").text()
                            var filComic = ComicFilter()
                            filComic.id = (types.size+1).toLong()
                            filComic.value = value
                            filComic.name = name
                            types.add(filComic)
                        }
                    }
                }
                if(typeText.contains("Order",true)){
                    var ulOrderBy = filter.getElementsByClass("dropdown-menu c1")
                    var lis = ulOrderBy[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (orderbys.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        orderbys.add(filComic)
                    }
                }
            }
        }
        var pageNextUrl = ""

        var mrgn = doc.select(".mrgn")
        if(mrgn.size > 0){
            var hpage = mrgn[0].lastElementChild()
            if(hpage?.className().equals("hpage")){
                var aList = hpage?.getElementsByTag("a")!!
                for(a in aList){
                    if(a.className().equals("r")){
                        pageNextUrl = a.attr("href")
                    }
                }
            }else{
                var checkPage = mrgn[0].getElementsByTag("a")
                for(page in checkPage){
                    if(page.text().contains("Next",true)){
                        pageNextUrl = page.attr("href")
                    }
                }

            }
        }

        var homePage = ComicList()
        homePage.pageNextUrl = pageNextUrl
        homePage.list = listThumb
        homePage.genres = genres
        homePage.types = types
        homePage.orderbys = orderbys
        return homePage
    }
    fun parsingKomikIndoList(doc: Document): ComicList{
        var listThumb = ArrayList<ComicThumbnail>()
        val filmList = doc.select(".film-list")
//        Log.d("OkCheck","check parsingKomikIndoList filmList.size : ${filmList.size}")
        var animeposts = filmList[0].getElementsByClass("animepost")
//        Log.d("OkCheck","check parsingKomikIndoList animeposts.size : ${animeposts.size}")
        var i = 0
        for(animepost in animeposts){
            var animposx = animepost.getElementsByClass("animposx")
//            Log.d("OkCheck","check parsingKomikIndoList animposx.size : ${animposx.size}")

            var a = animposx[0].getElementsByTag("a")
            var span = animposx[0].getElementsByTag("span")
            var type = ""
            span.forEach{
                if(it.className().contains("typeflag")){
                    type = it.className().replace("typeflag ","")
                }
            }
            var url = a.attr("href")
            var limit  = a[0].getElementsByClass("limit")
            var img = limit[0].getElementsByTag("img")
            var imgSrc = img.attr("src")
            var title = a.attr("title").replace("Komik ","")

            var bigor = animposx[0].getElementsByClass("bigor")

            var lsch = animposx[0].getElementsByClass("lsch")
            var chapter = ""
            var chapterLink = ""
            lsch.forEach {
                var a = it.getElementsByTag("a")
                chapter = a.text()
                if(chapter.contains("Ch.")){
                    chapter = chapter.replace("Ch. ","Chapter ")
                }
                chapterLink = a.attr("href")
            }


            var com = ComicThumbnail()
            com.title = title
            com.url = url
            com.type = type
            com.imgSrc = imgSrc
            com.lastChap = chapter
            com.urlLastChap = ""
            listThumb.add(com)
            i++
        }
        var qFilter = doc.select(".quickfilter")
        var genres = ArrayList<ComicFilter>()
        var types = ArrayList<ComicFilter>()
        var orderbys = ArrayList<ComicFilter>()
        if(qFilter.size > 0){
            var filters = qFilter[0].getElementsByClass("filter dropdown")
            for(filter in filters){
                var ulGenre = filter.getElementsByClass("dropdown-menu c4 genrez")
                if(ulGenre.size > 0){
                    var lis = ulGenre[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (genres.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        genres.add(filComic)
                    }
                }
                var typeText = filter.getElementsByTag("button")[0].text()
                if(typeText.contains("Type",true)){
                    var ulType = filter.getElementsByClass("dropdown-menu c1")
                    if(ulType.size > 0){
                        var lis = ulType[0].getElementsByTag("li")
                        for(li in lis){
                            var value = li.getElementsByTag("input").attr("value")
                            var name = li.getElementsByTag("label").text()
                            var filComic = ComicFilter()
                            filComic.id = (types.size+1).toLong()
                            filComic.value = value
                            filComic.name = name
                            types.add(filComic)
                        }
                    }
                }
                if(typeText.contains("Order",true)){
                    var ulOrderBy = filter.getElementsByClass("dropdown-menu c1")
                    var lis = ulOrderBy[0].getElementsByTag("li")
                    for(li in lis){
                        var value = li.getElementsByTag("input").attr("value")
                        var name = li.getElementsByTag("label").text()
                        var filComic = ComicFilter()
                        filComic.id = (orderbys.size+1).toLong()
                        filComic.value = value
                        filComic.name = name
                        orderbys.add(filComic)
                    }
                }
            }
        }
        var pageNextUrl = ""

        var mrgn = doc.select(".mrgn")
        if(mrgn.size > 0){
            var hpage = mrgn[0].lastElementChild()
            if(hpage?.className().equals("hpage")){
                var aList = hpage?.getElementsByTag("a")!!
                for(a in aList){
                    if(a.className().equals("r")){
                        pageNextUrl = a.attr("href")
                    }
                }
            }else{
                var checkPage = mrgn[0].getElementsByTag("a")
                for(page in checkPage){
                    if(page.text().contains("Next",true)){
                        pageNextUrl = page.attr("href")
                    }
                }

            }
        }

        var homePage = ComicList()
        homePage.pageNextUrl = pageNextUrl
        homePage.list = listThumb
        homePage.genres = genres
        homePage.types = types
        homePage.orderbys = orderbys
        return homePage
    }

    fun parsingKomikcastDetail(doc:Document) : ComicDetail {
        var listChap = ArrayList<ComicChapter>()
        val thumbnails = doc.select(".komik_info-content-thumbnail")
        val contentBody = doc.select(".komik_info-content-body")
        Log.d("OkCheck",contentBody.toString())
        val contentGenre = doc.select(".komik_info-content-genre")
        val contentMeta = doc.select(".komik_info-content-meta")
        val contentChapter = doc.select(".komik_info-chapters-item")
        val description = doc.select(".komik_info-description")
        var imgThumb = ""
        if(thumbnails.size > 0){
            var img = thumbnails[0].getElementsByTag("img")
            if(img.size>0){
                imgThumb = img[0].attr("src")
            }
            if(imgThumb.equals("") || imgThumb.contains("data:image")){
                var metas = thumbnails[0].getElementsByTag("meta")
                for(meta in metas){
                    if(meta.attr("itemprop").equals("url")){
                        imgThumb = meta.attr("content")
                    }
                }
            }
        }

        var h1T = contentBody[0].getElementsByTag("h1")[0].text()
        var sp = h1T.split(" Bahasa ")
        Log.d("OkCheck",h1T)
        Log.d("OkCheck",sp[0])
        var title = sp[0]
        var genreTags = contentGenre[0].getElementsByTag("a")
        var genre = ""
        for(i in 0 .. genreTags.size-1){
            genre += genreTags[i].text()
            if(i < genreTags.size-1){
                genre += ", "
            }
        }
        var release = contentMeta[0].getElementsByClass("komik_info-content-info-release")[0].text()
        var type = contentMeta[0].getElementsByClass("komik_info-content-info-type")[0].text()
        var synopsis = description[0].getElementsByClass("komik_info-description-sinopsis")[0].text()

        var i = 1
        for(chapter in contentChapter){
            var name = chapter.getElementsByTag("a").text()
            var url = chapter.getElementsByTag("a").attr("href")
            var time = chapter.getElementsByClass("chapter-link-time").text()

            var ch = ComicChapter()
            ch.name = name
            ch.time = time
            ch.url = url
            listChap.add(ch)
            i++
        }
        var detail = ComicDetail()
        detail.title = title
        detail.genre = genre
        detail.type = type
        detail.synopsis = synopsis
        detail.release = release
        detail.imgSrc = imgThumb
        detail.list = listChap
        return detail
    }

    fun parsingWestmangaDetail(doc:Document) : ComicDetail{
        var listChap = ArrayList<ComicChapter>()

        val thumbnails = doc.select(".seriestucontl")
        val contentBody = doc.select(".seriestucon")
        val contentGenre = doc.select(".seriestugenre")
        val contentMeta = doc.select(".infotable")
        val contentChapter = doc.select(".eplister")

        var imgThumb = thumbnails[0].getElementsByTag("img")[0].attr("src")
        var h1T = contentBody[0].getElementsByTag("h1")[0].text()
        var sp = h1T.split(" Bahasa ")
        var title = sp[0]
        var entryContent = contentBody[0].getElementsByClass("entry-content entry-content-single")
        var sinopsis = entryContent[0].text()
        var genreTags = contentGenre[0].getElementsByTag("a")
        var genre = ""
        for(i in 0 .. genreTags.size-1){
            genre += genreTags[i].text()
            if(i < genreTags.size-1){
                genre += ", "
            }
        }
        var trs = contentMeta[0].getElementsByTag("tr")
        var type = ""
        var release = ""
        for(tr in trs){
            if(tr.text().contains("Type",true)){
                type = tr.text().replace("Type","Type:")
            }
            if(tr.text().contains("Author",true)){
                release = tr.text().replace("Author","Author:")
            }
        }
        var chapters = contentChapter[0].getElementsByTag("li")
        var i = 1
        for(chapter in chapters){
            var name = chapter.getElementsByClass("chapternum").text()
            var url = chapter.getElementsByTag("a").attr("href")
            var time = chapter.getElementsByClass("chapterdate").text()

            var ch = ComicChapter()
            ch.name = name
            ch.time = time
            ch.url = url
            listChap.add(ch)
            i++
        }
        var chPage = ComicDetail()
        chPage.title = title
        chPage.genre = genre
        chPage.type = type
        chPage.release = release
        chPage.imgSrc = imgThumb
        chPage.list = listChap
        return chPage
    }

    fun parsingNgomikDetail(doc:Document) : ComicDetail{
        var listChap = ArrayList<ComicChapter>()

        val thumbnails = doc.select(".seriestucontl")
        val contentBody = doc.select(".seriestucon")
        val contentGenre = doc.select(".seriestugenre")
        val contentMeta = doc.select(".infotable")
        val contentChapter = doc.select(".eplister")

        var imgThumb = thumbnails[0].getElementsByTag("img")[0].attr("src")
        var h1T = contentBody[0].getElementsByTag("h1")[0].text()
        var sp = h1T.split(" Bahasa ")
        var title = sp[0]

        var genreTags = contentGenre[0].getElementsByTag("a")
        var genre = ""
        for(i in 0 .. genreTags.size-1){
            genre += genreTags[i].text()
            if(i < genreTags.size-1){
                genre += ", "
            }
        }
        var trs = contentMeta[0].getElementsByTag("tr")
        var type = ""
        var release = ""
        for(tr in trs){
            if(tr.text().contains("Type",true)){
                type = tr.text().replace("Type","Type:")
            }
            if(tr.text().contains("Author",true)){
                release = tr.text().replace("Author","Author:")
            }
        }
        var chapters = contentChapter[0].getElementsByTag("li")
        var i = 1
        for(chapter in chapters){
            var name = chapter.getElementsByClass("chapternum").text()
            var url = chapter.getElementsByTag("a").attr("href")
            var time = chapter.getElementsByClass("chapterdate").text()

            var ch = ComicChapter()
            ch.name = name
            ch.time = time
            ch.url = url
            listChap.add(ch)
            i++
        }
        var chPage = ComicDetail()
        chPage.title = title
        chPage.genre = genre
        chPage.type = type
        chPage.release = release
        chPage.imgSrc = imgThumb
        chPage.list = listChap
        return chPage
    }
    fun parsingKomikIndoDetail(doc:Document) : ComicDetail{
        var listChap = ArrayList<ComicChapter>()

        val postbody = doc.select(".postbody")

        var thumb = postbody[0].getElementsByClass("thumb")
        var imgThumb = thumb[0].getElementsByTag("img")
        var imgSrc = imgThumb[0].attr("src")
        var titles = imgThumb[0].attr("title")
        var sp = titles.split("Komik ")
        var title = titles
        if(sp.size > 1){
            title = sp[1]
        }

        var infox = postbody[0].getElementsByClass("infox")
        var genreInfo  = infox[0].getElementsByClass("genre-info")
        var genreTags = genreInfo[0].getElementsByTag("a")
        var genre = ""
        for(i in 0 .. genreTags.size-1){
            genre += genreTags[i].text()
            if(i < genreTags.size-1){
                genre += ", "
            }
        }
        var spe  = infox[0].getElementsByClass("spe")
        var spans = spe[0].getElementsByTag("span")
        var type = ""
        var release = ""
        for(span in spans){
            if(span.text().contains("Jenis Komik:",true)){
                type = span.text().replace("Jenis Komik","Type")
            }
//            if(span.text().contains("Pengarang:",true)){
//                release = span.text().replace("Pengarang","Author")
//            }
            if(span.text().contains("Tema:",true)){
                release = span.text().replace("Tema","Theme")
            }
        }

        var wSynopsis = postbody[0].getElementById("sinopsis")
        var synopsis = wSynopsis.getElementsByTag("p")[0].text()
        var epsLst = postbody[0].getElementsByClass("eps_lst")
        var chapterList = epsLst[0].getElementById("chapter_list")
        var chapters = chapterList.getElementsByTag("li")
        var i = 1
        for(chapter in chapters){
            var lchx = chapter.getElementsByClass("lchx")

            var dt = chapter.getElementsByClass("dt")
            var name = lchx[0].getElementsByTag("a")[0].text()
            var url = lchx[0].getElementsByTag("a")[0].attr("href")
            var time = dt[0].getElementsByTag("a")[0].text()

            var ch = ComicChapter()
            ch.name = name
            ch.time = time
            ch.url = url
            listChap.add(ch)
            i++
        }
        var chPage = ComicDetail()
        chPage.title = title
        chPage.genre = genre
        chPage.type = type
        chPage.release = release
        chPage.imgSrc = imgSrc
        chPage.list = listChap
        chPage.synopsis = synopsis
        return chPage
    }

    fun replaceStringForChapter(s : String) : String{
        var result = s
        if(result.contains("Chapter",true)){
            var split0 = result.split(" Chapter ")
            if(split0.size > 1){
                result = "Chapter " + split0[1]
            }
        }
        if(result.contains("ch",true)){
            var split0 = result.split(" ch")
            if(split0.size > 1){
                result = "Chapter " + split0[1]
            }
        }
        var list = listOf("bahasa","bahsa")
        list.forEach {
            if(s.contains(it,true)){
                var split = result.lowercase().split(it)
                if(split.size > 1){
                    result = split[0].replaceFirstChar(Char::titlecase)
                }
            }
        }
        return result
    }

    fun parsingKomikcastChapter(doc: Document,url:String) : ComicChapterPageList {
        var listThumb = ArrayList<ComicChapterPage>()
        val readingArea = doc.select(".main-reading-area")
        var listImage = readingArea[0].getElementsByTag("img")
        var i = 1
        for(image in listImage){
            var src = image.attr("data-src")
            if(src.equals("")){
                src = image.attr("src")
            }
            if(!src.equals("")){
                var com = ComicChapterPage()
                com.imgSrc = src
                listThumb.add(com)
                i++
            }
        }
        var nav = doc.select(".chapter_nav-control")
        var right = nav[0].getElementsByClass("right-control")
        var nextprev = right[0].getElementsByClass("nextprev")
        var rels = nextprev[0].getElementsByTag("a")
        var prev = ""
        var next = ""
        for(data in rels){
            var rel = data.attr("rel")
            if(rel.equals("prev",true)){
                prev = data.attr("href")
            }
            if(rel.equals("next",true)){
                next = data.attr("href")
            }
        }
        var allc = doc.select(".allc")[0].getElementsByTag("a")
        var all = allc.attr("href")
        var title = doc.select(".chapter_headpost")[0].getElementsByTag("h1").text()
        var chap = replaceStringForChapter(title)
        title = allc.text()
        var chPage = ComicChapterPageList()
        chPage.title = title
        chPage.currentChap = chap
        chPage.urlChapter = url
        chPage.pagePrev = if(!prev.equals("")) prev else null
        chPage.pageAll = all
        chPage.pageNext = if(!next.equals("")) next else null
        chPage.list = listThumb
        return chPage

    }

    fun parsingWestmangaChapter(doc: Document,url:String) : ComicChapterPageList{
        var listThumb = ArrayList<ComicChapterPage>()
        val scripts = doc.getElementsByTag("script")
        var listImage = ArrayList<String>()
        var prev = ""
        var next = ""
        for(script in scripts){
            for(node in script.dataNodes()){
                if(node.wholeData.contains("ts_reader.run(")){
                    var parse = node.wholeData.replace("ts_reader.run({","{")
                    parse = parse.replace("})","}")
                    var data = JSONObject(parse)
                    prev = data.getString("prevUrl")
                    next = data.getString("nextUrl")
                    var sources = data.getJSONArray("sources")
                    var images = sources.getJSONObject(0).getJSONArray("images")
                    for(i in 0 .. images.length()-1){
                        listImage.add(images[i].toString())
                    }
                }
            }
        }
        var i = 1
        for(image in listImage){
            var src = image
            if(!src.equals("")){
                var com = ComicChapterPage()
                com.imgSrc = src
                listThumb.add(com)
                i++
            }
        }
        var allc = doc.select(".allc")[0].getElementsByTag("a")
        var all = allc.attr("href")
        var title = doc.select(".entry-title")[0].text()
        var chap = replaceStringForChapter(title)
        title = allc.text()
        var chPage = ComicChapterPageList()
        chPage.title = title
        chPage.currentChap = chap
        chPage.urlChapter = url
        chPage.pagePrev = if(!prev.equals("")) prev else null
        chPage.pageAll = all
        chPage.pageNext = if(!next.equals("")) next else null
        chPage.list = listThumb
        return chPage

    }

    fun parsingNgomikChapter(doc: Document,url:String) : ComicChapterPageList{
        var listThumb = ArrayList<ComicChapterPage>()
        val scripts = doc.getElementsByTag("script")
        var listImage = ArrayList<String>()
        var prev = ""
        var next = ""
        for(script in scripts){
            for(node in script.dataNodes()){
                if(node.wholeData.contains("ts_reader.run(")){
                    var parse = node.wholeData.replace("ts_reader.run({","{")
                    parse = parse.replace("})","}")
                    var data = JSONObject(parse)
                    prev = data.getString("prevUrl")
                    next = data.getString("nextUrl")
                    var sources = data.getJSONArray("sources")
                    var images = sources.getJSONObject(0).getJSONArray("images")
                    for(i in 0 .. images.length()-1){
                        listImage.add(images[i].toString())
                    }
                }
            }
        }
        var i = 1
        for(image in listImage){
            var src = image
            if(!src.equals("")){
                var com = ComicChapterPage()
                com.imgSrc = src
                listThumb.add(com)
                i++
            }
        }
        var allc = doc.select(".allc")[0].getElementsByTag("a")
        var all = allc.attr("href")
        var title = doc.select(".entry-title")[0].text()
        var chap = replaceStringForChapter(title)
        title = allc.text()
        var chPage = ComicChapterPageList()
        chPage.title = title
        chPage.currentChap = chap
        chPage.urlChapter = url
        chPage.pagePrev = if(!prev.equals("")) prev else null
        chPage.pageAll = all
        chPage.pageNext = if(!next.equals("")) next else null
        chPage.list = listThumb
        return chPage

    }
    fun parsingKomikIndoChapter(doc: Document,url:String) : ComicChapterPageList{
        var listThumb = ArrayList<ComicChapterPage>()
        val auh = doc.getElementById("chimg-auh")
        val pages = auh.getElementsByTag("img")
        var prev = ""
        var next = ""
        var all = ""
        var i = 1
        for(image in pages){
            var src = image.attr("src")
            if(!src.equals("")){
                var com = ComicChapterPage()
                com.imgSrc = src
                listThumb.add(com)
                i++
            }
        }
        var nextprev = doc.select(".nextprev")[0]
        var tagsA = nextprev.getElementsByTag("a")
        tagsA.forEach {
            if(it.attr("rel").equals("prev")){
                prev = it.attr("href")
            }else if(it.attr("rel").equals("next")){
                next = it.attr("href")
            }else{
                all = it.attr("href")
            }
        }
        var titles = doc.select(".entry-title")[0].text()
        var chap = replaceStringForChapter(titles)

        var title = titles
        var split = title.split("Komik ")
        if(split.size > 1){
            title = split[1]
        }
        split = title.split(" Chapter ")
        if(split.size > 1){
            title = split[0]
        }
        var chPage = ComicChapterPageList()
        chPage.title = title
        chPage.currentChap = chap
        chPage.urlChapter = url
        chPage.pagePrev = if(!prev.equals("")) prev else null
        chPage.pageAll = all
        chPage.pageNext = if(!next.equals("")) next else null
        chPage.list = listThumb
        return chPage

    }
}