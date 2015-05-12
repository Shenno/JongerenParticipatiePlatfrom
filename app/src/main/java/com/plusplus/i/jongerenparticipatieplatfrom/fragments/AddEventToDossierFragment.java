package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddEvent;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddExtra;

import java.net.DatagramPacket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 8/05/2015.
 */
public class AddEventToDossierFragment extends Fragment implements Callback<DtoAddEvent> {
    EditText title;
    EditText desc;
    DatePicker datePicker;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addeventtodossier, container, false);
        title = (EditText) rootView.findViewById(R.id.addEventTitle);
        desc = (EditText) rootView.findViewById(R.id.addEventDesc);
        datePicker = (DatePicker) rootView.findViewById(R.id.addEventdatePicker);
        button = (Button) rootView.findViewById(R.id.addEventbutton);
        initListeners();
        return rootView;
    }

    public void initListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvent();
            }
        });
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void submitEvent() {
        DtoAddEvent dtoAddEvent = new DtoAddEvent();
        dtoAddEvent.setdId(3);
        dtoAddEvent.setTitle(title.getText().toString());
        dtoAddEvent.setDescription(desc.getText().toString());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = getDateFromDatePicker(datePicker);
        String reportDate = df.format(today);
        dtoAddEvent.setDate(reportDate);
        getJppService().addEventToDossier(dtoAddEvent, this);
    }

    @Override
    public void success(DtoAddEvent dtoAddEvent, Response response) {
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
