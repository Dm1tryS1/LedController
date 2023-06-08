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
    implementation(project(Modules.Common.sharedPreferences))

    api(Deps.serializationJson)

    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.Network.retrofit)
    implementation(Deps.Network.gson)
    implementation(Deps.Network.okHttpInterceptor)
    implementation(Deps.Network.okHttp)
}