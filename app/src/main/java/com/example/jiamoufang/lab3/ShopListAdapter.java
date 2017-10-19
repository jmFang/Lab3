package com.example.jiamoufang.lab3;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jiamoufang on 2017/10/18.
 */

public class ShopListAdapter extends ArrayAdapter<Product> {
    private int resourceId;

    public ShopListAdapter(Context context, int shopListViewResourceId, List<Product> shopList) {
        super(context, shopListViewResourceId, shopList);
        resourceId = shopListViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.productHead = view.findViewById(R.id.shoplist_product_head);
            viewHolder.productName = view.findViewById(R.id.shoplist_product_name);
            viewHolder.productPrice = view.findViewById(R.id.shoplist_product_price);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.productHead.setText(product.getProductHead());
        viewHolder.productName.setText(product.getProductName());
        viewHolder.productPrice.setText(product.getProductPrice());

        return view;
    }
    class ViewHolder {
        TextView productHead;
        TextView productName;
        TextView productPrice;
    }
}
