plugins {
    id("commonAndroid")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.Common.data))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)

    implementation(Deps.graphic)

    implementation(Deps.Coroutines.core)
    implementation(Deps.AndroidX.fragment)
    implementation(Deps.recycler)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.lottie)

    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.navigation)
}