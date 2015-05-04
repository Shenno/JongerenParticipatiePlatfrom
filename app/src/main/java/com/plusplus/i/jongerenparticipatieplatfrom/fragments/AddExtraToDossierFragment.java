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
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddExtra;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierPost;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 4/05/2015.
 */
public class AddExtraToDossierFragment extends Fragment implements Callback<DtoAddExtra> {
    EditText extra;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addextratodossier, container, false);
        extra = (EditText) rootView.findViewById(R.id.aeExtra);
        button = (Button) rootView.findViewById(R.id.aeSubmit);
        initListeners();
        return rootView;
    }

    public void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitExtra();
            }
        });
    }

    private void submitExtra() {
        DtoAddExtra dtoAddExtra = new DtoAddExtra();
        dtoAddExtra.setdID(3);
        dtoAddExtra.setExtra(extra.getText().toString());
        getJppService().addExtraToDossier(dtoAddExtra, this);
    }

    @Override
    public void success(DtoAddExtra dtoAddExtra, Response response) {
        Fragment fragment = new EditDossierFragment();
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
