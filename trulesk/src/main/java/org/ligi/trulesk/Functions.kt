package org.ligi.trulesk

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

fun invokeMenu(@IdRes menuId: Int, @StringRes menuStringRes: Int) = try {
    onView(withId(menuId)).perform(click())
} catch (nmv: NoMatchingViewException) {
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
    onView(withText(menuStringRes)).perform(click())
}