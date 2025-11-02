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

## [0.1.0] - 2025-01-02

### Added
- Initial project setup with multi-module architecture
- Project structure following Android training programme guidelines
- Core modules:
  - `app/` - Main application module with MainActivity and DevHubApplication
  - `core/core-common/` - Shared core utilities
  - `core/core-network/` - Networking layer (Retrofit, OkHttp)
  - `core/core-database/` - Database layer (Room)
  - `core/core-analytics/` - Analytics abstraction
  - `core/core-ui/` - Shared UI components
  - `feature/feature-feed/` - Feed feature module (MVI architecture)
  - `feature/feature-profile/` - Profile feature module (MVVM architecture)
  - `feature/feature-editor/` - Editor feature module (TDD-focused)
  - `feature/feature-notifications/` - Notifications feature module
  - `feature/feature-search/` - Search feature module
  - `sdk-devhub/` - Public SDK module
- Build configuration:
  - `buildSrc/` for centralized dependency management
  - Gradle Kotlin DSL configuration
  - Version management in `AndroidConfig.kt`
- Comprehensive training programme (11 phases, 33 topics, 20 weeks):
  - Phase 1: Architecture Foundation (MVVM, MVI, ViewModels)
  - Phase 2: Modern UI Development (Compose, Fragments, Navigation)
  - Phase 3: Dependency Injection (Hilt, SDK creation)
  - Phase 4: Reactive Programming (Coroutines, Flow)
  - Phase 5: Quality & Testing (TDD, Accessibility)
  - Phase 6: System Integration & Security Intro (FCM, Intents, Analytics, Security Basics)
  - Phase 7: Build System (Gradle, Delegation)
  - Phase 8: Cross-Platform (KMM)
  - Phase 9: CI/CD & DevOps (GitHub Actions, Automation, Quality)
  - Phase 10: Security Deep Dive (Encryption, SSL Pinning, Auth, Code Protection)
  - Phase 11: Performance & Advanced Topics (Profiling, Offline-First, Animations, Advanced Compose)
- Project documentation:
  - `README.md` - Comprehensive project overview with setup guide, training structure, and feature breakdown
  - `.claude/project-instructions.md` - Detailed training instructions for Claude Code integration (11 phases with code examples)
  - `.clinerules` - Alternative format project instructions (mirrors project-instructions.md)
  - `.claude/local-instructions.md` - Personal, git-ignored development preferences (logging convention)
  - `.claude/settings.json` - Security settings to protect sensitive files from AI access
  - `devhub-support-files/PROGRESS_TRACKER.md` - Detailed progress tracking for all 33 topics with learning goals and checklists
  - `devhub-support-files/INTERVIEW_QUESTIONS.md` - Comprehensive interview preparation questions covering all phases
  - `CLAUDE_CHEATSHEET.md` - Complete reference guide for using Claude Code effectively
  - `.github/CODEOWNERS` - Code ownership requiring @MigP approval for all PRs
  - `CHANGELOG.md` - This changelog following Keep a Changelog format
- Security and privacy configurations:
  - Pre-configured `.gitignore` for sensitive files (.env, local.properties, keystores, google-services.json)
  - Claude Code permissions to deny access to secrets and sensitive configuration
  - Documentation for secure API key management (BuildConfig, local.properties)
  - Security fundamentals in Phase 6.4
  - Complete security deep dive in Phase 10
- Jetpack Compose theme setup (Color, Type, Theme)
- Basic test structure (unit and instrumented tests)

### Configuration
- Application ID: `com.training.devhub`
- Version Code: 1
- Version Name: 0.1.0
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Compile SDK: 34
- Kotlin support enabled
- Multi-module architecture ready for feature development
- Git repository: https://github.com/MigP/devhub

### Security
- Configured `.claude/settings.json` to prevent AI access to:
  - Environment files (`.env`, `.env.*`)
  - Local configuration (`local.properties`)
  - Firebase config (`google-services.json`)
  - Signing keys (`.jks`, `.keystore`)
  - Git internals (`.git/**`)
  - Secrets directory (`secrets/**`)
- Added CODEOWNERS requiring PR approval from @MigP
- Documented secure storage practices (EncryptedSharedPreferences, Android Keystore)
- Documented API key management best practices

### Developer Experience
- Claude Code integration with project-specific instructions loaded automatically
- Personal logging convention: `Log.i("TESTE", "message")` (in local-only file)
- Complete cheatsheet for Claude Code usage (keyboard shortcuts, permissions, custom commands)
- Git-ignored local configuration support (`.claude/local-instructions.md`, `.claude/settings.local.json`)
- Training structure designed for AI-assisted development

---

## Training Progress

**Current Phase**: Phase 1 - Architecture Foundation
**Topics Completed**: 0 / 33
**Next Milestone**: Begin Phase 1 implementation

### Topic Checklist (33 Topics Across 11 Phases)

**Phase 1: Architecture Foundation**
- [ ] 1.1 MVVM vs MVI
- [ ] 1.2 ViewModels & Lifecycle

**Phase 2: Modern UI Development**
- [ ] 2.1 Jetpack Compose
- [ ] 2.2 Fragments
- [ ] 2.3 Navigation

**Phase 3: Dependency Injection & Modularisation**
- [ ] 3.1 Dagger-Hilt
- [ ] 3.2 Creating an SDK

**Phase 4: Reactive Programming & State Management**
- [ ] 4.1 Coroutines
- [ ] 4.2 StateFlow vs Flow vs SharedFlow vs LiveData

**Phase 5: Quality & Testing**
- [ ] 5.1 Test-Driven Development (TDD)
- [ ] 5.2 Android Testing
- [ ] 5.3 Accessibility

**Phase 6: System Integration & Security**
- [ ] 6.1 Intents & Intent Filters
- [ ] 6.2 Push Notifications
- [ ] 6.3 Tracking & Analytics
- [ ] 6.4 Security Fundamentals

**Phase 7: Build System & Advanced Kotlin**
- [ ] 7.1 Gradle
- [ ] 7.2 Kotlin Delegation

**Phase 8: Cross-Platform**
- [ ] 8.1 Kotlin Multiplatform Mobile (KMM)

**Phase 9: CI/CD & DevOps**
- [ ] 9.1 GitHub Actions for Android
- [ ] 9.2 Automated Testing Pipeline
- [ ] 9.3 Code Quality Automation
- [ ] 9.4 Automated Releases
- [ ] 9.5 Dependency Management Automation
- [ ] 9.6 Build Performance Monitoring

**Phase 10: Security Deep Dive**
- [ ] 10.1 Secure Storage & Encryption
- [ ] 10.2 Network Security & SSL Pinning
- [ ] 10.3 Authentication & Authorization
- [ ] 10.4 Code Protection & App Integrity

**Phase 11: Performance & Advanced Topics**
- [ ] 11.1 Performance Profiling & Optimization
- [ ] 11.2 Offline-First Architecture
- [ ] 11.3 Advanced Compose Layouts
- [ ] 11.4 Animations & Shared Elements

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

[Unreleased]: https://github.com/MigP/devhub/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/MigP/devhub/releases/tag/v0.1.0
