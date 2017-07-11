'What Clothes' is a sample application that reads weather information from OpenWeatherMap's API; and combines this with a user's temperature preferences in order to advise them on what clothes they should wear on a particular day.

The goals of writing the App were to:
  1. Demonstate my preferrerd library set
  2. Demonstate my preferred architecture
  3. Demonstrate my coding style
  4. Experiment with some new libraries which have been on my radar recently.


*My Preferred Library Set*

I am a strong believer that a modern Android App should be based around the holy trinity of RxJava, Retrofit and Dagger. One important trend of modern Android App development is to keep away from the Android OS until the necessary moment (see Thirty Inch discussion below). Doing so enables more of your codebase to be tested in the JVM (Java Virtual Machine) world. These tests are easier to write and, crucially, faster to run. Since time is a precious commodity it makes sense to push as much of the testing effort into pure-Java territory as possible. RxJava, among its other benefits, allows us to step away from AsyncTask: which as an OS framework component impedes our testing effort. In addition, the use of AsyncTask, in its most simple format, is also prone to memory leaks.

RxJava has a number of other benefits that make its inclusion extrememly valuable. It allows us to combine a number of operations into a pipeline: which makes processing events, processing errors, and cancelling entire pipelines much more managable. Observable pipelines are lazy by default, allowing their invocation to be more easily controlled, and allowing for their reuse. And additionally RxJava has a rich library of operators that allow convenient manipulations of data. All of this in a thread-safe manner (if you use immutable objects and adhere to the Rx principles correctly).

Retrofit is the standard library for REST API consumption, built upon OkHttp which is deemed the most efficient and reliable networking library. Since it is so well known and accepted, I won't dwell on its usage very much, except to highlight using it with the AutoValue-Gson plugin to allow direct mapping from the result of your API call to the family of POJO objects you use to represent the result, with no superfluous code. It leaves Model Object clean and intelligle (e.g. https://github.com/johncara/what-clothes/blob/master/app/src/main/java/com/caracode/whatclothes/api/response/PhotosResponse.java).

Dagger has two main benefits: it allows you to have a net of the standard objects you use in your application to draw from. E.g. if you needed to inject a PictureLoader class from multiple places in your project, where PictureLoader has switchable implementations based on Glide and Picasso: then Dagger would allow you to create this object once and inject it in the multiple places you wanted it. Secondly; static methods are a barrier to testing, so the construction of an object (which is a static method) can make testing difficult or impossible. Moving your construction to Module classes, as Dagger dictates, means that this barrier is removed; and you can have different configurations to replace real objects with mock objects for different testing purposes. The scope of this sample project is so small that I can't demonstrate the second of these purposes very easily; but having Dagger for the 'net of objects' reason is motivation enough to include it in this project.

*My Preferred Architecture*

MVP (Model-View-Presenter) has gained alot of popularity in Android as a useful architecture for the Presentation layer of an application. As with any architectural pattern, there are many subtle variations to MVP. The ethos I like the most is the one that entails a dumb View, meaning the view contains no logic. In some explanations the Model is decribed as being the logic in the Presenter (see Mosby literature); but I prefer to have an actual Model of the View (or ViewModel) owned by the Presenter. This allows for easy assertions of state for testing puposes; means that you have a clearly determined state at all time; and means that when a configuration change happens, you simply need to re-apply the Model to your view. In my search for an MVP library that shared this ethos, I came across Thirty Inch, which I experimented with in this sample. In addition to sharing these values I found it clean and easy to use so I would definitely give it consideration for any upcoming project.

In addition to MVP; it is important to architect the other parts of the app corrrectly too: there should be a service layer, a network layer etc rather than having these layers all mixed into Presenters (or even worse, Activities). These separations should be clear in this sample project.

*My Coding Style*

It's important that code works; but it's vastly more important that it is easy to read and tested. I endeavour to always write clean and understandable code. Additionally, I try to do as much in RxJava as possible, since it allows for easier testing of the codebase and much better thread-safety (when used properly). In MainPresenter.java, despite this containing all the logic of the app, there is not a single while / do-while / for / switch statement. In addition to this being idiomatic and highly testable; it means that all data processing can safely happen off the main thread which means the the app can be extremely responsive. Reading the ViewModel is easy since it's a 1:1 mapping for what you see on the screen. Reading the Activity class is easy since its job is simply to inject Views (using Butterknife), inject objects for the Presenter (using Dagger), and pass button clicks to the Presenter (using RxBinding which allows for convenient UI event composition).

*Experiment with new Libraries*

I experimented with JodaTime enough to be convinced of its merits for any future project. It is an immutable date and time library with much better interfaces that Calendar or Date from java.util with which we are otherwise stuck until Java 8 support back to necessary OS levels (~16) comes along. I also added a deserializer to Retrofit so that I only ever deal with DateTime in my code: no messy conversions getting in the way of the real work in the app. In addition this was my first time to experiment with ThirtyInch which I mentioned previously.

ThirtyInch gets its its name from keeping the Android OS at arms length (thirty inches) for as long as possible: an idea I find extrmemly valuable and one which dictates or influences a number of my library and architecture choices.
