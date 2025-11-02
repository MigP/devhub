# Android Developer Training Programme - Claude Code Guide

## Project: DevHub - Developer Community App
A comprehensive training project covering modern Android development practices.

---

## 📘 Using Claude Code

**Important:** When users ask about Claude Code features, commands, shortcuts, or workflows, direct them to **`CLAUDE_CHEATSHEET.md`** in the project root. This comprehensive guide covers:
- Privacy & data sharing
- Keyboard shortcuts and commands
- Permission modes
- Custom slash commands
- Memory vs Settings
- Security features
- Best practices

---

## 🎯 Project Structure & Learning Modules

### Module Overview
Each feature module focuses on specific Android concepts:

```
app/
├── core/                          # Core utilities, base classes
│   ├── network/                   # Networking (Retrofit, OkHttp)
│   ├── database/                  # Room database
│   ├── analytics/                 # Tracking implementation
│   └── accessibility/             # Accessibility utilities
├── core-ui/                       # Shared UI components (Compose)
├── feature/
│   ├── feed/                      # Article feed (MVI + Compose)
│   ├── profile/                   # User profile (MVVM + Fragments)
│   ├── editor/                    # Content editor (TDD focused)
│   ├── notifications/             # Push notifications
│   └── search/                    # Search functionality
├── sdk-devhub/                    # Public SDK module
└── shared/                        # KMM shared code (optional)
```

---

## 📚 Training Phases & Implementation Guide

### Phase 1: Architecture Foundation (Weeks 1-2)

#### Topic 1.1: MVVM vs MVI Architecture
**Goal**: Implement both patterns in different features to understand trade-offs

**Feature: Profile (MVVM)**
- Use MVVM for the profile feature
- ViewModel with LiveData/StateFlow
- Single state object vs multiple LiveData streams
- Handle user actions through methods

**Feature: Feed (MVI)**
- Use MVI for the feed feature
- Single immutable state
- Sealed class for user intents
- Unidirectional data flow

**Claude Code Instructions**:
- When working on `feature/profile`, enforce MVVM patterns
- When working on `feature/feed`, enforce MVI patterns
- Always explain the architectural choice for each feature
- Suggest refactoring opportunities between patterns

**Key Files**:
```
feature/profile/ProfileViewModel.kt        # MVVM example
feature/feed/FeedViewModel.kt              # MVI example
feature/feed/FeedContract.kt               # MVI state/intent/effect
```

#### Topic 1.2: ViewModels & Lifecycle
**Focus Areas**:
- ViewModel scope (ViewModelScope, lifecycleScope)
- SavedStateHandle for process death
- ViewModelFactory with Hilt
- Sharing ViewModels between fragments

**Practice Tasks**:
1. Implement SavedStateHandle in ProfileViewModel
2. Create shared ViewModel for multi-step flows
3. Handle configuration changes properly

---

### Phase 2: Modern UI Development (Weeks 3-4)

#### Topic 2.1: Jetpack Compose
**Goal**: Build modern UI with Compose fundamentals

**Features to Build**:
- Feed screen (LazyColumn, pull-to-refresh)
- Article detail (responsive layout)
- Comments section (nested composition)
- Custom theme system

**Claude Code Instructions**:
- Prioritise Compose for all new UI
- Use remember, rememberSaveable appropriately
- Implement proper state hoisting
- Create reusable composable functions in core-ui
- Always consider recomposition performance

**Key Concepts to Practice**:
- State management (remember, mutableStateOf)
- Side effects (LaunchedEffect, DisposableEffect)
- CompositionLocal for theme/dependencies
- Modifier chains and custom modifiers
- Preview annotations with different states

#### Topic 2.2: Fragments (Legacy Understanding)
**Goal**: Understand fragments for maintaining existing code

**Features Using Fragments**:
- Profile screen (demonstrate legacy approach)
- Settings screen

**Practice Tasks**:
1. Fragment lifecycle methods
2. FragmentManager transactions
3. Communication via ViewModel
4. Safe Args navigation
5. Plan migration path to Compose

#### Topic 2.3: Navigation
**Both Navigation Component and Compose Navigation**

**Implementation**:
```
navigation/
├── NavGraph.kt                    # Compose navigation
├── fragments/                     # XML navigation graph
└── deeplinks/                     # Deep link handling
```

**Practice Tasks**:
1. Set up Compose Navigation
2. Implement deep linking
3. Pass complex objects between screens
4. Handle back stack properly

---

### Phase 3: Dependency Injection (Weeks 5-6)

#### Topic 3.1: Dagger-Hilt
**Goal**: Proper DI setup across all modules

**Claude Code Instructions**:
- Always use Hilt for dependency injection
- Create proper module structures (NetworkModule, DatabaseModule, etc.)
- Use appropriate scopes (@Singleton, @ViewModelScoped, @ActivityRetainedScoped)
- Inject ViewModels using @HiltViewModel
- Provide test doubles in test modules

**Module Structure**:
```kotlin
// NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { /* ... */ }
}

// ViewModels
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel()
```

**Practice Areas**:
1. Multi-module Hilt setup
2. Providing different implementations (dev vs prod)
3. Assisted injection for factories
4. Testing with Hilt

#### Topic 3.2: Creating an SDK
**Goal**: Extract core functionality into a reusable SDK

**SDK Module**: `sdk-devhub`
- Authentication
- API client
- Data models
- Public interfaces

**Claude Code Instructions**:
- Keep SDK module framework-agnostic (no Android dependencies where possible)
- Design clean public API
- Use semantic versioning
- Provide clear documentation
- Include consumer ProGuard rules

**SDK Structure**:
```
sdk-devhub/
├── src/main/
│   ├── java/com/devhub/sdk/
│   │   ├── DevHubClient.kt           # Main entry point
│   │   ├── auth/                      # Authentication
│   │   ├── api/                       # API interfaces
│   │   └── models/                    # Data models
│   └── proguard-rules.pro
└── build.gradle.kts                   # Publishing config
```

---

### Phase 4: Reactive Programming (Weeks 7-8)

#### Topic 4.1: Coroutines
**Goal**: Master async programming with coroutines

**Claude Code Instructions**:
- Use `suspend` functions for async operations
- Launch coroutines in appropriate scopes
- Use proper dispatchers (IO, Default, Main)
- Implement structured concurrency
- Handle cancellation properly
- Use `supervisorScope` when appropriate

**Practice Areas**:
1. Repository layer with suspend functions
2. Parallel API calls with async/await
3. Exception handling with try-catch and CoroutineExceptionHandler
4. Flow operators (map, filter, combine, etc.)
5. Testing coroutines with runTest

#### Topic 4.2: StateFlow vs Flow vs SharedFlow vs LiveData
**Goal**: Choose the right reactive type for each use case

**Usage Guidelines**:
- **StateFlow**: UI state (always has value, conflation)
- **SharedFlow**: One-time events (navigation, toasts)
- **Flow**: Streams of data (database queries, API responses)
- **LiveData**: Legacy support only

**Claude Code Instructions**:
```kotlin
// ViewModel pattern
class FeedViewModel : ViewModel() {
    // UI State - StateFlow
    private val _state = MutableStateFlow(FeedState())
    val state: StateFlow<FeedState> = _state.asStateFlow()

    // One-time events - SharedFlow
    private val _events = MutableSharedFlow<FeedEvent>()
    val events: SharedFlow<FeedEvent> = _events.asSharedFlow()

    // Data streams - Flow
    val articles: Flow<List<Article>> = repository.observeArticles()
}
```

**Practice Tasks**:
1. Convert LiveData to StateFlow
2. Implement event handling with SharedFlow
3. Use Flow operators for data transformation
4. Handle configuration changes properly

---

### Phase 5: Quality & Testing (Weeks 9-10)

#### Topic 5.1: Test-Driven Development (TDD)
**Goal**: Write tests before implementation

**Claude Code Instructions**:
- For new features, write tests FIRST
- Follow Red-Green-Refactor cycle
- Test business logic thoroughly
- Use descriptive test names (Given-When-Then)

**Feature: Editor Module (TDD-focused)**
Build the content editor using strict TDD:
1. Write failing test
2. Implement minimum code to pass
3. Refactor
4. Repeat

**Test Structure**:
```
test/
├── unit/
│   ├── viewmodel/                 # ViewModel tests
│   ├── repository/                # Repository tests
│   └── usecase/                   # Use case tests
├── integration/
│   └── database/                  # Database tests
└── ui/
    └── compose/                   # Compose UI tests
```

#### Topic 5.2: Android Testing
**Comprehensive testing strategy**

**Claude Code Instructions**:
```kotlin
// Unit Tests
@Test
fun `when user loads feed, then articles are displayed`() = runTest {
    // Given
    val articles = listOf(/* test data */)
    coEvery { repository.getArticles() } returns flowOf(articles)

    // When
    viewModel.loadFeed()

    // Then
    assertEquals(articles, viewModel.state.value.articles)
}

// Compose UI Tests
@Test
fun feedScreen_displaysArticles() {
    composeTestRule.setContent {
        FeedScreen(articles = testArticles)
    }

    composeTestRule.onNodeWithText("Test Article").assertIsDisplayed()
}
```

**Testing Checklist**:
- [ ] Unit tests for ViewModels (>80% coverage)
- [ ] Unit tests for repositories and use cases
- [ ] Integration tests for database
- [ ] UI tests for critical user flows
- [ ] Screenshot tests (optional)

#### Topic 5.3: Accessibility
**Goal**: Make the app usable for everyone

**Claude Code Instructions**:
- Always add contentDescription to images/icons
- Ensure minimum touch target size (48dp)
- Use semantic Compose modifiers
- Test with TalkBack
- Support system font scaling
- Maintain colour contrast ratios (WCAG AA)

**Compose Accessibility**:
```kotlin
@Composable
fun ArticleCard(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "Article: ${article.title}"
                onClick(label = "Read article") { /* ... */ }
            }
    ) {
        // Content
    }
}
```

**Accessibility Checklist**:
- [ ] All images have content descriptions
- [ ] Interactive elements have semantic labels
- [ ] Navigation is keyboard/switch accessible
- [ ] Content respects font scaling
- [ ] TalkBack tested on key flows

---

### Phase 6: System Integration (Weeks 11-12)

#### Topic 6.1: Intents & Intent Filters
**Goal**: Handle system integration and deep links

**Practice Areas**:
1. Explicit intents (start activities)
2. Implicit intents (share, view, etc.)
3. Intent filters in manifest
4. App Links / Deep Links
5. Receiving shared content

**Implementation**:
```xml
<!-- AndroidManifest.xml -->
<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="https"
          android:host="devhub.app"
          android:pathPrefix="/article" />
</intent-filter>
```

#### Topic 6.2: Push Notifications
**Goal**: Implement FCM notifications

**Claude Code Instructions**:
- Use Firebase Cloud Messaging
- Handle notifications in foreground/background
- Create notification channels (Android O+)
- Implement custom notification layouts
- Handle notification actions
- Track notification analytics

**Implementation Structure**:
```
notifications/
├── NotificationHelper.kt          # Notification builder
├── DevHubMessagingService.kt      # FCM service
├── NotificationChannels.kt        # Channel definitions
└── NotificationWorker.kt          # Background processing
```

#### Topic 6.3: Tracking & Analytics
**Goal**: Implement event tracking

**Claude Code Instructions**:
- Create analytics abstraction layer
- Track key user events
- Respect user privacy (GDPR)
- Use Firebase Analytics or similar
- Create debug/release implementations

**Analytics Pattern**:
```kotlin
interface AnalyticsTracker {
    fun trackEvent(event: AnalyticsEvent)
    fun setUserProperty(key: String, value: String)
}

sealed class AnalyticsEvent {
    data class ArticleViewed(val articleId: String) : AnalyticsEvent()
    data class ArticleShared(val articleId: String) : AnalyticsEvent()
    object FeedRefreshed : AnalyticsEvent()
}
```

#### Topic 6.4: Security Fundamentals
**Goal**: Introduction to Android security basics

**Claude Code Instructions**:
- Never hardcode secrets (API keys, passwords)
- Use EncryptedSharedPreferences for sensitive data
- Understand Android Keystore basics
- Implement basic authentication flows
- Follow OWASP Mobile Top 10

**Basic Security Checklist**:
- [ ] No hardcoded API keys in code
- [ ] Sensitive data encrypted at rest
- [ ] Network traffic uses HTTPS
- [ ] Authentication tokens stored securely
- [ ] ProGuard/R8 enabled for release builds

**Simple Secure Storage Example**:
```kotlin
// Use EncryptedSharedPreferences for sensitive data
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

val securePrefs = EncryptedSharedPreferences.create(
    context,
    "secure_prefs",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)

// Store auth token securely
securePrefs.edit().putString("auth_token", token).apply()
```

**API Key Management**:
```kotlin
// Never do this:
// const val API_KEY = "sk_live_12345"  ❌

// Instead: Use BuildConfig with secrets in local.properties
android {
    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${project.properties["DEV_API_KEY"]}\"")
        }
        release {
            buildConfigField("String", "API_KEY", "\"${project.properties["PROD_API_KEY"]}\"")
        }
    }
}

// Access via: BuildConfig.API_KEY
```

**Note**: Phase 10 will cover advanced security topics in depth (SSL pinning, root detection, code obfuscation, etc.)

---

### Phase 7: Build System (Weeks 13-14)

#### Topic 7.1: Gradle
**Goal**: Master build configuration

**Claude Code Instructions**:
- Use Kotlin DSL (build.gradle.kts)
- Create buildSrc for dependency management
- Set up build variants (debug, release, staging)
- Configure product flavours if needed
- Implement ProGuard/R8 rules
- Optimise build performance

**Build Structure**:
```
buildSrc/
├── src/main/kotlin/
│   ├── Dependencies.kt            # Version catalog
│   ├── AndroidConfig.kt           # Shared config
│   └── plugins/                   # Custom plugins
```

**Gradle Best Practices**:
1. Use version catalogs
2. Enable build cache
3. Configure parallel execution
4. Use configuration cache
5. Minimise dependencies

#### Topic 7.2: Kotlin Delegation
**Goal**: Use delegation for cleaner code

**Claude Code Instructions**:
- Use property delegation (lazy, observable, delegates)
- Implement class delegation for composition
- Create custom delegates for common patterns

**Practical Examples**:
```kotlin
// Property delegation
class ProfileViewModel : ViewModel() {
    private val preferences by inject<UserPreferences>()

    val username by lazy {
        preferences.getUsername()
    }
}

// Class delegation
class CachingRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : Repository by local {
    // Override only what's needed
    override suspend fun getArticles() =
        local.getArticles().ifEmpty {
            remote.getArticles().also { local.save(it) }
        }
}

// Custom delegate
class PreferenceDelegate<T>(
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {
    // Implementation
}
```

---

### Phase 8: Cross-Platform (Weeks 14-15)

#### Topic 8.1: Kotlin Multiplatform Mobile (KMM)
**Goal**: Share business logic across platforms

**Claude Code Instructions**:
- Extract business logic to `shared` module
- Keep platform-specific code separate
- Use expect/actual for platform APIs
- Share data models, repositories, use cases

**Shared Module Structure**:
```
shared/
├── commonMain/
│   ├── kotlin/
│   │   ├── domain/                # Business logic
│   │   ├── data/                  # Repositories
│   │   └── models/                # Data models
├── androidMain/
│   └── kotlin/                    # Android-specific
└── iosMain/
    └── kotlin/                    # iOS-specific (future)
```

---

### Phase 9: CI/CD & DevOps (Weeks 15-16)

#### Topic 9.1: GitHub Actions for Android
**Goal**: Automate builds, tests, and deployments

**Claude Code Instructions**:
- Set up GitHub Actions workflows for Android
- Automate builds on every push/PR
- Run tests automatically
- Generate and upload build artifacts
- Configure proper caching for faster builds

**Workflow Structure**:
```yaml
# .github/workflows/android-ci.yml
name: Android CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Cache Gradle packages
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Run tests
        run: ./gradlew test
      - name: Build with Gradle
        run: ./gradlew assembleDebug
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

**Practice Tasks**:
1. Set up basic CI workflow
2. Add unit test execution
3. Configure build caching
4. Upload build artifacts
5. Add status badges to README

#### Topic 9.2: Automated Testing Pipeline
**Goal**: Run comprehensive tests automatically

**Claude Code Instructions**:
- Configure test execution in CI
- Set up matrix testing for multiple API levels
- Generate test coverage reports
- Upload test results as artifacts
- Fail builds on test failures

**Test Workflow Example**:
```yaml
test:
  runs-on: macos-latest
  strategy:
    matrix:
      api-level: [24, 28, 31, 34]
  steps:
    - uses: actions/checkout@v3
    - name: Run instrumented tests
      uses: reactivecircus/android-emulator-runner@v2
      with:
        api-level: ${{ matrix.api-level }}
        script: ./gradlew connectedAndroidTest
    - name: Upload test results
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: test-results-api-${{ matrix.api-level }}
        path: app/build/reports/androidTests/
```

**Coverage Reporting**:
```yaml
- name: Generate coverage report
  run: ./gradlew jacocoTestReport
- name: Upload coverage to Codecov
  uses: codecov/codecov-action@v3
  with:
    files: ./app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml
```

#### Topic 9.3: Code Quality Automation
**Goal**: Enforce code quality standards automatically

**Claude Code Instructions**:
- Set up Detekt for static analysis
- Configure Ktlint for code formatting
- Add lint checks to CI pipeline
- Use Danger bot for PR reviews
- Fail builds on quality issues

**Quality Checks Workflow**:
```yaml
quality:
  runs-on: ubuntu-latest
  steps:
    - uses: actions/checkout@v3
    - name: Run Detekt
      run: ./gradlew detekt
    - name: Run Ktlint
      run: ./gradlew ktlintCheck
    - name: Run Android Lint
      run: ./gradlew lint
    - name: Upload lint reports
      uses: actions/upload-artifact@v3
      with:
        name: lint-reports
        path: |
          app/build/reports/detekt/
          app/build/reports/ktlint/
          app/build/reports/lint-results.html
```

**Detekt Configuration** (`detekt.yml`):
```yaml
build:
  maxIssues: 0
  excludeCorrectable: false

complexity:
  LongMethod:
    threshold: 60
  ComplexMethod:
    threshold: 15

style:
  MagicNumber:
    ignoreNumbers: ['-1', '0', '1', '2']
```

#### Topic 9.4: Automated Releases
**Goal**: Automate versioning and deployment

**Claude Code Instructions**:
- Implement semantic versioning
- Auto-generate CHANGELOG from commits
- Deploy to Play Store (internal/beta/production)
- Distribute to Firebase App Distribution
- Tag releases automatically

**Release Workflow**:
```yaml
release:
  runs-on: ubuntu-latest
  if: github.ref == 'refs/heads/main'
  steps:
    - uses: actions/checkout@v3
    - name: Bump version
      id: bump
      uses: anothrNick/github-tag-action@1.36.0
      env:
        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        WITH_V: true
    - name: Build Release APK
      run: ./gradlew assembleRelease
    - name: Sign APK
      uses: r0adkll/sign-android-release@v1
      with:
        releaseDirectory: app/build/outputs/apk/release
        signingKeyBase64: ${{ secrets.SIGNING_KEY }}
        alias: ${{ secrets.ALIAS }}
        keyStorePassword: ${{ secrets.KEY_STORE_PASSWORD }}
        keyPassword: ${{ secrets.KEY_PASSWORD }}
    - name: Deploy to Play Store
      uses: r0adkll/upload-google-play@v1
      with:
        serviceAccountJsonPlainText: ${{ secrets.SERVICE_ACCOUNT_JSON }}
        packageName: com.migpapps.devhub
        releaseFiles: app/build/outputs/apk/release/*.apk
        track: internal
```

**Firebase App Distribution**:
```yaml
- name: Upload to Firebase App Distribution
  uses: wzieba/Firebase-Distribution-Github-Action@v1
  with:
    appId: ${{ secrets.FIREBASE_APP_ID }}
    token: ${{ secrets.FIREBASE_TOKEN }}
    groups: testers
    file: app/build/outputs/apk/release/app-release.apk
```

#### Topic 9.5: Dependency Management Automation
**Goal**: Keep dependencies up-to-date automatically

**Claude Code Instructions**:
- Set up Dependabot or Renovate
- Configure automatic PR creation for updates
- Group dependencies logically
- Set up auto-merge for minor updates
- Test updates before merging

**Dependabot Configuration** (`.github/dependabot.yml`):
```yaml
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
    open-pull-requests-limit: 10
    groups:
      kotlin:
        patterns:
          - "org.jetbrains.kotlin*"
      compose:
        patterns:
          - "androidx.compose*"
      test:
        patterns:
          - "*junit*"
          - "*mockk*"
          - "*turbine*"
```

**Renovate Configuration** (`renovate.json`):
```json
{
  "extends": ["config:base"],
  "packageRules": [
    {
      "matchUpdateTypes": ["minor", "patch"],
      "matchCurrentVersion": "!/^0/",
      "automerge": true
    }
  ],
  "gradle": {
    "enabled": true
  }
}
```

#### Topic 9.6: Build Performance Monitoring
**Goal**: Track and optimize build times

**Claude Code Instructions**:
- Monitor build times in CI
- Use Gradle build scans
- Configure remote build cache
- Optimize Gradle settings
- Track performance metrics

**Gradle Configuration** (`gradle.properties`):
```properties
# Enable caching
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.configureondemand=true

# Increase heap size
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=512m

# Enable build scan
org.gradle.buildScan=true
```

**Build Scan Integration**:
```yaml
- name: Build with Gradle
  run: ./gradlew build --scan
  env:
    GRADLE_OPTS: "-Dorg.gradle.caching=true"
```

---

**Practice Checklist for Phase 9**:
- [ ] Set up basic GitHub Actions CI workflow
- [ ] Add automated testing (unit + instrumented)
- [ ] Configure code quality checks (Detekt, Ktlint, Lint)
- [ ] Set up test coverage reporting
- [ ] Implement semantic versioning
- [ ] Configure Firebase App Distribution
- [ ] Set up Play Store deployment (optional)
- [ ] Add Dependabot/Renovate for dependency updates
- [ ] Enable Gradle build cache
- [ ] Add CI status badges to README

---

### Phase 10: Security Deep Dive (Weeks 17-18)

#### Topic 10.1: Secure Storage & Encryption
**Goal**: Master data protection at rest

**Claude Code Instructions**:
- Use Android Keystore for cryptographic keys
- Implement EncryptedSharedPreferences correctly
- Encrypt sensitive database content (SQLCipher)
- Understand encryption algorithms (AES, RSA)
- Handle key rotation and migration

**Implementation Examples**:
```kotlin
// Android Keystore for secure key storage
val keyGenerator = KeyGenerator.getInstance(
    KeyProperties.KEY_ALGORITHM_AES,
    "AndroidKeyStore"
)

val keyGenParameterSpec = KeyGenParameterSpec.Builder(
    "MyKeyAlias",
    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
)
    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
    .setUserAuthenticationRequired(true)
    .setUserAuthenticationValidityDurationSeconds(30)
    .build()

keyGenerator.init(keyGenParameterSpec)
val secretKey = keyGenerator.generateKey()
```

**Encrypted Database (SQLCipher)**:
```kotlin
// build.gradle.kts
implementation("net.zetetic:android-database-sqlcipher:4.5.4")

// Open encrypted database
val passphrase = SQLiteDatabase.getBytes("your-secure-passphrase".toCharArray())
val database = SQLiteDatabase.openOrCreateDatabase(
    databaseFile,
    passphrase,
    null
)
```

**Practice Tasks**:
1. Encrypt user credentials in SharedPreferences
2. Encrypt article drafts in Room database
3. Implement secure file encryption
4. Handle biometric authentication for decryption

#### Topic 10.2: Network Security & SSL Pinning
**Goal**: Secure network communications

**Claude Code Instructions**:
- Implement certificate pinning
- Validate SSL certificates
- Prevent man-in-the-middle attacks
- Use network security configuration
- Monitor certificate expiry

**SSL Pinning with OkHttp**:
```kotlin
val certificatePinner = CertificatePinner.Builder()
    .add("api.devhub.app", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
    .add("api.devhub.app", "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=") // Backup pin
    .build()

val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinner)
    .build()
```

**Network Security Config** (`res/xml/network_security_config.xml`):
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">api.devhub.app</domain>
        <pin-set expiration="2025-12-31">
            <pin digest="SHA-256">AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=</pin>
            <pin digest="SHA-256">BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=</pin>
        </pin-set>
    </domain-config>
</network-security-config>
```

**Practice Tasks**:
1. Implement SSL pinning for API calls
2. Add certificate validation
3. Test MITM attack prevention
4. Set up certificate rotation strategy

#### Topic 10.3: Authentication & Authorization
**Goal**: Secure user identity and access control

**Claude Code Instructions**:
- Implement OAuth 2.0 / OpenID Connect
- Handle JWT tokens securely
- Implement token refresh flows
- Use biometric authentication
- Implement proper logout

**JWT Handling**:
```kotlin
// Store JWT securely
class AuthManager @Inject constructor(
    private val securePrefs: EncryptedSharedPreferences,
    private val keyStore: KeyStore
) {
    fun saveAuthToken(token: String) {
        securePrefs.edit()
            .putString(KEY_AUTH_TOKEN, token)
            .putLong(KEY_TOKEN_EXPIRY, extractExpiry(token))
            .apply()
    }

    fun isTokenValid(): Boolean {
        val expiry = securePrefs.getLong(KEY_TOKEN_EXPIRY, 0)
        return System.currentTimeMillis() < expiry
    }

    suspend fun refreshTokenIfNeeded() {
        if (!isTokenValid()) {
            val refreshToken = securePrefs.getString(KEY_REFRESH_TOKEN, null)
            refreshToken?.let { token ->
                val newToken = authApi.refreshToken(token)
                saveAuthToken(newToken.accessToken)
            }
        }
    }
}
```

**Biometric Authentication**:
```kotlin
val biometricPrompt = BiometricPrompt(
    activity,
    executor,
    object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            // Access granted - decrypt sensitive data
            decryptUserData(result.cryptoObject)
        }

        override fun onAuthenticationFailed() {
            // Show error
        }
    }
)

val promptInfo = BiometricPrompt.PromptInfo.Builder()
    .setTitle("Authenticate to access DevHub")
    .setSubtitle("Use fingerprint or face")
    .setNegativeButtonText("Cancel")
    .build()

biometricPrompt.authenticate(promptInfo, cryptoObject)
```

**Practice Tasks**:
1. Implement OAuth 2.0 login flow
2. Add JWT token management
3. Implement biometric auth for sensitive actions
4. Add session timeout handling

#### Topic 10.4: Code Protection & Obfuscation
**Goal**: Protect app code from reverse engineering

**Claude Code Instructions**:
- Configure ProGuard/R8 properly
- Implement root detection
- Use Play Integrity API (SafetyNet successor)
- Detect tampering and repackaging
- Obfuscate sensitive logic

**ProGuard Rules** (`proguard-rules.pro`):
```pro
# Keep data models for Gson/kotlinx.serialization
-keep class com.migpapps.devhub.data.models.** { *; }
-keepclassmembers class com.migpapps.devhub.data.models.** { *; }

# Keep API interfaces
-keep interface com.migpapps.devhub.data.api.** { *; }

# Aggressive obfuscation for security-sensitive code
-repackageclasses 'obfuscated'
-allowaccessmodification
-overloadaggressively

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}
```

**Root Detection**:
```kotlin
object RootDetector {
    fun isDeviceRooted(): Boolean {
        return checkRootMethod1() ||
               checkRootMethod2() ||
               checkRootMethod3()
    }

    private fun checkRootMethod1(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su"
        )
        return paths.any { File(it).exists() }
    }

    private fun checkRootMethod2(): Boolean {
        return try {
            Runtime.getRuntime().exec("su")
            true
        } catch (e: IOException) {
            false
        }
    }

    private fun checkRootMethod3(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }
}
```

**Play Integrity API**:
```kotlin
class IntegrityChecker @Inject constructor(
    private val context: Context
) {
    suspend fun checkAppIntegrity(): IntegrityResult {
        val integrityManager = IntegrityManagerFactory.create(context)

        val integrityTokenResponse = integrityManager
            .requestIntegrityToken(
                IntegrityTokenRequest.builder()
                    .setCloudProjectNumber(CLOUD_PROJECT_NUMBER)
                    .build()
            )
            .await()

        return parseIntegrityToken(integrityTokenResponse.token())
    }
}
```

**Practice Tasks**:
1. Configure ProGuard for release builds
2. Implement root detection
3. Add Play Integrity API checks
4. Test obfuscated build
5. Add tamper detection

---

**Practice Checklist for Phase 10**:
- [ ] Encrypted SharedPreferences implemented
- [ ] Database encryption with SQLCipher
- [ ] SSL pinning configured
- [ ] Network security config set up
- [ ] OAuth 2.0 authentication flow
- [ ] JWT token management
- [ ] Biometric authentication
- [ ] ProGuard/R8 configured
- [ ] Root detection implemented
- [ ] Play Integrity API integrated

---

### Phase 11: Performance & Advanced Topics (Weeks 19-20)

#### Topic 11.1: Performance Profiling & Optimization
**Goal**: Build fast, responsive apps

**Claude Code Instructions**:
- Use Android Profiler effectively
- Identify and fix memory leaks
- Optimize app startup time
- Prevent ANRs (Application Not Responding)
- Reduce APK/AAB size

**Performance Profiling Tools**:
- **CPU Profiler**: Identify expensive operations
- **Memory Profiler**: Find memory leaks
- **Network Profiler**: Optimize API calls
- **Energy Profiler**: Battery optimization
- **Layout Inspector**: UI performance

**Memory Leak Detection**:
```kotlin
// Use LeakCanary in debug builds
dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}

// Monitor for leaks
class DevHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(
                dumpHeap = true,
                retainedVisibleThreshold = 3
            )
        }
    }
}
```

**App Startup Optimization**:
```kotlin
// Use App Startup library
class AnalyticsInitializer : Initializer<AnalyticsTracker> {
    override fun create(context: Context): AnalyticsTracker {
        return FirebaseAnalytics.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

// Lazy initialization
class DevHubApplication : Application() {
    val imageLoader by lazy {
        Coil.imageLoader(this)
    }
}
```

**ANR Prevention**:
```kotlin
// Move long operations off main thread
viewModelScope.launch(Dispatchers.IO) {
    val articles = repository.fetchArticles() // Network call
    withContext(Dispatchers.Main) {
        _state.update { it.copy(articles = articles) }
    }
}

// Use Flow for reactive updates
repository.observeArticles()
    .flowOn(Dispatchers.IO)
    .collect { articles ->
        _state.update { it.copy(articles = articles) }
    }
```

**APK Size Reduction**:
```kotlin
android {
    buildTypes {
        release {
            minifyEnabled = true
            shrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Enable resource shrinking
    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}
```

**Practice Tasks**:
1. Profile app with Android Profiler
2. Fix identified memory leaks
3. Optimize app startup time (<2s)
4. Reduce APK size by 30%
5. Eliminate all ANRs

#### Topic 11.2: Offline-First Architecture
**Goal**: Build apps that work without internet

**Claude Code Instructions**:
- Implement local-first data strategy
- Use WorkManager for background sync
- Handle conflict resolution
- Cache API responses intelligently
- Queue offline actions

**Offline-First Pattern**:
```kotlin
class ArticleRepository @Inject constructor(
    private val localDataSource: ArticleLocalDataSource,
    private val remoteDataSource: ArticleRemoteDataSource,
    private val syncManager: SyncManager
) {
    // Always return local data first
    fun getArticles(): Flow<List<Article>> = flow {
        // 1. Emit cached data immediately
        emitAll(localDataSource.observeArticles())

        // 2. Fetch fresh data in background
        try {
            val freshArticles = remoteDataSource.fetchArticles()
            localDataSource.saveArticles(freshArticles)
            // Flow automatically emits updated data
        } catch (e: IOException) {
            // Network error - continue showing cached data
            syncManager.scheduleRetry()
        }
    }

    // Queue actions for offline execution
    suspend fun likeArticle(articleId: String) {
        // Optimistic update
        localDataSource.likeArticle(articleId)

        // Queue for server sync
        if (networkMonitor.isOnline()) {
            remoteDataSource.likeArticle(articleId)
        } else {
            syncManager.queueAction(LikeAction(articleId))
        }
    }
}
```

**WorkManager for Background Sync**:
```kotlin
class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val pendingActions = actionQueue.getPendingActions()

            pendingActions.forEach { action ->
                when (action) {
                    is LikeAction -> api.likeArticle(action.articleId)
                    is CommentAction -> api.postComment(action.comment)
                    // ... handle other actions
                }
                actionQueue.markCompleted(action.id)
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

// Schedule periodic sync
val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
    .setConstraints(
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    )
    .build()

WorkManager.getInstance(context).enqueueUniquePeriodicWork(
    "article_sync",
    ExistingPeriodicWorkPolicy.KEEP,
    syncRequest
)
```

**Conflict Resolution**:
```kotlin
data class Article(
    val id: String,
    val content: String,
    val lastModified: Long,
    val version: Int // For conflict detection
)

class ConflictResolver {
    fun resolve(local: Article, remote: Article): Article {
        return when {
            local.version > remote.version -> local // Local is newer
            remote.version > local.version -> remote // Remote is newer
            local.lastModified > remote.lastModified -> local // Timestamp tiebreaker
            else -> remote
        }
    }
}
```

**Practice Tasks**:
1. Implement offline article reading
2. Queue likes/comments when offline
3. Add background sync with WorkManager
4. Handle conflict resolution
5. Show sync status to users

#### Topic 11.3: Advanced Compose Topics
**Goal**: Master advanced Compose techniques

**Claude Code Instructions**:
- Create custom layouts
- Use Canvas API for custom drawing
- Build custom modifiers
- Optimize recomposition
- Create complex animations

**Custom Layout**:
```kotlin
@Composable
fun CustomFlowLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        var xPosition = 0
        var yPosition = 0
        var maxHeight = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                if (xPosition + placeable.width > constraints.maxWidth) {
                    xPosition = 0
                    yPosition += maxHeight
                    maxHeight = 0
                }

                placeable.placeRelative(x = xPosition, y = yPosition)
                xPosition += placeable.width
                maxHeight = max(maxHeight, placeable.height)
            }
        }
    }
}
```

**Canvas API - Custom Drawing**:
```kotlin
@Composable
fun ArticleProgressChart(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(200.dp)) {
        val sweepAngle = 360f * progress

        // Background arc
        drawArc(
            color = Color.LightGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
        )

        // Progress arc
        drawArc(
            color = Color.Blue,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
        )

        // Center text
        drawContext.canvas.nativeCanvas.drawText(
            "${(progress * 100).toInt()}%",
            center.x,
            center.y,
            android.graphics.Paint().apply {
                textSize = 48f
                textAlign = android.graphics.Paint.Align.CENTER
            }
        )
    }
}
```

**Custom Modifier**:
```kotlin
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned { size = it.size }
}
```

**Practice Tasks**:
1. Create custom flow layout for tags
2. Draw custom chart with Canvas
3. Build shimmer loading effect
4. Optimize list with keys and remember

#### Topic 11.4: Animations & Motion
**Goal**: Create delightful user experiences

**Claude Code Instructions**:
- Implement Compose animations
- Use MotionLayout for complex transitions
- Create shared element transitions
- Add micro-interactions
- Follow Material Motion guidelines

**Compose Animations**:
```kotlin
@Composable
fun AnimatedArticleCard(article: Article) {
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .padding(extraPadding)
            .clickable { expanded = !expanded }
    ) {
        Column {
            Text(article.title)
            AnimatedVisibility(visible = expanded) {
                Text(article.content)
            }
        }
    }
}
```

**Shared Element Transition**:
```kotlin
@Composable
fun ArticleListScreen(navController: NavController) {
    LazyColumn {
        items(articles) { article ->
            SharedTransitionLayout {
                ArticleCard(
                    article = article,
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = "article-${article.id}"),
                        animatedVisibilityScope = this
                    )
                )
            }
        }
    }
}
```

**Gesture Animations**:
```kotlin
@Composable
fun SwipeToDeleteArticle(
    article: Article,
    onDelete: () -> Unit
) {
    val offsetX = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX.value < -300f) {
                            onDelete()
                        } else {
                            offsetX.animateTo(0f)
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX.snapTo(offsetX.value + dragAmount)
                    }
                )
            }
    ) {
        ArticleCard(article)
    }
}
```

**Practice Tasks**:
1. Add enter/exit animations to screens
2. Implement pull-to-refresh animation
3. Create swipe-to-delete gesture
4. Add shared element transitions
5. Build custom loading animations

---

**Practice Checklist for Phase 11**:
- [ ] Profile app performance
- [ ] Fix all memory leaks
- [ ] Optimize app startup
- [ ] Implement offline-first architecture
- [ ] Add background sync with WorkManager
- [ ] Create custom Compose layout
- [ ] Use Canvas for custom drawing
- [ ] Add animations to key interactions
- [ ] Implement shared element transitions
- [ ] Polish micro-interactions

---

## 🎨 Coding Standards & Best Practices

### General Guidelines
1. **Kotlin Conventions**: Follow official Kotlin coding conventions
2. **Clean Architecture**: Separate concerns (data, domain, presentation)
3. **SOLID Principles**: Keep code maintainable and testable
4. **Immutability**: Prefer immutable data classes
5. **Null Safety**: Avoid nullable types when possible

### File Organisation
```kotlin
// Order: Properties -> Init blocks -> Functions -> Companion
class MyClass {
    private val property = "value"

    init { }

    fun publicFunction() { }

    private fun privateFunction() { }

    companion object { }
}
```

### Naming Conventions
- Classes: PascalCase
- Functions/Variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Sealed classes for states: `sealed class FeedState`
- Composable functions: PascalCase

### Documentation
- KDoc for public APIs
- Inline comments for complex logic
- README for each module
- Architecture Decision Records (ADR) for major decisions

---

## 🚀 Getting Started Workflow

### For Each New Feature:
1. **Design**: Think about architecture (MVVM vs MVI?)
2. **Test First**: Write tests (TDD approach)
3. **Implement**: Build the feature
4. **Refactor**: Clean up code
5. **Review**: Check against best practices
6. **Document**: Add documentation

### Claude Code Interaction:
- Ask me to review your architecture before implementing
- Request code reviews with specific focus (testing, accessibility, etc.)
- Ask for refactoring suggestions
- Request explanations of patterns and concepts
- Get help debugging issues

---

## 📋 Progress Tracking

### Completion Checklist
Mark topics as you complete them:

- [ ] MVVM vs MVI (both patterns implemented)
- [ ] ViewModels & Lifecycle
- [ ] Jetpack Compose
- [ ] Fragments
- [ ] Navigation
- [ ] Dagger-Hilt
- [ ] Creating an SDK
- [ ] Coroutines
- [ ] StateFlow vs Flow vs SharedFlow vs LiveData
- [ ] TDD
- [ ] Android Testing
- [ ] Accessibility
- [ ] Intents & Intent Filters
- [ ] Push Notifications
- [ ] Tracking & Analytics
- [ ] Gradle
- [ ] Kotlin Delegation
- [ ] KMM

### Interview Readiness
For each topic, ensure you can:
- [ ] Explain the concept clearly
- [ ] Discuss trade-offs and alternatives
- [ ] Show practical implementation
- [ ] Describe real-world use cases
- [ ] Handle follow-up questions

---

## 💡 Tips for Maximum Learning

1. **Build incrementally**: Don't try to implement everything at once
2. **Refactor often**: Revisit code and improve it
3. **Write tests**: They'll save you time and teach you proper design
4. **Read code**: Check out popular open-source Android apps
5. **Stay updated**: Android development evolves rapidly
6. **Ask questions**: Use me to clarify concepts and review code
7. **Document learnings**: Keep notes on "aha moments"

---

## 🤝 Working with Claude Code

### Effective Prompts:
- "Review this ViewModel implementation for MVI best practices"
- "Help me write tests for this repository using TDD"
- "Explain why we should use StateFlow instead of LiveData here"
- "Refactor this code to use proper Hilt injection"
- "Make this composable more accessible"
- "Set up a new feature module for [feature name]"

### Code Review Focus Areas:
- Architecture patterns compliance
- Testing coverage
- Accessibility
- Performance considerations
- Memory leaks
- Proper resource handling
- Security best practices

---

## 📚 Additional Resources

### When to Deep Dive:
- Official Android documentation
- Kotlin coroutines guide
- Compose documentation
- Hilt/Dagger documentation
- Testing best practices

### Community:
- Android Dev Reddit
- Kotlin Slack
- Stack Overflow
- Medium articles
- Conference talks (Android Dev Summit, KotlinConf)

---

**Remember**: This is a training project. Don't be afraid to experiment, make mistakes, and refactor. The goal is learning, not perfection!
