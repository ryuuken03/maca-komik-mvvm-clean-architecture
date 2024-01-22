package mapan.developer.macakomik.data.model

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class ComicHome (

    var popular: ArrayList<ComicThumbnail>?= ArrayList(),

    var list: ArrayList<ComicThumbnail>?= ArrayList(),

    var message : String?= null
)