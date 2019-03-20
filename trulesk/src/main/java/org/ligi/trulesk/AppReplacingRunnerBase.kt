package org.ligi.trulesk

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler

abstract class AppReplacingRunnerBase : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application
            = super.newApplication(cl, testAppClass().name, context)

    abstract fun testAppClass(): Class<out Application>

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getInstrumentation().targetContext)
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getInstrumentation().targetContext)
        super.finish(resultCode, results)
    }
}
