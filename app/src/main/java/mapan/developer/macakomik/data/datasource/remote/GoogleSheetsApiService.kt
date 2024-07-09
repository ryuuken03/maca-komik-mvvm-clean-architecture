package mapan.developer.macakomik.data.datasource.remote

import mapan.developer.macakomik.data.datasource.remote.model.GoogleSheetsReponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
interface GoogleSheetsApiService {
    companion object {
        const val BASE_URL = "https://sheets.googleapis.com/"
        const val KEY = "AIzaSyCWVr3IqOgBXXedokEeYHrI7uvay1Ms5DE"
        const val SPREADSHEET_ID = "13rp1mHEC_ZJ-hgu1J-o4ebQxwARMF8fBJwNL2F5hR1k"
    }

    @GET("v4/spreadsheets/{spreadsheetId}/values/{range}")
    suspend fun getSource(
        @Path("spreadsheetId") spreadsheetId : String = SPREADSHEET_ID,
        @Path("range") range : String = "source!A2:Z1000",
        @Query("key") key : String = KEY,
        @Query("majorDimension") majorDimension : String,
    ): GoogleSheetsReponse

}