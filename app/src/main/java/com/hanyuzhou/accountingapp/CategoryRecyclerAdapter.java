package com.hanyuzhou.accountingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder> {

    // setting events
    public interface OnCategoryClickListener{
        void onItemClick(String category);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    private LayoutInflater mInflater;
    private Context mContext;

    private LinkedList<CategoryResBean> cellList = GlobalUtil.getInstance().costRes;

    private String selected="";
    private OnCategoryClickListener onCategoryClickListener;

    // Constructor
    public CategoryRecyclerAdapter(Context context){
        GlobalUtil.getInstance().setContext(context);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        selected = cellList.get(0).title;
    }

    // inner class
    class CategoryViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout background;
        ImageView imageView;
        TextView textView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.cell_background);
            imageView = itemView.findViewById(R.id.imageView_category);
            textView = itemView.findViewById(R.id.textView_category);

        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final CategoryResBean res = cellList.get(position);
        holder.imageView.setImageResource(res.resBlack);
        holder.textView.setText(res.title);
        // Define setOnClickListener method
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = res.title;
                notifyDataSetChanged();

                if (onCategoryClickListener!=null){
                    onCategoryClickListener.onItemClick(res.title);
                }

            }
        });

        if (holder.textView.getText().toString().equals(selected)){
            holder.background.setBackgroundResource(R.drawable.bg_edit_text);
        }
        else {
            holder.background.setBackgroundResource(R.color.colorPrimary);
        }

    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    public void changeType(RecordBean.RecordType type){
        if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
            cellList = GlobalUtil.getInstance().costRes;
        }else {
            cellList = GlobalUtil.getInstance().earnRes;
        }

        selected = cellList.get(0).title;
        notifyDataSetChanged();

    }

    public String getSelected() {
        return selected;
    }

}
