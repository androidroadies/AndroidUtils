[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Common%20Tasks-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1676) [ ![Download](https://api.bintray.com/packages/multidots/md-maven/android-common-tasks/images/download.svg) ](https://bintray.com/multidots/md-maven/android-common-tasks/_latestVersion) [![Build Status](https://travis-ci.org/multidots/android-app-common-tasks.svg?branch=master)](https://travis-ci.org/multidots/android-app-common-tasks)

# Android App Common Tasks

Android App Common Tasks library is developed to reduce efforts to achieve common features of the android apps. While developing the apps, we realized that we’re coding for many common tasks in all the apps. For e.g. check the network’s availability, using shared preferences, parsing, etc. And like us, many other android developers might be doing the same. So it needs to be reduced for all to save the development time with ease. And so, we decided to develop a library which can reduce developers’ time and efforts.

- This Project contains Library of commonly used methods, classes and it's examples.

- Library also contains easy way to implement social sdk i.e : Facebook, Twitter, Google+, LinkedIn.

This Project includes following common features.

    EditText empty validation.
    Check network available or not (Internet or Wifi).
    Email address validation.
    Get current date in String format.
    Get device id of from your device.
    Set Preferences and get Preferences. Like String, int, long, Boolean, Float.
    Remove all preferences. 
    Get current location.
    Pinch Zoom on image.
    Get application Icon drawable.
    Send local notification.
    Disable sleep mode on while using application.
    Enable sleep mode on while using application.
    Open image from selected directory path.
    Open video from selected directory path.
    Open URL to mobile browser.
    Shows address location on map.
    Create folder or directory.
    Download image from URL.
    Show date picker.
    Show time picker.
    Get number of file counts.
    Calculate date difference.
    Convert date from string to date format.
    Get device height.
    Get device width.
    Get random number.
    Add postfix to numbers.
    Convert comma separated string to array list.
    Convert arrayList to comma separated string.
    Play background music.
    Stop background music.
    Apply blur effect on image.
    Convert drawable to bitmap.
    Convert bitmap to drawable.
    Get device volume for application for sound.
    Set bitmap image to preferences.
    Get bitmap image form preference.
    Get application version code.
    Set vertical text-view (left & right).
    Check Whether Sd card is available on Device.
    Show Share Dialog.
    Change Device Profile (Silent or Vibrate or Normal).
    Change bitmap to Rounded Cornered.
    Show Alert Dialog or Toast.
    Preventing double click.
    Capture Image.
    Pick Image.
    Preview Captured Image.
    Record Video.
    Pick Video.
    Preview Captured Video.
    Get path of picked image or video (for all versions).
    Integrating Social Platforms.
    Adding Ripple Effect.
    Check Website url is valid or not.
    Get all contacts that have email address.
    Get bitmap of view (ScreenShot).
    Pick Color from ImageView.
    Get File Size.
    Download File.
    Invoke callback after ImageView is loaded.
    Notif ImageView


##Gradle:
Integrate this library into your build.gradle using below dependency.
```
repositories {
    maven {
        url  "http://dl.bintray.com/multidots/md-maven" 
    }
}
dependency {
    compile 'com.multidots:common-task:1.0'
}
```

##Demo
You can download the demo apk from [here](/docs/CommonTaskDemo.apk) .

##Documentation
Refer **[CommonTaskDoc.pdf](/docs/CommonTaskDoc.pdf)** for learning how to use methods of this SDK.

=======

**LICENSE**

The MIT License (MIT)

Copyright (c) 2015 Multidots Solutions Pvt Ltd

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
