package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class  Comic(
    @SerializedName("title")
    var title : String?= null,

    @SerializedName("url")
    var url : String?= null,

    @SerializedName("cover")
    var cover : String?= null,

    @SerializedName("latestChapter")
    var latestChapter : String?= null,

    @SerializedName("latestChapterUrl")
    var latestChapterUrl : String?= null,

    @SerializedName("rating")
    var rating : Double?= null,
)
