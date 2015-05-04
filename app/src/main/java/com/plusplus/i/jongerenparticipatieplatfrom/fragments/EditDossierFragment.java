package com.plusplus.i.jongerenparticipatieplatfrom.fragments;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoEvent;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoFixedQuestion;

import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 4/05/2015.
 */
public class EditDossierFragment extends Fragment implements Callback<DtoDossierDetailed> {
    OnSelectedListener mCallback;
    TextView username;
    TextView answer;
    LinearLayout rl;
    Spinner spinner;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_editdossier, container, false);
        username = (TextView) rootView.findViewById(R.id.edUsername);
        answer = (TextView) rootView.findViewById(R.id.edAnswer);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        button = (Button) rootView.findViewById(R.id.edBtn);
        rl = (LinearLayout) rootView.findViewById(R.id.edLinLayouy);
        List<String> list = new ArrayList<>();
        list.add("Extra");
        list.add("Locatie");
        list.add("Event");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        addListenerOnButton();

        return rootView;
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

    public void addListenerOnSpinnerItemSelection(){

        spinner.setOnItemSelectedListener(new CustomListener());
    }

    //get the selected dropdown list value

    public void addListenerOnButton() {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),
                        "On Button Click : " +
                                "\n" + String.valueOf(spinner.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
                if(String.valueOf(spinner.getSelectedItem()).equals("Extra"))
                {
                    mCallback.onAddExtraToDossier(3);
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Locatie"))
                {
                    mCallback.onAddLocationToDossier(3);
                }
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
//        if(getArguments() != null) {
//            Bundle b = getArguments();
//            int i = b.getInt("dId");
            getJppService().getDossier(3, this);
//        }
    }

    @Override
    public void success(DtoDossierDetailed dtoDossierDetailed, Response response) {
        username.setText(dtoDossierDetailed.getUsername());
        answer.setText(dtoDossierDetailed.getAnswer());
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
