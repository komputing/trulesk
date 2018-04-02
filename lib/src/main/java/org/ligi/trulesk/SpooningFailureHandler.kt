package org.ligi.trulesk

import android.app.Activity
import android.app.Instrumentation
import android.support.test.espresso.FailureHandler
import android.support.test.espresso.base.DefaultFailureHandler
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicReference


class SpooningFailureHandler(private val instrumentation: Instrumentation) : FailureHandler {

    private val delegate by lazy { DefaultFailureHandler(instrumentation.targetContext) }

    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        try {
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

        delegate.handle(error, viewMatcher)
    }

    private fun currentActivity() : Activity {
        instrumentation.waitForIdleSync()
        val activity = AtomicReference<Activity>()
        instrumentation.runOnMainSync {
            val activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            activity.set(activities.first())
        }
        return activity.get()
    }

}

