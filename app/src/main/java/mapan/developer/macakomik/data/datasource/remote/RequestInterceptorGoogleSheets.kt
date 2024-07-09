package mapan.developer.macakomik.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.Response

/***
 * Created By Mohammad Toriq on 11/01/2024
 */
class RequestInterceptorGoogleSheets : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter("key",GoogleSheetsApiService.KEY)
            .build()
        val request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}