LaporIn - B21-CAP0347 (Android)
=

Project Team Member
-
- Muhammad Rima Mustaghfirin Bil Ashar - A2962667
- Dwi Wahyu Effendi - A2962674
- Muhammad Rifqy Abdallah - C3122780
- Twinanda Hawary Nurfaizi - C3122788
- Faiz Khansa Adrika - M0080838
- Havid Sudarsono - M0080843

Capstone Project Introduction
-
There are many applications developed by the government with public complaint reporting as one of its features (or maybe the only feature). Some of these applications are Jakarta Smart City, Jalan Cantik, and SIKOJA. In these applications, the public can make complaints, reporting things such as damaged roads, damaged bridges, trees covering the highway, and many other cases concerning public facilities’ conditions.
To make a complaint report, the public (a user) is required to upload some photos of evidence, scene information, a short description of what happened; and then chooses the case of the report being made. After that, the report is simply sent to the system for then gets checked by related officers. This process might sound totally fine, but we find a problem there.
The problem we found is that these applications act as “a place for collecting reports”, literally. What we mean by it is that the system has no filtering procedure for incoming reports. All the reports being made are received by the system and need to be validated by officers one by one, including fake reports and the invalids. A user can pretend to make a road damage report while the photos of evidence are totally unrelated to the case being reported, and this fake report will still get received by the system and not considered spam. The absence of a filtering procedure in these applications makes the work of officers become inefficient and more time-consuming. Therefore we propose an idea to implement Machine Learning in the public complaint reporting system.
The first step that can be taken for that implementation is to make a filtering procedure for fake or invalid reports. This implementation will increase the efficiency of officers’ work, making them able to focus on handling only valid reports, therefore making the public services become more effective. There are many more possible steps that can be taken, but given the limited time we have, we will focus on this filtering procedure only.

Steps/Code Explanation
-

This application is able to identify reports in the form of images which will later be processed with machine learning models. And then it will enter the data into the database, which will store all the traffic problem information stored in it to be matched with the image of the report to be identified by machine learning. Currently, there are three categories of traffic problems :
- Traffic Jam,
- Flood,
- Road with Potholes.

And the app will tell us the report image is valid or not. If the report detected by the system is correct, then the report will be forwarded to all application users. But if the report is invalid, then the report will be included in the spam category, and not shown to general users.

The app works by fetching images from the user's gallery, and then uploading those images to a machine learning model applied to a Virtual Machine Instance with Cloud Computing, where the app retrieves the classification results.

Helper dependencies used in this app are:
- Glide: to display images,
- Picasso: to image loading and processing,
- Retrofit OkHttp3: framework for authenticating and interacting with APIs and sending network requests with OkHttp,
- Koin: for dependency injection,
- Firebase: 
  - Authentication: to authenticate users to our app
  - Firestore
  - Storage

There are two main modules in this android project: the App module and the Core module. The app module will contain most of the UI processes and classes needed for the UI to work, while Core will contain most of the background work.

Starting with the Core module, going to folder src/main/java/com/akiramenaide/core, there will be four main package:
- data,
- di,
- domain,
- util.

The app implements Clear Architecture with three main layers. The presentation layer which works on UI is on the app module, data layer which works on storing and fetching data is on package data inside core module, and domain layer which works as a bridge between the two other layers is stored in domain package inside core module.

Package data will contain most processes concerning data flow. The FruitRepository will be stored here, containing functions to get, insert, update fruit data, and get prediction results from an image. There will be another package named source, containing package local and package remote. Each package will have a DataSource. LocalDataSource will work with Room Database, containing Dao Function to insert, update and query fruit data. RemoteDataSource will work with Retrofit to send image data to an address with POST function, then fetch the result. The processes will be done on background thread using runBlocking function to prevent the application from freezing.

Package di contains only a single class named CoreModule. In this class dependency injection (di) will be done with Koin library to implement di on the repository, database and network classes. 

Package domain works as a bridge between data package and UI on app modules. Here the module of fruit data which was an entity on the data package will be converted to a simple Fruit model. There is an interface of the FruitRepository, and also UseCase class which will be used by UI classes, and Interactor class which is linked with UseCase class. 

Last is util package, containing helper classes such as AppExecutor to help run tasks on background and DataMapper to convert the data model.

Going to AppModule, before accessing the app package there will be MyApplication class which will ruin the Koin Module which is used on the application.

Inside UI package, there will be multiple packages:
- auth,
- detail,
- di,
- home,
- main,
- profile,
- splash,
- util.

When the app is ran, SplashActivity inside the splash package will start, showing Lottie animation to greet users. And then the app will go to the login page.

Package auth will contain all of the login and register function on the app. Firebase is used to handle login information, and is utilized to help create other functions such as registering to the app, creating forget password and change password functions. When you logged in onto the app, you won’t have to login anymore next time you access the app. This is done by accessing currentUser on the FirebaseAuth instance.

On the main app, there will only be a single activity, MainActivity. This activity uses BottomNavigationView to navigate between the three main fragments in the app, which is HomeFragment, DetailFragment and ProfileFragment. HomeFragment will display a BarChart showing the total of each fruit data in the database. The BarChart, and all the other charts in the app used a library called MPAndroidChart. The HomeFragment have three button:
Pick Image button,
Predict Image button,
Insert Data button.


The first button will fetch images from the gallery. The predict image button will convert the image as a UTF-8 string, which then will be sent to the HomeViewModel, which is linked with UseCase and will eventually go to the POST function. It will return data containing the class name of the fruit (example = “rottenapple”), and the percentage of its similarity. The insert data button will insert the predicted data into the database.

DetailFragment will get all the fruits data on the database and will first display it on a PieChart, showing the percentage of each fruit’s total in the database. And then there is also a horizontal bar chart dividing the fresh and rotten fruit total of each fruit.

Last is the ProfileFragment. This will work as a personalization page, where users can set their profile picture, name and change email address/password. The personal data and it’s changes will work with firebase. Users can also delete their account in this page, with a warning page using AlertDialog before confirmation. To logout, the MainActivity will have a Logout menu which can log out the user in a single click.

The di package is another dependency injection package. The injection will be used for UseCase and ViewModels, creating modules for one of each. 

Last is the util package. It contains helper classes such as MyColor class to store commonly used color and PredictedObject as a container class for the data fetched from Cloud.

