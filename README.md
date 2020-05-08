[TOC]

# TPMS SDK instructions-Android

[![](https://jitpack.io/v/elinkthings/TmpsSDKRepositoryAndroid.svg)](https://jitpack.io/#elinkthings/TmpsSDKRepositoryAndroid)

[aar package download address](https://github.com/elinkthings/TmpsSDKRepositoryAndroid/releases)

[key registered address](http://sdk.aicare.net.cn)




## 1. Conditions of use:
1. Android SDK minimum version android4.4 (API 19).
2. The Bluetooth version used by the device requires 4.0 or above.
3. Configure java1.8
4. The project depends on the androidx library

## Second, import the SDK


```
repositories {
    flatDir {
        dirs 'libs'
    }
}


1. Add the JitPack repository to your build file
Add it to the root build.gradle at the end of the repository:
allprojects {
repositories {
...
maven {url 'https://jitpack.io'}
}
}

2. Add dependencies
dependencies {
implementation 'com.github.elinkthings: TmpsSDKRepositoryAndroid: 1.2.2' // Bluetooth library

}

3. Configure java1.8 in gradle
    android {
        ...
        compileOptions {
            sourceCompatibility 1.8
            targetCompatibility 1.8
        }
    }

You can also use the aar package dependency, please download it yourself and put it in the project's libs, the download address is at the top of the document



```

## Second, permission settings

```
<!-In most cases, you need to ensure that the device supports BLE .-->
<uses-feature
    android: name = "android.hardware.bluetooth_le"
    android: required = "true" />

<uses-permission android: name = "android.permission.BLUETOOTH" />
<uses-permission android: name = "android.permission.BLUETOOTH_ADMIN" />

<!-Android 6.0 and above. Bluetooth scanning requires one of the following two permissions. You need to apply at run time .-->
<uses-permission android: name = "android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android: name = "android.permission.ACCESS_FINE_LOCATION" />

<!-Optional. If your app need dfu function .-->
<uses-permission android: name = "android.permission.INTERNET" />
```

> 6.0 and above systems must locate permissions and need to manually obtain permissions

## Three, start integration



- First configure the key and secret for the SDK, [application address](http://sdk.aicare.net.cn)

```
TpmsSDK.init(this, key, secret);
```

- Implement the TpmsScan.TpmsScanListener method

```
TpmsScan mTpmsScan = new TpmsScan (new TpmsScan.TpmsScanListener () {
            @Override
            public void onGetData (String mac, String deviceId, int rssi, float pressure, float battery, int temp, int status, float mcuVersion, int year, int month, int day, float bleVersion) {
                
            }
        });

```

### 3, search for TpmsScan.startScan (String [] deviceIdS);

```
    / **
     * Search device, search all devices by default and return through TpmsScanListener interface
     * /
     TpmsScan.startScan ()

   / **
     * Search for devices, search for devices in the incoming device id array and return through the TpmsScanListener interface
     *
     * /
     TpmsScan.startScan (String [] deviceIdS)

The found devices will be returned in onGetData (...) in the TpmsScan.TpmsScanListener interface

```

### 4, Data analysis returned by the TpmsScan.TpmsScanListener interface

```
public void onGetData (String mac, String deviceId, int rssi, float pressure, float battery, int temp, int status, float mcuVersion, int year, int month, int day, float bleVersion);

mac: device mac address
deviceId: device id
rssi: signal value
pressure: air pressure
battery: voltage
temp: temperature
status: status
    / **
     * device status
     * /
    public enum DeviceState {
        / **
         * Normal
         * /
        NORMAL,
        / **
         * Leakage
         * /
        LEAK,
        / **
         * Inflatable
         * /
        INFLATE,
        / **
         * start up
         * /
        START,
        / **
         * Power-on
         * /
        POWER_ON,
        / **
         * Wake up
         * /
        WEAK_UP,
        / **
         * Abnormal temperature
         * /
        TEMP_ERROR,
        / **
         * Abnormal battery
         * /
        BATTERY_ERROR,
        / **
         * Unknown
         * /
        UNKNOWN
    }
    
mcuVersion: mcu version number
year, month, day: date
bleVersion: Bluetooth version number


    / **
     * Equipment installation location
     * /
    public enum DevicePosition {
        / **
         * Front left
         * /
        LEFT_FRONT,
        / **
         * Rear left
         * /
        LEFT_REAR,
        / **
         * Front right
         * /
        RIGHT_FRONT,
        / **
         * right back
         * /
        RIGHT_REAR
    }

```

### 5, stop searching TpmsScan.stopScan ();



## FAQ

- Can't scan the Bluetooth device?

1. Check whether the permissions of the App are normal. The 6.0 and above systems must locate the permissions and need to manually obtain the permissions
2. Check whether the location service of the mobile phone is turned on, some mobile phones may need to turn on the GPS



## Contact Us
Shenzhen elink things Co., Ltd.

Phone: 0755-81773367

Official website: www.elinkthings.com

Email: app@elinkthings.com



