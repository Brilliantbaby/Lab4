package com.example.administrator.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Product> mProduct;
    private Context mContext;

    public MyAdapter(List<Product> mProduct, Context mContext){
        this.mProduct = mProduct;
        this.mContext = mContext;
    }

    @Override
    public Object getItem(int position) {
        if(mProduct==null){return null;}
        return mProduct.get(position);
    }

    @Override
    public int getCount() {
        if(mProduct==null){return 0;}
        return mProduct.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        View convertView;
        if (view == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.shopping_car_list,null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView) convertView.findViewById(R.id.shop_image);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.shop_name);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.shop_price);
            convertView.setTag(viewHolder);
        }
        else
        {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(mProduct.get(position).get_firstletter());
        viewHolder.textView2.setText(mProduct.get(position).get_name());
        viewHolder.textView3.setText(mProduct.get(position).get_price());
        return convertView;
    }

    private class ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public void add(Product data){
        if (mProduct == null) {
            mProduct = new ArrayList<>();
        }
        mProduct.add(data);
        notifyDataSetChanged();
    }
    public void remove(int position){
        mProduct.remove(position);
        notifyDataSetChanged();
    }
    public List<Product> get_mProduct(){
        return mProduct;
    }

}