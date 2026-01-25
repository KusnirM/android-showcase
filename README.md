# Android Showcase

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4.svg?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-36-3DDC84.svg?logo=android&logoColor=white)](https://developer.android.com)

A production-ready Android demo app showcasing modern mobile development with Jetpack Compose, clean architecture, and native platform integrations.

---

<table>
<tr>
<td style="width:50%">

### 🎨 UI & Navigation
- Jetpack Compose
- Material 3 + Dark Mode
- Navigation3
- 40+ Components

</td>
<td style="width:50%">

### 📱 Platform APIs
- Biometrics (Fingerprint)
- Camera & Gallery
- QR/Barcode Scanner
- Location & Permissions

</td>
</tr>
<tr>
<td style="width:50%">

### 🔌 Data & Network
- Retrofit HTTP Client
- Room Database
- DataStore Preferences
- Coil Image Loading

</td>
<td style="width:50%">

### 🔔 Notifications
- Push (FCM)
- Local Notifications
- Notification Channels
- Permission Handling

</td>
</tr>
</table>

---

## Tech Stack

<p>
<img src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white" alt="Kotlin" />
<img src="https://img.shields.io/badge/Compose-4285F4?logo=jetpackcompose&logoColor=white" alt="Compose" />
<img src="https://img.shields.io/badge/Hilt-34A853?logoColor=white" alt="Hilt" />
<img src="https://img.shields.io/badge/Retrofit-48B983?logoColor=white" alt="Retrofit" />
<img src="https://img.shields.io/badge/Room-005C99?logoColor=white" alt="Room" />
<img src="https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=black" alt="Firebase" />
<img src="https://img.shields.io/badge/Detekt-6F42C1?logoColor=white" alt="Detekt" />
<img src="https://img.shields.io/badge/Paparazzi-FF6B6B?logoColor=white" alt="Screenshot Tests" />
</p>

---

## Screenshots

<table>
<tr>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.login_LoginScreenScreenshotTest_loginScreen%5B0%5D_light.png" width="180" alt="Login"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.home_HomeScreenScreenshotTest_homeScreen%5B0%5D_light.png" width="180" alt="Home"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.database_DatabaseScreenScreenshotTest_databaseScreen%5B4%5D_light.png" width="180" alt="Database"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.calendar_CalendarScreenScreenshotTest_calendarScreen%5B2%5D_light.png" width="180" alt="Calendar"/></td>
</tr>
<tr>
<td style="text-align:center">Login</td>
<td style="text-align:center">Home</td>
<td style="text-align:center">Database</td>
<td style="text-align:center">Calendar</td>
</tr>
<tr>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.networking_NetworkingScreenScreenshotTest_networkingScreen%5B2%5D_light.png" width="180" alt="Networking"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.storage_StorageScreenScreenshotTest_storageScreen%5B0%5D_light.png" width="180" alt="Storage"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.notifications_NotificationsScreenScreenshotTest_notificationsScreen%5B0%5D_light.png" width="180" alt="Notifications"/></td>
<td><img src="app/src/test/snapshots/images/mk.digital.androidshowcase.presentation.screen.settings_SettingsScreenScreenshotTest_settingsScreen%5B0%5D_light.png" width="180" alt="Settings"/></td>
</tr>
<tr>
<td style="text-align:center">Networking</td>
<td style="text-align:center">Storage</td>
<td style="text-align:center">Notifications</td>
<td style="text-align:center">Settings</td>
</tr>
</table>

---

## Architecture

```
Presentation  →  Domain  →  Data
  (UI/VM)       (UseCase)   (Repository)
```

---

## Quick Start

```bash
./gradlew :app:installDebug
```

---

## Project Structure

```
app/
├── data/           # Data layer (repositories, data sources)
├── di/             # Hilt modules
├── domain/         # Domain layer (use cases, models)
├── presentation/   # UI layer (screens, viewmodels, components)
└── util/           # Utilities
```

---

## Roadmap

- [ ] Pagination
- [ ] Deep links
- [ ] Maps
- [ ] Video player
- [ ] Offline-first
