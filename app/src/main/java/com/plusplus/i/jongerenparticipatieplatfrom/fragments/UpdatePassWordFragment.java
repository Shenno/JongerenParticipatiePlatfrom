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

import com.plusplus.i.jongerenparticipatieplatfrom.R;

public class UpdatePassWordFragment extends Fragment {
    private Button button;
    private EditText txtCurrentPassword, txtNewPassword, txtConfirmNewPassword;


    public UpdatePassWordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_change_password_screen, container, false);

        button = (Button) rootView.findViewById(R.id.btnConfirmPasswordChange);
        txtCurrentPassword = (EditText) rootView.findViewById(R.id.txtCurrentPassword);
        txtNewPassword = (EditText) rootView.findViewById(R.id.txtNewPassword);
        txtConfirmNewPassword = (EditText) rootView.findViewById(R.id.txtConfirmNewPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtCurrentPassword.getText().length() == 0) {
                    txtCurrentPassword.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtNewPassword.getText().length() == 0) {
                    txtNewPassword.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if (txtConfirmNewPassword.getText().length() == 0) {
                    txtConfirmNewPassword.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                }
                if(!txtNewPassword.getText().equals(txtConfirmNewPassword.getText())){
                    if (txtConfirmNewPassword.getText().length()==0){
                        txtConfirmNewPassword.setError(getString(R.string.EmptyTextFieldCannotBeEmpty));
                    }else{
                        txtConfirmNewPassword.setError(getString(R.string.PasswordsMisMatch));
                    }

                }

                if (!(txtCurrentPassword.getText().length() == 0 || txtNewPassword.getText().length() == 0 || txtConfirmNewPassword.getText().length() == 0)){
                    Fragment frag = new MyProfileFragment();
                    FragmentManager fragMan = getFragmentManager();
                    FragmentTransaction fragTran = fragMan.beginTransaction();
                    fragTran.replace(R.id.frame_container, frag);
                    fragTran.addToBackStack(null);
                    fragTran.commit();
                }
            }
        });


        return rootView;
    }


}
