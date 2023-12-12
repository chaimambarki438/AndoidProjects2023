package com.example.myapplicationmobile2023.model;

// Import necessary packages
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

// Class definition
public class ImageArrayAdapter extends ArrayAdapter<Bitmap> {

    private List<Bitmap> imageList;

    public ImageArrayAdapter(Context context, int resource, int textViewResourceId, List<Bitmap> images) {
        super(context, resource, textViewResourceId, images);
        this.imageList = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) convertView;
        }

        // Set the bitmap image to the ImageView
        imageView.setImageBitmap(imageList.get(position));

        return imageView;
    }
}
