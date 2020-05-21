package com.dombroks.rafiqi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dombroks.rafiqi.Model.Surrah;
import com.dombroks.rafiqi.R;

import java.util.ArrayList;
import java.util.List;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.MyView> {
    private List<Surrah> surrahs = new ArrayList<>();

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new MyView(v);
    }


    public rvAdapter(List<Surrah> surrahs) {
        this.surrahs = surrahs;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Surrah surrah = surrahs.get(position);
        holder.surrahType.setText(surrah.getType());
        holder.surrahId.setText(String.valueOf(surrah.getId()));
        holder.surrahEng.setText(surrah.getNameEng());
        holder.surrahAr.setText(surrah.getNameAr());
        holder.ayatNumber.setText(String.valueOf(surrah.getAyaNumber()));

    }

    @Override
    public int getItemCount() {
        return surrahs.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView surrahId, surrahAr, surrahEng, ayatNumber, surrahType;

        public MyView(@NonNull View itemView) {
            super(itemView);
            this.ayatNumber = itemView.findViewById(R.id.ayatNumber);
            this.surrahAr = itemView.findViewById(R.id.SurrahNameAr);
            this.surrahEng = itemView.findViewById(R.id.SurrahNameEng);
            this.surrahId = itemView.findViewById(R.id.SurrahID);
            this.surrahType = itemView.findViewById(R.id.SurrahType);
        }
    }
}
