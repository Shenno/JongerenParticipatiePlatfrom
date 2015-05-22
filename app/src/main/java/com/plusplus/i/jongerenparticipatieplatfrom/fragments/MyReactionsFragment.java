package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.ReactionAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoReaction;
import com.software.shell.fab.ActionButton;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 21/05/2015.
 */
public class MyReactionsFragment extends Fragment implements Callback<List<DtoReaction>> {
    private ReactionAdapter reactionAdapter;
    private OnSelectedListener mCallback;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reactionAdapter = new ReactionAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_myreactions, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.rList);
        title = (TextView) rootView.findViewById(R.id.rTitle);
        title.setText("Mijn reacties");
        listView.setAdapter(reactionAdapter);
        View emptyView = rootView.findViewById(R.id.rEmpty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoReaction dto = reactionAdapter.getItem(arg2);
                mCallback.onReactionItemClicked(dto.getId());
            }

        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences userDetails = getActivity().getSharedPreferences("Logindetails", Context.MODE_PRIVATE);
        String mail = userDetails.getString("email", "dummy@email.be");
        String token = userDetails.getString("token","");
        token = "Bearer " + token;
        getJppService().getReactionsByEmail(token, mail, this);
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
    public void success(List<DtoReaction> dtoReactions, Response response) {
        reactionAdapter.setReactionList(dtoReactions);
    }

    @Override
    public void failure(RetrofitError error) {
        AppMsg.makeText(getActivity(), "U moet ingelogd zijn voor deze actie!", AppMsg.STYLE_ALERT).show();
    }
}