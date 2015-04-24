package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;
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
	
	public LogInFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_sign_in_screen, container, false);
        txtName = (EditText) rootView.findViewById(R.id.signInUsername);
        txtPwd = (EditText) rootView.findViewById(R.id.signInPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.signInBtn);
        initListeners();

        return rootView;
    }

    public void initListeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        getJppService().getToken("password", txtName.getText().toString(), txtPwd.getText().toString(), this);
    }

    @Override
    public void success(Token token, Response response) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("TokenHype", MODE_PRIVATE).edit();
        editor.putString("token", token.getAccess_token());
        editor.commit();
        SharedPreferences prefs = getActivity().getSharedPreferences("TokenHype", MODE_PRIVATE);
        String text = prefs.getString("token", null);
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
    }
}
