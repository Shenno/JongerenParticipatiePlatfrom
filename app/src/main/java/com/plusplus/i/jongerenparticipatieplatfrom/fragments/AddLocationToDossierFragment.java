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

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddExtra;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddLocation;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 4/05/2015.
 */
public class AddLocationToDossierFragment extends Fragment implements Callback<DtoAddLocation> {
    EditText location;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addlocationtodossier, container, false);
        location = (EditText) rootView.findViewById(R.id.alExtra);
        button = (Button) rootView.findViewById(R.id.alSubmit);
        initListeners();
        return rootView;
    }

    public void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLocation();
            }
        });
    }

    private void submitLocation() {
        DtoAddLocation dtoAddLocation = new DtoAddLocation();
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoAddLocation.setdId(i);
        }
        dtoAddLocation.setLocation(location.getText().toString());
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE);
        String token = prefs.getString("token","");
        token = "Bearer " + token;
        getJppService().addLocationToDossier(token, dtoAddLocation, this);
    }

    @Override
    public void success(DtoAddLocation dtoAddLocation, Response response) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}
