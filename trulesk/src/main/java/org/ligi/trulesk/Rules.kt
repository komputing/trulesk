package org.ligi.trulesk

import android.app.Activity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.setFailureHandler
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
import android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
import com.linkedin.android.testbutler.TestButler
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.ligi.tracedroid.TraceDroid


class TruleskActivityRule<T : Activity>(activityClass: Class<T>,
                                        autoLaunch: Boolean = true,
                                        private val before: () -> Unit = {})
    : ActivityTestRule<T>(activityClass, true, autoLaunch) {

    lateinit var description: Description

    override fun apply(base: Statement?, description: Description): Statement {
        this.description = description
        doOnApply(description)
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

    fun screenShot(tag: String) = screenshot(description, tag)
    fun launchActivity() = launchActivity(null)
}

class TruleskIntentRule<T : Activity>(activityClass: Class<T>,
                                      autoLaunch: Boolean = true,
                                      private val before: () -> Unit = {})
    : IntentsTestRule<T>(activityClass, true, autoLaunch) {

    lateinit var description: Description

    override fun apply(base: Statement?, description: Description): Statement {
        this.description = description
        doOnApply(description)
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

    fun screenShot(tag: String) = screenshot(description, tag)
    fun launchActivity() = launchActivity(null)
}


private fun ActivityTestRule<out Activity>.screenshot(description: Description, tag: String) = makeScreenshot(activity, description, tag)

private fun doOnApply( description: Description) {
    val newFailureHandler = ScreenshotFailureHandler(InstrumentationRegistry.getInstrumentation(), description)
    setFailureHandler(newFailureHandler)
}

private fun doBefore(additionalWork: () -> Unit) {

    TestButler.verifyAnimationsDisabled(InstrumentationRegistry.getInstrumentation().targetContext)

    TraceDroid.stackTraceFiles?.forEach { it.deleteRecursively() }

    additionalWork.invoke()
}

private fun doAfter(activity: Activity) {
    activity.runOnUiThread {
        activity.window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_DISMISS_KEYGUARD)
    }
}