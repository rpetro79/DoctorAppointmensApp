package com.example.assignment.firstPageAndLogIn;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.assignment.R;
import com.example.assignment.createAccountActivityANdVM.CreateAccount;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.logo), withContentDescription("What's up, doc?"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.welcomeText), withText("Set an appointment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.doctorLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.patientLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        button.perform(click());
        ViewInteraction logInEmail = onView(
                allOf(withId(R.id.emailLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        logInEmail.check(matches(isDisplayed()));
    }

    @Test
    public void testLogInDoctor()
    {
        ViewInteraction button = onView(
                allOf(withId(R.id.doctorLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                1),
                        isDisplayed()));

        button.perform(click());
        ViewInteraction logInEmail = onView(
                allOf(withId(R.id.emailLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        logInEmail.check(matches(isDisplayed()));
    }

    @Test
    public void testLogInPatient()
    {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.patientLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                2),
                        isDisplayed()));

        button2.perform(click());
        ViewInteraction logInEmail = onView(
                allOf(withId(R.id.emailLogIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                0),
                        isDisplayed()));
        logInEmail.check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccount() {
        Intents.init();
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        textView2.perform(click());
        intended(hasComponent(CreateAccount.class.getName()));
        Intents.release();
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
