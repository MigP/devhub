object Versions {
    const val kotlin = "1.9.20"
    const val compose = "1.5.4"
    const val composeCompiler = "1.5.4"
    const val hilt = "2.48.1"
    const val coroutines = "1.7.3"
    const val lifecycle = "2.6.2"
    const val room = "2.6.0"
    const val retrofit = "2.9.0"
    const val okhttp = "4.12.0"
    const val navigation = "2.7.5"

    // Testing
    const val junit = "4.13.2"
    const val mockk = "1.13.8"
    const val turbine = "1.0.0"
    const val espresso = "3.5.1"
    const val androidXTest = "1.5.0"
}

object Libs {
    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Android
    const val androidxCore = "androidx.core:core-ktx:1.12.0"
    const val androidxAppCompat = "androidx.appcompat:appcompat:1.6.1"
    const val androidxActivity = "androidx.activity:activity-ktx:1.8.1"
    const val androidxFragment = "androidx.fragment:fragment-ktx:1.6.2"

    // Compose
    const val composeBom = "androidx.compose:compose-bom:2023.10.01"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeActivity = "androidx.activity:activity-compose:1.8.1"
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"

    // Lifecycle
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.1.0"

    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    // Navigation
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:32.6.0"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"

    // Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val androidXTestCore = "androidx.test:core:${Versions.androidXTest}"
    const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
    const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val composeTest = "androidx.compose.ui:ui-test-junit4"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"
}