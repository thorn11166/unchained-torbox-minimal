# Quick Start - Unchained TorBox Minimal

Get the app running in 5 minutes.

## Prerequisites

- Java 11+ installed
- Android SDK 36 installed
- Android device (API 27+) or emulator
- TorBox API key (get from torbox.app)

## 1. Clone & Navigate

```bash
git clone https://github.com/thorn11166/unchained-torbox-minimal.git
cd unchained-torbox-minimal
```

## 2. Build APK

```bash
./gradlew assembleDebug
```

**Output**: `app/build/outputs/apk/debug/app-debug.apk` (~3-4 MB)

## 3. Install on Device

```bash
# Option A: Automatic (requires adb in PATH)
./gradlew installDebug
./gradlew runDebug

# Option B: Manual
adb install app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.thorn11166.uncharinedtorbox/.ui.LoginActivity
```

## 4. Test

1. Open app on device
2. Enter your TorBox API key
3. Tap "Login"
4. View user info and torrents list
5. Tap "Refresh" to reload data
6. Tap "Logout" to sign out

## Troubleshooting

### "SDK location not found"

```bash
echo "sdk.dir=$HOME/Android/Sdk" > local.properties
```

### "Build Tools not found"

Install via Android Studio SDK Manager or:
```bash
$ANDROID_SDK_ROOT/cmdline-tools/bin/sdkmanager "build-tools;36.0.0"
```

### "Unsupported class-file format"

Update Java to version 11+:
```bash
java -version  # Should be 11 or later
```

### "adb not found"

Add to PATH:
```bash
export PATH=$ANDROID_SDK_ROOT/platform-tools:$PATH
```

## Project Structure

```
unchained-torbox-minimal/
├── app/
│   ├── src/main/java/com/thorn11166/uncharinedtorbox/
│   │   ├── data/api/          (TorBoxApi, ApiClient)
│   │   ├── data/model/        (Data models)
│   │   └── ui/                (LoginActivity, MainActivity)
│   └── src/main/res/          (Layouts, resources)
├── build.gradle.kts           (Root config)
├── README.md                  (Full documentation)
├── BUILD_GUIDE.md             (Detailed build steps)
└── TESTING_GUIDE.md           (Test procedures)
```

## Key Files

| File | Purpose |
|------|---------|
| `TorBoxApi.kt` | API interface with Retrofit |
| `ApiClient.kt` | Singleton Retrofit builder |
| `LoginActivity.kt` | Login screen |
| `MainActivity.kt` | Main app screen |
| `TorBoxResponse.kt` | API response wrapper |
| `UserProfile.kt` | User data model |
| `TorrentData.kt` | Torrent data models |

## API Endpoints Used

- `GET /api/user/profile` - User information
- `GET /api/torrents` - Torrent list (limit 50)

All requests authenticated with API key via query parameter.

## Next Steps

- **Full Build Guide**: See [BUILD_GUIDE.md](BUILD_GUIDE.md)
- **Testing Procedures**: See [TESTING_GUIDE.md](TESTING_GUIDE.md)
- **Complete Documentation**: See [README.md](README.md)
- **Architecture Details**: See [../torbox-unchained-fork/TORBOX_ARCHITECTURE.md](../torbox-unchained-fork/TORBOX_ARCHITECTURE.md)

## Common Commands

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing)
./gradlew assembleRelease

# Install and run
./gradlew installDebug runDebug

# Run tests
./gradlew test

# View lint report
./gradlew lint
open app/build/reports/lint-results-debug.html

# Check dependencies
./gradlew dependencies

# Update dependencies
./gradlew dependencyUpdates
```

## Tips

1. **First run**: App will be on login screen
2. **API Key**: Get from [torbox.app](https://torbox.app) account settings
3. **Test Data**: App shows first 10 torrents, up to 50 available
4. **Offline**: Login persists API key, but data refresh requires internet
5. **Security**: API key stored locally in SharedPreferences (not encrypted in v1.0)

## Support

- Check [Troubleshooting](README.md#troubleshooting) in README
- Review [BUILD_GUIDE.md](BUILD_GUIDE.md) for detailed setup
- See [TESTING_GUIDE.md](TESTING_GUIDE.md) for test procedures

---

**Version**: 1.0  
**Ready**: ✅ Yes  
**Estimated Time**: 5-10 minutes to first run
