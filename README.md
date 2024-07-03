# MovieSearch

MovieSearch is a native Android app project that uses [OMDb webservice REST API](https://omdbapi.com/) to 
allow a user search for movies.

## Functionality

* When the app is newly installed and if the user opens it without an internet connectivity, the 
screen just shows a refresh button and an error message and allows the user to refresh the screen 
assuming they have an internet connectivity. A snack bar no internet connectivity error message is 
also shown which disappears after a short time. If the user tries refreshing without an internet 
connectivity, the above same thing happens. If there's an internet connectivity but it's not active, 
a refresh button with a different error message is shown but a snack bar no internet connectivity 
error message is not shown since there's an internet connectivity but just that it's not active.
* The app fetches a list of movies (from the www.omdbapi.com web service) when the user searches for 
movies when there's an active internet connectivity. The list of movies is then displayed on the 
screen. This list of searched movies is not cached to the Room database. A movie gets cached to the 
Room database when the user taps a movie from the list to view more details about it. So what this 
means is that the user can view a list of cached movies (previously viewed movies) and details of 
each offline.
* Assuming the app opens for the first time with an active internet connectivity, the app displays 
a screen without a movie and a search field that lets the user to search for movies.
* Whenever the user subsequently visits the "Movies" screen which is where the aforesaid searched 
movies gets displayed, if without internet connectivity, the cached list of movies is displayed. 
But with internet connectivity (which must be active), a fresh list of movies (based on the user's 
search term) is fetched from the web service and displayed on the screen. If there's an internet 
connectivity but is not active, rather than displaying a cached list of movies, a retry button with 
a different error message is shown but a snack bar no internet connectivity error message is not 
shown.

## Project Tech-stack and Characteristics

* Android Framework
* Kotlin
* Jetpack Compose (Google's first class new UI toolkit that's declarative with better and performant UI components 
than the old View UI toolkit with it's components such as RecyclerView)
* Jetpack Compose Material Design 3 Components
* ViewModel
* Kotlin Coroutine
* StateFlow (better than LiveData)
* MVVM Architectural Design Pattern
* Repository Pattern
* Navigation
* Offline Storage (via Room)
* Retrofit
* [OMDb REST API](https://omdbapi.com/)
* Dependency Injection (Hilt)