package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 4/05/2015.
 */
public class AddExtraToDossierFragment extends Fragment implements Callback<DtoAddExtra> {
    private EditText extra;
    private Button button;

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
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoAddExtra.setdID(i);
        }
        dtoAddExtra.setExtra(extra.getText().toString());
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE);
        String token = prefs.getString("token","");
        token = "Bearer " + token;
        getJppService().addExtraToDossier(token, dtoAddExtra, this);
    }

    @Override
    public void success(DtoAddExtra dtoAddExtra, Response response) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}
