package com.example.jiamoufang.lab3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jiamoufang on 2017/10/18.
 */

public class DetailsItemAdapter extends ArrayAdapter<DetailsItem> {
    private int resourceId;
    public DetailsItemAdapter(Context context, int id, List<DetailsItem> infoList) {
        super(context, id, infoList);
        resourceId = id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DetailsItem detailsItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.info_item);
        textView.setText(detailsItem.getInfo());
        return view;
    }
}
