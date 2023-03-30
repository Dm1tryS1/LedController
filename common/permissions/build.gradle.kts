plugins {
    id("commonAndroid")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(Modules.Common.core))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)

    implementation(Deps.Navigation.navigation)
    implementation(Deps.Navigation.cicerone)

    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.AndroidX.fragment)

    implementation(Deps.lottie)
}