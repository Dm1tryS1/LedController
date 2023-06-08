plugins {
    id("commonAndroid")
    id("kotlin-kapt")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.Room.runtime)
    kapt(Deps.Room.compiler)
    implementation(Deps.Room.ktx)
}