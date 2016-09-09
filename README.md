# README #

This is Dexterous Music Player App.

### What is this repository for? ###

* Music Player

### How do I get set up? ###

* clone it
```
    git clone https://kartik_agarwal01_self@bitbucket.org/kartik_agarwal01_self/dexterousmusic.git
```
* Add files from igonre folder to there specific location
    * Add ```/dexterousmusic/ignore/build.gradle``` into ```/dexterousmusic/app``` folder
    * Add ```/dexterousmusic/ignore/GlobalApplication.java``` into ```/dexterousmusic/app/src/main/java/music.dexterous.com.dexterousmusic``` folder
* open in Android Studio
* build OR gradle sync
* Run 

### Dependencies ###
* Event Bus
```
    compile 'org.greenrobot:eventbus:3.0.0'
```
* Green DAO
```
    compile "org.greenrobot:greendao:$greenDaoVersion"
```
* Rx Java
```
     compile 'io.reactivex:rxjava:1.1.5'
```
* Rx Android and Binding
```
    compile 'com.jakewharton.rxbinding:rxbinding:0.4.0'
```
* Glide
```
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.github.bumptech.glide:okhttp3-integration:1.4.0@aar'
```
* Logger
```
    compile 'com.orhanobut:logger:1.15'
```
* fastscroller     
```
    com.github.viethoa:fastscroller:1.1.0
```
* materialscrollbar 
```
   compile 'com.turingtechnologies.materialscrollbar:lib:10.+'
```
* permissionsdispatcher
```
    compile 'com.github.hotchemi:permissionsdispatcher:2.1.3'
    apt 'com.github.hotchemi:permissionsdispatcher-processor:2.1.3'
```
* firebase-messaging
```
    compile 'com.google.firebase:firebase-messaging:9.4.0'
```
* crashlytics
```
    compile('com.crashlytics.sdk.android:crashlytics:2.6.2@aar') {
        transitive = true;
    }
```

### Contribution guidelines ###
* Kartik Agarwal (Major Contributing)

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

You can only change in Entity classes which is only one i.e 
```Music in music.dexterous.com.dexterousmusic.database```

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