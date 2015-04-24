package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 13/04/2015.
 */
public class DmsAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoDms> openDms;

    public DmsAdapter(Context context) {
        this.context = context;
        this.openDms = new ArrayList();
    }

    public void setOpenDms(List<DtoDms> openDms) {
        this.openDms = openDms;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return openDms.size();
    }

    @Override
    public DtoDms getItem(int position) {
        return openDms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoDms dms = getItem(position);
         ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.dms_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(dms.getEndDate()));
        viewHolder.questionTextView.setText(dms.getQuestion());
        viewHolder.extraTextView.setText(dms.getExtraInfo());
        return convertView;
    }

    static class ViewHolder {
        TextView dateTextView;
        TextView questionTextView;
        TextView extraTextView;

        public ViewHolder(View view) {
            dateTextView = (TextView) view.findViewById(R.id.dmsDate);
            questionTextView = (TextView) view.findViewById(R.id.dmsQuestion);
            extraTextView = (TextView) view.findViewById(R.id.dmsExtra);
        }
    }
}