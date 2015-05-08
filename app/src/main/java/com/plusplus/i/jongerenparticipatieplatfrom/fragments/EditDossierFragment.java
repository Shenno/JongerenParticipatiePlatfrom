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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    ToggleButton toggleButton;
    Spinner spinner;
    Button button;
    TextView tvExtra;
    TextView tvLocation;
    EditText etExtra;
    EditText etLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_editdossier, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.edSpinner);
        button = (Button) rootView.findViewById(R.id.edBtn);
        tvExtra = (TextView) rootView.findViewById(R.id.edExtra);
        tvLocation = (TextView) rootView.findViewById(R.id.edLocation);
        etExtra = (EditText) rootView.findViewById(R.id.edExtraEdit);
        etLocation = (EditText) rootView.findViewById(R.id.edLocationEdit);
        etExtra.setVisibility(View.GONE);
        etLocation.setVisibility(View.GONE);
        toggleButton = (ToggleButton) rootView.findViewById(R.id.edToggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(),
                            "ON",
                            Toast.LENGTH_LONG).show();
                    etExtra.setVisibility(View.VISIBLE);
                    etLocation.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(getActivity(),
                            "OFF",
                            Toast.LENGTH_LONG).show();
                    etExtra.setVisibility(View.GONE);
                    etLocation.setVisibility(View.GONE);
                }
            }
        });
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

    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(),"niet goed",Toast.LENGTH_LONG).show();
    }
}
