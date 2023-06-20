# Crypto app
This is a an app that get crypto data form https://docs.coinapi.io/ website and shows the detail.
this app has two pages. first page is a list of crypto with ability to mark your favorite crypto and search among them.
the second page is just a simple view of showing the detail of crypto.
The last stage of the first screen would be save and you can have you last state of the app even if closed the app.
all the data would be cached in the app in case of error you can use the cached data.
The architecture of the app is MVVM. with Model layer, viewmodel and view.
You can find the Apk of the app in release folder.

# Note
In the code there are two types of of comments.
* Note: this type is some explanation about the code.
* TODO: this type is suggestions about how we can improve the code.

# How to run the app
the project config is Gradle 8 JVM 17.
So make sure that you have both of them on your machine the simply you can run the project from the android studio 
or if you do not access to android studio you can simply go to the root of the project with terminal and run this command
"gradlew assembleDebug"
you going to find the apk in CryptoPrices/app/build/outputs/apk/

# Libraries that used in the project
* Compose: for the Ui of the Application
* Dagger Hilt: for dependency Injection. it is easy to use instead of dagger and other libraries
* Retrofit: used for remote restApi. it is great for rest api and also works good with coroutine.
* Room: For persisting the data. as ORM.
* Paging 3: for pagination.
* OkHttp: for internet connection as client for retrofit.
* Coroutine: used int for thread handle. it is easy to understand and use instead of rxjava or  
  other ways.
* Navigation Component: for designing single activity app and also navigation of application and  
  pass parameters screens.
* architecture components
    * ViewModel
    * Lifecycle
* Turbine: for unit testing flows
* Truth: for unit test assertion
* Junit: and Mockito for unit testing
* Glide: for lazy loading the Images.
* CI: github actions