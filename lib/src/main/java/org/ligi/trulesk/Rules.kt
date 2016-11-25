package org.ligi.trulesk

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.setFailureHandler
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.view.WindowManager
import com.jraska.falcon.FalconSpoon
import org.ligi.tracedroid.TraceDroid

class TruleskActivityRule<T : Activity>(activityClass: Class<T>, autoLaunch: Boolean = true)
    : ActivityTestRule<T>(activityClass, true, autoLaunch) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        doBefore()
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        doAfter(activity)
    }

    fun screenShot(tag: String) = screenshot(tag)

}

class TruleskIntentRule<T : Activity>(activityClass: Class<T>, autoLaunch: Boolean = true)
    : IntentsTestRule<T>(activityClass, true, autoLaunch) {

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        doBefore()
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        doAfter(activity)
    }

    fun screenShot(tag: String) = screenshot(tag)
}


private fun ActivityTestRule<out Activity>.screenshot(tag: String) {
    try {
        FalconSpoon.screenshot(this.activity, tag)
    } catch (e: Exception) {
        // OK we could not make a screenshot - no big deal
        // do not fail the build - might just be a missing permission
        e.printStackTrace()
    }
}

private fun doBefore() {
    TraceDroid.getStackTraceFiles()?.forEach { it.deleteRecursively() }
    setFailureHandler(SpooningFailureHandler(InstrumentationRegistry.getInstrumentation()))
}

fun doAfter(activity: Activity) {
    activity.runOnUiThread { activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD) }
}