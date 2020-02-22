package org.ligi.trulesk

import android.os.Bundle
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.linkedin.android.testbutler.TestButler

open class BaseTruleskRunner : AndroidJUnitRunner() {

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getInstrumentation().targetContext)
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getInstrumentation().targetContext)
        super.finish(resultCode, results)
    }
}
