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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.EventAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.QAAdapter;
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
    private TextView tUsername;
    private TextView tAnswer;
    private TextView tExtra;
    private ListView tQA;
    private TextView tLocation;
    private ListView tEvents;
    EventAdapter tEventAdapter;
    QAAdapter tQAAdapter;
    OnSelectedListener mCallback;
    Spinner spinner;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_editdossier, container, false);
        tUsername = (TextView) rootView.findViewById(R.id.ddUsername);
        tAnswer = (TextView) rootView.findViewById(R.id.ddAnswer);
        tExtra = (TextView) rootView.findViewById(R.id.ddExtra);
        tLocation = (TextView) rootView.findViewById(R.id.ddLocation);
        tEvents = (ListView) rootView.findViewById(R.id.ddListEvents);
        tQA = (ListView) rootView.findViewById(R.id.ddListQA);
        tEventAdapter = new EventAdapter(getActivity());
        tQAAdapter = new QAAdapter(getActivity());
        spinner = (Spinner) rootView.findViewById(R.id.edSpinner);
        button = (Button) rootView.findViewById(R.id.edBtn);
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

                if(String.valueOf(spinner.getSelectedItem()).equals("Extra"))
                {
                    mCallback.onAddExtraToDossier(3);
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Locatie"))
                {
                    mCallback.onAddLocationToDossier(3);
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Event"))
                {
                    mCallback.onAddEventToDossier(3);
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
        tUsername.setText(dtoDossierDetailed.getUsername());
        tAnswer.setText(dtoDossierDetailed.getAnswer());
        if(dtoDossierDetailed.getExtra() != null)
        {
            tExtra.setText(dtoDossierDetailed.getExtra());
        }
        else
        {
            tExtra.setVisibility(View.GONE);
        }

        if(dtoDossierDetailed.getLocation() != null)
        {
            tLocation.setText(dtoDossierDetailed.getLocation());
        }
        else
        {
            tLocation.setVisibility(View.GONE);
        }

        if(dtoDossierDetailed.getCalendar() != null)
        {
            tEventAdapter.setEvents(dtoDossierDetailed.getCalendar());
            tEvents.setAdapter(tEventAdapter);
        }
        else
        {

            tEvents.setVisibility(View.GONE);
        }

        if(dtoDossierDetailed.getFixedQuestion() != null)
        {
            tQAAdapter.setEvents(dtoDossierDetailed.getFixedQuestion());
            tQA.setAdapter(tQAAdapter);
        }
        else
        {
            tQA.setVisibility(View.GONE);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(),"niet goed",Toast.LENGTH_LONG).show();
    }
}
