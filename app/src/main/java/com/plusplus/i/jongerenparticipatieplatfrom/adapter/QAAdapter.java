package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoFixedQuestion;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 11/05/2015.
 */
public class QAAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoFixedQuestion> qaList;

    public QAAdapter(Context context) {
        this.context = context;
        this.qaList = new ArrayList();
    }

    public void setEvents(List<DtoFixedQuestion> fq) {
        this.qaList = fq;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return qaList.size();
    }

    @Override
    public DtoFixedQuestion getItem(int position) {
        return qaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoFixedQuestion e = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.qa_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.question.setText(e.getQuestion());
        viewHolder.answer.setText(e.getAnswer());
        return convertView;
    }

    static class ViewHolder {
        TextView question;
        TextView answer;

        public ViewHolder(View view) {
            question = (TextView) view.findViewById(R.id.qaiQuestion);
            answer = (TextView) view.findViewById(R.id.qaiAnswer);
        }
    }
}