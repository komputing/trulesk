package org.ligi.trulesk

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.setFailureHandler
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.view.WindowManager
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

}

private fun doBefore() {
    TraceDroid.deleteStacktraceFiles()
    setFailureHandler(SpooningFailureHandler(InstrumentationRegistry.getInstrumentation()))
}


fun doAfter(activity: Activity) {
    activity.runOnUiThread { activity.window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD) }
}