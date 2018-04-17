package org.ligi.trulesk

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.setFailureHandler
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
import android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
import com.jraska.falcon.Falcon
import com.linkedin.android.testbutler.TestButler
import com.squareup.spoon.SpoonRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.ligi.tracedroid.TraceDroid


class TruleskActivityRule<T : Activity>(activityClass: Class<T>,
                                        autoLaunch: Boolean = true,
                                        private val before: () -> Unit = {})
    : ActivityTestRule<T>(activityClass, true, autoLaunch) {

    private val spoonRule = SpoonRule()

    override fun apply(base: Statement?, description: Description?): Statement {
        spoonRule.apply(base, description)
        return super.apply(base, description)
    }

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        doBefore(before)
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        doAfter(activity)
    }

    fun screenShot(tag: String) = screenshot(spoonRule, tag)
    fun launchActivity() = launchActivity(null)
}

class TruleskIntentRule<T : Activity>(activityClass: Class<T>,
                                      autoLaunch: Boolean = true,
                                      private val before: () -> Unit = {})
    : IntentsTestRule<T>(activityClass, true, autoLaunch) {

    private val spoonRule = SpoonRule()

    override fun apply(base: Statement?, description: Description?): Statement {
        spoonRule.apply(base, description)
        return super.apply(base, description)
    }

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        doBefore(before)
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        doAfter(activity)
    }

    fun screenShot(tag: String) = screenshot(spoonRule, tag)
    fun launchActivity() = launchActivity(null)
}


private fun ActivityTestRule<out Activity>.screenshot(spoonRule: SpoonRule, tag: String) {

    try {
        val screenshot = spoonRule.screenshot(activity, tag)
        Falcon.takeScreenshot(activity, screenshot)
    } catch (e: Exception) {
        // OK we could not make a screenshot - no big deal
        // do not fail the build - might just be a missing permission
        e.printStackTrace()
    }
}

private fun doBefore(additionalWork: () -> Unit) {
    TestButler.verifyAnimationsDisabled(InstrumentationRegistry.getTargetContext())

    TraceDroid.getStackTraceFiles()?.forEach { it.deleteRecursively() }
    setFailureHandler(SpooningFailureHandler(InstrumentationRegistry.getInstrumentation()))
    additionalWork.invoke()
}

private fun doAfter(activity: Activity) {
    activity.runOnUiThread {
        activity.window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_DISMISS_KEYGUARD)
    }
}