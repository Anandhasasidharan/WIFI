# Wi-Fi Monitor

A comprehensive Android application for monitoring and managing Wi-Fi networks, providing real-time insights into network performance, security, and usage.

## Features

- Real-time Wi-Fi network monitoring
- Network security analysis
- Usage statistics and logging
- Firebase integration for cloud features
- Modern Material Design 3 UI with Jetpack Compose
- Offline data storage with Room database
- Background monitoring with WorkManager

## Technologies Used

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room
- **Backend**: Firebase (Auth, Firestore, Cloud Messaging)
- **Background Tasks**: WorkManager
- **Build System**: Gradle with Kotlin DSL

## Requirements

- Android SDK 24 (Android 7.0) or higher
- Target SDK 34 (Android 14)
- Kotlin 1.9.22

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Anandhasasidharan/WIFI.git
   cd WIFI
   ```

2. Open the project in Android Studio.

3. Build and run on an Android device or emulator.

## Usage

1. Launch the app on your Android device.
2. Grant necessary permissions for Wi-Fi access and network monitoring.
3. The app will display real-time Wi-Fi network information and monitoring data.

## Permissions

The app requires the following permissions:

- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`
- `android.permission.ACCESS_WIFI_STATE`
- `android.permission.PACKAGE_USAGE_STATS`
- `android.permission.READ_PHONE_STATE`
- `android.permission.FOREGROUND_SERVICE`
- `android.permission.POST_NOTIFICATIONS`
- `android.permission.USE_BIOMETRIC`
- `android.permission.USE_FINGERPRINT`
- `android.permission.BIND_VPN_SERVICE`

## Project Structure

```
app/
├── src/main/java/com/example/wifimonitor/
│   ├── data/          # Data layer (repositories, models)
│   ├── di/            # Dependency injection (removed Hilt)
│   ├── service/       # Background services
│   ├── ui/            # UI layer (activities, composables)
│   │   ├── theme/     # App theming
│   │   └── screens/   # Screen composables
│   └── WifiMonitorApplication.kt
├── src/main/res/      # Resources (layouts, drawables, etc.)
└── build.gradle.kts   # Module build configuration
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Anandhasasidharan - [GitHub Profile](https://github.com/Anandhasasidharan)

Project Link: [https://github.com/Anandhasasidharan/WIFI](https://github.com/Anandhasasidharan/WIFI)
