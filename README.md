# README #

This is Dexterous Music Player App.

### What is this repository for? ###

* Music Player

### How do I get set up? ###

* clone it
* Add files from igonre folder to there specific location
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
         android:textSize="@dimen/text_large"
        app:fontName="Calibri-light.ttf"
        tools:text="5.30" />
```


* Use PrettyLogger instead of Log

```
#!java


PrettyLogger.d("Refreshed token: " + refreshedToken);
```

* Use ShortToast instead of Toast

```
#!java


ShortToast.displayToast(context, "Please Select At Least One song to play", 2000);
```

### Don't not chnage any thing in database package directly###

Its auto generated code by GreenDao lib, for more reference go https://github.com/greenrobot/greenDAO


### To Store any data in SharedPreference ###

create getter and setter method in ```OtherPreference.java``` or  ```UserPreference.java``` or ```UsersAppPreference.java```
then call that function where you required. 
For more help check allready created methods in ```OtherPreference.java```

### use TextSize form dimen ###
```

 android:textSize="@dimen/text_large"
```

### use Text and Vector images from UiUtils ###

Text and Vector images which are used again and again in HomeActivity load them in 
```

UiUtils.loadHomeActivitySpecificData
```

and use from there instead of direct access from drawable or strings