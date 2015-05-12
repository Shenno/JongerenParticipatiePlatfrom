package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
                if (txtName.getText().length() == 0) {
                    txtName.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtPwd.getText().length() == 0) {
                    txtPwd.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }

                if (!(txtName.getText().length() == 0 || txtPwd.getText().length() == 0)){
                    signIn();
                }

                //TODO Als de user foute username gebruik of foute pw de error code van retrofit opvangen en error weergeven in de textfields
            }
        });
    }

    private void signIn() {
        getJppService().getToken("password", txtName.getText().toString()+1, txtPwd.getText().toString(), this);
    }

    @Override
    public void success(Token token, Response response) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE).edit();
        editor.putString("token", token.getAccess_token());
        editor.putString("email", txtName.getText().toString());
        editor.commit();
        AppMsg.makeText(getActivity(),"Succesvol ingelogd! :)",AppMsg.STYLE_INFO).show();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), error.toString(), AppMsg.STYLE_ALERT).show();

    }
}
