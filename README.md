# DevHub - Android Developer Training Project

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-purple.svg?style=flat&logo=kotlin)](http://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8.1.4-green.svg?style=flat)](https://gradle.org)
[![Android](https://img.shields.io/badge/Android-24%2B-brightgreen.svg?style=flat&logo=android)](https://android.com)

## 📚 About This Project

**DevHub** is a comprehensive **Android training project** designed for developers preparing for senior Android positions.

### 📱 What You'll Build

**DevHub** is a **developer community platform** (think dev.to or Medium for developers) where users can:

**Core User Experience:**
- 📰 **Browse a feed** of technical articles, tutorials, and developer insights
- ✍️ **Write and publish** articles with Markdown support and code syntax highlighting
- 👤 **Create profiles** showcasing their expertise and contributions
- 🔍 **Search** for articles by topic, tags, or authors
- 🔔 **Receive notifications** for new articles from followed topics/authors
- 💬 **Comment and discuss** on articles (planned feature)
- 🔖 **Bookmark** articles for later reading
- 👥 **Follow** other developers and topics of interest

**Why This App?**
- **Rich feature set** that requires multiple architectural patterns
- **Real-world complexity** similar to production apps (auth, networking, database, notifications)
- **Portfolio-worthy** - demonstrates end-to-end Android development skills

**Example User Journey:**
```
1. User opens app → sees feed of latest dev articles (MVI architecture)
2. User taps article → reads full content with syntax-highlighted code
3. User likes article → taps author's name to view profile (MVVM architecture)
4. User follows author → receives push notification when author publishes
5. User creates article → uses markdown editor with preview (TDD implementation)
6. User searches "kotlin coroutines" → finds relevant articles instantly
```

### 🎯 Purpose

This project serves **three key purposes**:

1. **Learning Platform** - Hands-on practice with modern Android development patterns and tools
2. **Interview Preparation** - Demonstrates proficiency in topics commonly asked in Android interviews
3. **AI-Assisted Development** - Designed to be built **with Claude Code** as your AI pair programming partner

### 🤖 Why Claude Code?

This project is **specifically designed to be built using the [Claude Code](https://claude.com/claude-code) plugin** for two critical reasons:

#### 1. **Real-World AI Collaboration Skills**
- Modern development teams increasingly use AI assistants for code reviews, refactoring, and implementation
- Building this project with Claude Code teaches you **how to effectively work with AI tools**
- Learn to delegate tasks, review AI-generated code, and make architectural decisions with AI assistance

#### 2. **Expert Guidance Throughout**
- Claude Code has access to **project-specific instructions** (`.claude/project-instructions.md`) that ensure best practices
- Get **instant code reviews** for architectural patterns (MVVM vs MVI)
- Receive **explanations** of complex topics as you implement them
- **Automated test writing** following TDD principles
- **Accessibility checks** and suggestions as you build UI

**Without Claude Code**, you're just following tutorials. **With Claude Code**, you're learning how professional Android teams work today.

## 🎯 Learning Objectives

### Core Topics Covered

#### Architecture & Design Patterns
- ✅ MVVM Architecture (Profile feature)
- ✅ MVI Architecture (Feed feature)
- ✅ ViewModels & Lifecycle management
- ✅ Repository pattern
- ✅ Use cases / Interactors

#### Modern UI Development
- ✅ Jetpack Compose
- ✅ Fragments (legacy support)
- ✅ Navigation Component (XML & Compose)
- ✅ Material Design 3
- ✅ Responsive layouts

#### Dependency Injection
- ✅ Dagger-Hilt setup
- ✅ Multi-module DI
- ✅ Testing with Hilt

#### Reactive Programming
- ✅ Kotlin Coroutines
- ✅ Flow, StateFlow, SharedFlow
- ✅ Structured concurrency
- ✅ Error handling

#### Testing & Quality
- ✅ Test-Driven Development (TDD)
- ✅ Unit testing (JUnit, MockK)
- ✅ UI testing (Compose Testing, Espresso)
- ✅ Integration testing
- ✅ Accessibility compliance

#### System Integration
- ✅ Push notifications (FCM)
- ✅ Deep linking
- ✅ Analytics tracking
- ✅ Intents & Intent Filters

#### Security
- ✅ Secure data storage (EncryptedSharedPreferences)
- ✅ Android Keystore & encryption
- ✅ SSL/Certificate pinning
- ✅ OAuth 2.0 & JWT authentication
- ✅ Biometric authentication
- ✅ ProGuard/R8 & code obfuscation
- ✅ Root detection & app integrity
- ✅ API key management

#### Build System
- ✅ Gradle Kotlin DSL
- ✅ Multi-module architecture
- ✅ Build variants
- ✅ Dependency management

#### Advanced Topics
- ✅ Kotlin Multiplatform Mobile (KMM)
- ✅ Creating an SDK
- ✅ Kotlin delegation
- ✅ CI/CD with GitHub Actions
- ✅ Performance profiling & optimization
- ✅ Offline-first architecture
- ✅ Advanced Compose layouts & animations

## 🏗️ Project Structure

```
DevHub/
├── app/                           # Main application module
├── core/
│   ├── core-common/              # Common utilities
│   ├── core-network/             # Networking layer (Retrofit, OkHttp)
│   ├── core-database/            # Database layer (Room)
│   ├── core-analytics/           # Analytics abstraction
│   └── core-ui/                  # Shared UI components
├── feature/
│   ├── feature-feed/             # Article feed (MVI architecture)
│   ├── feature-profile/          # User profile (MVVM architecture)
│   ├── feature-editor/           # Content editor (TDD-focused)
│   ├── feature-notifications/    # Push notifications
│   └── feature-search/           # Search functionality
├── sdk-devhub/                   # Public SDK module
└── buildSrc/                     # Build logic & dependencies
```

### Architecture Layers

```
┌─────────────────────────────────────┐
│         Presentation Layer          │
│  (Compose UI, ViewModels, Fragments)│
├─────────────────────────────────────┤
│          Domain Layer               │
│      (Use Cases, Models)            │
├─────────────────────────────────────┤
│           Data Layer                │
│  (Repositories, Data Sources)       │
├─────────────────────────────────────┤
│      Infrastructure Layer           │
│  (Network, Database, Analytics)     │
└─────────────────────────────────────┘
```

## 🛠️ Tech Stack

### Core
- **Language:** Kotlin 1.9.20
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Build Tool:** Gradle 8.1.4 (Kotlin DSL)

### UI
- **Jetpack Compose** - Modern declarative UI
- **Material Design 3** - Latest design system
- **Coil** - Image loading
- **Accompanist** - Compose utilities

### Architecture
- **Dagger-Hilt** - Dependency injection
- **Kotlin Coroutines** - Asynchronous programming
- **Flow/StateFlow** - Reactive streams
- **ViewModel** - UI state management
- **Navigation Component** - App navigation

### Networking & Data
- **Retrofit** - REST API client
- **OkHttp** - HTTP client
- **Gson** - JSON parsing
- **Room** - Local database
- **DataStore** - Preferences storage

### Testing
- **JUnit 4** - Unit testing framework
- **MockK** - Mocking library
- **Turbine** - Flow testing
- **Compose Testing** - UI testing
- **Espresso** - UI testing (Views)
- **Truth** - Assertions

### Firebase
- **Firebase Cloud Messaging** - Push notifications
- **Firebase Analytics** - Event tracking
- **Crashlytics** - Crash reporting

### Tools
- **LeakCanary** - Memory leak detection
- **Timber** - Logging
- **Detekt** - Static code analysis

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Hedgehog | 2023.1.1 or later
- **JDK 17**
- **Android SDK 34**
- **Git**
- **[Claude Code Plugin](https://claude.com/claude-code)** (HIGHLY RECOMMENDED)

### Initial Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/MigP/devhub.git
   cd devhub
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Install Claude Code** (Recommended)
   - Follow the [official installation guide](https://docs.claude.com/en/docs/claude-code/overview)
   - Open the project in Claude Code
   - The project instructions in `.claude/project-instructions.md` will **automatically load**
   - **Configure security settings** (see below before starting)
   - Start building features with AI assistance

4. **Sync Gradle**
   - Android Studio should automatically sync
   - If not, click "Sync Now" in the notification bar

5. **Add Firebase Configuration** (Optional)
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Download `google-services.json`
   - Place it in `app/` directory

6. **Run the app**
   ```bash
   ./gradlew installDebug
   ```
   Or use the "Run" button in Android Studio

---

### 🔒 Security & Privacy Configuration (IMPORTANT)

Before using Claude Code with this project, **configure these security settings** to protect sensitive data:

#### 1. **Prevent Sensitive Files from Being Shared**

The project includes `.claude/settings.json` with security configurations that prevent Claude from accessing:
- Environment files (`.env`)
- Local configuration (`local.properties`)
- Firebase config (`google-services.json`)
- Signing keys (`.jks`, `.keystore`)
- Git internals

**✅ These settings are already configured** - review `.claude/settings.json` and customize if needed.

#### 2. **Configure Data Retention** (Optional)

**Consumer users (Free/Pro):**
- Data retained for 30 days by default
- Can opt out of training data usage in Claude settings

**Commercial users (Team/Enterprise):**
- Configure zero-retention via API settings
- Code never used for model training

**To minimize data sharing:**
```bash
# Add to your shell profile (~/.bashrc or ~/.zshrc)
export STATSIG_ENABLED=false  # Disable telemetry
export SENTRY_ENABLED=false   # Disable error reporting
```

#### 3. **Personal Settings** (Git-Ignored)

Create `.claude/settings.local.json` (this file is git-ignored):

```json
{
  "environment": {
    "ANDROID_HOME": "/path/to/your/android-sdk"
  }
}
```

Add personal configurations here that shouldn't be shared with the team.

#### 4. **Review Permissions Regularly**

While using Claude Code:
```bash
/permissions              # Check current permission settings
/sandbox                  # Enable filesystem/network isolation
```

**📘 For complete security documentation, see `CLAUDE_CHEATSHEET.md`**

---

### 🗺️ Navigating the Project

This project includes several **essential files** to guide your learning:

| File | Purpose |
|------|---------|
| **`CLAUDE_CHEATSHEET.md`** | 📘 Complete reference for using Claude Code effectively (start here!) |
| **`.claude/settings.json`** | 🔒 Security settings - prevents Claude from accessing sensitive files |
| **`.claude/project-instructions.md`** | Comprehensive training guide that Claude Code loads automatically |
| **`.clinerules`** | Alternative format of project instructions (for reference) |
| **`devhub-support-files/PROGRESS_TRACKER.md`** | Track your progress through all training phases and topics |
| **`devhub-support-files/INTERVIEW_QUESTIONS.md`** | Common interview questions for each topic you implement |
| **`CHANGELOG.md`** | Project evolution and feature implementation history |

### 🎓 How to Use This Project

**Option 1: With Claude Code (Recommended)**
1. **Read `CLAUDE_CHEATSHEET.md` first** to learn keyboard shortcuts, commands, and workflows
2. Open the project in Claude Code
3. Ask Claude to implement features following the training phases (see PROGRESS_TRACKER.md)
4. Review Claude's code and ask questions
5. Make architectural decisions with Claude's guidance
6. Learn by doing, not just reading

Example prompts:
- "Help me implement the Feed feature using MVI architecture"
- "Review this ViewModel for best practices"
- "Write tests for the ProfileRepository using TDD"
- "Make this composable accessible"

**Option 2: Self-Guided**
1. Read `.claude/project-instructions.md` for the training structure
2. Follow the phases in `PROGRESS_TRACKER.md`
3. Implement features yourself
4. Use `INTERVIEW_QUESTIONS.md` to test your knowledge

### Build Variants

```bash
# Debug build (with debugging enabled)
./gradlew assembleDebug

# Release build (optimised, minified)
./gradlew assembleRelease

# Run tests
./gradlew test
./gradlew connectedAndroidTest
```

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Run Specific Test
```bash
./gradlew test --tests "FeedViewModelTest"
```

### Test Coverage
```bash
./gradlew jacocoTestReport
```

## 📖 Training Structure

This project follows an **11-phase comprehensive training programme** covering everything from architecture to security and performance:

| Phase | Topics | Duration |
|-------|--------|----------|
| **Phase 1** | Architecture Foundation (MVVM, MVI, ViewModels) | Weeks 1-2 |
| **Phase 2** | Modern UI (Compose, Fragments, Navigation) | Weeks 3-4 |
| **Phase 3** | Dependency Injection (Hilt, SDK creation) | Weeks 5-6 |
| **Phase 4** | Reactive Programming (Coroutines, Flow) | Weeks 7-8 |
| **Phase 5** | Quality & Testing (TDD, Accessibility) | Weeks 9-10 |
| **Phase 6** | System Integration & Security Intro (FCM, Intents, Analytics, Security Basics) | Weeks 11-12 |
| **Phase 7** | Build System (Gradle, Delegation) | Weeks 13-14 |
| **Phase 8** | Cross-Platform (KMM) | Weeks 14-15 |
| **Phase 9** | CI/CD & DevOps (GitHub Actions, Automation, Quality) | Weeks 15-16 |
| **Phase 10** | Security Deep Dive (Encryption, SSL Pinning, Auth, Code Protection) | Weeks 17-18 |
| **Phase 11** | Performance & Advanced Topics (Profiling, Offline-First, Animations, Advanced Compose) | Weeks 19-20 |

**Full details**: See `.claude/project-instructions.md` or `PROGRESS_TRACKER.md`

## 🎨 Features to Build

This is a **training project** - features are meant to be **built by you** (ideally with Claude Code) as you progress through the training phases:

### Core Features (Build These First)

| Feature | User Experience | Technical Learning |
|---------|----------------|-------------------|
| **User Authentication** | Sign up/login with email, OAuth social login | Firebase Auth, secure token storage |
| **Article Feed** (MVI) | Scroll through dev articles, pull-to-refresh, infinite scroll | MVI architecture, pagination, StateFlow |
| **User Profiles** (MVVM) | View/edit profile, see published articles, follower count | MVVM architecture, image upload, caching |
| **Article Editor** (TDD) | Write articles with Markdown, live preview, code blocks | TDD approach, text processing, custom views |
| **Search** | Type query, see real-time filtered results | Debouncing, Flow operators, Room FTS |
| **Push Notifications** | Get notified when followed authors publish | FCM, notification channels, deep links |
| **Offline Support** | Read cached articles without internet | Room database, sync strategies, WorkManager |

### Additional Features (Optional)

| Feature | User Experience | Technical Learning |
|---------|----------------|-------------------|
| **Dark Mode** | Toggle between light/dark themes | Theme management, preferences |
| **Accessibility** | Screen reader support, semantic labels | TalkBack, accessibility APIs |
| **Comments System** | Reply to articles, nested comment threads | Recursive data structures, UI nesting |
| **Bookmarks** | Save articles, view saved list | Local persistence, collections |
| **Following System** | Follow authors/topics, customized feed | Many-to-many relationships, filters |
| **Categories/Tags** | Browse by topic (Android, Kotlin, etc.) | Taxonomy, filtering, chips UI |
| **Code Highlighting** | See syntax-colored code in articles | Third-party libraries, custom rendering |
| **KMM Shared Logic** | Share business logic with iOS (future) | Kotlin Multiplatform, expect/actual |

**See `PROGRESS_TRACKER.md` for the recommended order to build these features.**

## 🏛️ Architecture Patterns

### Feed Feature (MVI)
```kotlin
// State
data class FeedState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Intent
sealed class FeedIntent {
    object LoadFeed : FeedIntent()
    object Refresh : FeedIntent()
    data class LikeArticle(val id: String) : FeedIntent()
}

// Effect
sealed class FeedEffect {
    data class ShowError(val message: String) : FeedEffect()
    data class NavigateToArticle(val id: String) : FeedEffect()
}
```

### Profile Feature (MVVM)
```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadProfile(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _profile.value = repository.getProfile(userId)
            _isLoading.value = false
        }
    }
}
```

## 🤝 Contributing

This is a personal training project, but suggestions and feedback are welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Coding Standards

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Write self-documenting code
- Add KDoc for public APIs
- Maintain test coverage >80%
- Run `./gradlew detekt` before committing

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Miguel Pinto**
- GitHub: [@MigP](https://github.com/MigP)
- LinkedIn: [Miguel Pinto](https://www.linkedin.com/in/miguel-pinto-android/)

## 🙏 Acknowledgements

- Built as part of Android developer interview preparation
- Follows Modern Android Development best practices
- Inspired by real-world production apps
- Designed specifically for use with [Claude Code](https://claude.com/claude-code)

---

## 📊 Tracking Your Progress

**Don't track progress here!** Use the dedicated files:
- **`devhub-support-files/PROGRESS_TRACKER.md`** - Check off completed topics and track your learning journey
- **`devhub-support-files/INTERVIEW_QUESTIONS.md`** - Test your knowledge after implementing each feature

---

**⭐ If you find this project helpful for your Android learning journey, please give it a star!**

---

*This README describes the project structure and purpose. For progress tracking and detailed training content, see the files mentioned above.*
