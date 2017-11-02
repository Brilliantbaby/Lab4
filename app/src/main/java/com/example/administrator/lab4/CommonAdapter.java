package com.example.administrator.lab4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder>
{
    private List<Product> mProductList;
    private OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View RView;
        TextView Rfirstletter;
        TextView Rname;
        public ViewHolder(View view){
            super(view);
            RView = view;
            Rfirstletter = (TextView) view.findViewById(R.id.first_letter);
            Rname = (TextView) view.findViewById(R.id.name);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public CommonAdapter(List<Product> productList)
    {
        mProductList = productList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder,final int position) {

        Product product = mProductList.get(position);
        holder.Rfirstletter.setText(product.get_firstletter());
        holder.Rname.setText(product.get_name());

        if (mOnItemClickListener != null) {
            //点击事件的监听
            holder.RView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            //长按事件的监听
            holder.RView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }
    @Override
    public int getItemCount(){
        return mProductList.size();
    }
    public void remove(int position) {
        mProductList.remove(position);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
        if (position !=mProductList.size()) {
            notifyItemRangeChanged(position, mProductList.size() - position);
        }
    }
    public List<Product> getmProductList(){
        return mProductList;
    }
}