package mapan.developer.macakomik.data.datasource.local.sharepreference

import android.content.Context
import android.content.SharedPreferences

/***
 * Created By Mohammad Toriq on 15/01/2024
 */
class AppSharePreference{
    companion object {
        const val BASE = "SOURCE_MACA_PREF"
        const val KEY_ID = "ID"
        const val KEY_SOURCE_WEBSITE = "SOURCE_WEBSITE"
    }

    fun getSharePreferences(context: Context) : SharedPreferences{
        return context.getSharedPreferences(BASE, Context.MODE_PRIVATE)
    }
}