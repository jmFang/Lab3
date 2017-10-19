package com.example.jiamoufang.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private List<Product> shopList = new ArrayList<>();
    private boolean isMainPage = true;
    private ShopListAdapter shopListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);//成为订阅者
        initProducts();
        initShopList();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_product);
        final ListView listView = (ListView) findViewById(R.id.list_view_shopList);

        shopListAdapter = new ShopListAdapter(MainActivity.this, R.layout.product_in_shop_item, shopList);
        ProductAdapter adapter = new ProductAdapter( productList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

       listView.setAdapter(shopListAdapter);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(1000);
        recyclerView.setAdapter(animationAdapter);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = shopList.get(position);
                if (position > 0) { //购物车的第一个item是表头
                    Intent intent = new Intent(parent.getContext(), ProductDetails.class);
                    intent.putExtra("header", product.getProductHead());
                    intent.putExtra("name", product.getProductName());
                    intent.putExtra("price", product.getProductPrice());
                    intent.putExtra("type", product.getType());
                    intent.putExtra("info", product.getInformation());
                    intent.putExtra("image", product.getPicture());
                    parent.getContext().startActivity(intent);// 原来startActivity 启动也是要上下文的
                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = shopList.get(position);
                final int pos = position;
                if (position > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("移除商品");
                    builder.setMessage("从购物车移除 "+ product.getProductName()+" ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shopList.remove(pos);
                            shopListAdapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    return  true;
                }
                return false;
            }
        });

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMainPage) {
                    recyclerView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    fab.setImageResource(R.drawable.mainpage);
                    isMainPage = false;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    fab.setImageResource(R.drawable.shoplist);
                    isMainPage = true;
                }
            }
        });
}
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Product product) {
        shopList.add(product);
        shopListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initProducts() {
        Product product = new Product("E", "Enchated Forest","¥ 5.00","作者",  "Johanna Basford", R.drawable.enchated_forest);
        productList.add(product);
        product = new Product("A", "Arla Milk", "¥ 59.00", "产地", "德国", R.drawable.arla);
        productList.add(product);
        product = new Product("D", "Devondale Milk", "¥ 79.00", "产地", "澳大利亚",R.drawable.devondale);
        productList.add(product);
        product = new Product("K", "Kindle Oasis" ,"¥ 2399.00", "版本 ", "8GB",R.drawable.kindle );
        productList.add(product);
        product = new Product("W", "waitrose 早餐麦片", "¥ 179.00", "重量", "2Kg", R.drawable.waitrose);
        productList.add(product);
        product = new Product("M", "Mcvitie's 饼干", "¥ 14.90", "产地", "英国", R.drawable.mcvitie);
        productList.add(product);
        product = new Product("F", "Ferrero Rocher", "¥ 132.59", "重量", "300g",R.drawable.ferrero);
        productList.add(product);
        product = new Product("M", "Maltesers", "¥ 141.43", "重量","118g", R.drawable.maltesers);
        productList.add(product);
        product = new Product("L", "Lindt ","¥ 139.43", "重量", "249g", R.drawable.lindt);
        productList.add(product);
        product = new Product("B", "Borggreve","¥ 28.90", "重量","640g", R.drawable.borggreve);
        productList.add(product);
    }

    private void initShopList() {
        Product product = new  Product("*", "购物车", "价格", "", "",0);
        shopList.add(product);
        product = new  Product("D", "Devondale Milk", "¥ 79.00", "产地", "澳大利亚",R.drawable.devondale);
        shopList.add(product);
        product =  new Product("B", "Borggreve","¥ 28.90", "重量","640g", R.drawable.borggreve);
        shopList.add(product);
        product =  new Product("M", "Maltesers", "¥ 141.43", "重量","118g", R.drawable.maltesers);
        shopList.add(product);
    }

}
