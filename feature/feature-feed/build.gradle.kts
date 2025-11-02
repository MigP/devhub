plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.training.devhub.feature.feed"
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":core:core-ui"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-database"))

    // Compose
    implementation(platform(Libs.composeBom))
    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial3)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.composeViewModel)

    // Hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)

    // Lifecycle
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleRuntime)

    // Coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    // Testing
    testImplementation(Libs.junit)
    testImplementation(Libs.mockk)
    testImplementation(Libs.coroutinesTest)
    testImplementation(Libs.turbine)
    androidTestImplementation(Libs.composeTest)
}