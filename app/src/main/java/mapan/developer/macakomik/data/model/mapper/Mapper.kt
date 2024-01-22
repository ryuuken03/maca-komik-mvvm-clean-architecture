package mapan.developer.macakomik.data.model.mapper

import android.util.Log
import mapan.developer.macakomik.data.datasource.remote.model.Browse
import mapan.developer.macakomik.data.datasource.remote.model.ChapterComic
import mapan.developer.macakomik.data.datasource.remote.model.Comic
import mapan.developer.macakomik.data.datasource.remote.model.DetailComic
import mapan.developer.macakomik.data.datasource.remote.model.ImageList
import mapan.developer.macakomik.data.model.ComicChapter
import mapan.developer.macakomik.data.model.ComicChapterPage
import mapan.developer.macakomik.data.model.ComicChapterPageList
import mapan.developer.macakomik.data.model.ComicDetail
import mapan.developer.macakomik.data.model.ComicFilter
import mapan.developer.macakomik.data.model.ComicHome
import mapan.developer.macakomik.data.model.ComicList
import mapan.developer.macakomik.data.model.ComicThumbnail

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
object Mapper {
    fun mapFromBrowseAPIToComicHome(browse: Browse) : ComicHome{
        var result = ComicHome()
        var popular = ArrayList<ComicThumbnail>()
        var list = ArrayList<ComicThumbnail>()
        browse.hotList?.forEach { comic ->
            popular.add(mapFromComicAPIToComicThumbnail(comic))
        }
        browse.newsList?.forEach { comic ->
            list.add(mapFromComicAPIToComicThumbnail(comic))
        }
        result.popular = popular
        result.list = list

        return result
    }

    fun mapFromComicAPIToComicThumbnail(comic: Comic) =
        ComicThumbnail(
            url = comic.url,
            type = null,
            imgSrc = comic.cover,
            title = comic.title,
            lastChap  = comic.latestChapter,
            urlLastChap = comic.latestChapterUrl)

    fun mapFromDetailComicAPIToComicDetail(detailComic: DetailComic) : ComicDetail{
        var genre = ""
        var type = ""
        var author = ""
        var title = ""
        for(data in detailComic.detailList!!){
            if(data.name!!.equals("Rating",true)){
                var split = data.value!!.split("Average")
                title = split[0]
            }
            if(data.name!!.contains("Genre",true)){
                genre = data.value!!
            }
            if(data.name!!.contains("Type",true)){
                type = "Type: "+data.value!!
            }
            if(data.name!!.contains("Author",true)){
                author = "Author: "+data.value!!
            }
        }
        var chapters = ArrayList<ComicChapter>()
        for(i in  0 .. detailComic.chapterList!!.size-1){
            var data = detailComic.chapterList!![i]
            chapters.add(mapFromChapterComicAPIToComicChapter(data))
        }
        var result = ComicDetail(
            title = title,
            type = type,
            imgSrc = null,
            release = author,
            genre = genre,
            synopsis = detailComic.synopsis,
            list = chapters
        )
        return result
    }

    fun mapFromChapterComicAPIToComicChapter(chapterComic: ChapterComic) =
        ComicChapter(
            name = chapterComic.title!!.split(" - ")[0],
            time = chapterComic.releaseDate,
            url = chapterComic.url,)

    fun mapFromImageListAPIToComicChapterPageList(
        detailComic: DetailComic,
        imageList: ImageList,
        currentUrl:String,
        pageAll:String) : ComicChapterPageList{
        var pagePrev = ""
        var prevChap = ""
        var pageNext = ""
        var nextChap = ""
        var title = ""
        for(data in detailComic.detailList!!){
            if(data.name!!.equals("Rating",true)){
                var split = data.value!!.split("Average")
                title = split[0]
            }
        }
        var chapters = detailComic?.chapterList!!
        var currentChapter = ""
        for(i in 0 ..  chapters.size-1){
            if(chapters[i].url.equals(currentUrl)){
                currentChapter = chapters[i].title!!
                if(i == 0){
                    pagePrev = chapters[i+1].url!!
                    prevChap = chapters[i+1].title!!
                }else if(i > 0 && i < chapters.size-1){
                    pagePrev = chapters[i+1].url!!
                    prevChap = chapters[i+1].title!!
                    pageNext = chapters[i-1].url!!
                    nextChap = chapters[i-1].title!!
                }else{
                    pageNext = chapters[i-1].url!!
                    nextChap = chapters[i-1].title!!
                }
            }
        }
        var pages = ArrayList<ComicChapterPage>()
        imageList.imageList?.forEach { img ->
            var data = ComicChapterPage()
            data.imgSrc = img
            pages.add(data)
        }
        var result = ComicChapterPageList(
            title = title,
            currentChap = currentChapter,
            urlChapter = currentUrl,
            pagePrev =if (pagePrev.equals("")) null else pagePrev,
            prevChap =if (prevChap.equals("")) null else prevChap,
            pageNext =if (pageNext.equals("")) null else pageNext,
            nextChap =if (nextChap.equals("")) null else nextChap,
            pageAll = pageAll,
            list = pages
        )
        return result
    }

    fun mapFromArrayListComicToComicList(list : ArrayList<Comic>) : ComicList{
        var listThumb = ArrayList<ComicThumbnail>()

        list.forEach {
            listThumb.add(mapFromComicAPIToComicThumbnail(it))
        }

        var result = ComicList()
        result.pageNextUrl = null
        result.list = listThumb
        result.genres = ArrayList()
        result.types = ArrayList()
        result.orderbys = ArrayList()
        return result
    }

}