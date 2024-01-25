package mapan.developer.macakomik

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/***
 * Created By Mohammad Toriq on 02/01/2024
 */
@HiltAndroidApp
//class MyApplication : Application(){
class MyApplication : Application(), ImageLoaderFactory{
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
//            .okHttpClien
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
//            .logger(DebugLogger())
            .build()
    }
}