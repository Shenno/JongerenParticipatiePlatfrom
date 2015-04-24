package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plusplus.i.jongerenparticipatieplatfrom.R;

public class MyProfileFragment extends Fragment {
    private Button button;

    public MyProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);


        button = (Button) rootView.findViewById(R.id.btnUpdatePassword);



        return rootView;
    }


}
