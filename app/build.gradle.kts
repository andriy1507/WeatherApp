import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}
android {
    compileSdkVersion(Variables.targetSdkVersion)
    buildToolsVersion = Variables.toolsVersion
    defaultConfig {
        applicationId = Variables.appId
        minSdkVersion(Variables.minimalSdkVersion)
        targetSdkVersion(Variables.targetSdkVersion)
        versionCode = Variables.versionCode
        versionName = Variables.versionName
        testInstrumentationRunner = Variables.testRunner
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"f19f55d718abb04df88d9190337bd5b9\"")
            buildConfigField("String", "DOMAIN", "\"https://api.openweathermap.org/\"")
            buildConfigField("Integer", "HOUR", "3600")
        }
        getByName("debug") {
            isMinifyEnabled = false

            buildConfigField("String", "API_KEY", "\"f19f55d718abb04df88d9190337bd5b9\"")
            buildConfigField("String", "DOMAIN", "\"https://api.openweathermap.org/\"")
            buildConfigField("Integer", "HOUR", "3600")
            buildConfigField("String", "TAG", "\"DAGGER_DEBUG\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks {
        withType(KotlinCompile::class) {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}


androidExtensions {
    isExperimental = true
}

dependencies {

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