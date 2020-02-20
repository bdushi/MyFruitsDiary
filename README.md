My Fruit Dairy
=====================================================

My Fruits Diary is a mobile application where you can store the number of fruits you have eaten
each day. The user is able to add date entries and for each date choose the fruits eaten on that
specific day. The application consumes a webservice where the current entries and fruits are located.

Prerequisites
--------------

- Android SDK v29
- Latest Android Build Tools
- Android Support Repository

Architecture
---------------

The application is build on top of the Android Architecture component using MVVM pattern, following
Android Jetpack suite of libraries, tools, and guidance.

Libraries and Tools
---------------
- AppCompat
- Data Binding allows to bind UI components in your layouts to data sources in your app using a
  declarative format rather than programmatically
- Lifecycles provides classes and interfaces that let you build lifecycle-aware components—which are
  components that can automatically adjust their behavior based on the current lifecycle state of an
  activity or fragment
- LiveData for observing data from ViewModel and Room and update the UI
- Navigation for navigation and interaction
- Room for data persisting
- ViewModel for store and manage UI-related data in a lifecycle conscious way
- Retrofit for consuming WebApi
- Dagger for dependency injection
- Coroutines for asynchronous programming
