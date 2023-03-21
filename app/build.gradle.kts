plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id("kotlin-kapt")
}

@Suppress("UnstableApiUsage")
android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = Config.applicationId
}

dependencies {
    implementation(Deps.Navigation.navigation)
    implementation(Deps.Navigation.cicerone)

    implementation(Deps.animation)

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)

    testImplementation(Deps.Test.jUnit)
    androidTestImplementation(Deps.Test.test)
    androidTestImplementation(Deps.Test.espresso)

    implementation(Deps.recycler)

    implementation(Deps.zxing)

    implementation(Deps.graphic)

    implementation(Deps.serialiazation)

    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.gson)
    implementation(Deps.Network.okHttpInterceptor)
    implementation(Deps.Network.okHttp)

    implementation(Deps.esptouch)

    implementation(Deps.lottie)

    implementation(Deps.Room.runtime)
    kapt(Deps.Room.compiler)
    implementation(Deps.Room.ktx)
}