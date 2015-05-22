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
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.AmsAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.interfaces.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAsm;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 20/05/2015.
 */
public class AmsHomeFragment extends Fragment implements Callback<List<DtoAsm>> {
    private AmsAdapter amsAdapter;
    OnSelectedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        amsAdapter = new AmsAdapter(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_amshome, container, false);

        ListView listView  = (ListView) rootView.findViewById(R.id.amsList);
        listView.setAdapter(amsAdapter);
        View emptyView = rootView.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                DtoAsm dto = amsAdapter.getItem(arg2);
                mCallback.onHomeAmsItemClicked(dto.getId());
            }

        });

        getJppService().getOpenAms(1, this);


        return rootView;
    }

/*    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onItemClicked(int position);
    }*/

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
    public void success(List<DtoAsm> dtoAsms, Response response) {
        amsAdapter.setOpenAms(dtoAsms);
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