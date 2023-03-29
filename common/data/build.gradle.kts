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
    api(Deps.serializationJson)
}