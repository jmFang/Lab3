package com.example.jiamoufang.lab3;

import android.widget.ImageView;

/**
 * Created by jiamoufang on 2017/10/17.
 */

public class Product {
    private String productHead;
    private String productName;
    private String productPrice;
    private String type;
    private String information;
    private int imageId;

    public Product(String productHead, String productName, String productPrice, String type, String information, int imageId) {
        this.productHead = productHead;
        this.productName = productName;
        this.type = type;
        this.information = information;
        this.imageId = imageId;
        this.productPrice = productPrice;
    }

    public String getProductHead() {
        return productHead;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public String getInformation() {
        return information;
    }

    public int getPicture() {
        return imageId;
    }
    public String getProductPrice() {
        return productPrice;
    }
}
