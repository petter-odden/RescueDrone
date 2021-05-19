# RescueDrone
The finished prototype can be found in the branch named "DJI-module" in this repository. The project contains two modules, the RescueDrone module called "app", and the modified "DJI Sample Application" module called "android-uxsdk-beta-sample".
# Installation
To install the applications on your own device, either open the project on Android Studio and run both modules once on your device, or if you have access, install the applications by using the APK files provided in the project delivery.
# Usage
The registration page has some password requirements that are not adequately formulated to the user. The password for every new user must contain at least six characters and must be a combination of letters and numbers. This is a requirement from Firebase. 
If you just want to test the app, you can log in with test@test.com as email, and test123abc as password.
The "Dronehub" page in the RescueDrone app is deprecated (the one that says "Hello world" in the middle of the screen). Disregard this page, the logout button might cause issues. 
The "Home" page is not implemented, and has no content. 
In the modified "DJI Sample application", the main page for actual flying is called "Default Layout". Go there once a product is connected. 

The prototype is quite buggy, so here is a (relatively) fool proof guide to test the main functions:
1.	Log in/ Register new user
2.	Navigate to the "Missions" page
3.	Find a mission you want to accept, view its intel if you want, and press "Push to Dronehub". The DJI Sample application should open, with the mission coordinates displayed on the screen. 
4.	Connect your DJI product. Wait for the "Connected to product" message in the log box.
5.	Press the "Default Layout" button. A window resembling the DJI GO app should open, showing you the drone video feed and telemetry data.
6.	Take off (button on the left of the screen) if you want to, locate the rescue target
7.	Position the drone in roughly the same area as the rescue target, and hit "Send Intel" to provide the mission object with the coordinates of the rescue target
8.	Land the drone, and return to the RescueDrone app to see the coordinates displayed on the previously selected mission when pressing "View Intel"
9.	Done! The test is now complete.
