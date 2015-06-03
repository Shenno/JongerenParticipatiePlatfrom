package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 8/05/2015.
 */
public class AddEventToDossierFragment extends Fragment implements Callback<DtoAddEvent> {
    private EditText title;
    private EditText desc;
    private DatePicker datePicker;
    private Button button;

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

    // Helpermethode om datum uit datepicker te krijgen
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
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoAddEvent.setdId(i);
        }
        dtoAddEvent.setTitle(title.getText().toString());
        dtoAddEvent.setDescription(desc.getText().toString());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = getDateFromDatePicker(datePicker);
        String reportDate = df.format(today);
        dtoAddEvent.setDate(reportDate);
        SharedPreferences prefs = getActivity().getSharedPreferences("Logindetails", MODE_PRIVATE);
        String token = prefs.getString("token","");
        token = "Bearer " + token;
        getJppService().addEventToDossier(token, dtoAddEvent, this);
    }

    @Override
    public void success(DtoAddEvent dtoAddEvent, Response response) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}
