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

    //    implementation(fileTree(dir("libs"), include("*.jar")))
//    implementation(appDependencies.kotlin)
//    implementation(appDependencies.android)
//    implementation(appDependencies.ui)
//    implementation(appDependencies.gson)
//    implementation(appDependencies.retrofit)
//    implementation(appDependencies.okhttp)
//    implementation(appDependencies.coroutines)
//    implementation(appDependencies.utils)
//    implementation(appDependencies.dagger)
//    kapt(appDependencies.daggerCompiler)
//
//    testImplementation(appDependencies.test)
//    androidTestImplementation(appDependencies.androidTest)
}