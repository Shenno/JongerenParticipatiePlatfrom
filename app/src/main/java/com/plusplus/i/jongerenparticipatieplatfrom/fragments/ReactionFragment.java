package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.EventAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.ImageGridAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.QAAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;
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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            getJppService().getReaction(i, this);
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