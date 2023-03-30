plugins {
    id("commonAndroid")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)
}