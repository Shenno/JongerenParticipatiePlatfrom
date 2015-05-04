package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierPost;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
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
        dtoDossierPost.setUserId("test");
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoDossierPost.setDmsId(i);
        }
        dtoDossierPost.setAnswer(etAnswer.getText().toString());
        getJppService().postDossier(dtoDossierPost, this);
    }

    @Override
    public void success(DtoDossierPost dtoDossierPost, Response response) {
        Toast.makeText(getActivity(), "JOEPIE", Toast.LENGTH_LONG).show();
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "NIET JOEPIE", Toast.LENGTH_LONG).show();

    }
}
