# Demo of Android WebView without a Status Bar

Recently, I had to customize an app that was runn g on Android 7 to hide the status bar. This little project demonstrates how I solved the problem.

## What is the "status bar"?

The status bar is a rectangular bar at the very top of the display in an Android device. It contains things about your device's status: bluetooth, wifi, cell network strength, the time, and maybe notifications like download status or text messages or missed calls.

Here's a screenshot. The status bar is outlined in green.

![Android status bar](https://github.com/fullStackOasis/android-webview-textinput-no-statusbar/raw/master/android-status-bar.jpg)

Notice that the status bar is purple in this screenshot. That's in keeping with the theme for the app. The status bar can change colors, or be transparent to let the background show through. When transparent, its widgets will show up in white. (Not sure if they ever show up in a different color TBH).

The status bar is fine when you have an Android device that's for personal use. But Android devices are often used as cutomizable hardware devices for companies. They can be used in all sorts of ways that I won't get into now. In these cases, the status bar is not so nice to have because it reveals a detail about the customer-facing device that the customer really doesn't need to know.

So, how do you get rid of it?

## How to get rid of the status bar

It turns out to be both easy and tough. It depends on your use case.

The [instructions to remove the status bar are available at Google's Android Developer website](https://developer.android.com/training/system-ui/status).

The solution provided at the webpage above seems pretty easy. I didn't want "granular" control as described lower in the page; I just wanted that status bar to be **gone**. So I used the solution for Android 4.0 and lower. For that case, you just use an Activity theme that's "fullscreen".

I set up a new project based on an old project that was similar to the work I was doing - it had a WebView of a webpage with some form elements that needed focus. In the new simple project, I use the [Google URL to create an account](https://accounts.google.com/signup/v2/webcreateaccount?flowName=GlifWebSignIn&flowEntry=SignUp) because it demonstrates the problem nicely. To see all the form elements, you have to scroll down.

This demo app opens a WebView of that page in Android, focuses on the first text input, and opens the soft keyboard so the user can immediately start typing in their info.

The app works, but the status bar is visible. Here's the animation that demonstrates that - notice the status bar with wifi, time, and other info at the top:

![Android status bar with WebView and soft keyboard](https://github.com/fullStackOasis/android-webview-textinput-no-statusbar/raw/master/1-trial-without-fullscreen.gif)

If I use the recommended solution, I have to change my app theme (in styles.xml) to be fullscreen:

```
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:windowFullscreen">true</item>
    </style>

</resources>
```

That **does** get rid of the status bar, but you will immediately notice a problem (shown in the gif below). As you tab through the form elements, moving down the screen, the lowest text inputs remain hidden by the soft keyboard. And there's no way to make the view scroll up so that you can see them. It's a horrible user experience.

![Android status bar gone but cannot see lower text inputs due to soft keyboard](https://github.com/fullStackOasis/android-webview-textinput-no-statusbar/raw/master/2-fullscreen-trial-cannot-see-form-inputs.gif)

If you go down this rabbit hole you're going to find that "fullscreen" mode just wasn't apparently intended to be applied when a user was interacting with the screen. I haven't actually seen this mentioned, but it's the implication.

## Set status bar color to transparent

In the end, it was this **hack** that seemed to work:
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>

</resources>

Here's a new demo that shows the `WebView` scrolling nicely, and the status bar is "gone" (I guess it's there, but invisible):

![Android status bar invisible and now you can see lower text inputs even with soft keyboard](https://github.com/fullStackOasis/android-webview-textinput-no-statusbar/raw/master/demo-android-transparent-statusbar.gif)

That's it, I hope it helps someone!
