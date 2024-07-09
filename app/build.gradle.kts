plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "mapan.developer.macakomik"
    compileSdk = 34

    defaultConfig {
        applicationId = "mapan.developer.macakomik"
        minSdk = 24
        targetSdk = 33
        versionCode = 3
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Deps.core)
    implementation(Deps.appcompat)
    implementation(Deps.androidMaterial)
    implementation(Deps.coreSplashscreen)
    implementation(CoroutinesLifecycleScope.lifeCycleRuntime)
    implementation(CoroutinesLifecycleScope.lifeCycleRuntimeCompose)
    implementation(CoroutinesLifecycleScope.lifeCycleViewModel)
    implementation(JetpackCompose.composeActivity)
    implementation(platform(JetpackCompose.composeBom))
    implementation(JetpackCompose.composeUi)
    implementation(JetpackCompose.composeUiGraphics)
    implementation(JetpackCompose.composeUiToolingPreview)
    implementation(JetpackCompose.composeMaterial3)

    testImplementation(TestImplementation.junit)
    androidTestImplementation(AndroidTestImplementation.junit)
    androidTestImplementation(AndroidTestImplementation.espresso)

    androidTestImplementation(platform(JetpackCompose.composeBom))
    androidTestImplementation(ComposeAndroidTestImplementation.composeUiTest)
    debugImplementation(ComposeDebugImplementation.toolingUi)
    debugImplementation(ComposeDebugImplementation.manifestTest)

    implementation(Room.room)
    implementation(Room.roomRuntime)
    kapt(Room.roomCompiler)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.okhttp)
    implementation(Retrofit.okhttpLogging)
    implementation(Retrofit.gsonConverter)

    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hiltCompiler)
    implementation(DaggerHilt.hiltComposeNavigation)

    implementation(JetpackCompose.navigation)

    implementation(GoogleAccompanist.swiperefresh)
    implementation(GoogleAccompanist.systemuicontroller)

    implementation(Coil.coil)
    implementation(Jsoup.jsoup)
    implementation(platform(FirebaseDEP.firebaseBom))
    implementation(FirebaseDEP.firestore)
    implementation(Admob.admob)
    implementation("com.zhkrb.cloudflare-scrape-android:scrape-webview:0.0.4")
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}