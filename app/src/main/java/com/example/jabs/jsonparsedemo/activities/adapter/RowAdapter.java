package com.example.jabs.jsonparsedemo.activities.adapter;

/**
 * Created by jabs on 9/7/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jabs.jsonparsedemo.R;
import com.example.jabs.jsonparsedemo.activities.pojo.Popultion;

import java.util.ArrayList;


public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {

    private ArrayList<Popultion> mItems;
    private Context context;

    public RowAdapter(Context context, ArrayList<Popultion> arrayList) {
        this.context = context;
        this.mItems = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            if (mItems.get(i).getFlag() != null) {
                Glide.with(context).load(mItems.get(i).getFlag().toString())
                        .error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
                        .into(viewHolder.imgCountryFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            viewHolder.txtCountryName.setText(mItems.get(i).getCountry().toString());
            viewHolder.txtPopulation.setText(Html.fromHtml(mItems.get(i).getPopulation().toString()));
            viewHolder.txtRank.setText(mItems.get(i).getRank().toString());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCountryName, txtPopulation, txtRank;
        public ImageView imgCountryFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            imgCountryFlag = (ImageView) itemView.findViewById(R.id.imgCountryFlag);

            txtCountryName = (TextView) itemView.findViewById(R.id.txtCountryName);
            txtPopulation = (TextView) itemView.findViewById(R.id.txtPopulation);
            txtRank = (TextView) itemView.findViewById(R.id.txtRank);
        }
    }

}