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
public class PastAppointmentsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void pastAppointmentsTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.patientLogIn), withText("Patient log in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
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
        appCompatEditText.perform(replaceText("ralu0135@spcmidt.dk"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.emailLogIn), withText("ralu0135@spcmidt.dk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password), withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("Log in"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());

        SystemClock.sleep(1000);

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
                        3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.appointmentsList),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
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

        ViewInteraction textView = onView(
                allOf(withId(R.id.labelAppointmentDetails), withText("Dentist appointment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.datetimeAppointmentDetails), withText("23/5/2020 02:32"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.doctorNameAppointmentDetails), withText("Doctor: John Smith"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.clinicAppointmentDetails), withText("Clinic: Smith's smile center"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.addressAppointmentDetails), withText("Address: Sundvej 25, Horsens, Denmark"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                4),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.symptomsAppointmentDetails), withText("Symptoms: gg"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.appointmentContainer),
                                        0),
                                5),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        pressBack();
        pressBack();
        SystemClock.sleep(3000);

        ViewInteraction imageView = onView(
                allOf(withId(R.id.logoWelcomePage),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
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
