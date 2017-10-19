package com.example.jiamoufang.lab3;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    private  String productHead;
    private String productName;
    private String productPrice;
    private String productType;
    private String prodcutInfo;
    private int prodcutImageId;
    private ImageView retImage;
    private ImageView stateImage;
    private ImageView shopPackage;
    private boolean state = true;
    private List<DetailsItem> detailsItemList = new ArrayList<>();

    public void init() {
        DetailsItem detailsItem = new DetailsItem("一键下单");
        detailsItemList.add(detailsItem);
        detailsItem = new DetailsItem("分享商品");
        detailsItemList.add(detailsItem);
        detailsItem = new DetailsItem("不感兴趣");
        detailsItemList.add(detailsItem);
        detailsItem = new DetailsItem("查看更多商品促销信息");
        detailsItemList.add(detailsItem);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
        Intent intent = getIntent();
        productHead = intent.getStringExtra("header");
        productName = intent.getStringExtra("name");
        productPrice = intent.getStringExtra("price");
        productType = intent.getStringExtra("type");
        prodcutInfo = intent.getStringExtra("info");
        prodcutImageId = intent.getIntExtra("image",0);

        TextView textView_name = (TextView) findViewById(R.id.product_name);
        TextView textView_price = (TextView)findViewById(R.id.price);
        TextView textView_type = (TextView) findViewById(R.id.type);
        TextView textView_info = (TextView)findViewById(R.id.info);
        ImageView imageView_image = (ImageView) findViewById(R.id.product_image);

        textView_name.setText(productName);
        textView_price.setText(productPrice);
        textView_type.setText(productType);
        textView_info.setText(prodcutInfo);
        imageView_image.setImageResource(prodcutImageId);

        retImage = (ImageView)findViewById(R.id.retImage);
        stateImage = (ImageView)findViewById(R.id.starTab);
        shopPackage = (ImageView)findViewById(R.id.shoplist);

        ListView listView = (ListView) findViewById(R.id.listview_in_details);
        DetailsItemAdapter adapter = new DetailsItemAdapter(ProductDetails.this, R.layout.deetail_item, detailsItemList);
        listView.setAdapter(adapter);

        retImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    stateImage.setImageResource(R.drawable.full_star);
                    state = false;
                } else {
                    stateImage.setImageResource(R.drawable.empty_star);
                    state = true;
                }
            }
        });

        shopPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Product(productHead, productName, productPrice, productType, prodcutInfo, prodcutImageId ));
                Snackbar.make(v, "商品已添加到购物车", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
