package com.aemerse.networktools

import android.view.WindowManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private var activity: MainActivity? = null

    @Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )

    @Before
    fun setUp() {
        activity = mActivityRule.getActivity()

        // Code to wake up screen before running tests
        val wakeUpDevice = Runnable {
            activity!!.window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
        activity!!.runOnUiThread(wakeUpDevice)
    }

    @Test
    fun checkPing() {
//        Spoon.screenshot(mActivityRule.getActivity(), "ping")
        setUpText("google.com")

        // Click ping button
        Espresso.onView(ViewMatchers.withId(R.id.pingButton)).perform(ViewActions.click())
        sleep()
//        Spoon.screenshot(mActivityRule.getActivity(), "ping")
    }

    @Test
    fun checkWOL() {
//        Spoon.screenshot(mActivityRule.getActivity(), "wake-on-lan")
        setUpText("localhost")

        // Click ping button
        Espresso.onView(ViewMatchers.withId(R.id.wolButton)).perform(ViewActions.click())
        sleep()
//        Spoon.screenshot(mActivityRule.getActivity(), "wake-on-lan")
    }

    @Test
    fun checkPortScan() {
//        Spoon.screenshot(mActivityRule.getActivity(), "port_scan")
        setUpText("localhost")

        // Click ping button
        Espresso.onView(ViewMatchers.withId(R.id.portScanButton)).perform(ViewActions.click())
        sleep()
//        Spoon.screenshot(mActivityRule.getActivity(), "port_scan")
    }

    //    @Test
    //    public void checkGitHubButton(){
    //        onView(withId(R.id.action_github)).perform(click());
    //
    //        intended(allOf(hasData(hasHost(equalTo("www.google.com"))),
    //                hasAction(Intent.ACTION_VIEW)));
    //    }
    private fun setUpText(hostNameOrIp: String) {
        // Enter text
        Espresso.onView(ViewMatchers.withId(R.id.editIpAddress))
            .perform(
                ViewActions.clearText(),
                ViewActions.typeText(hostNameOrIp),
                ViewActions.closeSoftKeyboard()
            )

        // Check text is entered
        Espresso.onView(ViewMatchers.withId(R.id.editIpAddress))
            .check(ViewAssertions.matches(ViewMatchers.withText(hostNameOrIp)))
    }

    private fun sleep() {
        try {
            Thread.sleep(1500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}