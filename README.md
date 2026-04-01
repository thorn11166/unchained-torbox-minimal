# Unchained TorBox - Minimal Android App

A clean, simple Android app for TorBox API integration. Built with Kotlin, Retrofit, and Moshi.

## Features

- 🔐 **API Key Authentication**: Simple login with TorBox API key
- 👤 **User Profile**: Display account information (username, email, traffic usage)
- 📋 **Torrent List**: View your torrents with status and progress
- 🔄 **Refresh Data**: Manually refresh user and torrent information
- 🚪 **Logout**: Securely clear stored API key

## Tech Stack

- **Language**: Kotlin 1.9.24
- **Min SDK**: 27 (Android 8.1+)
- **Target SDK**: 36 (Android 15)
- **Networking**:
  - Retrofit 2.11.0 for REST API calls
  - OkHttp 4.12.0 for HTTP client
  - Moshi 1.15.2 for JSON serialization
- **UI**: Material Design 3, AndroidX components
- **Concurrency**: Kotlin Coroutines

## Project Structure

```
unchained-torbox-minimal/
├── app/
│   ├── src/main/
│   │   ├── java/com/thorn11166/uncharinedtorbox/
│   │   │   ├── data/
│   │   │   │   ├── api/
│   │   │   │   │   ├── ApiClient.kt       # Retrofit singleton
│   │   │   │   │   └── TorBoxApi.kt       # API interface
│   │   │   │   └── model/
│   │   │   │       ├── TorBoxResponse.kt  # API wrapper
│   │   │   │       ├── UserProfile.kt     # User data model
│   │   │   │       └── TorrentData.kt     # Torrent data model
│   │   │   ├── ui/
│   │   │   │   ├── LoginActivity.kt       # Login screen
│   │   │   │   └── MainActivity.kt        # Main app screen
│   │   │   └── UnchainedApplication.kt    # App entry point
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_login.xml
│   │   │   │   └── activity_main.xml
│   │   │   ├── values/
│   │   │   ├── drawable/
│   │   │   ├── mipmap/
│   │   │   └── xml/
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Setup & Build

### Prerequisites

- Android Studio Ladybug (2024.1.2) or later
- Android SDK 36 (installed via SDK Manager)
- Gradle 8.4.0

### Clone Repository

```bash
git clone https://github.com/thorn11166/unchained-torbox-minimal.git
cd unchained-torbox-minimal
```

### Build APK

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires signing config)
./gradlew assembleRelease
```

The debug APK will be output to: `app/build/outputs/apk/debug/app-debug.apk`

### Run on Device/Emulator

```bash
# Install and run
./gradlew installDebug
adb shell am start -n com.thorn11166.uncharinedtorbox/.ui.LoginActivity

# Or use Android Studio's Run button
```

## API Setup

### Get Your API Key

1. Go to [torbox.app](https://torbox.app)
2. Log in to your account
3. Navigate to API settings
4. Copy your API key

### Test Endpoints

The app uses these TorBox API endpoints:

- `GET /api/user/profile` - Fetch user account information
- `GET /api/torrents` - List user's torrents
- `GET /api/torrent/{id}` - Get specific torrent details
- `POST /api/torrents/addMagnet` - Add magnet link
- `DELETE /api/torrents/{id}` - Delete torrent

Base URL: `https://api.torbox.app/v1/`

All requests include `api_key` as query parameter.

## Usage

### Login Flow

1. Open app
2. Enter your TorBox API key
3. Tap "Login"
4. API key is validated and stored securely in SharedPreferences

### Main Screen

Once logged in, you'll see:

- **User Profile Section**: Account details (ID, username, email, premium status, traffic info)
- **Torrents List**: First 10 torrents from your account
  - Shows torrent name, ID, status, size, progress
  - File count if available

### Actions

- **Refresh**: Reload user data and torrent list
- **Logout**: Clear stored API key and return to login

## Data Models

### TorBoxResponse<T>
Generic wrapper for all API responses:
```kotlin
{
  "success": true,
  "data": { /* response body */ },
  "error": null
}
```

### UserProfile
```kotlin
{
  "id": "123",
  "username": "user",
  "email": "user@example.com",
  "premium": true,
  "remainingTraffic": 5368709120,
  "totalTraffic": 10737418240
}
```

### TorrentData
```kotlin
{
  "id": "abc123",
  "name": "Movie.Name.2024.1080p",
  "status": "completed",
  "size": 4700000000,
  "progress": 100,
  "files": [ { "id": "f1", "name": "file.mkv", "size": 4700000000 } ]
}
```

## Testing

### Unit Tests
Run unit tests:
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist

- [ ] App launches without crashes
- [ ] Login screen displays API key input field
- [ ] Invalid API key shows error message
- [ ] Valid API key logs in successfully
- [ ] User profile data loads correctly
- [ ] Torrent list displays (or "no torrents" if empty)
- [ ] Refresh button updates data
- [ ] Logout clears session and returns to login
- [ ] App persists login across app restarts
- [ ] Network errors display gracefully

## Debugging

### Enable Network Logging

Add to `ApiClient.kt`:
```kotlin
private val httpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
}
```

Add dependency:
```kotlin
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
```

### View Logs

```bash
adb logcat | grep uncharinedtorbox
```

## Troubleshooting

### Build Errors

**Error: `Unsupported class-file format`**
- Solution: Update Java to 11+ or match Gradle plugin version

**Error: `SDK 36 not found`**
- Solution: Open SDK Manager, install Android SDK Platform 36

**Error: `Manifest merger failed`**
- Solution: Check `AndroidManifest.xml` for duplicate activities

### Runtime Errors

**API Key Rejected**
- Verify API key is correct (no spaces)
- Check TorBox account is active/premium
- Ensure internet connection is working

**NullPointerException in MainActivity**
- Check API response format matches data models
- Add null-safety checks in response handling

**Network Timeout**
- Check internet connectivity
- Increase timeout in `ApiClient.kt`
- Verify TorBox API is accessible

## Performance Considerations

- API calls run on coroutines (non-blocking)
- Torrents list limited to 50 items per request
- No automatic refresh (manual refresh only)
- API key stored in plain SharedPreferences (not encrypted in this minimal version)

## Security Notes

⚠️ **Minimal Security Implementation**

Current version stores API key in plain SharedPreferences. For production:

1. Use EncryptedSharedPreferences from androidx.security
2. Implement certificate pinning with OkHttp
3. Add API key validation before requests
4. Consider using OAuth if TorBox API supports it

## Future Enhancements

- [ ] Encrypted API key storage
- [ ] Pagination for torrent lists
- [ ] Search/filter torrents
- [ ] Add magnet links or torrent files
- [ ] Delete torrents from app
- [ ] Download progress tracking
- [ ] Error handling with retry logic
- [ ] Dark/light theme toggle
- [ ] Offline mode with cached data

## References

- [TorBox API Architecture Guide](./torbox-unchained-fork/TORBOX_ARCHITECTURE.md)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Moshi Documentation](https://github.com/square/moshi)
- [Android Developers Guide](https://developer.android.com/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## License

MIT License - See LICENSE file for details

## Contributing

Pull requests welcome! For major changes, please open an issue first.

## Support

For issues or questions:
- Check Troubleshooting section above
- Review TorBox API documentation
- Open an issue on GitHub

---

**Version**: 1.0.0  
**Last Updated**: 2026-04-01  
**Status**: Ready for testing
