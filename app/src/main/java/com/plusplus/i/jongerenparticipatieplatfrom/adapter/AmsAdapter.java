package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoAsm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 20/05/2015.
 */
public class AmsAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoAsm> openAms;

    public AmsAdapter(Context context) {
        this.context = context;
        this.openAms = new ArrayList();
    }

    public void setOpenAms(List<DtoAsm> openAms) {
        this.openAms = openAms;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return openAms.size();
    }

    @Override
    public DtoAsm getItem(int position) {
        return openAms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoAsm ams = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.ams_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        Typeface fontttype = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface openSansBold = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Semibold.ttf");
        Typeface fonttype2 = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/pacifico.ttf");

        viewHolder.dateTextView.setTypeface(fontttype);
        viewHolder.questionTextView.setTypeface(openSansBold);
        viewHolder.questionExraInfo.setTypeface(fontttype);
        viewHolder.image.setTypeface(fonttype2);

        viewHolder.dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(ams.getEndDate()));
        viewHolder.questionTextView.setText(ams.getQuestion());
        viewHolder.questionExraInfo.setText(ams.getExtraInfo());
        viewHolder.image.setText(ams.getQuestion().substring(0,1));

        return convertView;
    }

    static class ViewHolder {
        TextView dateTextView;
        TextView questionTextView;
        TextView questionExraInfo;
        TextView image;

        public ViewHolder(View view) {
            dateTextView = (TextView) view.findViewById(R.id.amsDate);
            questionTextView = (TextView) view.findViewById(R.id.amsQuestion);
            questionExraInfo = (TextView) view.findViewById(R.id.amsQuestionExtra);
            image = (TextView)view.findViewById(R.id.amsImage);
        }
    }
}