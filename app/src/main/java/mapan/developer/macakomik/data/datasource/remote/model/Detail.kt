package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class Detail(
    @SerializedName("name")
    var name : String?= null,

    @SerializedName("value")
    var value : String?= null
)
