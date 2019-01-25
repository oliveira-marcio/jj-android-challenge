# Johnson & Johnson Android Coding Challenge

By Márcio Souza de Oliveira

LinkedIn: https://www.linkedin.com/in/marcio-oliveira/

## Architecture

App follows MVVM design pattern suggested by Google with their [Android Architecture Components (AAC)](https://developer.android.com/jetpack/docs/guide#common-principles).

So we have:

1) **Views:**

- **MainActivity** - Main screen that displays the device list where each item can be swiped left or right to be deleted and there's a FAB to add more devices. Also the main layout can be swiped down to force a manual sync with server.
- **DetailsActivity** - Screen to display the selected item details. It also has an option to check in/out the device.
- **AddDeviceActivity** - Screen to add a new device

2) **ViewModels:**

Each `Activity` has its own `ViewModel` to get or create/update data.

3) **Models:**

There's a **Repository** responsible to provide data from both local database and API server.

## Technical decisions

1) The MVVM design pattern was an excellent idea to separate the main concerns. So I've could initially focus on business logic and complete the UI with fake data provided by ViewModels. After I've checked everything was OK, I could gradually introduce database and real API data.

2) I've opted for implement a dependency injection mechanism with a helper class (**Injector.java**) and using singleton pattern for some components to avoid a lot of "spaghetti code".

3) The AAC have a great set of components like `ViewModels` and `LiveData` that implement observers to listen some data sources and react to changes. And it's also possible to cache them to avoid unnecessary re-queries.

4) All views only access local database that's regularly synced with API server.

5) The local database was implemented with Room, also from AAC, because it's pretty straightforward

6) RetroFit library was used to fetch API data, since it's widely used for this pourpose. **OBS:** It was required to implement a custom `Convert.Factory` since one of the API endpoints is returning an OK response code (200) but with an empty response body that ends up not being correctly interpreted by Retrofit standard callbacks.

7) Finally, because of this challenge nature and restricted time, I've decided for a basic data sync strategy:

- Everytime data is fetched from API, the local database is just completely rewrited with fetched data
- Everytime database is changed (device added, deleted or checked in/out) a corresponding call to the proper API endpoint is made, but there's no validation with API response. Only connections errors are handled where database keeps temporarily unsynced with server, but user gets a `Toast` message and he can always do a manual refresh on main screen

## To Do's

It's still necessary to keep track of database changes that weren't synced with server and try again in the next sync.

A good approach could be setting some `SharedPreferences` to store unsynced device ID's.