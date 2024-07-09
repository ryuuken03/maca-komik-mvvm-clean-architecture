package mapan.developer.macakomik.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mapan.developer.macakomik.data.datasource.remote.GoogleSheetsApiService
import mapan.developer.macakomik.data.datasource.remote.RequestInterceptor
import mapan.developer.macakomik.data.datasource.remote.RequestInterceptorGoogleSheets
import mapan.developer.macakomik.data.datasource.remote.ShinigamiApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/***
 * Created By Mohammad Toriq on 03/01/2024
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Provides
    @Singleton
    fun provideShinigamiApiService(converterFactory: GsonConverterFactory,logging: HttpLoggingInterceptor): ShinigamiApiService {
        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(logging)
            .connectTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(ShinigamiApiService.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(ShinigamiApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideGoogleSheetsApiService(converterFactory: GsonConverterFactory, logging: HttpLoggingInterceptor): GoogleSheetsApiService {
        var okHttpClient = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptorGoogleSheets  ())
            .addInterceptor(logging)
            .connectTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(GoogleSheetsApiService.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(GoogleSheetsApiService::class.java)
    }
}
