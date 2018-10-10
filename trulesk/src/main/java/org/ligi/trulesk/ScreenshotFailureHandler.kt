package org.ligi.trulesk

import android.app.Activity
import android.app.Instrumentation
import android.support.test.espresso.FailureHandler
import android.support.test.espresso.base.DefaultFailureHandler
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import org.hamcrest.Matcher
import org.junit.runner.Description
import java.util.concurrent.atomic.AtomicReference


class ScreenshotFailureHandler(private val instrumentation: Instrumentation,
                               private val description: Description) : FailureHandler {

    private val delegate by lazy { DefaultFailureHandler(instrumentation.targetContext) }

    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        currentActivity()?.let { currentActivity ->
            makeScreenshot(currentActivity, description, "error")
        }

        delegate.handle(error, viewMatcher)
    }

    private fun currentActivity(): Activity? {
        instrumentation.waitForIdleSync()
        val activity = AtomicReference<Activity?>()
        instrumentation.runOnMainSync {
            val activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            activity.set(activities.firstOrNull())
        }
        return activity.get()
    }

}

