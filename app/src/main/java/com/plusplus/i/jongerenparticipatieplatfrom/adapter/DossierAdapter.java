package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 28/04/2015.
 */
public class DossierAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoDossier> dossierList;

    public DossierAdapter(Context context) {
        this.context = context;
        this.dossierList = new ArrayList();
    }

    public void setDossierList(List<DtoDossier> dossierList) {
        this.dossierList = dossierList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dossierList.size();
    }

    @Override
    public DtoDossier getItem(int position) {
        return dossierList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoDossier dossier = getItem(position);
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.d_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        Typeface fontttype = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface openSansItalic = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Italic.ttf");


        viewHolder.usernameTextView.setTypeface(openSansItalic);
        viewHolder.votesTextView.setTypeface(fontttype);
        viewHolder.answerTextView.setTypeface(fontttype);


        viewHolder.usernameTextView.setText(dossier.getUsername());
        viewHolder.votesTextView.setText(Integer.toString(dossier.getVotes()));
        viewHolder.answerTextView.setText(dossier.getAnswer());

        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  "Hier komt nog ne push naar de db met fragment switch"
                Toast.makeText(context,"Je stem wordt verwerkt.",Toast.LENGTH_LONG).show();


            }
        });
        return convertView;
    }



    static class ViewHolder {
        TextView usernameTextView;
        TextView votesTextView;
        TextView answerTextView;
        ImageButton likeButton;

        public ViewHolder(View view) {
            usernameTextView = (TextView) view.findViewById(R.id.dUsername);
            votesTextView = (TextView) view.findViewById(R.id.dVotes);
            answerTextView = (TextView) view.findViewById(R.id.dAnswer);
            likeButton = (ImageButton) view.findViewById(R.id.dLikeButton);
        }
    }
}
