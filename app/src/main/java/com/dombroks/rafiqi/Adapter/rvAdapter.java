package com.dombroks.rafiqi.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.MyView> {

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyView extends RecyclerView.ViewHolder {

        public MyView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
