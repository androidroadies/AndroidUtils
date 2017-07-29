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

## API

* ### About Activity→[ActivityUtils.java][activity.java]→[Demo][activity.demo]
```
isActivityExists
startActivity
getLauncherActivity
getTopActivity
```

* ### About App→[AppUtils.java][app.java]→[Demo][app.demo]
```
isInstallApp
installApp
installAppSilent
uninstallApp
uninstallAppSilent
isAppRoot
launchApp
getAppPackageName
getAppDetailsSettings
getAppName
getAppIcon
getAppPath
getAppVersionName
getAppVersionCode
isSystemApp
isAppDebug
getAppSignature
getAppSignatureSHA1
isAppForeground
getForegroundApp
getAppInfo
getAppsInfo
cleanAppData
```

* ### About Bar→[BarUtils.java][bar.java]
```
setTransparentStatusBar
hideStatusBar
getStatusBarHeight
isStatusBarExists
getActionBarHeight
showNotificationBar
hideNotificationBar
```

* ### About Clean→[CleanUtils.java][clean.java]→[Demo][clean.demo]
```
cleanInternalCache
cleanInternalFiles
cleanInternalDbs
cleanInternalDbByName
cleanInternalSP
cleanExternalCache
cleanCustomCache
```

* ### About Clipboard→[ClipboardUtils.java][clipboard.java]
```
copyText
getText
copyUri
getUri
copyIntent
getIntent
```

* ### About Close→[CloseUtils.java][close.java]
```
closeIO
closeIOQuietly
```

* ### About Convert→[ConvertUtils.java][convert.java]→[Test][convert.test]
```
bytes2HexString, hexString2Bytes
chars2Bytes, bytes2Chars
memorySize2Byte, byte2MemorySize
byte2FitMemorySize
timeSpan2Millis, millis2TimeSpan
millis2FitTimeSpan
bytes2Bits, bits2Bytes
input2OutputStream, output2InputStream
inputStream2Bytes, bytes2InputStream
outputStream2Bytes, bytes2OutputStream
inputStream2String, string2InputStream
outputStream2String, string2OutputStream
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
view2Bitmap
dp2px, px2dp
sp2px, px2sp
```

* ### About Crash→[CrashUtils.java][crash.java]
```
getInstance
init
```

* ### About Device→[DeviceUtils.java][device.java]→[Demo][device.demo]
```
isDeviceRooted
getSDKVersion
getAndroidID
getMacAddress
getManufacturer
getModel
shutdown
reboot
reboot2Recovery
reboot2Bootloader
```

* ### About Empty→[EmptyUtils.java][empty.java]→[Test][empty.test]
```
isEmpty
isNotEmpty
```

* ### About Encode→[EncodeUtils.java][encode.java]→[Test][encode.test]
```
urlEncode
urlDecode
base64Encode
base64Encode2String
base64Decode
base64UrlSafeEncode
htmlEncode
htmlDecode
```

* ### About Encrypt→[EncryptUtils.java][encrypt.java]→[Test][encrypt.test]
```
encryptMD2, encryptMD2ToString
encryptMD5, encryptMD5ToString
encryptMD5File, encryptMD5File2String
encryptSHA1, encryptSHA1ToString
encryptSHA224, encryptSHA224ToString
encryptSHA256, encryptSHA256ToString
encryptSHA384, encryptSHA384ToString
encryptSHA512, encryptSHA512ToString
encryptHmacMD5, encryptHmacMD5ToString
encryptHmacSHA1, encryptHmacSHA1ToString
encryptHmacSHA224, encryptHmacSHA224ToString
encryptHmacSHA256, encryptHmacSHA256ToString
encryptHmacSHA384, encryptHmacSHA384ToString
encryptHmacSHA512, encryptHmacSHA512ToString
encryptDES, encryptDES2HexString, encryptDES2Base64
decryptDES, decryptHexStringDES, decryptBase64DES
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64
decrypt3DES, decryptHexString3DES, decryptBase64_3DES
encryptAES, encryptAES2HexString, encryptAES2Base64
decryptAES, decryptHexStringAES, decryptBase64AES
```

* ### About File→[FileUtils.java][file.java]→[Test][file.test]
```
getFileByPath
isFileExists
rename
isDir
isFile
createOrExistsDir
createOrExistsFile
createFileByDeleteOldFile
copyDir
copyFile
moveDir
moveFile
deleteDir
deleteFile
listFilesInDir
listFilesInDir
listFilesInDirWithFilter
listFilesInDirWithFilter
listFilesInDirWithFilter
listFilesInDirWithFilter
searchFileInDir
writeFileFromIS
writeFileFromString
readFile2List
readFile2String
readFile2Bytes
getFileLastModified
getFileCharsetSimple
getFileLines
getDirSize
getFileSize
getDirLength
getFileLength
getFileMD5
getFileMD5ToString
getDirName
getFileName
getFileNameNoExtension
getFileExtension
```

* ### About Fragment→[FragmentUtils.java][fragment.java]→[Demo][fragment.demo]
```
addFragment
hideAddFragment
addFragments
removeFragment
removeToFragment
removeFragments
removeAllFragments
replaceFragment
popFragment
popToFragment
popFragments
popAllFragments
popAddFragment
hideFragment
hideFragments
showFragment
hideShowFragment
getLastAddFragment
getLastAddFragmentInStack
getTopShowFragment
getTopShowFragmentInStack
getFragments
getFragmentsInStack
getAllFragments
getAllFragmentsInStack
getPreFragment
findFragment
dispatchBackPress
setBackgroundColor
setBackgroundResource
setBackground
```

* ### About Handler→[HandlerUtils.java][handler.java]→[Demo][handler.demo]
```
HandlerHolder
```

* ### About Image→[ImageUtils.java][image.java]→[Demo][image.demo]
```
bitmap2Bytes, bytes2Bitmap
drawable2Bitmap, bitmap2Drawable
drawable2Bytes, bytes2Drawable
getBitmap
scale
clip
skew
rotate
getRotateDegree
toRound
toRoundCorner
fastBlur
renderScriptBlur
stackBlur
addFrame
addReflection
addTextWatermark
addImageWatermark
toAlpha
toGray
save
isImage
getImageType
compressByScale
compressByQuality
compressBySampleSize
```

* ### About Intent→[IntentUtils.java][intent.java]
```
getInstallAppIntent
getUninstallAppIntent
getLaunchAppIntent
getAppDetailsSettingsIntent
getShareTextIntent
getShareImageIntent
getComponentIntent
getShutdownIntent
getCaptureIntent
```

* ### About Keyboard→[KeyboardUtils.java][keyboard.java]→[Demo][keyboard.demo]
```
hideSoftInput
clickBlankArea2HideSoftInput
showSoftInput
toggleSoftInput
```

* ### About Location→[LocationUtils.java][location.java]→[Demo][location.demo]
```
isGpsEnabled
isLocationEnabled
openGpsSettings
register
unregister
getAddress
getCountryName
getLocality
getStreet
isBetterLocation
isSameProvider
```

* ### About Log→[LogUtils.java][log.java]→[Demo][log.demo]
```
Builder.setLogSwitch
Builder.setGlobalTag
Builder.setLogHeadSwitch
Builder.setLog2FileSwitch
Builder.setDir
Builder.setBorderSwitch
Builder.setLogFilter
v
d
i
w
e
a
file
json
xml
```

* ### About Network→[NetworkUtils.java][network.java]→[Demo][network.demo]
```
openWirelessSettings
isConnected
isAvailableByPing
getDataEnabled
setDataEnabled
is4G
getWifiEnabled
setWifiEnabled
isWifiConnected
isWifiAvailable
getNetworkOperatorName
getNetworkType
getIPAddress
getDomainAddress
```

* ### About Phone→[PhoneUtils.java][phone.java]→[Demo][phone.demo]
```
isPhone
getIMEI
getIMSI
getPhoneType
isSimCardReady
getSimOperatorName
getSimOperatorByMnc
getPhoneStatus
dial
call
sendSms
sendSmsSilent
getAllContactInfo
getContactNum
getAllSMS
```

* ### About Pinyin→[PinyinUtils.java][pinyin.java]→[Test][pinyin.test]
```
ccs2Pinyin
ccs2Pinyin
getPinyinFirstLetter
getPinyinFirstLetters
getSurnamePinyin
getSurnameFirstLetter
```

* ### About Process→[ProcessUtils.java][process.java]→[Demo][process.demo]
```
getForegroundProcessName
killAllBackgroundProcesses
killBackgroundProcesses
```

* ### About Regex→[RegexUtils.java][regex.java]→[Test][regex.test]
```
isMobileSimple
isMobileExact
isTel
isIDCard15
isIDCard18
isEmail
isURL
isZh
isUsername
isDate
isIP
isMatch
getMatches
getSplits
getReplaceFirst
getReplaceAll
```

* ### About Screen→[ScreenUtils.java][screen.java]
```
getScreenWidth
getScreenHeight
setLandscape
setPortrait
isLandscape
isPortrait
getScreenRotation
captureWithStatusBar
captureWithoutStatusBar
isScreenLock
```

* ### About SDCard→[SDCardUtils.java][sdcard.java]→[Demo][sdcard.demo]
```
isSDCardEnable
getSDCardPath
getDataPath
getFreeSpace
getSDCardInfo
```

* ### About Service→[ServiceUtils.java][service.java]
```
getAllRunningService
startService
stopService
bindService
unbindService
isServiceRunning
```

* ### About Shell→[ShellUtils.java][shell.java]
```
execCmd
```

* ### About Size→[SizeUtils.java][size.java]
```
dp2px, px2dp
sp2px, px2sp
applyDimension
forceGetViewSize
measureView
getMeasuredWidth
getMeasuredHeight
```

* ### About Snackbar→[SnackbarUtils.java][snackbar.java]→[Demo][snackbar.demo]
```
showShort
showLong
showIndefinite
addView
dismiss
```

* ### About SpannableString→[SpannableStringUtils.java][spannable.java]→[Demo][spannable.demo]
```
Builder.setFlag
Builder.setForegroundColor
Builder.setBackgroundColor
Builder.setQuoteColor
Builder.setLeadingMargin
Builder.setMargin
Builder.setBullet
Builder.setFontSize
Builder.setFontProportion
Builder.setFontXProportion
Builder.setStrikethrough
Builder.setUnderline
Builder.setSuperscript
Builder.setSubscript
Builder.setBold
Builder.setItalic
Builder.setBoldItalic
Builder.setFontFamily
Builder.setTypeface
Builder.setAlign
Builder.setBitmap
Builder.setDrawable
Builder.setUri
Builder.setResourceId
Builder.setClickSpan
Builder.setUrl
Builder.setBlur
Builder.append
Builder.create
```

* ### About SP→[SPUtils.java][sp.java]→[Test][sp.test]
```
SPUtils
put
getString
getInt
getLong
getFloat
getBoolean
getAll
remove
contains
clear
```

* ### About String→[StringUtils.java][string.java]→[Test][string.test]
```
isEmpty
isTrimEmpty
isSpace
equals
equalsIgnoreCase
null2Length0
length
upperFirstLetter
lowerFirstLetter
reverse
toDBC
toSBC
```

* ### About ThreadPool→[ThreadPoolUtils.java][thread_pool.java]
```
ThreadPoolUtils
execute
execute
shutDown
shutDownNow
isShutDown
isTerminated
awaitTermination
submit
submit
invokeAll, invokeAny
schedule
schedule
scheduleWithFixedRate
scheduleWithFixedDelay
```

* ### About Time→[TimeUtils.java][time.java]→[Test][time.test]
```
millis2String
string2Millis
string2Date
date2String
date2Millis
millis2Date
getTimeSpan
getFitTimeSpan
getNowMills
getNowString
getNowDate
getTimeSpanByNow
getFitTimeSpanByNow
getFriendlyTimeSpanByNow
getMillis
getString
getDate
getMillisByNow
getStringByNow
getDateByNow
isToday
isLeapYear
getChineseWeek
getUSWeek
getWeekIndex
getWeekOfMonth
getWeekOfYear
getChineseZodiac
getZodiac
```

* ### About Toast→[ToastUtils.java][toast.java]→[Demo][toast.demo]
```
setGravity
setView
getView
showShortSafe
showLongSafe
showShort
showLong
cancel
```

* ### About Zip→[ZipUtils.java][zip.java]→[Test][zip.test]
```
zipFiles
zipFile
unzipFiles
unzipFile
unzipFileByKeyword
getFilesPath
getComments
getEntries
```

* ### About Log→[update_log.md][update_log.md]**

***

## About

* [![jianshu][jianshusvg]][jianshu] [![weibo][weibosvg]][weibo]  [![Blog][blogsvg]][blog] [![QQ0Group][qq0groupsvg]][qq0group] [![QQ1Group][qq1groupsvg]][qq1group]

* **I'm so sorry for that the code is annotated with Chinese.**


## Download

Gradle:
``` groovy
compile 'com.androidroadies:utilcode:1.5.1'
```


## How to use

```
// init it in the function of onCreate in ur Application
Utils.init(context);
```


## Proguard

```
-keep class com.androidroadies.utilcode.** { *; }
-keepclassmembers class com.androidroadies.utilcode.** { *; }
-dontwarn com.androidroadies.utilcode.**
```



[aucsvg]: https://img.shields.io/badge/AndroidUtils-v1.5.1-brightgreen.svg
[auc]: https://github.com/androidroadies/AndroidUtils

[apisvg]: https://img.shields.io/badge/API-15+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=15

[buildsvg]: https://travis-ci.org/androidroadies/AndroidUtils.svg?branch=master
[build]: https://travis-ci.org/androidroadies/AndroidUtils

[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/androidroadies/AndroidUtils/blob/master/LICENSE

[jianshusvg]: https://img.shields.io/badge/简书-androidroadies-brightgreen.svg
[jianshu]: http://www.jianshu.com/u/46702d5c6978

[weibosvg]: https://img.shields.io/badge/weibo-__androidroadies-brightgreen.svg
[weibo]: http://weibo.com/3076228982

[blogsvg]: https://img.shields.io/badge/Blog-androidroadies-brightgreen.svg
[blog]: http://androidroadies.com

[qq0groupsvg]: https://img.shields.io/badge/QQ0群(满)-74721490-fba7f9.svg
[qq0group]: https://shang.qq.com/wpa/qunwpa?idkey=62baf2c3ec6b0863155b0c7a10c71bba2608cb0b6532fc18515835e54c69bdd3

[qq1groupsvg]: https://img.shields.io/badge/QQ1群-25206533-fba7f9.svg
[qq1group]: https://shang.qq.com/wpa/qunwpa?idkey=d906789f84484465e2736f7b524366b4c23afeda38733d5c7b10fc3f6e406e9b

[readme.md]: https://github.com/androidroadies/AndroidUtils
[readme-cn.md]: https://github.com/androidroadies/AndroidUtils/blob/master/README-CN.md

[activity.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ActivityUtils.java
[activity.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/ActivityActivity.java

[app.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/AppUtils.java
[app.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/AppActivity.java

[bar.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/BarUtils.java

[clean.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/CleanUtils.java
[clean.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/CleanActivity.java

[clipboard.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ClipboardUtils.java

[close.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/CloseUtils.java

[convert.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ConvertUtils.java
[convert.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/ConvertUtilsTest.java

[crash.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/CrashUtils.java

[device.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/DeviceUtils.java
[device.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/DeviceActivity.java

[empty.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/EmptyUtils.java
[empty.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/EmptyUtilsTest.java

[encode.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/EncodeUtils.java
[encode.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/EncodeUtilsTest.java

[encrypt.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/EncryptUtils.java
[encrypt.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/EncryptUtilsTest.java

[file.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/FileUtils.java
[file.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/FileUtilsTest.java

[fragment.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/FragmentUtils.java
[fragment.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/FragmentActivity.java

[handler.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/HandlerUtils.java
[handler.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/HandlerActivity.java

[image.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ImageUtils.java
[image.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/ImageActivity.java

[intent.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/IntentUtils.java

[keyboard.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/KeyboardUtils.java
[keyboard.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/KeyboardActivity.java

[location.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/LocationUtils.java
[location.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/LocationActivity.java

[log.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/LogUtils.java
[log.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/LogActivity.java

[network.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/NetworkUtils.java
[network.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/NetworkActivity.java

[phone.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/PhoneUtils.java
[phone.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/PhoneActivity.java

[pinyin.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/PinyinUtils.java
[pinyin.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/PinyinUtilsTest.java

[process.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ProcessUtils.java
[process.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/ProcessActivity.java

[regex.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/RegexUtils.java
[regex.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/RegexUtilsTest.java

[screen.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ScreenUtils.java

[sdcard.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/SDCardUtils.java
[sdcard.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/SDCardActivity.java

[service.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ServiceUtils.java

[shell.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ShellUtils.java

[size.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/SizeUtils.java

[snackbar.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/SnackbarUtils.java
[snackbar.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/SnackbarActivity.java

[spannable.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/SpannableStringUtils.java
[spannable.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/SpannableActivity.java

[sp.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/SPUtils.java
[sp.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/SPUtilsTest.java

[string.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/StringUtils.java
[string.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/StringUtilsTest.java

[thread_pool.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ThreadPoolUtils.java

[time.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/TimeUtils.java
[time.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/TimeUtilsTest.java

[toast.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ToastUtils.java
[toast.demo]: https://github.com/androidroadies/AndroidUtils/blob/master/app/src/main/java/com/androidroadies/AndroidUtils/activity/ToastActivity.java

[zip.java]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/main/java/com/androidroadies/utilcode/util/ZipUtils.java
[zip.test]: https://github.com/androidroadies/AndroidUtils/blob/master/utilcode/src/test/java/com/androidroadies/utilcode/util/ZipUtilsTest.java

[update_log.md]: https://github.com/androidroadies/AndroidUtils/blob/master/update_log.md

[group]: http://www.jianshu.com/p/8938015df951
[weibo]: http://weibo.com/blankcmj

##Demo
You can download the demo apk from [here](/docs/CommonTaskDemo.apk) .

##Documentation
Refer **[CommonTaskDoc.pdf](/docs/CommonTaskDoc.pdf)** for learning how to use methods of this SDK.

=======

**LICENSE**

The MIT License (MIT)

Copyright (c) 2015

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
