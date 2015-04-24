package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;

public class UpdatePassWordFragment extends Fragment {

    public UpdatePassWordFragment() {
        // DIt is een test
        //test2
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_change_password_screen, container, false);
        return rootView;
    }



}
