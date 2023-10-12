package com.hook.sample.instrument;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SuppressLint("MissingPermission")
public class ShadowPrivacyApi {
    private static final String TAG = "HOOK_ShadowPrivacyApi";
    public static boolean needHook = true;

    /**
     * BluetoothAdapter
     **/
    public static Set<BluetoothDevice> getBondedDevices(BluetoothAdapter adapter) {
        Log.i(TAG, "BluetoothAdapter#getBondedDevices() hooked");
        return new HashSet<>();
    }

    public static String getString(ContentResolver resolver, String name) {
        if (TextUtils.equals(Settings.Secure.ANDROID_ID, name)) {
            Log.i(TAG, "Settings$System#getString() protected");
            return "";
        }

        return Settings.System.getString(resolver, name);
    }

    public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses(ActivityManager activityManager) {
        if (needHook) {
            Log.e(TAG, "ActivityManager#getRunningAppProcesses() hooked!");
            return new ArrayList<>();

        }
        return activityManager.getRunningAppProcesses();
    }

    public static Location getLastLocation(LocationManager locationManager) {
        Log.i(TAG, "getLastLocation");
        return needHook ? new Location("gps") : locationManager.getLastKnownLocation("gps");
    }

    public static Location getLastKnownLocation(LocationManager locationManager, String provider) {
        Log.i(TAG, "getLastKnownLocation");
        return needHook ? new Location("gps") : locationManager.getLastKnownLocation(provider);
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, long minTime,
                                              float minDistance, LocationListener listener) {
        Log.i(TAG, "requestLocationUpdates 1");
        if (!needHook) {
            locationManager.requestLocationUpdates(provider, minTime, minDistance, listener);
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, long minTime,
                                              float minDistance, LocationListener listener, Looper looper) {
        Log.i(TAG, "requestLocationUpdates 2");
        if (!needHook) {
            locationManager.requestLocationUpdates(provider, minTime, minDistance, listener, looper);
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, long minTime, float minDistance,
                                              Criteria criteria, LocationListener listener, Looper looper) {
        Log.i(TAG, "requestLocationUpdates 3");
        if (!needHook) {
            locationManager.requestLocationUpdates(minTime, minDistance, criteria, listener, looper);
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, String provider, long minTime,
                                              float minDistance, PendingIntent intent) {
        Log.i(TAG, "requestLocationUpdates 4");
        if (!needHook) {
            locationManager.requestLocationUpdates(provider, minTime, minDistance, intent);
        }
    }

    public static void requestLocationUpdates(LocationManager locationManager, long minTime, float minDistance,
                                              Criteria criteria, PendingIntent intent) {
        Log.i(TAG, "requestLocationUpdates 5");
        if (!needHook) {
            locationManager.requestLocationUpdates(minTime, minDistance, criteria, intent);
        }
    }

    public static void requestSingleUpdate(LocationManager locationManager, String provider, LocationListener listener,
                                           Looper looper) {
        Log.i(TAG, "requestSingleUpdate 1");
        if (!needHook) {
            locationManager.requestSingleUpdate(provider, listener, looper);
        }
    }

    public static void requestSingleUpdate(LocationManager locationManager, Criteria criteria, LocationListener listener,
                                           Looper looper) {
        Log.i(TAG, "requestSingleUpdate 2");
        if (!needHook) {
            locationManager.requestSingleUpdate(criteria, listener, looper);
        }
    }

    public static void requestSingleUpdate(LocationManager locationManager, String provider, PendingIntent intent) {
        Log.i(TAG, "requestSingleUpdate 3");
        if (!needHook) {
            locationManager.requestSingleUpdate(provider, intent);
        }
    }

    public static void requestSingleUpdate(LocationManager locationManager, Criteria criteria, PendingIntent intent) {
        Log.i(TAG, "requestSingleUpdate 4");
        if (!needHook) {
            locationManager.requestSingleUpdate(criteria, intent);
        }
    }

    public static Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
        if (!needHook) {
            Log.e(TAG, "NetworkInterface#getNetworkInterfaces() is not hooked");
            return NetworkInterface.getNetworkInterfaces();
        }

        Log.i(TAG, "hook getNetworkInterfaces");
        return Collections.emptyEnumeration();
    }

    public static byte[] getHardwareAddress(NetworkInterface networkInterface) throws SocketException {
        if (!needHook) {
            return networkInterface.getHardwareAddress();
        }
        Log.i(TAG, "hook getHardwareAddress");
        throw new SocketException("ShadowNetworkInterface: hook!");
    }

    @NonNull
    public static List<PackageInfo> getInstalledPackages(PackageManager packageManager, int flags) {
        if (needHook) {
            Log.e(TAG, "getInstalledPackages hooked");
            return new ArrayList<>();
        }
        return packageManager.getInstalledPackages(flags);
    }

    @NonNull
    public static List<ApplicationInfo> getInstalledApplications(PackageManager packageManager, int flags) {
        if (needHook) {
            Log.e(TAG, "getInstalledApplications hooked");
            return new ArrayList<>();
        }
        return packageManager.getInstalledApplications(flags);
    }


    @NonNull
    public static List<ResolveInfo> queryIntentActivities(PackageManager packageManager, @NonNull Intent intent, int flags) {
        if (needHook) {
            Log.e(TAG, "queryIntentActivities hooked");
            return new ArrayList<>();
        }
        return packageManager.queryIntentActivities(intent, flags);
    }

    @Nullable
    public static String[] getPackagesForUid(PackageManager packageManager, int uid) {
        if (needHook) {
            Log.e(TAG, "getPackagesForUid hooked");
            return null;
        }
        return packageManager.getPackagesForUid(uid);
    }

    public static Process exec(Runtime runtime, String command) throws IOException {
        // 放行 PhoneUtils#Rom#check
        if (needHook && (command != null && !command.startsWith("getprop"))) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(command);
    }

    public static Process exec(Runtime runtime, String cmdarray[]) throws IOException {
        if (needHook) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(cmdarray);
    }

    public static Process exec(Runtime runtime, String command, String[] envp) throws IOException {
        if (needHook) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(command, envp);
    }

    public static Process exec(Runtime runtime, String command, String[] envp, File dir)
            throws IOException {
        if (needHook) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(command, envp, dir);
    }

    public static Process exec(Runtime runtime, String[] cmdarray, String[] envp) throws IOException {
        if (needHook) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(cmdarray, envp);
    }

    public static Process exec(Runtime runtime, String[] cmdarray, String[] envp, File dir)
            throws IOException {
        if (needHook) {
            Log.e(TAG, "java.lang.Runtime hooked");
            throw new IOException();
        }
        return runtime.exec(cmdarray, envp, dir);
    }


    public static Sensor getDefaultSensor(SensorManager sensorManager, int type) {
        if (needHook) {
            return null;
        } else {
            return sensorManager.getDefaultSensor(type);
        }
    }

    public static boolean registerListener(SensorManager sensorManager, SensorListener listener, int sensors) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensors);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }

    public static boolean registerListener(SensorManager sensorManager, SensorListener listener, int sensors, int rate) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensors, rate);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }

    public static boolean registerListener(SensorManager sensorManager, SensorEventListener listener, Sensor sensor, int samplingPeriodUs) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensor, samplingPeriodUs);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }

    public static boolean registerListener(SensorManager sensorManager, SensorEventListener listener, Sensor sensor, int samplingPeriodUs, int maxReportLatencyUs) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensor, samplingPeriodUs, maxReportLatencyUs);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }

    public static boolean registerListener(SensorManager sensorManager, SensorEventListener listener, Sensor sensor, int samplingPeriodUs, Handler handler) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensor, samplingPeriodUs, handler);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }

    public static boolean registerListener(SensorManager sensorManager, SensorEventListener listener, Sensor sensor, int samplingPeriodUs, int maxReportLatencyUs, Handler handler) {
        if (!needHook) {
            return sensorManager.registerListener(listener, sensor, samplingPeriodUs, maxReportLatencyUs, handler);
        }
        Log.d(TAG, "registerListener hooked");
        return false;
    }


    public static String getDeviceId(TelephonyManager telephonyManager) {
        return "Others";
    }

    public static String getDeviceId(TelephonyManager telephonyManager, int slotIndex) {
        return "Others";
    }

    public static String getLine1Number(TelephonyManager telephonyManager) {
        return "Others";
    }

    public static String getLine1Number(TelephonyManager telephonyManager, int subId) {
        return "Others";
    }

    public static String getSubscriberId(TelephonyManager telephonyManager) {
        return "Others";
    }

    public static String getSubscriberId(TelephonyManager telephonyManager, int subId) {
        return "Others";
    }

    public static String getMeid(TelephonyManager telephonyManager) {
        Log.e(TAG, "TelephonyManager#getMeid() hooked");
        return "";
    }

    public static String getMeid(TelephonyManager telephonyManager, int slotIndex) {
        Log.e(TAG, "TelephonyManager#getMeid(slotIndex) hooked");
        return "";
    }

    public static void listen(TelephonyManager telephonyManager, PhoneStateListener listener, int events) {
        Log.e(TAG, "TelephonyManager#listen() hooked");
    }

    public static String getSimSerialNumber(TelephonyManager telephonyManager) {
        Log.e(TAG, "TelephonyManager#getSimSerialNumber() hooked");
        return "";
    }

    public static String getSimSerialNumber(TelephonyManager telephonyManager, int subId) {
        Log.e(TAG, "TelephonyManager#getSimSerialNumber(subId) hooked");
        return "";
    }

    public static String getManufacturerCode(TelephonyManager telephonyManager) {
        Log.e(TAG, "TelephonyManager#getManufacturerCode() hooked");
        return "";
    }

    public static String getManufacturerCode(TelephonyManager telephonyManager, int slotIndex) {
        Log.e(TAG, "TelephonyManager#getManufacturerCode(slotIndex) hooked");
        return "";
    }

    @Nullable
    public static WifiInfo getConnectionInfo(WifiManager wifiManager) {
        if (!needHook) {
            Log.e(TAG, "WifiManager#getConnectionInfo() not hooked");
            return wifiManager.getConnectionInfo();
        }
        Log.e(TAG, "WifiManager#getConnectionInfo() hooked");
        return null;
    }
}
