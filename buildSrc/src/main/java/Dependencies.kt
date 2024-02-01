/***
 * Created By Mohammad Toriq on 20/12/2023
 */

object Version{
    const val core = "1.9.0"
    const val appcompat = "1.6.1"
    const val androidMaterial = "1.11.0"

    const val coroutinesLifecycleScope = "2.6.2"

    const val dagger = "2.45"
    const val hiltComposeNavigation = "1.0.0"

    const val roomVersion = "2.6.0"

    const val composeActivity = "1.8.2"
    const val composeBom = "2023.03.00"
    const val composeNavigation = "2.5.3"

    //TestImplementation
    const val junit = "4.13.2"

    const val testJunit = "1.1.5"
    const val testEspresso = "3.5.1"

    const val retrofit = "2.9.0"
    const val gsonConverter = "2.9.0"
    const val okhttp = "4.11.0"

    const val coil = "2.5.0"

    const val swipeRefresh = "0.23.1"
    const val systemUiController = "0.33.2-alpha"

    const val jsoup = "1.17.2"
    const val firebase = "32.7.1"
}

object Deps{
    const val core = "androidx.core:core-ktx:${Version.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Version.androidMaterial}"
}

object CoroutinesLifecycleScope{
    const val lifeCycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.coroutinesLifecycleScope}"
    const val lifeCycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Version.coroutinesLifecycleScope}"
    const val lifeCycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Version.coroutinesLifecycleScope}"
}
object DaggerHilt{
    const val hilt = "com.google.dagger:hilt-android:${Version.dagger}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Version.dagger}"
    const val hiltComposeNavigation =
        "androidx.hilt:hilt-navigation-compose:${Version.hiltComposeNavigation}"
}
object Room{
    const val roomCompiler =
        "androidx.room:room-compiler:${Version.roomVersion}"
    const val room =
        "androidx.room:room-ktx:${Version.roomVersion}"
    const val roomRuntime =
        "androidx.room:room-runtime:${Version.roomVersion}"
}
object JetpackCompose{
    const val composeActivity = "androidx.activity:activity-compose:${Version.composeActivity}"
    const val composeBom = "androidx.compose:compose-bom:${Version.composeBom}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val navigation = "androidx.navigation:navigation-compose:${Version.composeNavigation}"
}
object TestImplementation{
    const val junit = "junit:junit:${Version.junit}"
}
object AndroidTestImplementation{
    const val junit = "androidx.test.ext:junit:${Version.testJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.testEspresso}"
}
object ComposeAndroidTestImplementation{
    //    const val composeBom =
//        "androidx.compose:compose-bom:${Version.composeBom}"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4"
}
object ComposeDebugImplementation{
    const val toolingUi = "androidx.compose.ui:ui-tooling"
    const val manifestTest = "androidx.compose.ui:ui-test-manifest"
}
object Retrofit{
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.gsonConverter}"
}
object Coil{
    const val coil = "io.coil-kt:coil-compose:${Version.coil}"
}

object GoogleAccompanist{
    const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:${Version.swipeRefresh}"
    const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:${Version.systemUiController}"
}
object Jsoup{
    const val jsoup = "org.jsoup:jsoup:${Version.jsoup}"
}
object FirebaseDEP{
    const val firebaseBom = "com.google.firebase:firebase-bom:${Version.firebase}"
    const val firestore = "com.google.firebase:firebase-firestore"
}