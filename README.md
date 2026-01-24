# Android Showcase

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4.svg?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-35-3DDC84.svg?logo=android&logoColor=white)](/)

**Modern Android app** showcasing best practices & architecture patterns

---

<table>
<tr>
<td width="50%">

### 🎨 UI & Navigation
- Jetpack Compose
- Material 3 + Dark Mode
- Navigation3
- 40+ Components

</td>
<td width="50%">

### 📱 Platform APIs
- Biometrics (Fingerprint)
- Camera & Gallery
- QR/Barcode Scanner
- Location & Permissions

</td>
</tr>
<tr>
<td width="50%">

### 🔌 Data & Network
- Ktor HTTP Client
- Room Database
- DataStore Preferences
- Coil Image Loading

</td>
<td width="50%">

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
<img src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white" />
<img src="https://img.shields.io/badge/Compose-4285F4?logo=jetpackcompose&logoColor=white" />
<img src="https://img.shields.io/badge/Hilt-34A853?logoColor=white" />
<img src="https://img.shields.io/badge/Ktor-7F52FF?logoColor=white" />
<img src="https://img.shields.io/badge/Room-005C99?logoColor=white" />
<img src="https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=black" />
<img src="https://img.shields.io/badge/Detekt-6F42C1?logoColor=white" />
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
