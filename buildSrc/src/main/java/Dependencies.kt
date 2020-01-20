object Dependencies {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val junit = "junit:junit:${Variables.junitVersion}"

    object AndroidTest {
        const val junit = "androidx.test.ext:junit:${Variables.junitAndroidVersion}"
        const val espresso = "androidx.test.espresso:espresso-core:${Variables.espressoVersion}"
    }

    object Dagger {
        const val dagger = "com.google.dagger:dagger:${Variables.daggerVersion}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Variables.daggerVersion}"
        const val daggerAndroidSupport =
            "com.google.dagger:dagger-android-support:${Variables.daggerVersion}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Variables.daggerVersion}"
        const val daggerAndroidProcessor =
            "com.google.dagger:dagger-android-processor:${Variables.daggerVersion}"
    }

    object AndroidX {
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Variables.recyclerViewVersion}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Variables.constraintLayoutVersion}"
        const val legacySupport =
            "androidx.legacy:legacy-support-v4:${Variables.legacySupportVersion}"
        const val lifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Variables.lifecycleVersion}"
        const val livecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Variables.lifecycleVersion}"
    }

    object Coroutines {
        const val core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Variables.coroutinesVersion}"

        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Variables.coroutinesVersion}"
    }

    object Utils {
        const val threetenbp = "org.threeten:threetenbp:${Variables.threetenbpVersion}"
    }

    object Gson {
        const val gson = "com.google.code.gson:gson:${Variables.gsonVersion}"
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Variables.kotlinVersion}"
    }

    object OkHttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Variables.okhttpVersion}"

        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Variables.okhttpVersion}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Variables.retrofitVersion}"

        const val converter = "com.squareup.retrofit2:converter-gson:${Variables.retrofitVersion}"
    }
}