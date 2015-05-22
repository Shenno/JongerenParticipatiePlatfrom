package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoEvent;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 11/05/2015.
 */
public class EventAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoEvent> events;

    public EventAdapter(Context context) {
        this.context = context;
        this.events = new ArrayList();
    }

    public void setEvents(List<DtoEvent> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public DtoEvent getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoEvent e = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.event_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.title.setText(e.getTitle());
        viewHolder.desc.setText(e.getDescription());
        viewHolder.date.setText(e.getDate());
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView desc;
        TextView date;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.eiTitle);
            desc = (TextView) view.findViewById(R.id.eiDesc);
            date = (TextView) view.findViewById(R.id.eiDate);
        }
    }
}
