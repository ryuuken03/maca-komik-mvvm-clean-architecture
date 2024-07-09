package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class Browse(
    @SerializedName("hotList")
    var hotList : ArrayList<Comic>?= null,

    @SerializedName("newsList")
    var newsList : ArrayList<Comic>?= null,

    @SerializedName("trendingList")
    var trendingList : ArrayList<Comic>?= null
)
