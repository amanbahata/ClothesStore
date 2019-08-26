# Clothes-Store

Clothes-Store is a test mobile shopping application that runs on the android platform.
Clothes-Stroe is written in Java.

## Functionality
The app contains the following functionalities which the customer is able to do:
- browse a catalogue of clothing
- add items to a wishlist for later use, and
- add items to shopping cart

## Code structure
The app follows the MVP (Model, View, Presenter) architectural pattern. The code is organised into packages according to 
functionality.
- Models: model classes
- Services: functionality related to network connection
- Storage: functionality related to local storage
- UI: all views with their presenters.

## Unit Tests
An example unit test has been written for the HomePresenter class. It is located at 
```sh
app/src/test/java/com/aman/clothesshop/ui/home/
```


## Getting started
As usual, you get started by cloning the project to your local machine.

## Open and run project in Android Studio
Now that you have cloned the repo, you import the project to android studio and run it on an emulator or an external device

## Android version targeting
The app runs on Android Studio 3.x and API level 19 and above.

