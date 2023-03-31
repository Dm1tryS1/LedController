plugins {
    id("commonAndroid")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Feature.information.api))
    implementation(project(Modules.Feature.system.api))

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.fragment)

    implementation(Deps.Coroutines.core)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.Koin.koinCore)

    implementation(Deps.recycler)

    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.gson)

    implementation(project(Modules.Common.sharedPreferences))
    implementation(project(Modules.Common.storage))
    implementation(project(Modules.Common.network))

    api(Deps.serializationJson)

    implementation(Deps.Room.runtime)
    kapt(Deps.Room.compiler)
    implementation(Deps.Room.ktx)
}