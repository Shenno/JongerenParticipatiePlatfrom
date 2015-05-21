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
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAmsDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDmsDetailed;
import com.software.shell.fab.ActionButton;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 20/05/2015.
 */
public class AmsFragment extends Fragment implements Callback<DtoAmsDetailed> {
    OnSelectedListener mCallback;
    TextView startDate;
    TextView endDate;
    TextView questioner;
    TextView tags;
    TextView question;
    TextView extra;
    Button showReactions;
    ImageView infoRewards;
    //ActionButton newDossier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_admin_question, container, false);
        startDate = (TextView) rootView.findViewById(R.id.amsDetStartDate);
        endDate = (TextView) rootView.findViewById(R.id.amsDetEndDate);
        questioner = (TextView) rootView.findViewById(R.id.amsDetQuestioner);
        tags = (TextView) rootView.findViewById(R.id.txtvTags);
        question = (TextView) rootView.findViewById(R.id.amsDetQuestion);
        extra = (TextView) rootView.findViewById(R.id.amsDetExtra);
 //       newDossier = (ActionButton) rootView.findViewById(R.id.dmsNewDossier);
        showReactions = (Button) rootView.findViewById(R.id.btnShowReactions);
        infoRewards = (ImageView) rootView.findViewById(R.id.imageIcon);

        Typeface fontttype = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface opensansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Semibold.ttf");
        Typeface openSansItalic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Italic.ttf");


        startDate.setTypeface(fontttype);
        endDate.setTypeface(fontttype);
        questioner.setTypeface(openSansItalic);
        tags.setTypeface(fontttype);
        question.setTypeface(opensansBold);
        extra.setTypeface(fontttype);


   /*     showDossiers.setOnClickListener(new View.OnClickListener() {
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
                AppMsg.makeText(getActivity(), "Maximum aantal winnende antwoorden", AppMsg.STYLE_INFO).show();

            }
        }); */


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
            getJppService().getAms(i, this);
        }
    }

    @Override
    public void success(final DtoAmsDetailed dtoAmsDetailed, Response response) {
        startDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoAmsDetailed.getStartDate()));
        endDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoAmsDetailed.getEndDate()));
        questioner.setText(dtoAmsDetailed.getQuestioner());
        tags.setText(convertTags(dtoAmsDetailed.getTags()));
        question.setText(dtoAmsDetailed.getQuestion());
        extra.setText(dtoAmsDetailed.getExtraInfo());
        infoRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMsg.makeText(getActivity(), convertRewards(dtoAmsDetailed.getRewards()), AppMsg.STYLE_INFO).show();
            }
        });
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "Er is iets mis gegaan :(", AppMsg.STYLE_ALERT).show();
    }

    private String convertRewards(String[] rewards)
    {
        String temp = "Beloningen:\n";
        for(int i = 0 ; i < rewards.length ; i++)
        {
            temp = temp + (i+1) + ". "+ rewards[i] + "\n";
        }
        return temp;
    }

    private String convertTags(String[] tags)
    {
        String temp = new String();
        for(int i = 0 ; i < tags.length ; i++)
        {
            temp = temp + " #" + tags[i];
        }
        return temp;
    }
}