package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

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

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierPost;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 4/05/2015.
 */
public class CreateDossierFragment extends Fragment implements Callback<DtoDossierPost> {
    TextView tvTitle;
    EditText etAnswer;
    Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_createdossier, container, false);
        tvTitle = (TextView) rootView.findViewById(R.id.cdTitel);
        etAnswer = (EditText) rootView.findViewById(R.id.cdAnswer);
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
        DtoDossierPost dtoDossierPost = new DtoDossierPost();
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE);
        String text = prefs.getString("email", null);
        dtoDossierPost.setUserId(text);
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoDossierPost.setDmsId(i);
        }
        dtoDossierPost.setAnswer(etAnswer.getText().toString());
        String token = prefs.getString("token","");
        token = "Bearer " + token;
        getJppService().postDossier(token, dtoDossierPost, this);
    }

    @Override
    public void success(DtoDossierPost dtoDossierPost, Response response) {
        AppMsg.makeText(getActivity(), "Uw dossier is aangemaakt :)", AppMsg.STYLE_INFO).show();
        Fragment fragment = new MyDossiersFragment();
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
