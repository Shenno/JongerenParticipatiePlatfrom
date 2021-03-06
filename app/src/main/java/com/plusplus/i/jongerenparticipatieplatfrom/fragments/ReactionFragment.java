package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoReactionDetailed;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 21/05/2015.
 */
public class ReactionFragment extends Fragment implements Callback<DtoReactionDetailed> {
    private OnSelectedListener mCallback;
    private TextView rDate;
    private TextView rQuestion;
    private TextView rAnswer;
    private TextView rExtra;
    private TextView rUsername;
    private TextView rVotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_reaction, container, false);
        rDate = (TextView) rootView.findViewById(R.id.rDate);
        rQuestion = (TextView) rootView.findViewById(R.id.rQuestion);
        rAnswer = (TextView) rootView.findViewById(R.id.rAnswer);
        rExtra = (TextView) rootView.findViewById(R.id.rExtra);
        rUsername = (TextView) rootView.findViewById(R.id.rQuestioner);
        rVotes = (TextView) rootView.findViewById(R.id.fbvote);
        rVotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() != null) {
                    Bundle b = getArguments();
                    int i = b.getInt("dId");
                    mCallback.onVoteAgendaReaction(i);
                }
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
            int i = b.getInt("dId");
            getJppService().getReaction(i, this); //Haalt een gedetailleerde agendareaction op met een GET call
        }
    }

    @Override
    public void success(DtoReactionDetailed dtoReactionDetailed, Response response) {
        rQuestion.setText(dtoReactionDetailed.getQuestion());
        rDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(dtoReactionDetailed.getDate()));
        rAnswer.setText(dtoReactionDetailed.getAnswer());
        rExtra.setText(dtoReactionDetailed.getExtra());
        rUsername.setText(dtoReactionDetailed.getUsername());
        rVotes.setText(Integer.toString(dtoReactionDetailed.getVotes()));
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "niet goed", Toast.LENGTH_LONG).show();
    }

}