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
    implementation(project(Modules.Feature.system.api))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.fragment)

    implementation(project(Modules.Common.sharedPreferences))
    implementation(project(Modules.Common.network))

    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.gson)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.Koin.koinCore)
}