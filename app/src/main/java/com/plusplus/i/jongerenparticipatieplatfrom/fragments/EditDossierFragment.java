package com.plusplus.i.jongerenparticipatieplatfrom.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.EventAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.ImageGridAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.QAAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.custom.CustomListener;
import com.plusplus.i.jongerenparticipatieplatfrom.custom.ExpandableHeightGridView;
import com.plusplus.i.jongerenparticipatieplatfrom.custom.ExpandableHeightListView;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;

import java.util.ArrayList;
import java.util.List;

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
    private ExpandableHeightListView tQA;
    private ExpandableHeightListView tEvents;
    private ExpandableHeightGridView tImages;
    private ImageView tImage;
    public int id;
    private ImageGridAdapter tGridAdapter;
    private EventAdapter tEventAdapter;
    private QAAdapter tQAAdapter;
    private String tempstring;
    private Spinner spinner;
    private Button button;
    private OnSelectedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_editdossier, container, false);
        tUsername = (TextView) rootView.findViewById(R.id.ddUsername);
        tAnswer = (TextView) rootView.findViewById(R.id.ddAnswer);
        tExtra = (TextView) rootView.findViewById(R.id.ddExtra);
        tEvents = (ExpandableHeightListView) rootView.findViewById(R.id.ddListEvents);
        tImage = (ImageView) rootView.findViewById(R.id.ddImage);
        tEvents.setExpanded(true);
        tQA = (ExpandableHeightListView) rootView.findViewById(R.id.ddListQA);
        tQA.setExpanded(true);
        tEventAdapter = new EventAdapter(getActivity());
        tQAAdapter = new QAAdapter(getActivity());
        tImages = (ExpandableHeightGridView) rootView.findViewById(R.id.ddGrid);
        tImages.setExpanded(true);
        spinner = (Spinner) rootView.findViewById(R.id.ddSpinner);
        button = (Button) rootView.findViewById(R.id.button);
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
                    // Zorgt voor fragmentswitch met juiste dossierid parameter
                    mCallback.onAddExtraToDossier(id);
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Locatie"))
                {
                    // Zorgt voor fragmentswitch met juiste dossierid parameter
                    mCallback.onAddLocationToDossier(id);
                }
                else if(String.valueOf(spinner.getSelectedItem()).equals("Event"))
                {
                    // Zorgt voor fragmentswitch met juiste dossierid parameter
                    mCallback.onAddEventToDossier(id);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            id = i;
            // Haalt dossier op met een GET call
            getJppService().getDossier(i, this);
        }
    }

    @Override
    public void success(DtoDossierDetailed dtoDossierDetailed, Response response) {
        if(dtoDossierDetailed.getPhotos().length != 0) {
            tImage.setVisibility(View.GONE);
            tGridAdapter = new ImageGridAdapter(getActivity(), dtoDossierDetailed.getPhotos());
            tImages.setAdapter(tGridAdapter);
            tImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    tImage.setVisibility(View.VISIBLE);
                    tImage.setImageBitmap(tGridAdapter.getItem(arg2));
                }

            });
        }
        else
        {
            // Verberg velden als er geen afbeeldingen zijn
            tImage.setVisibility(View.GONE);
            tImages.setVisibility(View.GONE);
        }
        tempstring = "Dossier van : "+ dtoDossierDetailed.getUsername();
        tUsername.setText(tempstring);
        tAnswer.setText(dtoDossierDetailed.getAnswer());
        if (dtoDossierDetailed.getExtra() != null) {
            tExtra.setText(dtoDossierDetailed.getExtra());
        }
        else
        {
            // Verberg veld als er geen extra informatie is
            tExtra.setVisibility(View.GONE);
        }

        if (dtoDossierDetailed.getLocation() != null) {
            tempstring = tempstring + " \nLocatie: " + dtoDossierDetailed.getLocation();
            tUsername.setText(tempstring);
        }

        if (dtoDossierDetailed.getCalendar() != null) {
            tEventAdapter.setEvents(dtoDossierDetailed.getCalendar());
            tEvents.setAdapter(tEventAdapter);
        }
        else
        {
            // Verberg veld als er geen kalendergebeurtenissen zijn
            tEvents.setVisibility(View.GONE);
        }

        if (dtoDossierDetailed.getFixedQuestion() != null) {
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
        Toast.makeText(getActivity(), "niet goed", Toast.LENGTH_LONG).show();
    }
}
