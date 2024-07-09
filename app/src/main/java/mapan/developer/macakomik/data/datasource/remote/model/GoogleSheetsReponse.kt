package mapan.developer.macakomik.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
data class GoogleSheetsReponse(
    @SerializedName("range")
    var range : String?= null,

    @SerializedName("majorDimension")
    var majorDimension : String?= null,

    @SerializedName("hotList")
    var values : ArrayList<ArrayList<String>> = ArrayList(),
)
