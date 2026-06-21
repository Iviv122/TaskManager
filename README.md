# Task manager, date based todo app
Made thanks to ![Macondo Hack Club](https://macondo.hackclub.com/)! 

> Initally i thought this project has nice feature which i hadn't met before but in practice it was already done in some other projects TwT

Made with Android Studio, including
- Kotlin (as code base language)
- Room (wrapper for sqlite)
- DataStore (user setting in separate file)
- Jetpack (React like UI)

> All libs were targeted to compile with MinSdk 21 and Java 11

Devices tested
- Google Pixel 10A with GrapheneOS (Android 16)
- Xiamo Redmi 9 (Android 12)
- Redmi Note 12 (Android 14) 

Features
- Notifications every so often when you need to do your tasks *
- Responsive UI

> *- <img width="1280" height="720" alt="New Project(1)" src="https://github.com/user-attachments/assets/50ffb9e7-ec52-4a9d-a811-b15833c61250" />
  Notification won't appear imidiatly as task date approached. It will be sent according to notification schedule


Permissions used
- POST_NOTIFICATIONS - Remind about your task *
- FOREGROUND_SERVICE and FOREGROUND_SERVICE_SPECIAL_USE - Used to run service, it runs every X minutes **

> *- It is possible to use app without notifications, no errors encoutered
> *-- On tested devices no permissions were asked, with and without google services
