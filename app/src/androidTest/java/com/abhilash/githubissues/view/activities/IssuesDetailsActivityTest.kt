package com.abhilash.githubissues.view.activities


import TestUtil.initData
import TestUtil.issuesList
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.abhilash.githubissues.R
import com.abhilash.githubissues.utils.AppConstants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
class IssuesDetailsActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun issuesDetailsActivity() {
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup() {
        initData()
        val intent: Intent = Intent().apply {
            putExtra(AppConstants.ISSUE_ITEM_KEY, issuesList.issuesList[0])
        }
        mActivityTestRule.launchActivity(intent)
    }

    @Test
    fun testIssueDetails() {
        //Assert Title
        Espresso.onView(withId(R.id.imageview_user_avator))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textview_user_name))
            .check(ViewAssertions.matches(withText(issuesList.issuesList[0].user.login)))
        //Assert Issues Description
        Espresso.onView(withId(R.id.textview_issue_description)).perform(ViewActions.scrollTo())
        Espresso.onView(withId(R.id.textview_issue_description))
            .check(ViewAssertions.matches(withText(issuesList.issuesList[0].body)))
        //Assert Issue Title
        Espresso.onView(withId(R.id.textview_issue_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textview_last_modified))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        mActivityTestRule.finishActivity()
    }
}
