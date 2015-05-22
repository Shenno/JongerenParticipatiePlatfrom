package com.plusplus.i.jongerenparticipatieplatfrom.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusplus.i.jongerenparticipatieplatfrom.R;

/**
 * Created by Shenno on 12/05/2015.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] images;

    public ImageGridAdapter(Context c, String[] images ) {
        mContext = c;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Bitmap getItem(int position) {
        byte[] decodedString = Base64.decode(this.images[position], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            byte[] decodedString = Base64.decode(this.images[position], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
