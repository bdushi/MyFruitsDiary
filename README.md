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

The application is build on top of the Android Architecture component using MVVM pattern and Repository
pattern, following Android Jetpack suite of libraries, tools, and guidance. The application use Single
Activity patterns, The UI is driven by a model, in our context from ViewModel. Each UI-base classes
is associates with a specific ViewModel, but in some cases we associate a ViewModel with another view,
e.x AddFruitFragment and AddDialogFragment both share AddFruitViewModel. This solution help us to notify
parent for any update form the childe, to achieve this we need to keep the state of the ViewModel
which mean we need to recreate ViewModel that's scoped to our navigation graph, enabling you to share
UI-related data between the graph's destinations. Another key point which I want to mention was
when we need to update the UI when we popping up the previous UI from the FragmentManager back stack.
Solution represented in this project for this topic was using the idea of the Basket, I create a Basket
class in which I have Injected the constructor and to keep the value of the basket throughout all the
application life cycle I have provided it as Singleton into the Dagger Application Module. Another solution
that could be more elegant, offered by Navigation Component library starting from version 2.3.0-alpha02
and higher, NavBackStackEntry gives access to a SavedStateHandle.
For more ref: https://developer.android.com/guide/navigation/navigation-programmatic
To mange the object state and object initialization I have configutate the Dagger. Webservice Endpoints
are consumed using Retrofit and multimedia content such as images is consumed using Picasso.

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

Feature
---------------

- My Fruit Dairy Entries
- Add Entries
- Entries Details
- Delete an Entries
- Add Fruit into specific Date Entries

Improvement
---------------
- Testing
- Retrofit Coroutines batter error handling
- Application State management

References
---------------
https://developer.android.com/guide/navigation/navigation-programmatic
https://developer.android.com/jetpack/docs/guide
https://developer.android.com/jetpack
https://square.github.io/retrofit/
https://dagger.dev

