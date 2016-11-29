TRulesK
=======

Test Rules Kotlin

Why?
====

I had some code that was the same over several Android Apps of mine - this library is created to DRY this out and provide some test-rules with common functionality.

These are test-rules that do the following:
 - Install a SpooningFailureHandler ( Making screenshots with [falcon-spoon](https://github.com/jraska/Falcon) - so you easily see in the report when the test failed because of some system dialog)
 - Set the FLAG_TURN_SCREEN_ON or FLAG_DISMISS_KEYGUARD
 - Provide screenshot facade as extension function ( I was switching from native spoon to falcon-spoon once - with this extension function there is a single place to change the implementation )
 - Delete [TraceDroid](https://github.com/ligi/tracedroid) stacktrace files
 - Base for custom Runner which lets you replace the Application class easily
 - Activate and Deactivate [TestButtler](https://github.com/linkedin/test-butler) ( And verify animations are disabled )
 
These functions come from one ActivityTestRule and one IntentTestRule

Where?
======

get it via jitpack:

[![Release](https://jitpack.io/v/ligi/trulesk.svg)](https://jitpack.io/#ligi/trulesk)

License
=======

MIT License
