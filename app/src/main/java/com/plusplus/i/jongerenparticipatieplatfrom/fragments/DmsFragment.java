package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDmsDetailed;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


/**
 * Created by Shenno on 17/04/2015.
 */
public class DmsFragment extends Fragment implements Callback<DtoDmsDetailed> {
    TextView startDate;
    TextView endDate;
    TextView questioner;
    TextView winner;
    TextView question;
    TextView extra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dms, container, false);
        startDate = (TextView) rootView.findViewById(R.id.dmsDetStartDate);
        endDate = (TextView) rootView.findViewById(R.id.dmsDetEndDate);
        questioner = (TextView) rootView.findViewById(R.id.dmsDetQuestioner);
        winner = (TextView) rootView.findViewById(R.id.dmsDetWinner);
        question = (TextView) rootView.findViewById(R.id.dmsDetQuestion);
        extra = (TextView) rootView.findViewById(R.id.dmsDetExtra);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("parameter");
            getJppService().getDms(i, this);
        }
    }

    @Override
    public void success(DtoDmsDetailed dtoDmsDetailed, Response response) {
        Toast.makeText(getActivity(),"Kevinisblij", Toast.LENGTH_LONG).show();
        startDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoDmsDetailed.getStartDate()));
        endDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoDmsDetailed.getEndDate()));
        questioner.setText("QuestionerDummy");
        winner.setText(Integer.toString(dtoDmsDetailed.getWinnersCount()));
        question.setText(dtoDmsDetailed.getQuestion());
        extra.setText(dtoDmsDetailed.getExtraInfo());

    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(),"Kevinisnietblij", Toast.LENGTH_LONG).show();

    }
}