package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;


import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


public class RegisterFragment extends Fragment implements Callback<Account> {

    private EditText txtName;
    private EditText txtEmail;
    private EditText txtBirthdate;
    private EditText txtPassword;
    private EditText txtConfirmPwd;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_up_screen, container, false);
        txtName = (EditText) rootView.findViewById(R.id.txtName);
        txtEmail = (EditText) rootView.findViewById(R.id.etEmail);
        txtPassword = (EditText) rootView.findViewById(R.id.txtSignUpPassword);
        txtConfirmPwd = (EditText) rootView.findViewById(R.id.txtConfirmSignUpPassword);
        btnRegister = (Button) rootView.findViewById(R.id.btnSingIn);
        txtBirthdate = (EditText) rootView.findViewById(R.id.txtBirthDay);
        initListeners();
        return rootView;
    }


    public void initListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controle van alle velden (mogen niet leeg zijn)
                if (txtName.getText().length() == 0) {
                    txtName.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtEmail.getText().length() == 0) {
                    txtEmail.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtPassword.getText().length() == 0) {
                    txtPassword.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtConfirmPwd.getText().length() == 0) {
                    txtConfirmPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtBirthdate.getText().length() == 0) {
                    txtBirthdate.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }

                if (!txtPassword.getText().toString().equals(txtConfirmPwd.getText().toString())) {
                    //controleren of pw en confirm pw overeenkomen, indien niet melding gegeven dat de PW's niet overeenkomen.
                    if (txtConfirmPwd.getText().length() == 0) {
                        txtConfirmPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                    } else {
                        txtConfirmPwd.setError(getString(R.string.PasswordsMisMatch));
                    }
                }

                if (!(txtName.getText().length() == 0 || txtEmail.getText().length() == 0 || txtPassword.getText().length() == 0 || txtBirthdate.getText().length() == 0 || txtConfirmPwd.getText().length() == 0)) {
                    //Als alle velden een waarde bevatten wordt de account geregistreerd.
                    registerAccount();

                }

            }
        });
    }

    private void registerAccount() {
        final Account acc = new Account();
        acc.setFullName(txtName.getText().toString());
        acc.setEmail(txtEmail.getText().toString());
        acc.setBirthDate(new Date());
        acc.setPassword(txtPassword.getText().toString());
        acc.setConfirmPassword(txtConfirmPwd.getText().toString());

        //Lees alle gegevens uit de textfields, brengen deze samen in een Account en registreer deze account via de getJppService.
        getJppService().registerAccount(acc, this);
    }

    @Override
    public void success(Account account, Response response) {
        //Registratie succesvol? Geef melding weer en breng de user naar het LogInScherm zpdat hij daar kan inloggen.
        AppMsg.makeText(getActivity(), "Succesvol geregistreerd", AppMsg.STYLE_INFO).show();
        Fragment frag = new LogInFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, frag);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "Er is iets mis gegaan :(", AppMsg.STYLE_ALERT).show();
    }
}
