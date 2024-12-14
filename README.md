# Charger Checker Application

This is a new **Android** project, built to monitor and notify the charging state of a device using **Kotlin** or **Java**.

---

## Getting Started

> **Note**: Make sure you have completed the [Android Studio - Environment Setup](https://developer.android.com/studio) instructions before proceeding.

### Step 1: Clone the Repository
Clone this repository to your local machine:

```bash
git clone https://github.com/yourusername/charger-checker.git
cd charger-checker
```

### Step 2: Open the Project in Android Studio
1. Launch **Android Studio**.
2. Select **Open an existing project**.
3. Navigate to the cloned directory and open the project.

### Step 3: Build the Project
Sync and build the project:
1. Open the `build.gradle` file at the project level.
2. Click **Sync Now** if prompted.
3. Make sure the dependencies are resolved correctly.

### Step 4: Run the Application
1. Connect an Android device via USB or set up an emulator.
2. Click the **Run** button in Android Studio.
3. Select the target device to deploy the app.

If everything is set up correctly, you should see the Charger Checker app running on your Android device or emulator.

---

## Features
- **Charging State Detection**: Automatically detects when the charger is connected or disconnected.
- **Real-Time Notifications**: Provides real-time notifications about the charging state.
- **Lightweight and Efficient**: Optimized to run with minimal system resource usage.

---

## Modifying the Application
### Step 1: Locate Main Files
- **Kotlin**: Check the `MainActivity.kt` file in the `src/main/java` directory.
- **Java**: Locate the `MainActivity.java` file in the same directory.

### Step 2: Make Changes
1. Modify the UI or logic in the `Activity` file.
2. Update the `AndroidManifest.xml` file to adjust app permissions or intent filters.

### Step 3: Test Changes
1. Rebuild and redeploy the app using the **Run** button.
2. Test the charging state notifications by connecting and disconnecting a charger.

---

## Troubleshooting
If the app fails to build or run, try the following:
1. Ensure the Android SDK and Gradle are up-to-date.
2. Check that the required permissions are added to the `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.BROADCAST_STICKY" />
   ```
3. Review the logs in the **Logcat** tab for any errors.

For more help, consult the [Android Developer Documentation](https://developer.android.com/docs).

---

## Learn More
To learn more about Android development, check out these resources:

- [Android Developer Documentation](https://developer.android.com/docs) - learn about Android app development.
- [Kotlin for Android Developers](https://developer.android.com/kotlin) - explore Kotlin as a language for Android.
- [Building Android Apps](https://developer.android.com/studio/intro) - an introduction to building Android apps with Android Studio.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contributions
Contributions are welcome! Feel free to fork this project, submit issues, or create pull requests for improvements.

