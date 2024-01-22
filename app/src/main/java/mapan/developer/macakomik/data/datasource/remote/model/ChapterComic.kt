package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class ChapterComic(
    @SerializedName("title")
    var title : String?= null,

    @SerializedName("url")
    var url : String?= null,

    @SerializedName("cover")
    var cover : String?= null,

    @SerializedName("releaseDate")
    var releaseDate : String?= null,
)
