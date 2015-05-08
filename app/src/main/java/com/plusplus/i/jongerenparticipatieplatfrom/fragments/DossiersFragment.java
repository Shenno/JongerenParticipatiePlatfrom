package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.DmsAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.DossierAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossier;
import com.software.shell.fab.ActionButton;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 28/04/2015.
 */
public class DossiersFragment extends Fragment implements Callback<List<DtoDossier>> {
    private DossierAdapter dossierAdapter;
    private OnSelectedListener mCallback;
    ActionButton actionButtonNewDossier;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dossierAdapter = new DossierAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_dossiers, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.dList);
        title = (TextView) rootView.findViewById(R.id.dTitle);
        if(getArguments() != null) {
            Bundle b = getArguments();
            String i = b.getString("answer");
            title.setText(i);
        }
        listView.setAdapter(dossierAdapter);
        View emptyView = rootView.findViewById(R.id.dEmpty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoDossier dto = dossierAdapter.getItem(arg2);
                mCallback.onDossierItemClicked(dto.getId());
            }

        });
        actionButtonNewDossier = (ActionButton) rootView.findViewById(R.id.dsNewDossier);
        actionButtonNewDossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() != null) {
                    Bundle b = getArguments();
                    int i = b.getInt("dId");
                    mCallback.onNewDossierClicked(i);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            getJppService().getDossiers(i, this);
        }
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
        Toast.makeText(getActivity(), "Fout", Toast.LENGTH_LONG).show();
    }
}
