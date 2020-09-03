package com.example.ctistest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ctistest.R;
import com.example.ctistest.db.entity.CompanyName;
import com.example.ctistest.model.Response;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CompanyName> venuesArrayList;

    public RecyclerViewAdapter(ArrayList<CompanyName> venuesArrayList) {
        this.venuesArrayList = venuesArrayList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
holder.tvName.setText(venuesArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return venuesArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);

        }
    }
}
