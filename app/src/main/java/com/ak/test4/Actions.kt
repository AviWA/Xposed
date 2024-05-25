package com.ak.test4

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Actions : IXposedHookLoadPackage {

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedBridge.log("Loaded app: " + lpparam.packageName);

        XposedHelpers.findAndHookMethod(
            "com.ak.testx2.MainActivity",  // The class name to hook
            lpparam.classLoader,
            "onCreate",  // The method to hook
            Bundle::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    XposedBridge.log("Hooked into MainActivity onCreate")
                    try {
                        // Access the activity instance
                        val activity = param.thisObject
                        // Find the TextView by its ID
                        val textView = XposedHelpers.getObjectField(activity, "textView") as TextView
                        // Change the color of the TextView text
                        textView.setTextColor(Color.MAGENTA)
                    } catch (e: Exception) {
                        XposedBridge.log(e)
                    }
                }
            }
        )
    }

}