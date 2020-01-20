buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Dependencies.gradle)
        classpath(Dependencies.kotlinPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
