package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Token;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


public class LogInFragment extends Fragment implements Callback<Token> {
    public EditText txtName;
    private EditText txtPwd;
    private Button btnSignIn;
    private boolean alreadyLoggedIn;

    public LogInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_in_screen, container, false);
        txtName = (EditText) rootView.findViewById(R.id.signInUsername);
        txtPwd = (EditText) rootView.findViewById(R.id.signInPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.signInBtn);
        initListeners();
        initUsernameAndPassWordFields();

        Typeface fontttype = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");

        txtName.setTypeface(fontttype);
        txtPwd.setTypeface(fontttype);




        return rootView;
    }

    public void initUsernameAndPassWordFields() {
        SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String email = userDetails.getString("email", "");
        String password = userDetails.getString("password", "");

        //Haalt in de SP het email & PW op zodat deze gegevens al ingevuld kunnen worden op beide textFields in het inlogscherm.

        if (!email.isEmpty()) {
            txtName.setText(email);
        }
        if (!password.isEmpty()) {
            txtPwd.setText(password);
        }
    }


    public void initListeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Bij het klikken op de knop wordt er gekeken of de email en pw van de gebruiker nog in de Sharedprefence zit.
                Staat hier niets in, dan is de gebruiker niet aangelogd. ==> We controleren dan elke veld om te zien of deze een waarde bevat. Zoja probeer dan in te loggen.
                Staat in de Sharedpref wel een waarde dan is de gebruiker al inglogd. Probeert hij opnieuw in te loggen met de gegevens die al in de sharedpref staan dan krijgt hij een melding dat hij al aangmeld is
                Probeert hij in te loggen met andere gegevens dan in de sharedpref staan dan probeert het systeem gewoon in te loggen.
                Zijn de inlog gegevens verkeerd dan wordt er een melding weergegeven.
                 */
                SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
                String email = userDetails.getString("email", ""); // email uit de SP ophalen
                String password = userDetails.getString("password", ""); //pw uit de SP ophalen

                if (txtName.getText().length() == 0) {
                    txtName.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtPwd.getText().length() == 0) {
                    txtPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }

                if (email.equalsIgnoreCase(txtName.getText().toString()) && password.equals(txtPwd.getText().toString())) { // Email uit de SP zijn hetzelfde als ingegeven email? ==> Al ingelogd
                    alreadyLoggedIn = true;
                } else {
                    alreadyLoggedIn = false;
                }

                if (email.isEmpty() || password.isEmpty()) { // is de sharedpref leeg? Dan kan er niemand ingelogd zijn.
                    alreadyLoggedIn = false;
                }


                if (alreadyLoggedIn) { //melding geven als de gebruiker al ingelogd is en nog eens probeert in te loggen met dezelfde gegevens
                    AppMsg.makeText(getActivity(), getResources().getString(R.string.already_logged_in_as) + " " + email, AppMsg.STYLE_ALERT).show();

                } else {
                    if (!(txtName.getText().length() == 0 || txtPwd.getText().length() == 0)) { // Als beide velden een waarde bevatten mag er een inlog poging gebeuren.
                        signIn();
                    }
                }
            }

        });
    }

    private void signIn() {
        getJppService().getToken("password", txtName.getText().toString() + 1, txtPwd.getText().toString(), this);
    }

    @Override
    public void success(Token token, Response response) {
        //Als de login succes vol is, zet dan de token, email en password in een sharedPrefence zodat deze gegevens later kunnen worden opgehaald.
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE).edit();
        editor.putString("token", token.getAccess_token());
        editor.putString("email", txtName.getText().toString());
        editor.putString("password", txtPwd.getText().toString());
        editor.apply();
        AppMsg.makeText(getActivity(), getResources().getString(R.string.successfullLogin) + " " + txtName.getText().toString(), AppMsg.STYLE_INFO).show();

        //Als het inloggen succesvol is gebeurd wissel dan naar het QuestionFragment (lijst met vragen)
        Fragment frag = new QuestionFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, frag);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        //Gaat het inloggen verkeerd, vang dan de json error code die retrofit teruggeeft. Match de json code aan gekende errors en geef gepaste feedback.
        String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());

        if (json.equals(getResources().getString(R.string.retrofit_username_or_password_wrong_json))) {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.username_or_password_wrong), AppMsg.STYLE_ALERT).show();
        } else {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.something_went_wrong) + error, AppMsg.STYLE_ALERT).show();

        }


    }
}
