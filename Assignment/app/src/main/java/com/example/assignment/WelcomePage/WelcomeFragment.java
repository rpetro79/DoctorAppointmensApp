package com.example.assignment.WelcomePage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WelcomeFragment extends Fragment {

    private String name;
    private boolean fabVisibility = false;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance(String name) {
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setName(name);
        return fragment;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        TextView tv = view.findViewById(R.id.newId);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        if(fabVisibility)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.INVISIBLE);
        tv.setText(getResources().getString(R.string.welcomeText) + " " + name );
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setFabVisibility(boolean visibility)
    {
        fabVisibility = visibility;
    }
}
