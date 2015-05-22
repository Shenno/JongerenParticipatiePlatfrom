package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoVote;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 22/05/2015.
 */
public class VoteAgendaFragment extends Fragment implements Callback<DtoVote> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        DtoVote dtoVote = new DtoVote();
        //Haalt de parameter id op van de agendareaction waarop gestemd moet worden
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            dtoVote.setId(i);
        }
        SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String email = userDetails.getString("email", "");
        dtoVote.setUserId(email);
        String token = userDetails.getString("token","");
        token = "Bearer " + token;
        getJppService().addVoteR(token, dtoVote, this); //Roept back-end op met een post call om te stemmen op een agendareaction
        return rootView;
    }

    @Override
    public void success(DtoVote dto, Response response) {
        AppMsg.makeText(getActivity(), "Uw stem is verwerkt", AppMsg.STYLE_INFO).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "Er is iets foutgelopen", AppMsg.STYLE_ALERT).show();
        getFragmentManager().popBackStack();
    }
}