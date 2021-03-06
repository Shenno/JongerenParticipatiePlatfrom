package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


public class RegisterFragment extends Fragment implements Callback<Account> {
    private EditText txtRegister;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtBirthdate;
    private EditText txtPassword;
    private EditText txtConfirmPwd;
    private EditText txtZipCode;
    private Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_up_screen, container, false);
        txtRegister = (EditText)rootView.findViewById(R.id.register);
        txtName = (EditText) rootView.findViewById(R.id.txtName);
        txtEmail = (EditText) rootView.findViewById(R.id.etEmail);
        txtPassword = (EditText) rootView.findViewById(R.id.txtSignUpPassword);
        txtConfirmPwd = (EditText) rootView.findViewById(R.id.txtConfirmSignUpPassword);
        btnRegister = (Button) rootView.findViewById(R.id.btnSingIn);
        txtBirthdate = (EditText) rootView.findViewById(R.id.txtBirthDay);
        txtZipCode = (EditText) rootView.findViewById(R.id.txtZipCode);
        initListeners();

        Typeface robto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface opensans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");

        txtName.setTypeface(opensans);
        txtEmail.setTypeface(opensans);
        txtPassword.setTypeface(opensans);
        txtConfirmPwd.setTypeface(opensans);
        txtZipCode.setTypeface(opensans);
        btnRegister.setTypeface(opensans);
        txtBirthdate.setTypeface(opensans);
        txtZipCode.setTypeface(opensans);
        txtRegister.setTypeface(robto);
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
                if (txtZipCode.getText().length() == 0) {
                    txtZipCode.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }

                if (!txtPassword.getText().toString().equals(txtConfirmPwd.getText().toString())) {
                    //controleren of pw en confirm pw overeenkomen, indien niet melding gegeven dat de PW's niet overeenkomen.
                    if (txtConfirmPwd.getText().length() == 0) {
                        txtConfirmPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                    } else {
                        txtConfirmPwd.setError(getString(R.string.PasswordsMisMatch));
                    }
                }

                if (!(txtName.getText().length() == 0 || txtEmail.getText().length() == 0 || txtPassword.getText().length() == 0 || txtBirthdate.getText().length() == 0 || txtConfirmPwd.getText().length() == 0 || txtZipCode.getText().length() == 0)) {
                    //Als alle velden een waarde bevatten wordt de account geregistreerd.
                    try {
                        registerAccount();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void registerAccount() throws ParseException {
        final Account acc = new Account();
        String birthDay = txtBirthdate.getText().toString();
        String regex = "^\\d{1,2}\\-\\d{1,2}\\-\\d{4}$";
        String regex2 = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$";

        Date date = null;

        if (birthDay.matches(regex)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = formatter.parse(birthDay);
        } else if (birthDay.matches(regex2)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(birthDay);
        } else {
            AppMsg.makeText(getActivity(), "Datum formaat = dd-mm-yyyy of dd/mm/yyyy", AppMsg.STYLE_ALERT).show();
            return;
        }

        acc.setFullName(txtName.getText().toString());
        acc.setEmail(txtEmail.getText().toString());
        acc.setBirthDate(date);
        acc.setZipCode(Integer.parseInt(txtZipCode.getText().toString()));
        acc.setPassword(txtPassword.getText().toString());
        acc.setConfirmPassword(txtConfirmPwd.getText().toString());

        //Lees alle gegevens uit de textfields, brengen deze samen in een Account en registreer deze account via de getJppService.
        getJppService().registerAccount(acc, this);
    }

    @Override
    public void success(Account account, Response response) {
        //Registratie succesvol? Geef melding weer en breng de user naar het LogInScherm zpdat hij daar kan inloggen.
        AppMsg.makeText(getActivity(), getResources().getString(R.string.succesfullregister), AppMsg.STYLE_INFO).show();
        Fragment frag = new LogInFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, frag);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        //controle op veel voorkomende fouten, is het passwoord lang genoeg? Email in juiste formaat, is het emailadres al eens geregistreerd?
        String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());

        if (json.equals(getResources().getString(R.string.retrofit_invalid_emailformat))) {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.emailAdresError), AppMsg.STYLE_ALERT).show();
        } else if (json.equals(getResources().getString(R.string.retrofit_password_minimum))) {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.minimum_password_length_error), AppMsg.STYLE_ALERT).show();
        } else if (json.equals("{\"Message\":\"The request is invalid.\",\"ModelState\":{\"\":[\"Name " + txtEmail.getText().toString() + "1 is already taken.\"]}}")) {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.email_already_registered), AppMsg.STYLE_ALERT).show();
        } else {
            AppMsg.makeText(getActivity(), getResources().getString(R.string.something_went_wrong) + " " + error, AppMsg.STYLE_ALERT).show();

        }

    }
}


