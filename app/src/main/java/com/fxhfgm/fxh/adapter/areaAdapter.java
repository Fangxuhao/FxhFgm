package com.fxhfgm.fxh.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fxhfgm.fxh.R;
import com.fxhfgm.fxh.domain.City;

import java.util.List;

/**
 * 方徐浩
 */
public class areaAdapter extends RecyclerView.Adapter<areaAdapter.ViewHolder> {
    private List<String> mDataList;
private Context context;
    public areaAdapter(List<String> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.area_item,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String string = mDataList.get(i);
        viewHolder.textView.setText(string);

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListent(OnItemClickListener onItemClickListent) {
        this.onItemClickListener = onItemClickListent;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.area_item_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(v,getLayoutPosition());
                    }
                }
            });
        }
    }
}
