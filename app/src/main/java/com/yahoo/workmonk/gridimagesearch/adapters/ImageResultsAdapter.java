package com.yahoo.workmonk.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.workmonk.gridimagesearch.R;
import com.yahoo.workmonk.gridimagesearch.models.ImageResult;

import java.util.List;

/**
 * Created by workmonk on 9/15/14.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        //Clear out image from last time
        ivImage.setImageResource(0);
        //Populate title and remote download the image url
        tvTitle.setText(Html.fromHtml(imageInfo.title));
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);

        return convertView;
    }
}
