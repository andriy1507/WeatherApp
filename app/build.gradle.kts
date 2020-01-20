plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.2"
    defaultConfig {
        applicationId = "com.goryachok.forecastapp"
        minSdkVersion(15)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false

            buildConfigField("String", "API_KEY", "\"f19f55d718abb04df88d9190337bd5b9\"")
            buildConfigField("String", "DOMAIN", "\"https://api.openweathermap.org/\"")
            buildConfigField("String", "TAG", "\"DAGGER_DEBUG\"")
            buildConfigField("Integer", "HOUR", "3600")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8
//    }
}


androidExtensions {
    isExperimental = true
}

dependencies {

    //    This seems to be unnecessary
//    implementation(fileTree(dir("libs"), include("*.jar")))

//    AndroidX
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.lifecycleExtensions)
    implementation(Dependencies.AndroidX.livecycleViewModel)
    implementation(Dependencies.AndroidX.recyclerView)

//    Coroutines
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

//    Utils
    implementation(Dependencies.Utils.threetenbp)

//    Gson
    implementation(Dependencies.Gson.gson)

//    Kotlin
    implementation(Dependencies.Kotlin.kotlin)

//    OkHttp
    implementation(Dependencies.OkHttp.okhttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

//    Dagger
    implementation(Dependencies.Dagger.dagger)
    implementation(Dependencies.Dagger.daggerAndroid)
    implementation(Dependencies.Dagger.daggerAndroidSupport)
    kapt(Dependencies.Dagger.daggerAndroidProcessor)
    kapt(Dependencies.Dagger.daggerCompiler)

//    Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.converter)

//    Tests
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.AndroidTest.junit)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
}