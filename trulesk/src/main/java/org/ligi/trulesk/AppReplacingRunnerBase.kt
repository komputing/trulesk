package org.ligi.trulesk

import android.app.Application
import android.content.Context

abstract class AppReplacingRunnerBase : BaseTruleskRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application
            = super.newApplication(cl, testAppClass().name, context)

    abstract fun testAppClass(): Class<out Application>
}
