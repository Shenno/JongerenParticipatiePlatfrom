package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.software.shell.fab.ActionButton;

/**
 * Created by Maxim on 21/04/2015.
 */
public class UserReactionFragment extends Fragment {

    ActionButton actionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.reactiegebruiker, container, false);



        actionButton= (ActionButton) rootView.findViewById(R.id.action_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"A unicorn was born!",Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }
}