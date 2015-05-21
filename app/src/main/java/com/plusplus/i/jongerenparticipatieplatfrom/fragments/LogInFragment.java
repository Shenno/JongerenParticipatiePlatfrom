package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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
import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


public class LogInFragment extends Fragment implements Callback<Token> {
    private EditText txtName;
    private EditText txtPwd;
    private Button btnSignIn;

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
                SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
                String email = userDetails.getString("email", ""); // email uit de SP ophalen
                String password = userDetails.getString("password", ""); //password uit de SP ophalen

                if (email.equalsIgnoreCase(txtName.getText().toString()) && password.equals(txtPwd.getText().toString())) { //Pw & Email uit de SP zijn hetzelfde als ingegeven PW en email? ==> Al ingelogd
                    AppMsg.makeText(getActivity(), "Je bent al ingelogd als " + email, AppMsg.STYLE_ALERT).show();

                } else {

                    if (txtName.getText().length() == 0) {
                        txtName.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                    }
                    if (txtPwd.getText().length() == 0) {
                        txtPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                    }

                    if (!(txtName.getText().length() == 0 || txtPwd.getText().length() == 0)) { // Als beide velden een waarde bevatten mag er een inlog poging gebeuren.
                        signIn();
                    }

                    //TODO Als de user foute username gebruik of foute pw de error code van retrofit opvangen en error weergeven in de textfields
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
        AppMsg.makeText(getActivity(), "Succesvol ingelogd! :)", AppMsg.STYLE_INFO).show();

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
        AppMsg.makeText(getActivity(), error.getMessage(), AppMsg.STYLE_ALERT).show();


    }
}
