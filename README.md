# Iconfinder

### Deep linking can be tested using this quick link it will take user to iconset screen which is second screen of our app

https://hlioc.test-app.link/icon_sets

### In icon sets fragment on click of share icon a sharable link will generate which we can share on diffrent apps which can hande links eg whatsapp twitter and when user will click on that generated link we will parse id from deeplink and call api with that id and also navigate user directly to icons screen

### Branch loging events using rest api also show in app for eg when we click share icon it will log event by calling branch events api instead of branch android sdk

### Event logging using android sdk also shown in app 






## How to build the app
Get api key from [Iconfinder](https://www.iconfinder.com/) and paste api key in local.properties file 
(API_KEY=your_api_key)

[APK](https://github.com/shubham423/Iconfinder/blob/master/app-debug.apk)

## Built With
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Glide](https://github.com/bumptech/glide) - An image loading library for Android 
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
  
