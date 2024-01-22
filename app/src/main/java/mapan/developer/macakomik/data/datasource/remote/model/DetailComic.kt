package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class DetailComic(
    @SerializedName("synopsis")
    var synopsis : String?= null,

    @SerializedName("detailList")
    var detailList : ArrayList<Detail>?= null,

    @SerializedName("chapterList")
    var chapterList : ArrayList<ChapterComic>?= null,

    @SerializedName("related")
    var related : ArrayList<Comic>?= null,
)
