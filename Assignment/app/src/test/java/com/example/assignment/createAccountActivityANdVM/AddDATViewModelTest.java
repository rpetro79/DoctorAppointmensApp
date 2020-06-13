package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

public class AddDATViewModelTest {

    private AddDATViewModel viewModel;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Application application;

    @Before
    public void setup()
    {
        viewModel = new AddDATViewModel(application);
    }

    @Test
    public void testAddDATCorrect()
    {
        assertTrue(viewModel.addTime("12", "00"));
    }

    @Test
    public void testAddDATHourWrong()
    {
        assertFalse(viewModel.addTime("a2", "00"));
    }

    @Test
    public void testAddDATMinuteWrong()
    {
        assertFalse(viewModel.addTime("12", "i0"));
    }
}