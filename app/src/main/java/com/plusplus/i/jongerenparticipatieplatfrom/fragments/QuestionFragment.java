package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.DmsAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;
import com.plusplus.i.jongerenparticipatieplatfrom.service.JppService;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

public class QuestionFragment extends Fragment implements Callback<List<DtoDms>> {
    private DmsAdapter dmsAdapter;
    OnHeadlineSelectedListener mCallback;
    ActionButton actionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dmsAdapter = new DmsAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        ListView listView  = (ListView) rootView.findViewById(R.id.dmsList);
        listView.setAdapter(dmsAdapter);
        View emptyView = rootView.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoDms dto = dmsAdapter.getItem(arg2);
                Toast.makeText(getActivity(), dto.getExtraInfo(), Toast.LENGTH_LONG).show();
                mCallback.onItemClicked(dto.getId());
            }

        });

        getJppService().getOpenDms(1, this);


        return rootView;
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onItemClicked(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void success(List<DtoDms> dtoDmses, Response response) {
        Toast.makeText(getActivity(), "Ok dit werkt al", Toast.LENGTH_LONG).show();
        dmsAdapter.setOpenDms(dtoDmses);
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "Fout", Toast.LENGTH_LONG).show();
    }
}


