# Android Showcase

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4.svg?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-36-3DDC84.svg?logo=android&logoColor=white)](https://developer.android.com)

**Modern Android app** showcasing best practices & architecture patterns

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
</p>

---

## Screens

| | | | |
|:---:|:---:|:---:|:---:|
| 🔐 **Login** | 📝 **Register** | 🏠 **Home** | 🎨 **Components** |
| 🌐 **Networking** | 💾 **Storage** | 🗄️ **Database** | 📱 **APIs** |
| 📷 **Scanner** | 📅 **Calendar** | 🔔 **Notifications** | ⚙️ **Settings** |

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
