package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDmsDetailed;
import com.software.shell.fab.ActionButton;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;


/**
 * Created by Shenno on 17/04/2015.
 */
public class DmsFragment extends Fragment implements Callback<DtoDmsDetailed> {
    private OnSelectedListener mCallback;
    private TextView startDate;
    private TextView endDate;
    private TextView questioner;
    private TextView winner;
    private TextView question;
    private TextView extra;
    private Button showDossiers;
    private ActionButton newDossier;
    private ImageView calIcon;
    private ImageView infoIcon;
    private String winners;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dms, container, false);
        startDate = (TextView) rootView.findViewById(R.id.dmsDetStartDate);
        endDate = (TextView) rootView.findViewById(R.id.dmsDetEndDate);
        questioner = (TextView) rootView.findViewById(R.id.dmsDetQuestioner);
        winner = (TextView) rootView.findViewById(R.id.dmsDetWinner);
        question = (TextView) rootView.findViewById(R.id.dmsDetQuestion);
        extra = (TextView) rootView.findViewById(R.id.dmsDetExtra);
        newDossier = (ActionButton) rootView.findViewById(R.id.dmsNewDossier);
        showDossiers = (Button) rootView.findViewById(R.id.btnShowReactions);
        calIcon = (ImageView)rootView.findViewById(R.id.imageCal);
        infoIcon = (ImageView)rootView.findViewById(R.id.imageIcon);

        Typeface fontttype = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface opensansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Semibold.ttf");
        Typeface openSansItalic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Italic.ttf");

        startDate.setTypeface(fontttype);
        endDate.setTypeface(fontttype);
        questioner.setTypeface(openSansItalic);
        winner.setTypeface(fontttype);
        question.setTypeface(opensansBold);
        extra.setTypeface(fontttype);


        calIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMsg.makeText(getActivity(),"Beschikbaar van " +startDate.getText().toString() + " tot " + endDate.getText().toString(),AppMsg.STYLE_INFO).show();

            }
        });

        infoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMsg.makeText(getActivity(),winners+ " winnende dossiers voor deze vraag",AppMsg.STYLE_INFO).show();
            }
        });

        newDossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    Bundle b = getArguments();
                    int i = b.getInt("parameter");
                    mCallback.onNewDossierClicked(i);
                }
            }
        });

        showDossiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    Bundle b = getArguments();
                    int i = b.getInt("parameter");
                    mCallback.onDmsItemClicked(i, question.getText().toString());
                }
            }
        });

        winner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMsg.makeText(getActivity(),"Maximum aantal winnende antwoorden",AppMsg.STYLE_INFO).show();
            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("parameter");
            getJppService().getDms(i, this);
        }
    }

    @Override
    public void success(DtoDmsDetailed dtoDmsDetailed, Response response) {
        // Vul de velden op het scherm in met de waarden uit de back-end
        startDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoDmsDetailed.getStartDate()));
        endDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoDmsDetailed.getEndDate()));
        questioner.setText(dtoDmsDetailed.getQuestioner());
        winner.setText(Integer.toString(dtoDmsDetailed.getWinnersCount()));
        winners = Integer.toString(dtoDmsDetailed.getWinnersCount());
        question.setText(dtoDmsDetailed.getQuestion());
        extra.setText(dtoDmsDetailed.getExtraInfo());
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "Er is iets mis gegaan :(", AppMsg.STYLE_ALERT).show();
    }
}