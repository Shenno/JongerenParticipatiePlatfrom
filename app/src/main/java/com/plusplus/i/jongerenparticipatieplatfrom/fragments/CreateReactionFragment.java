package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierPost;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoReactionPost;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 22/05/2015.
 */
public class CreateReactionFragment extends Fragment implements Callback<DtoReactionPost> {
    TextView tvTitle;
    EditText etAnswer;
    EditText etExtra;
    Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_createreaction, container, false);
        tvTitle = (TextView) rootView.findViewById(R.id.cdTitel);
        etAnswer = (EditText) rootView.findViewById(R.id.cdAnswer);
        etExtra = (EditText) rootView.findViewById(R.id.cdExtra);
        btnSubmit = (Button) rootView.findViewById(R.id.cdSubmit);
        initListeners();
        return rootView;
    }

    public void initListeners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDossier();
            }
        });
    }

    private void submitDossier() {
        DtoReactionPost dto = new DtoReactionPost();
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE);
        String text = prefs.getString("email", null);
        String token = prefs.getString("token", "");
        token = "Bearer " + token;
        dto.setEmail(text);
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dto.setsId(i);
        }
        dto.setAnswer(etAnswer.getText().toString());
        dto.setExtra(etExtra.getText().toString());
        getJppService().postReaction(token, dto, this);
    }

    @Override
    public void success(DtoReactionPost dtoReactionPost, Response response) {
        AppMsg.makeText(getActivity(), "Uw reactie is aangemaakt :)", AppMsg.STYLE_INFO).show();
        Fragment fragment = new MyReactionsFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}