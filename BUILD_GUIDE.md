# Build Guide - Unchained TorBox Minimal

This guide walks through building and testing the Unchained TorBox Android app.

## System Requirements

- **OS**: Linux, macOS, or Windows
- **Java**: JDK 11 or later (LTS recommended)
- **Android SDK**: API 36 (Android 15)
- **Gradle**: 8.4.0+ (included via wrapper)
- **Git**: For cloning and version control

## Installation Steps

### 1. Install Java Development Kit (JDK)

**macOS** (using Homebrew):
```bash
brew install openjdk@11
export JAVA_HOME=/opt/homebrew/opt/openjdk@11
```

**Ubuntu/Debian**:
```bash
sudo apt-get install openjdk-11-jdk-headless
```

**Windows** (using Chocolatey):
```powershell
choco install openjdk11
```

Verify installation:
```bash
java -version
# Should show java version 11 or later
```

### 2. Install Android SDK

#### Option A: Using Android Studio

1. Download [Android Studio](https://developer.android.com/studio)
2. Run installer and complete setup
3. Open SDK Manager (Tools → SDK Manager)
4. Install:
   - Android SDK Platform 36
   - Android SDK Build-Tools 36.0.0
   - Android Emulator (optional)
   - Android SDK Platform-Tools

#### Option B: Command-line SDK Tools

Download from: https://developer.android.com/studio#downloads

```bash
# Extract to desired location
unzip cmdline-tools-linux-*.zip -d ~/android-sdk
cd ~/android-sdk/cmdline-tools/bin

# Accept licenses
./sdkmanager --licenses

# Install required packages
./sdkmanager "platforms;android-36"
./sdkmanager "build-tools;36.0.0"
./sdkmanager "platform-tools"

# Set ANDROID_SDK_ROOT environment variable
export ANDROID_SDK_ROOT=$HOME/android-sdk
export PATH=$ANDROID_SDK_ROOT/platform-tools:$PATH
```

### 3. Clone Repository

```bash
git clone https://github.com/thorn11166/unchained-torbox-minimal.git
cd unchained-torbox-minimal
```

### 4. Configure SDK Path

Create `local.properties`:

```bash
# Linux/macOS
echo "sdk.dir=$HOME/Android/Sdk" > local.properties

# Or if using custom location
echo "sdk.dir=/path/to/Android/Sdk" > local.properties
```

The Gradle wrapper will automatically find your Android SDK.

## Building

### Build Debug APK

```bash
./gradlew assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk`

Size: ~3-4 MB

### Build Release APK

```bash
./gradlew assembleRelease
```

**Note**: Release builds require a signing configuration. See [Signing](#signing-apk) section.

### Build Without Installation

```bash
# Just compile, don't install
./gradlew build
```

This will:
- Validate code
- Run linters
- Compile Kotlin
- Create APK
- Generate APK info

### Clean Build

```bash
# Remove all build artifacts
./gradlew clean

# Full rebuild
./gradlew clean assembleDebug
```

## Installation & Testing

### Install on Connected Device

Prerequisites:
- Android device running Android 8.1+ (API 27+)
- USB debugging enabled on device
- USB cable connected

```bash
# Install debug APK
./gradlew installDebug

# Or manual install
adb install app/build/outputs/apk/debug/app-debug.apk
```

Verify installation:
```bash
adb shell pm list packages | grep uncharinedtorbox
# Should show: package:com.thorn11166.uncharinedtorbox
```

### Run on Device

```bash
# Using gradle
./gradlew runDebug

# Or manual
adb shell am start -n com.thorn11166.uncharinedtorbox/.ui.LoginActivity
```

### Run on Android Emulator

First, create/start an emulator:

```bash
# List available emulators
./android-sdk/cmdline-tools/bin/emulator -list-avds

# Start emulator
./android-sdk/cmdline-tools/bin/emulator -avd <name>

# Or use Android Studio:
# - Tools → Device Manager → Create Virtual Device
# - Select Pixel 6 or similar
# - Target: Android 15 (API 36)
```

Then install and run:

```bash
./gradlew installDebug
./gradlew runDebug
```

## Verifying Build

### Check APK Contents

```bash
# List files in APK
unzip -l app/build/outputs/apk/debug/app-debug.apk

# Verify app classes
unzip -l app/build/outputs/apk/debug/app-debug.apk | grep "\.class"
```

### View APK Size Breakdown

```bash
./gradlew analyzeApkSize
```

### Check Compilation Warnings

```bash
./gradlew build --warning-mode all
```

## Troubleshooting

### Build Issues

**Error: Unsupported class-file format**
```
Error: com.sun.tools.classfile.ClassFileFormatException: 
    unsupported class-file format: 65.0
```
**Solution**: Update to Java 11+
```bash
export JAVA_HOME=/path/to/jdk-11-or-later
java -version  # Verify
./gradlew clean assemble
```

**Error: SDK location not found**
```
Error: SDK location not found. Define location with 
       an ANDROID_SDK_ROOT environment variable
```
**Solution**: Set SDK path
```bash
export ANDROID_SDK_ROOT=$HOME/Android/Sdk
# Or create local.properties
echo "sdk.dir=$HOME/Android/Sdk" > local.properties
```

**Error: Failed to find Build Tools**
```
Error: Failed to find Build Tools version 36.0.0
```
**Solution**: Install build tools
```bash
$ANDROID_SDK_ROOT/cmdline-tools/bin/sdkmanager "build-tools;36.0.0"
```

### Gradle Issues

**Gradle daemon timeout**
```bash
# Increase timeout
./gradlew --no-daemon assemble
```

**Out of memory**
```bash
# Set heap size
export GRADLE_OPTS="-Xmx2048m -XX:MaxPermSize=512m"
./gradlew clean build
```

### Runtime Issues

**App crashes on startup**
```bash
# Check logs
adb logcat | grep "uncharinedtorbox"

# Verbose logging
adb logcat -v threadtime | grep "com.thorn11166"
```

**Cannot connect to API**
```bash
# Check device internet
adb shell ping api.torbox.app

# Check if API is accessible
adb shell "curl -I https://api.torbox.app/v1/api/user/profile"
```

## Advanced Options

### Custom Gradle Properties

Create `gradle.properties`:

```properties
# Increase memory allocation
org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m

# Enable parallel builds
org.gradle.parallel=true

# Configure offline mode
org.gradle.offline=false

# Set Java toolchain
org.gradle.java.home=/path/to/jdk-11
```

### Build with Specific Java Version

```bash
# Use different Java version for build
./gradlew build \
    -Dorg.gradle.java.home=/path/to/jdk-11 \
    -Dorg.gradle.jvmargs="-Xmx2048m"
```

### Generate Build Report

```bash
./gradlew assembleDebug --profile
# Report: app/build/reports/profile/
```

## CI/CD Integration

### GitHub Actions Example

Create `.github/workflows/build.yml`:

```yaml
name: Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 11
          distribution: 'temurin'
      - run: ./gradlew build
      - uses: actions/upload-artifact@v2
        with:
          name: APK
          path: app/build/outputs/apk/debug/
```

## Performance Optimization

### Enable Build Optimization

```bash
# Enable gradle build cache
./gradlew --build-cache assemble

# Profile build
./gradlew --profile assembleDebug
# Report: build/reports/profile/
```

### Reduce Build Time

1. **Enable incremental compilation**
   ```properties
   kotlin.incremental=true
   ```

2. **Use gradle daemon**
   ```bash
   ./gradlew assemble  # Daemon enabled by default
   ```

3. **Parallel builds**
   ```properties
   org.gradle.parallel=true
   org.gradle.workers.max=8
   ```

## Next Steps

- [TESTING_GUIDE.md](TESTING_GUIDE.md) - Complete testing procedures
- [README.md](README.md) - App usage and API documentation
- [TORBOX_ARCHITECTURE.md](../torbox-unchained-fork/TORBOX_ARCHITECTURE.md) - API architecture reference

---

**Version**: 1.0  
**Last Updated**: 2026-04-01
