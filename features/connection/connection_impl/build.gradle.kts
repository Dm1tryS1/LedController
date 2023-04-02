plugins {
    id("commonAndroid")
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
    implementation(project(Modules.Feature.connection.api))

    implementation(Deps.Coroutines.core)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.Koin.koinCore)

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.fragment)

    api(Deps.serializationJson)

    implementation(Deps.recycler)

    implementation(Deps.esptouch)

    implementation(project(Modules.Common.network))
    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.gson)
}