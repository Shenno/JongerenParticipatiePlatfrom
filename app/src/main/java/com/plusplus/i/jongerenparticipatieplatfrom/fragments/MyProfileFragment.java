package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;

public class MyProfileFragment extends Fragment {
    private Button updatePassword;
    private Button logOut;


    public MyProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);


        updatePassword = (Button) rootView.findViewById(R.id.btnUpdatePassword);

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new UpdatePassWordFragment();
                FragmentManager fragMan = getFragmentManager();
                FragmentTransaction fragTran = fragMan.beginTransaction();
                fragTran.replace(R.id.frame_container, frag);
                fragTran.addToBackStack(null);
                fragTran.commit();
            }
        });

        logOut = (Button) rootView.findViewById(R.id.btnSignOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);

                String token = userDetails.getString("token", "");
                String email = userDetails.getString("email", "");


                if (!token.equalsIgnoreCase("") || !email.equalsIgnoreCase("")) // als er iets in de token zit, wis het dan!
                {

                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putString("token", null);
                    editor.putString("email", null);
                    editor.apply();
                    AppMsg.makeText(getActivity(), "Succesvol uitgelogd", AppMsg.STYLE_ALERT).show();


                } else {
                    AppMsg.makeText(getActivity(), "Je bent al uitgelogd", AppMsg.STYLE_ALERT).show();

                }


            }
        });


        return rootView;
    }


}
