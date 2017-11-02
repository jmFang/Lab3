package com.example.jiamoufang.lab3;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

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
    private ImageView share;
    private boolean state = true;
    private List<DetailsItem> detailsItemList = new ArrayList<>();

    private void init() {
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
        Bundle bundle = intent.getExtras();
        productHead = bundle.getString("header");
        productName = bundle.getString("name");
        productPrice = intent.getStringExtra("price");
        productType = intent.getStringExtra("type");
        prodcutInfo = intent.getStringExtra("info");
        prodcutImageId = intent.getIntExtra("imageId",0);

        ListView listView = (ListView) findViewById(R.id.listview_in_details);
        DetailsItemAdapter adapter = new DetailsItemAdapter(ProductDetails.this, R.layout.deetail_item, detailsItemList);
        listView.setAdapter(adapter);

        TextView textView_name = (TextView) findViewById(R.id.product_name);
        TextView textView_price = (TextView)findViewById(R.id.product_price);
        TextView textView_type = (TextView) findViewById(R.id.product_type);
        TextView textView_info = (TextView)findViewById(R.id.product_info);
        ImageView imageView_image = (ImageView) findViewById(R.id.product_image);

        textView_name.setText(productName);
        textView_price.setText(productPrice);
        textView_type.setText(productType);
        textView_info.setText(prodcutInfo);
        imageView_image.setImageResource(prodcutImageId);

        retImage = (ImageView)findViewById(R.id.retImage);
        stateImage = (ImageView)findViewById(R.id.starTab);
        shopPackage = (ImageView)findViewById(R.id.shoplist);
        share = (ImageView) findViewById(R.id.share);

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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "尚未实现", Snackbar.LENGTH_SHORT).show();
            }
        });

        shopPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Product(productHead, productName, productPrice,
                        productType, prodcutInfo, prodcutImageId ));
                Intent intent1 = new Intent("com.example.jiamoufang.lab3.MyDynamicFilter");
                Bundle bundle = new Bundle();
                bundle.putString("name", productName);
                bundle.putString("header", productHead);
                bundle.putInt("imageId", prodcutImageId);
                bundle.putString("type", productType);
                bundle.putString("info", prodcutInfo);
                bundle.putString("price", productPrice);
                intent1.putExtras(bundle);
                sendBroadcast(intent1);
                Snackbar.make(v, "商品已添加到购物车", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
