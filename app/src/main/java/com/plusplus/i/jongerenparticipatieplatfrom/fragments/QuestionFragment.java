package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.DmsAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

public class QuestionFragment extends Fragment implements Callback<List<DtoDms>> {
    private DmsAdapter dmsAdapter;
    private OnSelectedListener mCallback;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dmsAdapter = new DmsAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        listView  = (ListView) rootView.findViewById(R.id.dmsList);
        listView.setAdapter(dmsAdapter);
        View emptyView = rootView.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoDms dto = dmsAdapter.getItem(arg2);
                mCallback.onItemClicked(dto.getId());
            }

        });
        // Haalt alle actieve dossiermodules op
        getJppService().getOpenDms(1, this);
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
    public void success(List<DtoDms> dtoDmses, Response response) {
        dmsAdapter.setOpenDms(dtoDmses);
    }

    @Override
    public void failure(RetrofitError error) {
        try {
            AppMsg.makeText(getActivity(), "Er is iets mis gegaan :(", AppMsg.STYLE_ALERT).show();
        }
        catch (Exception e) {
            Log.e("test", e.toString());
        }
    }
}


