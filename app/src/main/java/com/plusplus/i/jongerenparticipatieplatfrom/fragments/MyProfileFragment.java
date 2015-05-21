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

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoUserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyProfileFragment extends Fragment implements Callback<DtoUserInfo> {
    private Button updatePassword;
    private Button logOut;
    private EditText name;
    private EditText email2;
    private EditText gemeente;
    private EditText birthDate;

    public MyProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);

        name = (EditText) rootView.findViewById(R.id.txtName);
        email2 = (EditText) rootView.findViewById(R.id.txtEmail);
        gemeente = (EditText) rootView.findViewById(R.id.txtGemeente);
        birthDate = (EditText) rootView.findViewById(R.id.txtGeboorteDatum);


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
                    AppMsg.makeText(getActivity(), getResources().getString(R.string.alreadyloggedOut), AppMsg.STYLE_ALERT).show();

                } else {
                    userDetails.edit().remove("token").apply();
                    userDetails.edit().remove("email").apply();
                    userDetails.edit().remove("password").apply();

                    AppMsg.makeText(getActivity(), getResources().getString(R.string.succesfullLogOut), AppMsg.STYLE_INFO).show();

                }

                // maak alle textfields leeg op de mijn profiel fragment
                name.setText("");
                email2.setText("");
                gemeente.setText("");
                birthDate.setText("");
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
            AppMsg.makeText(getActivity(), getResources().getString(R.string.profileDateWillAppearAfterLogin), AppMsg.STYLE_INFO).show();
        } else {
            getJppService().getUserInfo(email, this);
        }
        //TODO: Als de SP gegevens bevat, de account opvragen met (HOE? getaccount methode maken?) Dan de gegevens van deze account (naam, email, pw, geboortedatum en gemeente plaatsen in de textfields
    }


    @Override
    public void success(DtoUserInfo dtoUserInfo, Response response) {
        AppMsg.makeText(getActivity(), "Hier init je alles he", AppMsg.STYLE_INFO).show();
        //TODO fonttypes



        name.setText(dtoUserInfo.getName());
        email2.setText(dtoUserInfo.getEmail());
        gemeente.setText(Integer.toString(dtoUserInfo.getZip()));
        Date geboortedatum = dtoUserInfo.getBday();

        SimpleDateFormat formatter5=new SimpleDateFormat("dd-MM-yyyy");
        String formats1 = formatter5.format(geboortedatum);
        System.out.println(formats1);

        birthDate.setText(formats1);

    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "Foutje he", AppMsg.STYLE_INFO).show();

    }
}
