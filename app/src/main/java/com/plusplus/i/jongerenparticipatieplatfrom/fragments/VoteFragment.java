package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAddExtra;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoVote;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 21/05/2015.
 */
public class VoteFragment extends Fragment implements Callback<DtoVote> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        DtoVote dtoVote = new DtoVote();
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
        getJppService().addVote(token, dtoVote, this);
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