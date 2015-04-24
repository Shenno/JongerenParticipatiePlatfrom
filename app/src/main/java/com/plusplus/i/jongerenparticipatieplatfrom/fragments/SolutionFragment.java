package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;

public class SolutionFragment extends Fragment {
	
	public SolutionFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_solution, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Kevin is een homo");
        if(getArguments() != null) {
            Bundle b = getArguments();
            String i = b.getString("parameter");
            Toast.makeText(getActivity(), i, Toast.LENGTH_LONG).show();
        }
    }
}
