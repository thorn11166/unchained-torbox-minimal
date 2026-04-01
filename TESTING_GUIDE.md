# Testing Guide - Unchained TorBox Minimal

Complete testing procedures for the Unchained TorBox Android app.

## Test Categories

1. **Compilation Tests** - Build and syntax validation
2. **Unit Tests** - Individual component testing
3. **Integration Tests** - API interaction testing
4. **UI Tests** - User interface and flow testing
5. **Manual Tests** - Real device functionality verification

## Compilation Tests

### Basic Compilation

```bash
# Compile debug build
./gradlew build

# Expected: BUILD SUCCESSFUL
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Lint & Code Quality

```bash
# Run Android lint
./gradlew lint

# View report
open app/build/reports/lint-results-debug.html  # macOS
xdg-open app/build/reports/lint-results-debug.html  # Linux
```

### Kotlin Compiler Checks

```bash
# Strict null-safety
./gradlew build -Pkotlin.strictMode=true

# Check for warnings
./gradlew build --warning-mode all
```

## Unit Tests

### Run All Unit Tests

```bash
./gradlew test
```

### Run Specific Test

```bash
./gradlew test --tests "*LoginActivity*"
```

### Run with Coverage

```bash
./gradlew jacocoTestDebugUnitTestReport
# Report: app/build/reports/jacoco/jacocoTestDebugUnitTestReport/html/index.html
```

### Example Unit Tests to Add

Create `app/src/test/java/com/thorn11166/uncharinedtorbox/ApiClientTest.kt`:

```kotlin
import org.junit.Test
import org.junit.Assert.*
import com.thorn11166.uncharinedtorbox.data.api.ApiClient

class ApiClientTest {
    
    @Test
    fun testApiClientInitialization() {
        val api = ApiClient.getTorBoxApi()
        assertNotNull(api)
    }
    
    @Test
    fun testBaseUrl() {
        // Verify base URL is correctly configured
        val api = ApiClient.getTorBoxApi()
        assertNotNull(api)
    }
}
```

## Integration Tests

### Run Instrumented Tests (on device/emulator)

```bash
# Requires connected device or running emulator
./gradlew connectedAndroidTest
```

### Example Integration Test

Create `app/src/androidTest/java/com/thorn11166/uncharinedtorbox/LoginActivityTest.kt`:

```kotlin
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.thorn11166.uncharinedtorbox.ui.LoginActivity
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {
    
    @Rule
    @JvmField
    val activity = ActivityTestRule(LoginActivity::class.java)
    
    @Test
    fun testLoginActivityDisplaysApiKeyInput() {
        val editText = activity.activity.findViewById<EditText>(R.id.editTextApiKey)
        assertNotNull(editText)
    }
}
```

## Manual Testing

### Pre-Test Setup

1. **Prepare Test Device**
   ```bash
   # Connect device and enable USB debugging
   adb devices
   # Should list: [device-name] device
   ```

2. **Get Test API Key**
   - Create TorBox account (if not existing)
   - Navigate to API settings
   - Generate API key
   - Note: Use test account for safety

3. **Install App**
   ```bash
   ./gradlew installDebug
   adb shell am start -n com.thorn11166.uncharinedtorbox/.ui.LoginActivity
   ```

### Test Case 1: Login Screen Verification

**Objective**: Verify login screen displays correctly

**Steps**:
1. Launch app
2. Observe login screen

**Expected Results**:
- [ ] Title "Unchained TorBox" visible
- [ ] Subtitle "Enter your TorBox API Key" visible
- [ ] Edit text field for API key visible
- [ ] "Login" button visible
- [ ] Info text "Get your API key from torbox.app" visible
- [ ] All text is readable
- [ ] UI follows Material Design 3

**Test Data**: None required

**Pass/Fail**: ___

### Test Case 2: Invalid API Key

**Objective**: Verify error handling for invalid API key

**Steps**:
1. Launch app
2. Enter invalid API key (e.g., "invalid123")
3. Tap "Login" button

**Expected Results**:
- [ ] Progress bar appears
- [ ] Login button becomes disabled
- [ ] Network request is made to API
- [ ] Error toast appears (e.g., "Invalid API key")
- [ ] Progress bar disappears
- [ ] Login button becomes re-enabled
- [ ] User remains on login screen

**Test Data**:
- API Key: `invalid123`

**Pass/Fail**: ___

### Test Case 3: Valid API Key Login

**Objective**: Verify successful login with valid API key

**Steps**:
1. Launch app
2. Enter valid TorBox API key
3. Tap "Login" button
4. Wait for API response

**Expected Results**:
- [ ] Progress bar appears
- [ ] Login button becomes disabled
- [ ] Network request succeeds
- [ ] Success toast appears
- [ ] App navigates to MainActivity
- [ ] Login activity closes

**Test Data**:
- API Key: `[Your valid TorBox API key]`

**Pass/Fail**: ___

### Test Case 4: Main Screen - User Info Display

**Objective**: Verify user profile information displays correctly

**Prerequisites**: Successfully logged in

**Steps**:
1. Observe main screen after login
2. Scroll to user info section

**Expected Results**:
- [ ] Title "👤 User Profile" visible
- [ ] User ID displayed
- [ ] Username displayed
- [ ] Email displayed
- [ ] Premium status displayed
- [ ] Traffic info displayed
- [ ] Refresh button visible
- [ ] Logout button visible
- [ ] ScrollView contains both user info and torrents

**Pass/Fail**: ___

### Test Case 5: Main Screen - Torrent List Display

**Objective**: Verify torrents list displays correctly

**Prerequisites**: Successfully logged in, account has torrents

**Steps**:
1. Observe main screen after login
2. Scroll to torrents list section

**Expected Results**:
- [ ] Title "📋 Torrents List" visible with count
- [ ] For each torrent (up to 10):
  - [ ] Torrent name visible
  - [ ] Status visible (completed, downloading, etc.)
  - [ ] Size displayed
  - [ ] Progress percentage visible
  - [ ] File count shown (if available)
- [ ] "... and X more torrents" if more than 10

**Pass/Fail**: ___

### Test Case 6: Empty Torrents List

**Objective**: Verify proper display when no torrents exist

**Prerequisites**: Successfully logged in, account has no torrents

**Steps**:
1. Observe main screen after login
2. Scroll to torrents section

**Expected Results**:
- [ ] Message "No torrents found" displayed
- [ ] No error shown
- [ ] UI still responsive

**Pass/Fail**: ___

### Test Case 7: Refresh Data

**Objective**: Verify refresh functionality works correctly

**Prerequisites**: Successfully logged in

**Steps**:
1. Wait for initial data load
2. Make change in TorBox web interface (if possible)
3. Tap "Refresh" button
4. Wait for data reload

**Expected Results**:
- [ ] Progress bar appears
- [ ] Refresh button becomes disabled
- [ ] Existing data cleared or grayed out
- [ ] API requests made
- [ ] New data displays
- [ ] Progress bar disappears
- [ ] Refresh button becomes re-enabled

**Pass/Fail**: ___

### Test Case 8: Logout Functionality

**Objective**: Verify logout clears session and returns to login

**Prerequisites**: Successfully logged in

**Steps**:
1. Tap "Logout" button
2. Confirm app behavior

**Expected Results**:
- [ ] App navigates back to LoginActivity
- [ ] Login screen displays
- [ ] API key input field is empty
- [ ] Stored API key is cleared
- [ ] App can be logged in again with different key

**Pass/Fail**: ___

### Test Case 9: Persistence Across App Restart

**Objective**: Verify API key persists when app is closed and reopened

**Prerequisites**: Successfully logged in

**Steps**:
1. Successfully login
2. Close app completely (swipe from recent apps)
3. Reopen app from launcher

**Expected Results**:
- [ ] App skips login screen
- [ ] Main screen displays directly
- [ ] User data loads automatically
- [ ] No re-authentication required

**Pass/Fail**: ___

### Test Case 10: Network Connectivity

**Objective**: Verify app handles network errors gracefully

**Prerequisites**: None

**Steps**:
1. Enable Airplane Mode on device
2. Try to login
3. Observe error handling
4. Disable Airplane Mode
5. Try login again

**Expected Results**:
- [ ] With airplane mode: Error message displays
- [ ] With airplane mode: No crash
- [ ] Without airplane mode: Login succeeds (with valid key)
- [ ] Error messages are user-friendly

**Pass/Fail**: ___

### Test Case 11: UI Responsiveness

**Objective**: Verify UI remains responsive during API calls

**Prerequisites**: None

**Steps**:
1. Start login process
2. While progress bar showing, try scrolling (if possible)
3. Try tapping buttons
4. Wait for response

**Expected Results**:
- [ ] UI doesn't freeze during network requests
- [ ] Buttons disabled during loading
- [ ] Can cancel with back button if desired
- [ ] Progress bar smooth and visible

**Pass/Fail**: ___

### Test Case 12: Text Selection & Copy

**Objective**: Verify data can be selected and copied

**Prerequisites**: Successfully logged in

**Steps**:
1. Long-press on user ID in user info
2. Try to select and copy text
3. Paste elsewhere (notes app, etc.)

**Expected Results**:
- [ ] Text becomes selectable
- [ ] Copy/paste works
- [ ] Formatting preserved

**Pass/Fail**: ___

## Automated API Testing

### Test API Endpoints

```bash
# Test user profile endpoint
curl -X GET "https://api.torbox.app/v1/api/user/profile?api_key=[YOUR_KEY]" \
  -H "Accept: application/json"

# Test torrents list endpoint  
curl -X GET "https://api.torbox.app/v1/api/torrents?api_key=[YOUR_KEY]&offset=0&limit=50" \
  -H "Accept: application/json"
```

### Verify Response Format

Check responses match expected format:

```json
{
  "success": true,
  "data": { /* response data */ },
  "error": null
}
```

## Performance Testing

### Measure API Response Time

```bash
# Time a single API call
time curl -X GET "https://api.torbox.app/v1/api/user/profile?api_key=[YOUR_KEY]"

# Expected: < 2 seconds for typical calls
```

### Memory Usage

```bash
# Monitor memory during app usage
adb shell dumpsys meminfo com.thorn11166.uncharinedtorbox

# Look for:
# - Total memory < 50 MB (reasonable)
# - No memory leaks (stable over time)
```

### Battery Impact

1. Note battery level
2. Run app for 10 minutes
3. Check battery drain
4. Expected: < 2% drain

## Device Compatibility Testing

### Test Devices

Test on devices with:
- [ ] Android 8.1 (API 27) - minimum
- [ ] Android 10 (API 29)
- [ ] Android 12 (API 31)
- [ ] Android 15 (API 36) - target

### Screen Sizes

- [ ] Small phone (4.5")
- [ ] Large phone (6.5"+)
- [ ] Tablet (7"+)

Test landscape mode:
```bash
adb shell settings put system user_rotation 1  # Enable auto-rotate
# Rotate device and observe
```

## Security Testing

### API Key Security

- [ ] API key not logged to console
- [ ] API key not in error messages
- [ ] API key cleared on logout
- [ ] No API key in app cache

Check with:
```bash
adb logcat | grep -i "api_key"
# Should show nothing
```

### Network Security

- [ ] All API calls use HTTPS
- [ ] No cleartext requests
- [ ] Certificate validation enabled

Check:
```bash
adb shell pm grant com.thorn11166.uncharinedtorbox \
  android.permission.INTERNET

# Monitor with Wireshark or Charles Proxy
```

## Test Report Template

```
=====================================
TEST EXECUTION REPORT
Date: [DATE]
Build: [BUILD NUMBER]
Tester: [NAME]
Device: [MODEL - ANDROID VERSION]
API Key: [TEST KEY]
=====================================

Test Case Results:
1. Login Screen Verification: PASS/FAIL
2. Invalid API Key: PASS/FAIL
3. Valid API Key Login: PASS/FAIL
4. Main Screen - User Info: PASS/FAIL
5. Main Screen - Torrent List: PASS/FAIL
6. Empty Torrents List: PASS/FAIL
7. Refresh Data: PASS/FAIL
8. Logout: PASS/FAIL
9. Persistence: PASS/FAIL
10. Network Connectivity: PASS/FAIL
11. UI Responsiveness: PASS/FAIL
12. Text Selection: PASS/FAIL

Overall Result: PASS/FAIL

Issues Found:
- [Issue 1]
- [Issue 2]

Notes:
[Additional observations]

=====================================
```

## Continuous Testing

### GitHub Actions Test Workflow

Create `.github/workflows/test.yml`:

```yaml
name: Test

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
      - run: ./gradlew test
      - uses: actions/upload-artifact@v2
        with:
          name: Test Reports
          path: app/build/reports/
```

---

**Version**: 1.0  
**Last Updated**: 2026-04-01  
**Next**: [BUILD_GUIDE.md](BUILD_GUIDE.md)
