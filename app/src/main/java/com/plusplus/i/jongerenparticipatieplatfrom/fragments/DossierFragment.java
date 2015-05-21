package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.EventAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.ImageGridAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.adapter.QAAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAsm;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoEvent;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoFixedQuestion;
import com.software.shell.fab.ActionButton;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.plusplus.i.jongerenparticipatieplatfrom.application.JppApplication.getJppService;

/**
 * Created by Shenno on 28/04/2015.
 */
public class DossierFragment extends Fragment implements Callback<DtoDossierDetailed> {
    private TextView tUsername;
    private TextView tAnswer;
    private TextView tExtra;
    private ExpandableHeightListView tQA;
    private ExpandableHeightListView tEvents;
    private ExpandableHeightGridView tImages;
    private ImageView tImage;
    ImageGridAdapter tGridAdapter;
    EventAdapter tEventAdapter;
    QAAdapter tQAAdapter;
    String tempstring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dossier, container, false);
        tUsername = (TextView) rootView.findViewById(R.id.ddUsername);
        tAnswer = (TextView) rootView.findViewById(R.id.ddAnswer);
        tExtra = (TextView) rootView.findViewById(R.id.ddExtra);
        tEvents = (ExpandableHeightListView) rootView.findViewById(R.id.ddListEvents);
        tImage = (ImageView) rootView.findViewById(R.id.ddImage);
        tEvents.setExpanded(true);
        tQA = (ExpandableHeightListView) rootView.findViewById(R.id.ddListQA);
        tQA.setExpanded(true);
        tEventAdapter = new EventAdapter(getActivity());
        tQAAdapter = new QAAdapter(getActivity());
        tImages = (ExpandableHeightGridView) rootView.findViewById(R.id.ddGrid);
        tImages.setExpanded(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Bundle b = getArguments();
            int i = b.getInt("dId");
            getJppService().getDossier(i, this);
        }
    }

    @Override
    public void success(DtoDossierDetailed dtoDossierDetailed, Response response) {
        if(dtoDossierDetailed.getPhotos().length != 0) {
            tImage.setVisibility(View.GONE);
            tGridAdapter = new ImageGridAdapter(getActivity(), dtoDossierDetailed.getPhotos());
            tImages.setAdapter(tGridAdapter);
            tImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    tImage.setVisibility(View.VISIBLE);
                    tImage.setImageBitmap(tGridAdapter.getItem(arg2));
                }

            });
        }
        else
        {
            tImage.setVisibility(View.GONE);
            tImages.setVisibility(View.GONE);
        }
        tempstring = "Dossier van : "+ dtoDossierDetailed.getUsername();
        tUsername.setText(tempstring);
        tAnswer.setText(dtoDossierDetailed.getAnswer());
        if (dtoDossierDetailed.getExtra() != null) {
            tExtra.setText(dtoDossierDetailed.getExtra());
        } else {
            tExtra.setVisibility(View.GONE);
        }

        if (dtoDossierDetailed.getLocation() != null) {
            tempstring = tempstring + " \nLocatie: " + dtoDossierDetailed.getLocation();
            tUsername.setText(tempstring);
           // tLocation.setText("Locatie: " + dtoDossierDetailed.getLocation());
        } else {
        //    tLocation.setVisibility(View.GONE);
        }

        if (dtoDossierDetailed.getCalendar() != null) {
            tEventAdapter.setEvents(dtoDossierDetailed.getCalendar());

            tEvents.setAdapter(tEventAdapter);



        } else {

            tEvents.setVisibility(View.GONE);
        }

        if (dtoDossierDetailed.getFixedQuestion() != null) {
            tQAAdapter.setEvents(dtoDossierDetailed.getFixedQuestion());
            tQA.setAdapter(tQAAdapter);

        } else {
            tQA.setVisibility(View.GONE);
        }




    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(), "niet goed", Toast.LENGTH_LONG).show();
    }

}
