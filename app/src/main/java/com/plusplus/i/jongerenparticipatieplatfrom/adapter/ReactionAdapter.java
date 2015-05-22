package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoReaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

/**
 * Created by Shenno on 21/05/2015.
 */
public class ReactionAdapter extends BaseAdapter {
    private final Context context;
    private List<DtoReaction> reactionList;

    public ReactionAdapter(Context context) {
        this.context = context;
        this.reactionList = new ArrayList();
    }

    public void setReactionList(List<DtoReaction> reactionList) {
        this.reactionList = reactionList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reactionList.size();
    }

    @Override
    public DtoReaction getItem(int position) {
        return reactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DtoReaction reaction = getItem(position);
        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(parent.getContext()).inflate(R.layout.reaction_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        Typeface fontttype = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        Typeface openSansItalic = Typeface.createFromAsset(convertView.getContext().getAssets(), "fonts/OpenSans-Italic.ttf");


        viewHolder.usernameTextView.setTypeface(openSansItalic);
        viewHolder.votesTextView.setTypeface(fontttype);
        viewHolder.answerTextView.setTypeface(fontttype);

        String tmp = new SimpleDateFormat("dd/MM/yyyy").format(reaction.getDate());
        viewHolder.usernameTextView.setText(tmp + " - " + reaction.getUsername());
        viewHolder.votesTextView.setText(Integer.toString(reaction.getVotes()));
        viewHolder.answerTextView.setText(reaction.getAnswer());
        return convertView;
    }

    static class ViewHolder {
        TextView usernameTextView;
        TextView votesTextView;
        TextView answerTextView;

        public ViewHolder(View view) {
            usernameTextView = (TextView) view.findViewById(R.id.rUserAndDate);
            votesTextView = (TextView) view.findViewById(R.id.rVotes);
            answerTextView = (TextView) view.findViewById(R.id.rAnswer);
        }
    }
}
