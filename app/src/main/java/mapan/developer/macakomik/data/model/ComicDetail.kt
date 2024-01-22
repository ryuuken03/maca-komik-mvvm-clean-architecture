package mapan.developer.macakomik.data.model

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class ComicDetail (
    var title: String?= null,

    var type: String?= null,

    var imgSrc: String?= null,

    var release: String?= null,

    var genre: String?= null,

    var synopsis: String?= null,

    var list: ArrayList<ComicChapter>?= null
)