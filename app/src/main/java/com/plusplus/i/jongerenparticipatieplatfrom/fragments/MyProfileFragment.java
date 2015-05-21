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
        initTextFields();
        updatePassword = (Button) rootView.findViewById(R.id.btnUpdatePassword);

        //Wissel naar het fragment op het password te updaten
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

                //Vraag de SP op. Leeg? ==> Al uitglogd. Niet leeg? Verwijder de prefs en geef bericht succesvol uitgelogd.
                SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
                String token = userDetails.getString("token", "");
                String email = userDetails.getString("email", "");

                if (token.isEmpty() && email.isEmpty()) {
                    AppMsg.makeText(getActivity(), "Je bent al uitgelogd", AppMsg.STYLE_ALERT).show();

                } else {
                    userDetails.edit().remove("token").apply();
                    userDetails.edit().remove("email").apply();
                    userDetails.edit().remove("password").apply();

                    AppMsg.makeText(getActivity(), "Je bent succesvol uitgelogd", AppMsg.STYLE_INFO).show();

                }

            }
        });

        return rootView;
    }

    public void initTextFields() {
        //bevat de SP gegevens (maw er is iemand ingelogd) vraag dan de account gegevens op en geef deze weer in de textfields. Is de SP leeg, geef dan een melding dat de gegevens zichtbaar worden na inloggen.
        SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String token = userDetails.getString("token", "");
        String email = userDetails.getString("email", "");

        if (token.isEmpty() && email.isEmpty()) {
            AppMsg.makeText(getActivity(), "Je profielgegevens verschijnen na het inloggen (TODO)", AppMsg.STYLE_ALERT).show();
        }

        //TODO: Als de SP gegevens bevat, de account opvragen met (HOE? getaccount methode maken?) Dan de gegevens van deze account (naam, email, pw, geboortedatum en gemeente plaatsen in de textfields
    }


}
