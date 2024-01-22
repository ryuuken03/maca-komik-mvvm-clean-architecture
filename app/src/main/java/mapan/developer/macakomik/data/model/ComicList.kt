package mapan.developer.macakomik.data.model

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
class ComicList {

    var pageNextUrl: String?= null

    var list: ArrayList<ComicThumbnail>?= ArrayList()

    var genres: ArrayList<ComicFilter>?= ArrayList()

    var types: ArrayList<ComicFilter>?= ArrayList()

    var orderbys: ArrayList<ComicFilter>?= ArrayList()
}