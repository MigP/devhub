# DevHub Training Programme - Interview Questions

A comprehensive collection of interview questions for each topic in the Android Developer training programme. Use these to prepare for technical interviews and assess your understanding.

---

## How to Use This Document

1. **Self-assessment**: Try answering before looking at hints
2. **Practice**: Explain answers out loud as in real interviews
3. **Depth**: Be prepared for follow-up questions
4. **Examples**: Always relate to your DevHub project
5. **Draw**: Use diagrams where applicable

**Difficulty Levels:**
- 🟢 Junior: Entry-level understanding
- 🟡 Medior: Practical experience expected
- 🔴 Senior: Deep understanding and trade-offs

---

## Phase 1: Architecture Foundation

### MVVM vs MVI Architecture

#### 🟢 What is MVVM?
**Expected Answer:**
- Model-View-ViewModel pattern
- Separation of concerns
- ViewModel holds UI state
- View observes ViewModel
- Lifecycle awareness

**Follow-up:** How does it differ from MVP?

---

#### 🟢 What is MVI?
**Expected Answer:**
- Model-View-Intent pattern
- Unidirectional data flow
- Single immutable state
- User intents trigger state changes
- Predictable state management

---

#### 🟡 Compare MVVM and MVI. When would you use each?
**Expected Answer:**

**MVVM Pros:**
- Simpler for small features
- Less boilerplate
- Easier team adoption
- Multiple LiveData/StateFlows okay

**MVI Pros:**
- Predictable state changes
- Easier to test
- Better for complex UIs
- Time-travel debugging possible
- Clear separation of concerns

**Use MVVM when:**
- Simple CRUD operations
- Team unfamiliar with MVI
- Quick prototypes

**Use MVI when:**
- Complex state management
- Need high testability
- Multiple state sources
- Team experienced with reactive programming

**DevHub Example:** "I used MVI for the feed feature because it has complex loading states, pagination, and refresh logic. I used MVVM for the profile feature because it's mostly straightforward data display."

---

#### 🔴 How do you handle one-time events in MVVM vs MVI?
**Expected Answer:**

**MVVM:** 
```kotlin
// SharedFlow for events
private val _events = MutableSharedFlow<ProfileEvent>()
val events = _events.asSharedFlow()

// In UI
viewModel.events.collectAsEffect { event ->
    when (event) {
        is ProfileEvent.NavigateToSettings -> navigate()
    }
}
```

**MVI:**
```kotlin
// Part of state or separate effect
sealed class FeedEffect {
    object ShowError : FeedEffect()
}

// Consumed once
viewModel.effect.collect { effect ->
    // Handle and clear
}
```

**Trade-off discussion:** Event replay, configuration changes, race conditions

---

### ViewModels & Lifecycle

#### 🟢 What is a ViewModel and why use it?
**Expected Answer:**
- Survives configuration changes
- Holds UI state
- Separates UI logic from UI controller
- Lifecycle-aware
- Cleared when no longer needed

---

#### 🟡 Explain ViewModel lifecycle. When is it created and destroyed?
**Expected Answer:**
- Created on first access
- Survives configuration changes
- Destroyed when scope finished (Activity/Fragment/Navigation)
- onCleared() called before destruction
- Can draw lifecycle diagram

**Follow-up:** What about process death?

---

#### 🟡 What is SavedStateHandle and when do you use it?
**Expected Answer:**
```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedState: SavedStateHandle
) : ViewModel() {
    
    var username: String
        get() = savedState.get<String>("username") ?: ""
        set(value) { savedState["username"] = value }
    
    // Or with StateFlow
    val username = savedState.getStateFlow("username", "")
}
```

**Use for:**
- Surviving process death
- Navigation arguments
- Temporary UI state
- Form data

---

#### 🔴 How would you share a ViewModel between two fragments?
**Expected Answer:**
```kotlin
// In both fragments
private val sharedViewModel: SharedViewModel by activityViewModels()

// Or with Navigation
private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_graph)
```

**Discussion points:**
- Scope selection
- Lifecycle considerations
- Communication patterns
- When to avoid sharing

---

#### 🔴 What's the difference between viewModelScope and lifecycleScope?
**Expected Answer:**
- **viewModelScope:** Tied to ViewModel, cleared in onCleared()
- **lifecycleScope:** Tied to lifecycle owner, cancelled when destroyed

**When to use each:**
- viewModelScope: Repository calls, long-running operations
- lifecycleScope: UI updates, collect Flows

---

## Phase 2: Modern UI Development

### Jetpack Compose

#### 🟢 What is Jetpack Compose?
**Expected Answer:**
- Declarative UI framework
- Kotlin-based
- Less code than Views
- Reactive
- Modern Android UI toolkit

---

#### 🟡 Explain recomposition. When does it occur?
**Expected Answer:**
- Re-execution of composable functions
- Occurs when state changes
- Only affected composables recompose
- Automatic and smart
- Can skip if inputs unchanged

**Example:**
```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    // Only this button recomposes when count changes
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

---

#### 🟡 Difference between remember and rememberSaveable?
**Expected Answer:**
- **remember:** Survives recomposition only
- **rememberSaveable:** Survives configuration changes AND process death

**Use remember for:**
- Temporary UI state
- Performance optimisations
- Objects not serialisable

**Use rememberSaveable for:**
- User input
- Scroll positions
- Navigation state

---

#### 🔴 Explain state hoisting in Compose. Why is it important?
**Expected Answer:**
- Moving state up to caller
- Makes composables reusable and testable
- Single source of truth
- Unidirectional data flow

**Example:**
```kotlin
// Bad: State inside composable
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    TextField(value = query, onValueChange = { query = it })
}

// Good: State hoisted
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(value = query, onValueChange = onQueryChange)
}
```

---

#### 🔴 What are side effects in Compose? Name and explain different types.
**Expected Answer:**

**LaunchedEffect:**
- Runs in coroutine scope
- Key changes restart
```kotlin
LaunchedEffect(key) {
    // Suspend operations
}
```

**DisposableEffect:**
- Cleanup needed
```kotlin
DisposableEffect(lifecycle) {
    onDispose { cleanup() }
}
```

**SideEffect:**
- Runs after each recomposition
- Sync Compose state with non-Compose

**derivedStateOf:**
- Computed state
- Minimise recomposition

---

#### 🔴 How do you optimise Compose performance?
**Expected Answer:**
1. **Skip unnecessary recompositions:**
   - Immutable data
   - Stable parameters
   - Remember expensive calculations

2. **Use derivedStateOf:**
```kotlin
val filteredList by remember {
    derivedStateOf {
        list.filter { it.matches(query) }
    }
}
```

3. **Keys in lazy lists:**
```kotlin
LazyColumn {
    items(list, key = { it.id }) { item ->
        ItemRow(item)
    }
}
```

4. **Avoid reading state in composition:**
5. **Use remember for expensive objects:**

---

### Fragments

#### 🟢 What is a Fragment?
**Expected Answer:**
- Reusable portion of UI
- Has own lifecycle
- Lives within Activity
- Can be added/removed dynamically
- Modular screen component

---

#### 🟡 Explain Fragment lifecycle. Draw it.
**Expected Answer:**
Key methods in order:
1. onAttach()
2. onCreate()
3. onCreateView()
4. onViewCreated()
5. onStart()
6. onResume()
7. onPause()
8. onStop()
9. onDestroyView()
10. onDestroy()
11. onDetach()

**Important:** View lifecycle vs Fragment lifecycle

---

#### 🟡 How do you communicate between Fragments?
**Expected Answer:**

**1. Shared ViewModel (Preferred):**
```kotlin
private val sharedViewModel: SharedViewModel by activityViewModels()
```

**2. Fragment Result API:**
```kotlin
// Receiver
setFragmentResultListener("key") { _, bundle ->
    val result = bundle.getString("data")
}

// Sender
setFragmentResult("key", bundleOf("data" to value))
```

**3. Interfaces (Legacy):**
- Define interface
- Activity implements
- Fragment calls interface

---

#### 🔴 When should you still use Fragments vs Compose?
**Expected Answer:**
**Use Fragments:**
- Existing large codebase
- Team not ready for Compose
- Interacting with View-based libraries
- Gradual migration

**Use Compose:**
- New projects
- Modern UI needs
- Less boilerplate desired
- Better performance needs

**Migration strategy:**
- ComposeView in Fragments
- Fragment in Compose with AndroidView
- Gradual feature-by-feature migration

---

### Navigation

#### 🟢 What is the Navigation Component?
**Expected Answer:**
- Jetpack library for navigation
- Handles back stack
- Type-safe arguments
- Deep linking support
- Visual navigation graph

---

#### 🟡 How do you navigate in Compose?
**Expected Answer:**
```kotlin
val navController = rememberNavController()

NavHost(navController, startDestination = "feed") {
    composable("feed") { FeedScreen(onArticleClick = { id ->
        navController.navigate("article/$id")
    })}
    
    composable(
        "article/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id")
        ArticleScreen(id)
    }
}
```

---

#### 🟡 Explain deep linking. How do you implement it?
**Expected Answer:**
**Manifest:**
```xml
<intent-filter android:autoVerify="true">
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data
        android:scheme="https"
        android:host="devhub.app"
        android:pathPrefix="/article" />
</intent-filter>
```

**Navigation:**
```kotlin
composable(
    "article/{id}",
    deepLinks = listOf(navDeepLink {
        uriPattern = "https://devhub.app/article/{id}"
    })
) { /* ... */ }
```

**Testing:** `adb shell am start -a android.intent.action.VIEW -d "https://devhub.app/article/123"`

---

#### 🔴 How do you handle back stack with complex navigation flows?
**Expected Answer:**
- popUpTo and popUpToInclusive
- saveState and restoreState
- singleTop launchMode
- clearBackStack()

**Example:**
```kotlin
navController.navigate("home") {
    popUpTo("login") { inclusive = true }
    launchSingleTop = true
}
```

---

## Phase 3: Dependency Injection

### Dagger-Hilt

#### 🟢 What is Dependency Injection?
**Expected Answer:**
- Design pattern
- Dependencies provided externally
- Reduces coupling
- Easier testing
- Improves reusability

---

#### 🟡 Why use Hilt over manual DI?
**Expected Answer:**
**Benefits:**
- Less boilerplate
- Compile-time safety
- Android lifecycle integration
- Scoping support
- Easier to maintain

**Trade-offs:**
- Build time
- Learning curve
- Less flexibility than manual DI

---

#### 🟡 Explain Hilt components and scopes
**Expected Answer:**

**Main Components:**
- SingletonComponent (@Singleton)
- ActivityComponent (@ActivityScoped)
- ViewModelComponent (@ViewModelScoped)
- FragmentComponent (@FragmentScoped)

**Example:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit { /* ... */ }
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel()
```

---

#### 🔴 @Provides vs @Binds. When to use each?
**Expected Answer:**

**@Provides:**
- For third-party classes
- Need to run code
- Factory pattern

```kotlin
@Provides
@Singleton
fun provideOkHttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
```

**@Binds:**
- For interfaces
- More efficient (no instance needed)
- Abstract functions only

```kotlin
@Binds
abstract fun bindRepository(
    impl: FeedRepositoryImpl
): FeedRepository
```

---

#### 🔴 How do you test with Hilt?
**Expected Answer:**
```kotlin
@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class FeedViewModelTest {
    
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    
    @Module
    @InstallIn(SingletonComponent::class)
    object TestNetworkModule {
        @Provides
        fun provideTestRepository(): FeedRepository = FakeRepository()
    }
    
    @Test
    fun testFeed() = runTest {
        // Test with fake dependencies
    }
}
```

---

### Creating an SDK

#### 🟡 What are key considerations when designing an SDK?
**Expected Answer:**
1. **Public API:**
   - Clear and intuitive
   - Minimal surface area
   - Backwards compatibility

2. **Dependencies:**
   - Minimise external deps
   - Use api vs implementation properly
   - Handle conflicts

3. **Documentation:**
   - KDoc for all public APIs
   - Sample code
   - Migration guides

4. **Versioning:**
   - Semantic versioning
   - Deprecation strategy
   - Changelog

5. **ProGuard:**
   - Consumer rules provided
   - Keep public API
   - Shrink internals

---

#### 🔴 How do you handle breaking changes in an SDK?
**Expected Answer:**
1. **Deprecation cycle:**
```kotlin
@Deprecated(
    message = "Use newMethod() instead",
    replaceWith = ReplaceWith("newMethod()"),
    level = DeprecationLevel.WARNING
)
fun oldMethod() { }
```

2. **Versioning:**
   - Major version for breaking changes
   - Communication plan
   - Migration period

3. **Dual support:**
   - Support old and new APIs
   - Eventually remove old

---

## Phase 4: Reactive Programming

### Coroutines

#### 🟢 What are Kotlin Coroutines?
**Expected Answer:**
- Light-weight threads
- Asynchronous programming
- Sequential looking code
- Structured concurrency
- Cancellation support

---

#### 🟡 launch vs async. When to use each?
**Expected Answer:**

**launch:**
- Fire and forget
- No return value
- Returns Job

```kotlin
viewModelScope.launch {
    repository.saveData(data)
}
```

**async:**
- Returns Deferred<T>
- Use for parallel operations
- Must call await()

```kotlin
val users = async { getUsers() }
val posts = async { getPosts() }
val result = users.await() + posts.await()
```

---

#### 🟡 Explain Dispatchers in coroutines
**Expected Answer:**
- **Dispatchers.Main:** UI operations
- **Dispatchers.IO:** Network, disk, database
- **Dispatchers.Default:** CPU-intensive work
- **Dispatchers.Unconfined:** Don't use (advanced cases only)

```kotlin
suspend fun loadData() = withContext(Dispatchers.IO) {
    database.getData()
}
```

---

#### 🔴 How do you handle exceptions in coroutines?
**Expected Answer:**

**1. Try-catch:**
```kotlin
viewModelScope.launch {
    try {
        val data = repository.getData()
    } catch (e: Exception) {
        _error.value = e.message
    }
}
```

**2. CoroutineExceptionHandler:**
```kotlin
val handler = CoroutineExceptionHandler { _, exception ->
    logError(exception)
}

viewModelScope.launch(handler) {
    // Code
}
```

**3. supervisorScope:**
- Child failure doesn't cancel siblings

---

#### 🔴 What is structured concurrency?
**Expected Answer:**
- Coroutines have hierarchy
- Parent waits for children
- Cancellation propagates
- No leaked coroutines

**Example:**
```kotlin
suspend fun loadUserData() = coroutineScope {
    val profile = async { getProfile() }
    val posts = async { getPosts() }
    
    UserData(profile.await(), posts.await())
}
// If one fails, both cancelled
```

---

### Flow, StateFlow, SharedFlow, LiveData

#### 🟢 What is a Flow?
**Expected Answer:**
- Cold stream of values
- Asynchronous data stream
- Builder functions (flow {})
- Terminal operators (collect)
- Transformation operators (map, filter)

---

#### 🟡 Cold vs Hot streams. Explain the difference.
**Expected Answer:**

**Cold (Flow):**
- Starts when collected
- Each collector gets independent stream
- Example: API calls, database queries

```kotlin
val flow = flow {
    emit(apiCall()) // Called for each collector
}
```

**Hot (StateFlow, SharedFlow):**
- Always active
- Shared between collectors
- Example: UI state, events

```kotlin
val stateFlow = MutableStateFlow(initialValue)
// New collectors get current value
```

---

#### 🔴 When to use StateFlow vs SharedFlow vs Flow?
**Expected Answer:**

**StateFlow:**
- UI state
- Always has value
- Conflates (only latest matters)
- Replays last value

```kotlin
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

**SharedFlow:**
- One-time events
- Configurable replay
- No conflation

```kotlin
private val _events = MutableSharedFlow<Event>()
val events = _events.asSharedFlow()
```

**Flow:**
- Data streams
- Database queries
- Each collector independent

```kotlin
fun observeArticles(): Flow<List<Article>> =
    database.articlesDao().observeAll()
```

---

#### 🟡 How do you collect Flows in Compose?
**Expected Answer:**

**StateFlow:**
```kotlin
val state by viewModel.state.collectAsStateWithLifecycle()
```

**For events (SharedFlow):**
```kotlin
LaunchedEffect(Unit) {
    viewModel.events.collect { event ->
        when (event) {
            is Event.ShowToast -> showToast(event.message)
        }
    }
}
```

---

#### 🔴 Convert LiveData to Flow and vice versa
**Expected Answer:**
```kotlin
// LiveData to Flow
val flow: Flow<Data> = liveData.asFlow()

// Flow to LiveData
val liveData: LiveData<Data> = flow.asLiveData()

// StateFlow to LiveData
val liveData = stateFlow.asLiveData()
```

---

## Phase 5: Quality & Testing

### Test-Driven Development (TDD)

#### 🟢 What is TDD?
**Expected Answer:**
- Write test first
- Red-Green-Refactor
- Test defines requirements
- Confidence in refactoring
- Better design

---

#### 🟡 Explain the Red-Green-Refactor cycle
**Expected Answer:**

**Red:**
1. Write failing test
2. Test defines desired behavior

**Green:**
3. Write minimal code to pass
4. Don't worry about perfection

**Refactor:**
5. Improve code quality
6. Tests ensure nothing broke

---

#### 🔴 What makes code testable?
**Expected Answer:**
1. **Dependency Injection:**
   - Can replace with test doubles

2. **Single Responsibility:**
   - Small, focused functions

3. **No hidden dependencies:**
   - No static access, singletons

4. **Separation of concerns:**
   - Logic separate from framework

**Example:**
```kotlin
// Hard to test
class UserService {
    fun getUser(id: String): User {
        val db = Database.getInstance() // Hard-coded
        return db.getUser(id)
    }
}

// Easy to test
class UserService(
    private val database: Database // Injected
) {
    fun getUser(id: String): User = database.getUser(id)
}
```

---

### Android Testing

#### 🟢 What is the testing pyramid?
**Expected Answer:**
- **Unit tests (Most):** Fast, isolated, many
- **Integration tests (Some):** Multiple components
- **UI tests (Few):** Slow, brittle, expensive

---

#### 🟡 How do you test ViewModels?
**Expected Answer:**
```kotlin
@Test
fun `when loadFeed called, then articles loaded`() = runTest {
    // Given
    val articles = listOf(Article("1", "Title"))
    coEvery { repository.getArticles() } returns flowOf(articles)
    
    // When
    viewModel.loadFeed()
    
    // Then
    assertEquals(articles, viewModel.state.value.articles)
}
```

**Key points:**
- runTest for coroutines
- MockK or Mockito
- Test state changes
- Test error handling

---

#### 🟡 How do you test Compose UI?
**Expected Answer:**
```kotlin
@Test
fun feedScreen_showsArticles() {
    composeTestRule.setContent {
        FeedScreen(
            state = FeedState(articles = testArticles),
            onArticleClick = {}
        )
    }
    
    composeTestRule
        .onNodeWithText("Test Article")
        .assertIsDisplayed()
}
```

---

#### 🔴 How do you test coroutines and Flows?
**Expected Answer:**

**Coroutines:**
```kotlin
@Test
fun testCoroutine() = runTest {
    // Test runs immediately
    viewModel.loadData()
    advanceUntilIdle() // Advance virtual time
    // Assert
}
```

**Flows with Turbine:**
```kotlin
@Test
fun testFlow() = runTest {
    repository.observeArticles().test {
        assertEquals(emptyList(), awaitItem())
        repository.addArticle(article)
        assertEquals(listOf(article), awaitItem())
        cancelAndIgnoreRemainingEvents()
    }
}
```

---

### Accessibility

#### 🟢 What is accessibility in Android?
**Expected Answer:**
- Making apps usable by everyone
- Including people with disabilities
- TalkBack, Switch Access, etc.
- Required in many countries
- Better UX for all

---

#### 🟡 How do you make Compose UIs accessible?
**Expected Answer:**
```kotlin
Image(
    painter = painterResource(R.drawable.logo),
    contentDescription = "DevHub logo"
)

Button(
    onClick = { },
    modifier = Modifier.semantics {
        contentDescription = "Submit article"
        role = Role.Button
    }
) {
    Icon(Icons.Default.Send, contentDescription = null)
    Text("Submit")
}
```

**Key points:**
- Content descriptions
- Semantic roles
- Touch target size (48dp)
- Colour contrast
- Font scaling support

---

#### 🔴 How do you test accessibility?
**Expected Answer:**
1. **TalkBack:** Enable and navigate
2. **Accessibility Scanner:** Automated tool
3. **UI tests:**
```kotlin
composeTestRule
    .onNode(hasContentDescription("Submit"))
    .assertIsDisplayed()
```
4. **Manual testing:** Font size, colour contrast
5. **User testing:** Real users with disabilities

---

## Phase 6: System Integration

### Intents & Intent Filters

#### 🟢 Explicit vs Implicit intents. Difference?
**Expected Answer:**

**Explicit:**
- Specifies exact component
- For your own app components

```kotlin
Intent(context, ProfileActivity::class.java)
```

**Implicit:**
- Describes action
- System finds handler
- Inter-app communication

```kotlin
Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "Share this")
}
```

---

#### 🟡 How do you implement deep linking?
[Covered in Navigation section above]

---

#### 🟡 How do you handle incoming shares?
**Expected Answer:**
```xml
<intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="text/plain" />
</intent-filter>
```

```kotlin
when (intent?.action) {
    Intent.ACTION_SEND -> {
        if (intent.type == "text/plain") {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            // Handle shared text
        }
    }
}
```

---

### Push Notifications

#### 🟢 How do push notifications work on Android?
**Expected Answer:**
1. Device registers with FCM
2. Gets registration token
3. Token sent to your server
4. Server sends message to FCM
5. FCM delivers to device
6. App handles notification

---

#### 🟡 How do you handle notifications in foreground vs background?
**Expected Answer:**

**Foreground:**
```kotlin
override fun onMessageReceived(message: RemoteMessage) {
    // App is open
    // Show notification or update UI directly
    showNotification(message.notification)
}
```

**Background:**
- System shows notification automatically
- onMessageReceived called when notification tapped

---

#### 🔴 Notification channels. What are they and why?
**Expected Answer:**
- Introduced in Android O
- Group notifications by type
- Users control per-channel
- Cannot be deleted after creation

```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val channel = NotificationChannel(
        "articles",
        "New Articles",
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "Notifications for new articles"
    }
    
    notificationManager.createNotificationChannel(channel)
}
```

---

### Tracking & Analytics

#### 🟡 How do you implement analytics abstraction?
**Expected Answer:**
```kotlin
interface AnalyticsTracker {
    fun trackEvent(event: AnalyticsEvent)
    fun setUserProperty(key: String, value: String)
}

sealed class AnalyticsEvent(
    val name: String,
    val properties: Map<String, Any> = emptyMap()
) {
    data class ArticleViewed(val articleId: String) : 
        AnalyticsEvent("article_viewed", mapOf("id" to articleId))
    
    object FeedRefreshed : AnalyticsEvent("feed_refreshed")
}

// Implementation
class FirebaseAnalyticsTracker @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {
    override fun trackEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name, Bundle().apply {
            event.properties.forEach { (key, value) ->
                putString(key, value.toString())
            }
        })
    }
}
```

**Benefits:**
- Can swap implementations
- Easy to test
- Consistent tracking

---

### Security Fundamentals

#### 🟢 How do you store sensitive data on Android?
**Expected Answer:**
- **EncryptedSharedPreferences:** For key-value pairs
- **Room with SQLCipher:** For database encryption
- **Android Keystore:** For cryptographic keys
- **Never:** Hardcode secrets in code

```kotlin
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

val encryptedPrefs = EncryptedSharedPreferences.create(
    context,
    "secret_prefs",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)

encryptedPrefs.edit().putString("auth_token", token).apply()
```

---

#### 🟡 How do you manage API keys securely?
**Expected Answer:**

**1. local.properties:**
```properties
# local.properties (git-ignored)
API_KEY=your_key_here
```

**2. build.gradle.kts:**
```kotlin
android {
    buildTypes {
        all {
            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        }
    }
}
```

**3. Usage:**
```kotlin
val apiKey = BuildConfig.API_KEY
```

**Never:**
- Hardcode in source
- Commit to version control
- Log keys

---

#### 🟡 What is Android Keystore?
**Expected Answer:**
- Hardware-backed key storage
- Keys never leave secure hardware
- Used for encryption/decryption
- Cannot be extracted
- Survives app uninstall (optional)

**Use cases:**
- Encrypting user data
- Signing authentication tokens
- Biometric authentication
- Certificate pinning

---

#### 🔴 How do you handle ProGuard/R8 for release builds?
**Expected Answer:**
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**proguard-rules.pro:**
```
# Keep models used with Gson/Moshi
-keep class com.devhub.models.** { *; }

# Keep Retrofit interfaces
-keep interface com.devhub.api.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
}
```

**Testing:**
- Test release builds thoroughly
- Use mapping file to deobfuscate crashes
- Check APK with APK Analyzer

---

## Phase 7: Build System

### Gradle

#### 🟢 What is Gradle?
**Expected Answer:**
- Build automation tool
- Manages dependencies
- Compiles code
- Runs tests
- Creates APK/AAB

---

#### 🟡 implementation vs api dependency. Difference?
**Expected Answer:**

**implementation:**
- Dependency not exposed to consumers
- Faster builds
- Use by default

**api:**
- Dependency exposed to consumers
- Use when consumers need the dependency
- Example: If your library returns Retrofit types

```kotlin
dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Hidden
    api("com.my.library:models:1.0.0") // Exposed
}
```

---

#### 🟡 Build types vs product flavors. When to use each?
**Expected Answer:**

**Build Types:**
- How app is built
- debug, release
- debuggable, minification

```kotlin
buildTypes {
    debug {
        isDebuggable = true
    }
    release {
        isMinifyEnabled = true
        proguardFiles(...)
    }
}
```

**Product Flavors:**
- What is built
- free vs paid, dev vs prod

```kotlin
productFlavors {
    create("free") {
        applicationIdSuffix = ".free"
    }
    create("paid") {
        applicationIdSuffix = ".paid"
    }
}
```

**Result:** freeDebug, freeRelease, paidDebug, paidRelease

---

#### 🔴 How do you optimise Gradle build times?
**Expected Answer:**
1. **gradle.properties:**
```properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true
org.gradle.jvmargs=-Xmx4096m
```

2. **Use buildSrc or version catalogs**
3. **Avoid dynamic versions:** `implementation("lib:1.+")` ❌
4. **Use implementation over api**
5. **Enable configuration cache**
6. **Modularise app**
7. **Keep tools updated**

---

### Kotlin Delegation

#### 🟡 What is property delegation in Kotlin?
**Expected Answer:**
- Delegates property get/set to another object
- Built-in: lazy, observable, vetoable
- Custom delegates possible

**Examples:**
```kotlin
// Lazy
val data by lazy { loadData() }

// Observable
var name by Delegates.observable("initial") { _, old, new ->
    println("Changed from $old to $new")
}

// Custom
class Preference<T>(private val key: String, private val default: T)
    : ReadWriteProperty<Any?, T> {
    
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return prefs.get(key, default)
    }
    
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        prefs.set(key, value)
    }
}

// Usage
var username by Preference("username", "")
```

---

#### 🟡 What is class delegation?
**Expected Answer:**
- Delegation pattern built into language
- Forward calls to another object

```kotlin
interface Repository {
    fun getData(): Data
}

class CachingRepository(
    private val remote: RemoteRepository,
    private val cache: CacheRepository
) : Repository by cache {
    
    // Only override what you need
    override fun getData(): Data {
        return cache.getData().ifEmpty {
            remote.getData().also { cache.save(it) }
        }
    }
}
```

---

## Phase 8: Cross-Platform

### Kotlin Multiplatform Mobile (KMM)

#### 🟡 What is KMM?
**Expected Answer:**
- Share code between Android and iOS
- Write business logic once
- Platform-specific UI
- expect/actual mechanism
- Kotlin on both platforms

---

#### 🟡 expect/actual. How does it work?
**Expected Answer:**
```kotlin
// Common
expect class Platform() {
    val name: String
}

// Android
actual class Platform {
    actual val name: String = "Android"
}

// iOS
actual class Platform {
    actual val name: String = "iOS"
}
```

---

#### 🔴 When should you use KMM vs native?
**Expected Answer:**

**Use KMM when:**
- Want to share business logic
- Have Kotlin expertise
- Multiple platforms needed
- Logic is complex

**Use Native when:**
- Platform-specific features critical
- Small team (can't support both)
- UI-heavy app
- Existing large codebases

**What to share:**
- Business logic ✅
- Data models ✅
- Repository layer ✅
- Use cases ✅
- Networking ✅
- Database ✅

**What not to share:**
- UI ❌
- Platform-specific APIs ❌
- Navigation ❌

---

## Phase 9: CI/CD & DevOps

### GitHub Actions for Android

#### 🟢 What is CI/CD?
**Expected Answer:**
- **CI (Continuous Integration):** Automatically build and test code on every commit
- **CD (Continuous Deployment):** Automatically deploy to production/distribution
- Catch bugs early
- Faster feedback
- Consistent builds
- Automated workflows

---

#### 🟡 How do you set up GitHub Actions for Android?
**Expected Answer:**
```yaml
name: Android CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

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
        cache: gradle

    - name: Grant execute permission for gradlew
      run: chmod +x gradlew

    - name: Build with Gradle
      run: ./gradlew build

    - name: Run tests
      run: ./gradlew test

    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app
        path: app/build/outputs/apk/debug/app-debug.apk
```

---

#### 🔴 How do you handle secrets in CI/CD?
**Expected Answer:**

**GitHub Secrets:**
1. Repository Settings → Secrets → New secret
2. Add: `API_KEY`, `KEYSTORE_PASSWORD`, etc.

**Usage in workflow:**
```yaml
- name: Build Release APK
  env:
    API_KEY: ${{ secrets.API_KEY }}
    KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
  run: ./gradlew assembleRelease
```

**For local.properties:**
```yaml
- name: Create local.properties
  run: |
    echo "API_KEY=${{ secrets.API_KEY }}" > local.properties
```

**Never:**
- Commit secrets to repo
- Log secrets in CI output
- Use secrets in pull requests from forks (disabled by default)

---

### Automated Testing Pipeline

#### 🟡 How do you run instrumented tests on CI?
**Expected Answer:**
```yaml
test:
  runs-on: macos-latest

  steps:
  - uses: actions/checkout@v3

  - name: Run instrumented tests
    uses: reactivecircus/android-emulator-runner@v2
    with:
      api-level: 31
      arch: x86_64
      profile: pixel_6
      script: ./gradlew connectedCheck
```

**Note:** macOS runners have hardware acceleration for emulators

---

#### 🔴 How do you implement matrix testing across API levels?
**Expected Answer:**
```yaml
test:
  runs-on: macos-latest
  strategy:
    matrix:
      api-level: [24, 28, 31, 34]

  steps:
  - uses: actions/checkout@v3

  - name: Test on API ${{ matrix.api-level }}
    uses: reactivecircus/android-emulator-runner@v2
    with:
      api-level: ${{ matrix.api-level }}
      script: ./gradlew connectedCheck
```

**Benefits:**
- Test on multiple Android versions
- Catch version-specific bugs
- Parallel execution

**Trade-offs:**
- Longer CI times
- More CI minutes consumed
- Use caching to optimize

---

### Code Quality Automation

#### 🟡 What static analysis tools do you use for Android?
**Expected Answer:**

**1. Detekt (Kotlin static analysis):**
```kotlin
// build.gradle.kts
plugins {
    id("io.gitlab.arturbosch.detekt") version "1.23.0"
}

detekt {
    config = files("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}
```

**2. Ktlint (Kotlin formatting):**
```kotlin
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0"
}
```

**3. Android Lint:**
```kotlin
android {
    lint {
        abortOnError = true
        warningsAsErrors = true
    }
}
```

**CI Integration:**
```yaml
- name: Run code quality checks
  run: |
    ./gradlew detekt
    ./gradlew ktlintCheck
    ./gradlew lint
```

---

#### 🔴 How do you enforce code quality in pull requests?
**Expected Answer:**

**1. Branch protection rules:**
- Require status checks to pass
- Require reviews
- No direct push to main

**2. GitHub Actions:**
```yaml
name: Code Quality

on: pull_request

jobs:
  quality:
    runs-on: ubuntu-latest
    steps:
    - name: Detekt
      run: ./gradlew detekt

    - name: Ktlint
      run: ./gradlew ktlintCheck

    - name: Lint
      run: ./gradlew lintDebug

    - name: Tests
      run: ./gradlew testDebugUnitTest
```

**3. Fail on violations:**
- Configure tools to fail build
- Upload reports as artifacts
- Comment on PR with issues

---

### Automated Releases

#### 🟡 How do you automate versioning?
**Expected Answer:**

**Semantic Versioning:**
```kotlin
// build.gradle.kts
android {
    val versionMajor = 1
    val versionMinor = 2
    val versionPatch = 3
    val versionBuild = System.getenv("GITHUB_RUN_NUMBER")?.toInt() ?: 0

    defaultConfig {
        versionCode = versionMajor * 10000 + versionMinor * 1000 +
                      versionPatch * 100 + versionBuild
        versionName = "$versionMajor.$versionMinor.$versionPatch"
    }
}
```

**Git tags:**
```yaml
- name: Create Release
  if: github.ref == 'refs/heads/main'
  run: |
    git tag v${{ env.VERSION }}
    git push origin v${{ env.VERSION }}
```

---

#### 🔴 How do you automate deployment to Firebase App Distribution?
**Expected Answer:**
```yaml
- name: Build Release APK
  run: ./gradlew assembleRelease

- name: Upload to Firebase App Distribution
  uses: wzieba/Firebase-Distribution-Github-Action@v1
  with:
    appId: ${{ secrets.FIREBASE_APP_ID }}
    serviceCredentialsFileContent: ${{ secrets.FIREBASE_CREDENTIALS }}
    groups: testers
    file: app/build/outputs/apk/release/app-release.apk
    releaseNotes: |
      Changes in this release:
      ${{ github.event.head_commit.message }}
```

**Benefits:**
- Automatic distribution to testers
- Release notes from commits
- Track versions
- Crash reports

---

### Build Performance Monitoring

#### 🟡 What Gradle optimizations improve build speed?
**Expected Answer:**

**gradle.properties:**
```properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m
org.gradle.daemon=true

# Kotlin
kotlin.incremental=true
kotlin.daemon.jvmargs=-Xmx2g

# AndroidX
android.useAndroidX=true
android.enableJetifier=false
```

**Configuration cache:**
```yaml
- name: Build with configuration cache
  run: ./gradlew build --configuration-cache
```

---

#### 🔴 How do you track build performance over time?
**Expected Answer:**

**1. Gradle Build Scans:**
```kotlin
// settings.gradle.kts
plugins {
    id("com.gradle.enterprise") version "3.14"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
```

**2. CI metrics:**
```yaml
- name: Build with metrics
  run: |
    time ./gradlew assembleDebug --scan
```

**3. Track over time:**
- Store build times in database
- Create dashboard
- Alert on regressions
- Compare branches

**Key metrics:**
- Configuration time
- Task execution time
- Cache hit rate
- Incremental build time

---

## Phase 10: Security Deep Dive

### Secure Storage & Encryption

#### 🟡 Explain symmetric vs asymmetric encryption
**Expected Answer:**

**Symmetric (AES):**
- Same key for encrypt/decrypt
- Fast
- Good for large data
- Example: File encryption

```kotlin
val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
keyGenerator.init(
    KeyGenParameterSpec.Builder(
        "MyKeyAlias",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
    .build()
)
val secretKey = keyGenerator.generateKey()
```

**Asymmetric (RSA):**
- Public key encrypts, private key decrypts
- Slower
- Good for small data
- Example: Key exchange, signatures

```kotlin
val keyPairGenerator = KeyPairGenerator.getInstance(
    KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore"
)
keyPairGenerator.initialize(
    KeyGenParameterSpec.Builder(
        "MyKeyAlias",
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
    .setKeySize(2048)
    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
    .build()
)
val keyPair = keyPairGenerator.generateKeyPair()
```

---

#### 🔴 How do you implement database encryption with SQLCipher?
**Expected Answer:**
```kotlin
// build.gradle.kts
dependencies {
    implementation("net.zetetic:android-database-sqlcipher:4.5.4")
    implementation("androidx.sqlite:sqlite:2.3.1")
}

// Database
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        fun create(context: Context): AppDatabase {
            val passphrase = getOrCreatePassphrase(context)
            val factory = SupportFactory(SQLiteDatabase.getBytes(passphrase))

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app.db"
            )
            .openHelperFactory(factory)
            .build()
        }

        private fun getOrCreatePassphrase(context: Context): CharArray {
            // Store passphrase in EncryptedSharedPreferences
            // Or use Android Keystore
        }
    }
}
```

**Key points:**
- Passphrase stored securely
- All tables encrypted
- Small performance overhead
- Protects against device access

---

### Network Security & SSL Pinning

#### 🟡 What is certificate pinning? Why use it?
**Expected Answer:**
**What:**
- Pin specific certificate or public key
- Reject connections if certificate doesn't match
- Prevents MITM attacks even with compromised CAs

**Why:**
- Extra security for sensitive apps (banking, healthcare)
- Protection against CA compromise
- Ensure talking to right server

**Trade-offs:**
- Certificate rotation requires app update
- Need backup pins
- Can break during debugging

---

#### 🔴 How do you implement SSL pinning with OkHttp?
**Expected Answer:**
```kotlin
val certificatePinner = CertificatePinner.Builder()
    .add(
        "api.devhub.com",
        "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="
    )
    .add(
        "api.devhub.com", // Backup pin
        "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB="
    )
    .build()

val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinner)
    .build()
```

**Get certificate pin:**
```bash
openssl s_client -connect api.devhub.com:443 | \
  openssl x509 -pubkey -noout | \
  openssl pkey -pubin -outform der | \
  openssl dgst -sha256 -binary | \
  openssl enc -base64
```

**Network Security Config (alternative):**
```xml
<!-- res/xml/network_security_config.xml -->
<network-security-config>
    <domain-config>
        <domain includeSubdomains="true">api.devhub.com</domain>
        <pin-set expiration="2025-12-31">
            <pin digest="SHA-256">AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=</pin>
            <pin digest="SHA-256">BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=</pin>
        </pin-set>
    </domain-config>
</network-security-config>
```

---

### Authentication & Authorization

#### 🟡 Explain the OAuth 2.0 flow for mobile apps
**Expected Answer:**

**Authorization Code Flow with PKCE:**

1. **Generate code verifier and challenge:**
```kotlin
val codeVerifier = generateCodeVerifier() // Random string
val codeChallenge = base64UrlEncode(sha256(codeVerifier))
```

2. **Redirect to authorization URL:**
```kotlin
val authUrl = "https://auth.devhub.com/authorize?" +
    "client_id=YOUR_CLIENT_ID&" +
    "redirect_uri=devhub://callback&" +
    "response_type=code&" +
    "code_challenge=$codeChallenge&" +
    "code_challenge_method=S256&" +
    "scope=read write"

// Open in Custom Tab or browser
```

3. **Receive authorization code:**
```kotlin
// Handle deep link: devhub://callback?code=AUTH_CODE
```

4. **Exchange code for tokens:**
```kotlin
val response = api.getToken(
    grant_type = "authorization_code",
    code = authCode,
    code_verifier = codeVerifier,
    redirect_uri = "devhub://callback"
)

val accessToken = response.access_token
val refreshToken = response.refresh_token
```

**PKCE benefits:**
- Prevents authorization code interception
- No client secret needed
- Secure for public clients

---

#### 🔴 How do you implement biometric authentication?
**Expected Answer:**
```kotlin
class BiometricAuthenticator(private val context: Context) {

    fun authenticate(
        onSuccess: (BiometricPrompt.AuthenticationResult) -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    onSuccess(result)
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    onError(errString.toString())
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate to access DevHub")
            .setSubtitle("Use your fingerprint or face")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        // Check if biometric available
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            else -> {
                onError("Biometric not available")
            }
        }
    }

    // Use with CryptoObject for encryption
    fun authenticateWithCrypto(cipher: Cipher, onSuccess: (Cipher) -> Unit) {
        val cryptoObject = BiometricPrompt.CryptoObject(cipher)
        // ... authenticate with cryptoObject
    }
}
```

---

### Code Protection & App Integrity

#### 🟡 What is the Play Integrity API?
**Expected Answer:**
- Replaces SafetyNet
- Checks app integrity
- Detects tampering
- Verifies device is genuine

**Verdicts:**
- **Device:** Play Protect status, device recognition
- **App:** Installed from Play Store, not tampered
- **Account:** User account status

**Implementation:**
```kotlin
val integrityManager = IntegrityManagerFactory.create(context)

val tokenRequest = IntegrityTokenRequest.builder()
    .setCloudProjectNumber(PROJECT_NUMBER)
    .setNonce(generateNonce())
    .build()

integrityManager.requestIntegrityToken(tokenRequest)
    .addOnSuccessListener { response ->
        val token = response.token()
        // Send to your server for verification
    }
```

---

#### 🔴 How do you detect root/jailbreak?
**Expected Answer:**

**Detection methods:**
```kotlin
object RootDetection {

    fun isRooted(): Boolean {
        return checkBuildTags() ||
               checkSuperuserApk() ||
               checkSuBinary() ||
               checkRWPaths()
    }

    private fun checkBuildTags(): Boolean {
        return Build.TAGS?.contains("test-keys") == true
    }

    private fun checkSuperuserApk(): Boolean {
        val packages = listOf(
            "com.noshufou.android.su",
            "com.thirdparty.superuser",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser"
        )
        return packages.any { isPackageInstalled(it) }
    }

    private fun checkSuBinary(): Boolean {
        val paths = listOf(
            "/system/app/Superuser.apk",
            "/system/xbin/su",
            "/system/bin/su",
            "/sbin/su",
            "/data/local/su"
        )
        return paths.any { File(it).exists() }
    }

    private fun checkRWPaths(): Boolean {
        val paths = listOf("/system", "/system/bin", "/system/sbin")
        return paths.any { canWriteToPath(it) }
    }
}
```

**Note:** Determined attackers can bypass these. Use as deterrent, not absolute security.

**Better approach:** Play Integrity API + server-side verification

---

## Phase 11: Performance & Advanced Topics

### Performance Profiling & Optimization

#### 🟡 What tools do you use to profile Android apps?
**Expected Answer:**

**1. Android Studio Profiler:**
- CPU: Method tracing, sampling
- Memory: Heap dumps, allocation tracking
- Network: Request monitoring
- Energy: Battery usage

**2. LeakCanary:**
```kotlin
// Automatic setup in debug builds
dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}
```

**3. Macrobenchmark:**
```kotlin
@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.devhub",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }
}
```

**4. Baseline Profiles:**
- Pre-compile critical paths
- Improve startup time
- Generate from Macrobenchmark

---

#### 🔴 How do you optimize app startup time?
**Expected Answer:**

**1. Lazy initialization:**
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Essential only
        Timber.plant(Timber.DebugTree())

        // Lazy init on background
        lifecycleScope.launch(Dispatchers.Default) {
            initAnalytics()
            initCrashReporting()
        }
    }
}
```

**2. Content Provider elimination:**
```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    tools:node="remove" />
```

**3. Baseline Profiles:**
```kotlin
// Generate profile
./gradlew :app:generateBaselineProfile

// Profile installed automatically on first launch
```

**4. App Startup library:**
```kotlin
class AnalyticsInitializer : Initializer<Analytics> {
    override fun create(context: Context): Analytics {
        return Analytics.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
```

**Measure:**
- Reportfully.startTime
- Time to initial display (TTID)
- Time to full display (TTFD)

---

### Offline-First Architecture

#### 🟡 What is offline-first architecture?
**Expected Answer:**
- Local database is single source of truth
- Network updates database
- UI always reads from database
- Works offline automatically
- Sync when connected

**Benefits:**
- Fast app (no network wait)
- Works offline
- Better user experience
- Resilient to poor network

---

#### 🔴 How do you implement data synchronization?
**Expected Answer:**

**Repository pattern:**
```kotlin
class ArticleRepository(
    private val api: ArticleApi,
    private val dao: ArticleDao,
    private val workManager: WorkManager
) {
    // Single source of truth
    fun observeArticles(): Flow<List<Article>> {
        return dao.observeAll()
    }

    // Refresh from network
    suspend fun refresh() {
        try {
            val articles = api.getArticles()
            dao.insertAll(articles)
        } catch (e: IOException) {
            // Continue with cached data
        }
    }

    // Create article (sync later)
    suspend fun createArticle(article: Article) {
        // 1. Save locally immediately
        dao.insert(article.copy(syncStatus = SyncStatus.PENDING))

        // 2. Schedule sync
        val work = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueue(work)
    }
}

// Worker for background sync
class SyncWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val pendingArticles = dao.getPending()
            pendingArticles.forEach { article ->
                api.createArticle(article)
                dao.update(article.copy(syncStatus = SyncStatus.SYNCED))
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
```

**Conflict resolution:**
- Last write wins
- Server wins
- Client wins
- Merge strategies
- Timestamp comparison

---

### Advanced Compose Layouts

#### 🔴 How do you create a custom Layout in Compose?
**Expected Answer:**
```kotlin
@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Measure children
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(minHeight = 0))
        }

        // Calculate layout height
        val height = placeables.sumOf { it.height }

        // Set size and position children
        layout(constraints.maxWidth, height) {
            var yPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}
```

**Key concepts:**
- `measurables`: Children to measure
- `constraints`: Size constraints
- `measure()`: Get placeable
- `layout()`: Set size and place children

---

#### 🔴 When would you use SubcomposeLayout?
**Expected Answer:**
**Use when:** Need to measure some children to determine layout of others

**Example: Custom TabRow**
```kotlin
@Composable
fun CustomTabRow(
    selectedIndex: Int,
    tabs: @Composable () -> Unit
) {
    SubcomposeLayout { constraints ->
        // 1. Measure tabs first
        val tabMeasurables = subcompose("tabs", tabs)
        val tabPlaceables = tabMeasurables.map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }

        val tabWidth = tabPlaceables[selectedIndex].width

        // 2. Now measure indicator with exact width
        val indicator = subcompose("indicator") {
            Indicator(width = tabWidth.toDp())
        }[0].measure(constraints)

        // 3. Layout
        layout(constraints.maxWidth, constraints.maxHeight) {
            tabPlaceables.forEachIndexed { index, placeable ->
                placeable.place(x = index * placeable.width, y = 0)
            }
            indicator.place(x = selectedIndex * tabWidth, y = tabHeight)
        }
    }
}
```

**Use cases:**
- Adaptive layouts
- Dependent measurements
- Dynamic composition

---

### Animations & Shared Elements

#### 🟡 What animation APIs does Compose provide?
**Expected Answer:**

**1. Animate*AsState (Simple):**
```kotlin
val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    label = "alpha"
)

Box(modifier = Modifier.alpha(alpha))
```

**2. Transition (Multiple properties):**
```kotlin
val transition = updateTransition(state, label = "state")

val alpha by transition.animateFloat(label = "alpha") { state ->
    if (state) 1f else 0f
}

val scale by transition.animateFloat(label = "scale") { state ->
    if (state) 1f else 0.8f
}
```

**3. Animatable (Manual control):**
```kotlin
val offset = remember { Animatable(0f) }

LaunchedEffect(Unit) {
    offset.animateTo(
        targetValue = 100f,
        animationSpec = spring(dampingRatio = 0.5f)
    )
}
```

**4. AnimatedVisibility:**
```kotlin
AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + slideInVertically(),
    exit = fadeOut() + slideOutVertically()
) {
    Content()
}
```

---

#### 🔴 How do you implement shared element transitions?
**Expected Answer:**
```kotlin
// List screen
@Composable
fun ArticleList(navController: NavController) {
    LazyColumn {
        items(articles) { article ->
            SharedTransitionScope.rememberSharedContentState(
                key = "article-${article.id}"
            ).let { sharedContentState ->
                ArticleCard(
                    article = article,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState,
                            animatedVisibilityScope = this@AnimatedVisibility
                        )
                        .clickable {
                            navController.navigate("article/${article.id}")
                        }
                )
            }
        }
    }
}

// Detail screen
@Composable
fun ArticleDetail(articleId: String) {
    val sharedContentState = SharedTransitionScope.rememberSharedContentState(
        key = "article-$articleId"
    )

    Column {
        Image(
            painter = painterResource(article.image),
            modifier = Modifier
                .sharedElement(
                    sharedContentState,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
        )
        // Rest of detail
    }
}
```

**Key requirements:**
- Same shared element key
- SharedTransitionScope
- AnimatedVisibilityScope

---

## General Android Questions

### 🟢 Activity lifecycle. Explain it.
**Expected Answer:**
1. onCreate()
2. onStart()
3. onResume()
4. onPause()
5. onStop()
6. onDestroy()

**Configuration change:** Destroys and recreates

---

### 🟡 How do you handle configuration changes?
**Expected Answer:**
1. **ViewModel:** Survives configuration changes
2. **SavedInstanceState:** Save UI state
3. **Manifest:** android:configChanges (avoid)

---

### 🟡 What is Context? Types of Context?
**Expected Answer:**
- Interface to app environment
- Access resources, launch activities, etc.

**Types:**
- **Application Context:** Lives for app lifetime
- **Activity Context:** Tied to Activity
- **Service Context:** Tied to Service

**Use Application Context for:**
- Singletons
- Long-lived objects

**Use Activity Context for:**
- UI operations
- Showing dialogs

---

### 🔴 Memory leaks. How do you prevent them?
**Expected Answer:**

**Common causes:**
1. Static references to Context
2. Non-static inner classes
3. Handler without weak references
4. Uncancelled coroutines
5. LiveData observers not removed

**Prevention:**
```kotlin
// Bad
class MyActivity : AppCompatActivity() {
    companion object {
        lateinit var context: Context // Leak!
    }
}

// Good
class MyActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.data.observe(this) { // Auto-cleaned }
    }
}
```

**Tools:**
- LeakCanary
- Android Studio Profiler

---

### 🔴 How does Android handle process death?
**Expected Answer:**
- System can kill process to free memory
- Low priority processes killed first
- onCreate() called again
- ViewModels cleared
- SavedInstanceState survives

**Handling:**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    if (savedInstanceState != null) {
        // Restore state
        val data = savedInstanceState.getString("key")
    }
}

override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString("key", data)
}
```

---

## Coding Challenges

### Challenge 1: Implement a debounced search
**Question:** User types in search box. Wait 300ms after they stop typing before searching.

**Answer:**
```kotlin
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(query) {
        delay(300) // Debounce
        viewModel.search(query)
    }
    
    TextField(
        value = query,
        onValueChange = { query = it },
        label = { Text("Search") }
    )
    
    LazyColumn {
        items(state.results) { result ->
            ResultItem(result)
        }
    }
}
```

---

### Challenge 2: Paginated list with Compose
**Question:** Load articles 20 at a time as user scrolls.

**Answer:**
```kotlin
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    LazyColumn(state = listState) {
        items(state.articles) { article ->
            ArticleItem(article)
        }
        
        if (state.isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
    }
    
    // Load more when near end
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisible ->
            if (lastVisible != null && 
                lastVisible >= state.articles.size - 5) {
                viewModel.loadMore()
            }
        }
    }
}
```

---

### Challenge 3: Retry logic with exponential backoff
**Question:** Retry failed API calls with increasing delays.

**Answer:**
```kotlin
suspend fun <T> retryWithExponentialBackoff(
    times: Int = 3,
    initialDelay: Long = 1000,
    maxDelay: Long = 10000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
    }
    return block() // Last attempt
}

// Usage
val data = retryWithExponentialBackoff {
    api.getData()
}
```

---

## System Design Questions

### Design Instagram feed
**Expected Answer:**

**Architecture:**
- MVI for feed screen
- Repository pattern
- Room for offline caching
- Paging for infinite scroll

**Components:**
1. **FeedScreen (Compose)**
   - LazyColumn with images
   - Like/comment buttons
   - Pull to refresh

2. **FeedViewModel (MVI)**
   - State: posts, loading, error
   - Intents: LoadFeed, Refresh, LikePost
   - Effects: Navigation, errors

3. **FeedRepository**
   - Network source (API)
   - Local source (Room)
   - Caching strategy

4. **Database (Room)**
   - Post entity
   - User entity
   - Relationships

5. **Networking (Retrofit)**
   - Get feed endpoint
   - Like post endpoint
   - Upload image endpoint

**Additional:**
- Image loading (Coil)
- Video playback (ExoPlayer)
- Analytics tracking
- Push notifications

---

## Behavioural Questions

### Tell me about a challenging bug you fixed
**Structure:**
- **Situation:** What was the bug?
- **Task:** Your responsibility
- **Action:** Steps you took
- **Result:** Outcome and learnings

**Example:** "In DevHub, users reported crashes when rotating device during image upload..."

---

### How do you stay updated with Android?
**Expected Answer:**
- Official Android blog
- Android Dev Summit
- KotlinConf
- Medium articles
- Open source contributions
- Side projects (like DevHub!)
- Reddit r/androiddev
- Twitter/X Android developers

---

## Tips for Interviews

1. **Think out loud:** Explain your reasoning
2. **Ask questions:** Clarify requirements
3. **Start simple:** Basic solution first, then optimise
4. **Test your code:** Walk through edge cases
5. **Discuss trade-offs:** Show you understand options
6. **Use examples:** Reference DevHub project
7. **Be honest:** It's okay to say "I don't know, but..."
8. **Stay calm:** Take your time to think

---

**Remember:** Interviews are conversations. Show your thought process, enthusiasm for learning, and ability to communicate technical concepts clearly. Good luck! 🚀
