package com.hook.sample.transformers

val hookConfig = HookConfig(
    listOf(
        HookClass(
            needHookClass = "android/app/ActivityManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethod = HookMethod(methodName = "getRunningAppProcesses")
        ),
        HookClass(
            needHookClass = "android/bluetooth/BluetoothAdapter",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethod = HookMethod(methodName = "getBondedDevices")
        ),
        HookClass(
            needHookClass = "android/location/LocationManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(methodName = "getLastLocation"),
                HookMethod(methodName = "getLastKnownLocation"),
                HookMethod(
                    methodName = "requestLocationUpdates",
                    methodFilter = { methodNode -> !methodNode.desc.contains("LocationRequest") }
                ),
                HookMethod("requestSingleUpdate")
            )
        ),
        HookClass(
            needHookClass = "java/net/NetworkInterface",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(methodName = "getNetworkInterfaces"),
                HookMethod(methodName = "getHardwareAddress"),
            )
        ),
        HookClass(
            needHookClass = "android/content/pm/PackageManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(methodName = "getInstalledPackages"),
                HookMethod(methodName = "getInstalledApplications"),
                HookMethod(methodName = "queryIntentActivities"),
                HookMethod(methodName = "getPackagesForUid"),
            )
        ),
        HookClass(
            needHookClass = "java/lang/Runtime",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethod = HookMethod(methodName = "exec"),
        ),
        HookClass(
            needHookClass = "android/hardware/SensorManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(
                    methodName = "getDefaultSensor",
                    methodDesc = "(I)Landroid/hardware/Sensor;"
                ),
                HookMethod(methodName = "registerListener"),
            )
        ),
        HookClass(
            needHookClass = "android/provider/Settings\$System",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethod = HookMethod(methodName = "getString")
        ),
        HookClass(
            needHookClass = "android/telephony/TelephonyManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(methodName = "getDeviceId"),
                HookMethod(methodName = "getLine1Number"),
                HookMethod(methodName = "getSubscriberId"),
                HookMethod(methodName = "getMeid"),
                HookMethod(methodName = "listen"),
                HookMethod(methodName = "getSimSerialNumber"),
                HookMethod(methodName = "getManufacturerCode")
            )
        ),
        HookClass(
            needHookClass = "android/net/wifi/WifiManager",
            afterHookClass = "com/hook/sample/instrument/ShadowPrivacyApi",
            hookMethods = listOf(
                HookMethod(methodName = "getConnectionInfo"),
            )
        )
    )
)
