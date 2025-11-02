plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = AndroidConfig.applicationId
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Modules
    implementation(project(":core:core-common"))
    implementation(project(":core:core-ui"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-database"))
    implementation(project(":core:core-analytics"))

    implementation(project(":feature:feature-feed"))
    implementation(project(":feature:feature-profile"))
    implementation(project(":feature:feature-editor"))
    implementation(project(":feature:feature-notifications"))
    implementation(project(":feature:feature-search"))

    // Android
    implementation(Libs.androidxCore)
    implementation(Libs.androidxAppCompat)
    implementation(Libs.androidxActivity)

    // Compose
    implementation(platform(Libs.composeBom))
    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial3)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.composeActivity)
    implementation(Libs.composeNavigation)
    debugImplementation(Libs.composeUiTooling)

    // Hilt
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)
    implementation(Libs.hiltNavigationCompose)

    // Firebase
    implementation(platform(Libs.firebaseBom))
    implementation(Libs.firebaseMessaging)
    implementation(Libs.firebaseAnalytics)

    // Testing
    testImplementation(Libs.junit)
    testImplementation(Libs.mockk)
    testImplementation(Libs.coroutinesTest)
    androidTestImplementation(Libs.androidXTestRunner)
    androidTestImplementation(Libs.espressoCore)
    androidTestImplementation(Libs.composeTest)
    debugImplementation(Libs.composeTestManifest)
}