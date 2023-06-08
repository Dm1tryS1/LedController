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
    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.storage))

    implementation(project(Modules.Feature.charts.api))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.fragment)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.Koin.koinCore)

    implementation(Deps.graphic)

    implementation(Deps.Room.runtime)
    kapt(Deps.Room.compiler)
    implementation(Deps.Room.ktx)
}