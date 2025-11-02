# Changelog

All notable changes to the DevHub Android Training Project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Planned
- Feed feature with MVI architecture
- Profile feature with MVVM architecture
- Editor feature with TDD approach
- Hilt dependency injection setup
- Room database implementation
- Network layer with Retrofit

---

## [0.1.0] - 2025-10-28

### Added
- Initial project setup with multi-module architecture
- Project structure following Android training programme guidelines
- Core modules:
  - `app` - Main application module with MainActivity and DevHubApplication
  - `core/core-common` - Shared core utilities
  - `feature/feature-feed` - Feed feature module (MVI)
  - `sdk-devhub` - Public SDK module
- Build configuration:
  - `buildSrc` for centralized dependency management
  - Gradle Kotlin DSL configuration
- Project documentation:
  - `.clinerules` - Comprehensive Android training guide with coding standards
  - `devhub-support-files/PROGRESS_TRACKER.md` - Learning progress tracker
  - `devhub-support-files/INTERVIEW_QUESTIONS.md` - Interview preparation materials
- Logging convention: All test logs use `Log.i("TESTE", "message")`
- Jetpack Compose theme setup (Color, Type, Theme)
- Basic test structure (unit and instrumented tests)

### Configuration
- Package name: `com.migpapps.devhub`
- Target SDK: Android 14+
- Kotlin support enabled
- Multi-module architecture ready for feature development

---

## Training Progress

**Current Phase**: Getting Started
**Next Milestone**: Choose learning path (Architecture-first vs Testing-first)

### Completed Topics
- [ ] MVVM vs MVI
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

---

## Notes

This changelog tracks both technical changes to the codebase and learning progress through the Android training programme. Each feature implementation should be documented here with:
- What was built
- Which architectural pattern was used
- Key learnings and concepts applied
- Tests written
- Accessibility considerations

**Legend:**
- `Added` - New features, files, or modules
- `Changed` - Changes to existing functionality
- `Deprecated` - Features that will be removed
- `Removed` - Removed features
- `Fixed` - Bug fixes
- `Security` - Security improvements

[Unreleased]: https://github.com/migpapps/devhub/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/migpapps/devhub/releases/tag/v0.1.0
