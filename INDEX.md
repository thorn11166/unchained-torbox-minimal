# Unchained TorBox Minimal - Complete File Index

## Project Overview

**33 files** in complete Android project:
- **8 Kotlin source files** (520 LOC)
- **13 XML resource files** (300+ LOC)
- **6 Documentation files** (1,200+ LOC)
- **6 Configuration files** (180 LOC)

## Quick Navigation

### 🚀 Getting Started
- **[QUICK_START.md](QUICK_START.md)** - 5-minute setup guide
- **[README.md](README.md)** - Complete documentation
- **[BUILD_GUIDE.md](BUILD_GUIDE.md)** - Detailed build instructions

### 📋 Testing & Verification
- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Complete test procedures (12 test cases)
- **[DELIVERY_CHECKLIST.md](DELIVERY_CHECKLIST.md)** - Verification checklist
- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Architecture overview

### 📁 Source Code Files

#### Application Entry Point
```
app/src/main/java/com/thorn11166/uncharinedtorbox/
└── UnchainedApplication.kt (26 lines)
    - App lifecycle entry point
    - Initialize global state
```

#### Data Layer - API Integration
```
app/src/main/java/com/thorn11166/uncharinedtorbox/data/api/
├── TorBoxApi.kt (113 lines)
│   - Retrofit interface with 8 endpoints
│   - User, Torrents, Downloads, Unrestrict operations
│   - Data classes: AvailableHost, UnrestrictedLink
│
└── ApiClient.kt (43 lines)
    - Retrofit singleton factory
    - Moshi JSON adapter setup
    - OkHttp configuration (10s timeout)
```

#### Data Layer - Models
```
app/src/main/java/com/thorn11166/uncharinedtorbox/data/model/
├── TorBoxResponse.kt (33 lines)
│   - Generic response wrapper <T>
│   - List response wrapper
│   - JSON field mappings
│
├── UserProfile.kt (24 lines)
│   - User account information
│   - Premium status and traffic limits
│
└── TorrentData.kt (47 lines)
    - Torrent entry model
    - File information model
    - Add torrent response model
```

#### UI Layer - Activities
```
app/src/main/java/com/thorn11166/uncharinedtorbox/ui/
├── LoginActivity.kt (123 lines)
│   - API key input screen
│   - Login validation (calls getUserProfile)
│   - Session persistence
│   - Error handling with Toast messages
│
└── MainActivity.kt (175 lines)
    - User profile display
    - Torrent list (first 10 of 50)
    - Refresh and Logout buttons
    - Formatting and error handling
```

### 📐 Resource Files

#### Layouts (2 files)
```
app/src/main/res/layout/
├── activity_login.xml (43 lines)
│   - Linear layout with centered content
│   - EditText for API key input
│   - Login button + progress bar
│   - Material Design 3 theme
│
└── activity_main.xml (70 lines)
    - Top navigation bar (Refresh, Logout)
    - ScrollView with content
    - User info and torrents list (monospace font)
    - Progress indicator
```

#### Values (4 files)
```
app/src/main/res/values/
├── strings.xml (2 lines)
│   - App name
│
├── colors.xml (7 lines)
│   - Material Design 3 color palette
│   - Primary, secondary, error colors
│
├── themes.xml (15 lines)
│   - Material Components theme
│   - Color mappings
│
└── ic_launcher_background.xml (2 lines)
    - Background color reference
```

#### Drawables (2 files)
```
app/src/main/res/drawable/
├── ic_launcher_foreground.xml (23 lines)
│   - SVG path for app icon
│   - User profile avatar icon
│
└── ic_launcher_background.xml (10 lines)
    - Solid color background (purple)
```

#### Mipmaps (2 directories)
```
app/src/main/res/mipmap/
├── mipmap-anydpi-v26/ic_launcher.xml (6 lines)
│   - Adaptive icon for Android 8+
│
└── mipmap-anydpi-v33/ic_launcher.xml (6 lines)
    - Adaptive icon for Android 13+
```

#### XML Configuration (2 files)
```
app/src/main/res/xml/
├── data_extraction_rules.xml (6 lines)
│   - Cleartext traffic policy
│   - Torbox API domain configuration
│
└── backup_rules.xml (4 lines)
    - Include SharedPreferences in backup
```

#### Manifest (1 file)
```
app/src/main/AndroidManifest.xml (32 lines)
├── Internet permission declaration
├── LoginActivity (launcher)
└── MainActivity
```

### ⚙️ Configuration Files

#### Build Configuration (2 files)
```
Root project/
├── build.gradle.kts (18 lines)
│   - Plugin versions
│   - Kotlin compiler settings
│
└── settings.gradle.kts (12 lines)
    - Module inclusion
    - Repository configuration
```

#### App Configuration (2 files)
```
app/
├── build.gradle.kts (60 lines)
│   - App ID: com.thorn11166.uncharinedtorbox
│   - Min SDK: 27, Target: 36, Compile: 36
│   - Dependencies: Retrofit, Moshi, Coroutines, AndroidX
│   - View binding enabled
│   - ProGuard rules configured
│
└── proguard-rules.pro (22 lines)
    - ProGuard configuration
    - Retrofit/Moshi obfuscation rules
```

#### Local Configuration (2 files)
```
Root project/
├── local.properties.example
│   - SDK path template
│
└── .gitignore
    - Gradle, Android Studio, OS exclusions
```

### 📚 Documentation Files

#### Getting Started
- **[QUICK_START.md](QUICK_START.md)** (123 lines)
  - 5-minute setup walkthrough
  - Prerequisites
  - Build and installation
  - Troubleshooting

#### Complete Guide
- **[README.md](README.md)** (290 lines)
  - Features overview
  - Tech stack
  - Project structure
  - Setup and build
  - API documentation
  - Testing procedures
  - Troubleshooting

#### Development Guides
- **[BUILD_GUIDE.md](BUILD_GUIDE.md)** (280 lines)
  - System requirements
  - Java and SDK installation
  - Compilation and building
  - Installation procedures
  - Verification and testing
  - Troubleshooting

- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** (430 lines)
  - Compilation tests
  - Unit and integration test examples
  - 12 manual test cases with steps
  - API endpoint testing
  - Performance testing
  - Security testing
  - Test report template

#### Project Information
- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** (470 lines)
  - Architecture overview
  - File structure and metrics
  - Technology stack
  - API integration details
  - Implementation details
  - Build information
  - Quality metrics

- **[DELIVERY_CHECKLIST.md](DELIVERY_CHECKLIST.md)** (420 lines)
  - Complete verification checklist
  - Project structure confirmation
  - Code quality review
  - Build configuration review
  - API integration verification
  - Testing coverage
  - Security review
  - Deliverables status

## Code Statistics

### By Category

| Category | Files | Lines | Purpose |
|----------|-------|-------|---------|
| **API Layer** | 2 | 156 | Retrofit interface + factory |
| **Data Models** | 3 | 104 | JSON data classes |
| **Activities** | 2 | 298 | Login + Main screens |
| **App Entry** | 1 | 26 | Application class |
| **Layouts** | 2 | 113 | UI definitions |
| **Resources** | 11 | 140 | Strings, colors, drawables |
| **Config** | 4 | 130 | Gradle, manifest |
| **Documentation** | 6 | 1,200+ | Guides and references |
| **Total** | **33** | **2,000+** | Complete project |

### Kotlin Source Summary
```
8 files, 520+ LOC

API Layer:        156 LOC (30%)
UI Layer:         298 LOC (57%)
Data Models:      104 LOC (20%)
App Entry:         26 LOC (5%)
─────────────────────────
Total Kotlin:     584 LOC
```

## File Organization

### By Purpose

**Core Application**
```
uncharinedtorbox/
├── data/
│   ├── api/        API client + interface
│   └── model/      Data models
└── ui/             Activities
```

**Resources**
```
res/
├── layout/         UI layouts
├── values/         Strings, colors, themes
├── drawable/       Icons
├── mipmap/         App icons
└── xml/            Configuration
```

**Configuration**
```
Root & app/
├── build.gradle.kts    Build config
├── settings.gradle.kts Gradle settings
├── AndroidManifest.xml App manifest
└── proguard-rules.pro  Obfuscation
```

**Documentation**
```
Root
├── README.md              Main guide
├── QUICK_START.md         5-min setup
├── BUILD_GUIDE.md         Build instructions
├── TESTING_GUIDE.md       Test procedures
├── PROJECT_SUMMARY.md     Architecture
└── DELIVERY_CHECKLIST.md  Verification
```

## File Dependencies

```
LoginActivity
    └── ApiClient → TorBoxApi → UserProfile
    └── SharedPreferences
    └── MainActivity (intent)

MainActivity
    └── ApiClient → TorBoxApi
        ├── UserProfile
        └── TorrentData
    └── SharedPreferences
    └── LoginActivity (logout)

ApiClient
    └── TorBoxApi (Retrofit interface)
    └── Moshi (JSON serialization)
    └── OkHttp (HTTP client)

Data Models
    └── UserProfile
    └── TorrentData
    └── TorBoxResponse (wrapper)
```

## Dependency Tree

```
App Dependencies
├── Android Framework
│   ├── androidx.core:core-ktx
│   ├── androidx.appcompat:appcompat
│   ├── androidx.activity:activity-ktx
│   ├── androidx.lifecycle:lifecycle-runtime-ktx
│   └── android.material:material (Material Design 3)
│
├── Networking
│   ├── retrofit2:retrofit
│   ├── okhttp3:okhttp
│   └── squareup.moshi:moshi (+ kotlin adapter)
│
├── Concurrency
│   └── kotlinx.coroutines (core + android)
│
└── Kotlin
    └── kotlin:stdlib

Total: 13 production dependencies
```

## Build Artifacts

### Generated Files (Not in Repository)
```
.gradle/          # Gradle cache
build/            # Build output
  ├── outputs/apk/debug/app-debug.apk
  ├── generated/
  └── intermediates/
.idea/            # Android Studio metadata
local.properties  # SDK configuration
*.iml             # Module files
```

### What Gets Built
```
APK Contents:
├── AndroidManifest.xml
├── classes.dex     (Compiled Kotlin)
├── resources.arsc  (Compiled resources)
├── res/            (App resources)
└── lib/            (Native libraries - none in this app)

Size: ~3-4 MB (debug)
```

## How to Use This Index

1. **First Time**: Start with [QUICK_START.md](QUICK_START.md)
2. **Understanding Structure**: Read [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)
3. **Building**: Follow [BUILD_GUIDE.md](BUILD_GUIDE.md)
4. **Testing**: See [TESTING_GUIDE.md](TESTING_GUIDE.md)
5. **Deep Dive**: Check individual files referenced below

## Verification

All 33 files present:
```bash
cd unchained-torbox-minimal
find . -type f ! -path './.git/*' | wc -l
# Expected: 33
```

All Kotlin files compile:
```bash
./gradlew build
# Expected: BUILD SUCCESSFUL
```

---

**File Index Version**: 1.0  
**Last Updated**: 2026-04-01  
**Total Files**: 33  
**Total Lines**: 2,000+  
**Status**: ✅ Complete
