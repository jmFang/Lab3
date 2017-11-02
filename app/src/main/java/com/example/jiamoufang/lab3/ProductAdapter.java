package com.example.jiamoufang.lab3;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jiamoufang on 2017/10/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mProductList;
/*    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }*/

    static class ViewHolder extends RecyclerView.ViewHolder {
        View product;
        TextView productHead;
        TextView productName;

        public ViewHolder(View itemView) {
            super(itemView);
            product = itemView;
            productHead =  itemView.findViewById(R.id.product_head);
            productName =  itemView.findViewById(R.id.product_name);
        }
    }

    public ProductAdapter(List<Product> productList) {
        this.mProductList = productList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.productHead.setText(product.getProductHead());
        holder.productName.setText(product.getProductName());
/*        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }*/
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
       final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Product product = mProductList.get(position);
                Intent intent = new Intent(parent.getContext(), ProductDetails.class);
                intent.putExtra("header", product.getProductHead());
                intent.putExtra("name", product.getProductName());
                intent.putExtra("price", product.getProductPrice());
                intent.putExtra("type", product.getType());
                intent.putExtra("info", product.getInformation());
                intent.putExtra("imageId", product.getPicture());
                parent.getContext().startActivity(intent);// 原来startActivity 启动也是要上下文的
            }
        });
        viewHolder.product.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                mProductList.remove(position);
                notifyItemRemoved(position);
                //Toast.makeText(parent.getContext(), "移除第"+ (position+1) + "个商品", Toast.LENGTH_SHORT).show();
                Snackbar.make(parent, "移除第"+ (position+1) + "个商品", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
