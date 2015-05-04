package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoEvent;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoFixedQuestion;
import com.software.shell.fab.ActionButton;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 28/04/2015.
 */
public class DossierFragment extends Fragment implements Callback<DtoDossierDetailed> {
    LinearLayout rl;
    private TextView tUsername;
    private TextView tAnswer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dossier, container, false);
        rl = (LinearLayout) rootView.findViewById(R.id.linLay);
        tUsername = (TextView) rootView.findViewById(R.id.ddUsername);
        tAnswer = (TextView) rootView.findViewById(R.id.ddAnswer);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            getJppService().getDossier(i, this);
        }
    }

    @Override
    public void success(DtoDossierDetailed dtoDossierDetailed, Response response) {
        tUsername.setText("Username: " + dtoDossierDetailed.getUsername());
        tAnswer.setText("Answer: " + dtoDossierDetailed.getAnswer());
        if(dtoDossierDetailed.getExtra() != null)
        {
            TextView tv = new TextView(getActivity());
            tv.setText("Extra: " + dtoDossierDetailed.getExtra());
            rl.addView(tv);
        }

        if(dtoDossierDetailed.getLocation() != null)
        {
            TextView tv = new TextView(getActivity());
            tv.setText("Location: " + dtoDossierDetailed.getLocation());
            rl.addView(tv);
        }

        if(dtoDossierDetailed.getCalendar() != null)
        {
            for(DtoEvent d : dtoDossierDetailed.getCalendar())
            {
                TextView tv = new TextView(getActivity());
                tv.setText("Event title: " + d.getTitle());
                rl.addView(tv);
                TextView tv2 = new TextView(getActivity());
                tv2.setText("Event desc: " + d.getDescription());
                rl.addView(tv2);
                TextView tv3 = new TextView(getActivity());
                tv3.setText("Event date: " + d.getDate());
                rl.addView(tv3);
            }
        }

        if(dtoDossierDetailed.getFixedQuestion() != null)
        {
            for(DtoFixedQuestion d : dtoDossierDetailed.getFixedQuestion())
            {
                TextView q = new TextView(getActivity());
                q.setText("Fixed q: " + d.getQuestion());
                rl.addView(q);
              TextView a = new TextView(getActivity());
                a.setText("Fixed a: " + d.getAnswer());
                rl.addView(a);
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(),"niet goed",Toast.LENGTH_LONG).show();
    }
}
