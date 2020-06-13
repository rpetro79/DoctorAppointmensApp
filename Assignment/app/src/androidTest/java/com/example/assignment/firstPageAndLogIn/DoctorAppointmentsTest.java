package com.example.assignment.firstPageAndLogIn;


import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.assignment.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DoctorAppointmentsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void doctorAppointmentsTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.doctorLogIn), withText("Doctor log in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("claired@nmb.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password), withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("Log in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        SystemClock.sleep(5000);

        ViewInteraction imageView = onView(
                allOf(withId(R.id.logoWelcomePage),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigation open"),
                        childAtPosition(
                                allOf(withId(R.id.myToolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction button = onView(
                allOf(withId(R.id.button5),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button5), withText("Select date"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

        SystemClock.sleep(1000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.dateTextDoctorViews)/*, withText("29/6/2020")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.appointmentsList),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentsListDoctor),
                                        0),
                                0),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.appointmentsList),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0)),
                        0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.appointmentsList),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        constraintLayout.perform(click());

        SystemClock.sleep(3000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.labelDoctorAppointmentDetails)/*, withText("ralu")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ageDoctorAppointmentDetails)/*, withText("Age: 21")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.symptomsDoctorAppointmentDetails)/*, withText("Symptoms: discussion")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.contactInfoTextDoctorAppointmentDetails)/*, withText("Contact")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                3),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.patientEmailDoctorAppointmentDetails)/*, withText("Email: ralu0135@spcmidt.dk")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                4),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.patientPhoneDoctorAppointmentDetails)/*, withText("Phone number: 12345678")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                5),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.dateTimeOfAppointmentDoctorAppointmentDetails)/*, withText("29/6/2020 10:00")*/,
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                6),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        pressBack();

        SystemClock.sleep(500);

        ViewInteraction button2 = onView(
                allOf(withId(R.id.button5),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.log_out_button), withContentDescription("Log out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myToolbarDoctorViews),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        SystemClock.sleep(2000);

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.logo), withContentDescription("What's up, doc?"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
