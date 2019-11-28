package org.ligi.trulesk

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_WORLD_READABLE
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Environment.DIRECTORY_PICTURES
import com.jraska.falcon.Falcon
import org.junit.runner.Description
import java.io.File


fun makeScreenshot(activity: Activity, description: Description, tag: String) {

    try {
        val screenshotDirectory = obtainDirectory(activity, description)
        val screenshotFile = File(screenshotDirectory, "$tag.png")
        Falcon.takeScreenshot(activity, screenshotFile)
    } catch (e: Exception) {
        // OK we could not make a screenshot - no big deal
        // do not fail the build - might just be a missing permission
        e.printStackTrace()
    }

}

private fun obtainDirectory(context: Context, description: Description): File {
    val directoryName = "spoon-screenshots"
    val directory = if (SDK_INT >= LOLLIPOP) {
        // Use external storage.
        File(context.getExternalFilesDir(DIRECTORY_PICTURES), "app_$directoryName")
    } else {
        // Use internal storage.
        context.getDir(directoryName, MODE_WORLD_READABLE)
    }

    val dirClass = File(directory, description.className)
    val dirMethod = File(dirClass, description.methodName)
    createDir(dirMethod)
    return dirMethod
}

private fun createDir(dir: File) {
    val parent = dir.parentFile
    if (parent?.exists() == false) {
        createDir(parent)
    }
    if (!dir.exists() && !dir.mkdirs()) {
        throw RuntimeException("Unable to create output dir: " + dir.absolutePath)
    }

    dir.setReadable(true, false)
    dir.setWritable(true, false)
    dir.setExecutable(true, false)
}
