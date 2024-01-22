package mapan.developer.macakomik.data.model

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class ComicChapterPageList (
    var title: String?= null,

    var currentChap: String?= null,

    var urlChapter: String?= null,

    var prevChap: String?= null,

    var pagePrev: String?= null,

    var nextChap: String?= null,

    var pageNext: String?= null,

    var pageAll: String?= null,

    var list: ArrayList<ComicChapterPage>?= null
)