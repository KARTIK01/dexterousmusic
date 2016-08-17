# README #

This is Dexterous Music Player App.

### What is this repository for? ###

* Music Player

### How do I get set up? ###

* clone it
* open in Android Studio
* build-run

### Dependencies ###


### Contribution guidelines ###


### Custom ###

* Use FontTextView instead of TextView

```
#!xml


 <music.dexterous.com.dexterousmusic.customeviews.FontTextView
        android:id="@+id/song_duration"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toLeftOf="@+id/showPopup"
        android:orientation="vertical"
        android:textSize="20sp"
        app:fontName="Calibri-light.ttf"
        tools:text="5.30" />
```


* Use PrettyLogger instead of Lod

```
#!java


PrettyLogger.d("Refreshed token: " + refreshedToken);
```