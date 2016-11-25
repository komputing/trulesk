package org.ligi.trulesk

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

abstract class AppReplacingRunnerBase : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application
            = super.newApplication(cl, testAppClass().name, context)

    abstract fun testAppClass(): Class<out Application>
}
