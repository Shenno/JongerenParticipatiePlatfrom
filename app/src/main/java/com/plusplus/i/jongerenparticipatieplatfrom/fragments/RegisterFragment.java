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

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


public class RegisterFragment extends Fragment implements Callback<Account> {
	
	private EditText txtName;
    private EditText txtEmail;
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
        initListeners();
        return rootView;
    }

    public void initListeners() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
    }

    private void registerAccount() {
       // txtName.setEnabled(false);
       // txtEmail.setEnabled(false);
       // txtPassword.setEnabled(false);
       // txtConfirmPwd.setEnabled(false);

        final Account acc = new Account();
        acc.setUserName(txtName.getText().toString());
        acc.setEmail(txtEmail.getText().toString());
        acc.setPassword(txtPassword.getText().toString());
        acc.setConfirmPassword(txtConfirmPwd.getText().toString());
        getJppService().registerAccount(acc, this);
    }

    @Override
    public void success(Account account, Response response) {
        Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();
        Fragment frag = new LogInFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, frag);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
    }
}