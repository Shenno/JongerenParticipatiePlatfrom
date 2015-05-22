package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.DossierAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossier;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 22/05/2015.
 */
public class MyDossiersFragment extends Fragment implements Callback<List<DtoDossier>> {
    private DossierAdapter dossierAdapter;
    private OnSelectedListener mCallback;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dossierAdapter = new DossierAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_mydossiers, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.dList);
        title = (TextView) rootView.findViewById(R.id.dTitle);
        title.setText("Mijn dossiers");
        listView.setAdapter(dossierAdapter);
        View emptyView = rootView.findViewById(R.id.dEmpty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoDossier dto = dossierAdapter.getItem(arg2);
                mCallback.onEditDossierClicked(dto.getId());
            }

        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String mail = userDetails.getString("email", "dummy@email.be");
        String token = userDetails.getString("token","");
        token = "Bearer " + token;
        getJppService().getDossiersByEmail(token, mail, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void success(List<DtoDossier> dtoDossiers, Response response) {
        dossierAdapter.setDossierList(dtoDossiers);
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}