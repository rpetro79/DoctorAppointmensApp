package com.example.assignment.firstPageAndLogIn;


import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.assignment.R;
import com.example.assignment.createAccountActivityANdVM.CreateAccount;
import com.example.assignment.createAccountActivityANdVM.PatientCreateAccount;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateAccountTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createAccountTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.topText), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameText), withText("Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ssnText), withText("SSN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.emailText), withText("E-mail address"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.phoneNoText), withText("Phone number"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.countryText), withText("Country"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.regionText), withText("Region"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.cityText), withText("City"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));



        ViewInteraction editText2 = onView(
                allOf(withId(R.id.ssnCreateAccount),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));



        ViewInteraction editText3 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));



        ViewInteraction editText4 = onView(
                allOf(withId(R.id.phoneNo),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                11),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction spinner = onView(
                allOf(withId(R.id.country),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                12),
                        isDisplayed()));
        spinner.check(matches(isDisplayed()));

        ViewInteraction spinner2 = onView(
                allOf(withId(R.id.region),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                13),
                        isDisplayed()));
        spinner2.check(matches(isDisplayed()));

        ViewInteraction spinner3 = onView(
                allOf(withId(R.id.city),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()));
        spinner3.check(matches(isDisplayed()));

        ViewInteraction radioGroup = onView(
                allOf(withId(R.id.type),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                15),
                        isDisplayed()));
        radioGroup.check(matches(isDisplayed()));

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.patientRadioButton),
                        childAtPosition(
                                allOf(withId(R.id.type),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                15)),
                                0),
                        isDisplayed()));
        radioButton.check(matches(isDisplayed()));

        ViewInteraction radioButton2 = onView(
                allOf(withId(R.id.doctorRadioButton),
                        childAtPosition(
                                allOf(withId(R.id.type),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                                15)),
                                1),
                        isDisplayed()));
        radioButton2.check(matches(isDisplayed()));

        ViewInteraction textViewx = onView(
                allOf(withId(R.id.createAccountStep1Error)/*, withText("")*/,
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                16),
                        Matchers.not(isDisplayed())));
        textViewx.check(matches(Matchers.not(isDisplayed())));

        ViewInteraction button = onView(
                allOf(withId(R.id.button2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                17),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void nextStepEmptyFields()
    {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.button2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                17),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        button.perform(click());

        ViewInteraction textViewx = onView(
                allOf(withId(R.id.createAccountStep1Error), withText("All fields are required"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                16),
                        isDisplayed()));
        textViewx.check(matches(isDisplayed()));
    }

    @Test
    public void nextStepBadInput()
    {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
        editText.perform(typeText("Raluu"));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.ssnCreateAccount),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        editText2.perform(typeText("2154"));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));
        editText3.perform(typeText("laa"));



        ViewInteraction editText4 = onView(
                allOf(withId(R.id.phoneNo),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                11),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));
        editText4.perform(typeText("123456"));

        ViewInteraction button = onView(
                allOf(withId(R.id.button2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                17),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        pressBack();
        button.perform(click());

        SystemClock.sleep(500);

        ViewInteraction textViewx = onView(
                allOf(withId(R.id.createAccountStep1Error), withText("Invalid input"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                16),
                        isDisplayed()));
        textViewx.check(matches(isDisplayed()));
    }

    @Test
    public void nextStep()
    {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.createAccount), withText("Create account"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));
        editText.perform(typeText("Raluu"));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.ssnCreateAccount),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        editText2.perform(typeText("123456"));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));
        editText3.perform(typeText("laa@aa.aa"));



        ViewInteraction editText4 = onView(
                allOf(withId(R.id.phoneNo),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                11),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));
        editText4.perform(typeText("123456"));

        ViewInteraction button = onView(
                allOf(withId(R.id.button2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                17),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
        pressBack();
        SystemClock.sleep(500);
        Intents.init();
        button.perform(click());
        intended(hasComponent(PatientCreateAccount.class.getName()));
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
