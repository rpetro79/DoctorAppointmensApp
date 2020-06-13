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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
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
public class MakeAppointmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void makeAppointmentTest() {
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

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.log_out_button), withContentDescription("Log out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myToolbar),
                                        2),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.logoWelcomePage),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.logoWelcomePage),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.topTextSearchDoctors), withText("Search doctors"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.topTextSearchDoctors), withText("Search doctors"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button2), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.doctorList),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.doctorNameDoctorView), withText("Claire Duchon"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Claire Duchon")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.aboutTextDoctorView), withText("About"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("About")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.contactTextDoctorView), withText("Contact"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                4),
                        isDisplayed()));
        textView6.check(matches(withText("Contact")));

        ViewInteraction button = onView(
                allOf(withId(R.id.button3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                7),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.clinicNameDoctorView), withText("Clinic: So numb"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                2),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.addressDoctorView), withText("Address: Lindealle 53, Horsens, Denmark"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                3),
                        isDisplayed()));
        textView8.check(matches(withText("Address: Lindealle 53, Horsens, Denmark")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.addressDoctorView), withText("Address: Lindealle 53, Horsens, Denmark"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                3),
                        isDisplayed()));
        textView9.check(matches(isDisplayed()));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.emailDoctorView), withText("Email: claired@nmb.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                5),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.phoneDoctorView), withText("Phone number: 123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                6),
                        isDisplayed()));
        textView11.check(matches(isDisplayed()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button3), withText("Make appointment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.selectAppointmentDate), withText("Select date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.selectAppointmentDate),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                1),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.appointmentDate), withText("13/6/2020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                2),
                        isDisplayed()));
        textView12.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.label), withText("Anesthesiologist appointment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                7),
                        isDisplayed()));
        editText.check(matches(withText("Anesthesiologist appointment")));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.doneButtonMakeAppointment),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                9),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.doneButtonMakeAppointment), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.errorMessageMakeAppointment), withText("Invalid input"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                8),
                        isDisplayed()));
        textView13.check(matches(withText("Invalid input")));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.symptoms),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("symptoms"), closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.doneButtonMakeAppointment), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.simpleViewFragment),
                                        0),
                                9),
                        isDisplayed()));
        appCompatButton8.perform(click());
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
