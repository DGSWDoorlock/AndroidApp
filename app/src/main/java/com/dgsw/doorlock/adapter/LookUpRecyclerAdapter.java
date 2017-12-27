package com.dgsw.doorlock.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.viewholder.LookUpViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alsdb on 2017-12-20.
 */

public class LookUpRecyclerAdapter extends RecyclerView.Adapter<LookUpViewHolder> {


    private Activity activity;
    private ArrayList<EntryInfo> entryInfos;

    public LookUpRecyclerAdapter(Activity activity, ArrayList<EntryInfo> entryInfos) {
        this.activity = activity;
        this.entryInfos = entryInfos;
    }

    @Override
    public LookUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_look_up, parent, false);
        return new LookUpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LookUpViewHolder holder, int position) {
        final EntryInfo item = entryInfos.get(position);

        switch (item.getState()) {
            case "-1":
                holder.statusView.setImageResource(R.drawable.ic_close);
                holder.statusView.setContentDescription(activity.getString(R.string.approve_no));
                holder.statusView.setColorFilter(ContextCompat.getColor(activity, R.color.no), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case "0":
                holder.statusView.setImageResource(R.drawable.ic_help);
                holder.statusView.setContentDescription(activity.getString(R.string.not_approved));
                holder.statusView.setColorFilter(ContextCompat.getColor(activity, R.color.md_black_1000), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case "1":
                holder.statusView.setImageResource(R.drawable.ic_check);
                holder.statusView.setContentDescription(activity.getString(R.string.approve_ok));
                holder.statusView.setColorFilter(ContextCompat.getColor(activity, R.color.ok), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
        }

        holder.date.setText(item.getDate());
        holder.startTime.setText(item.getClockStart());
        holder.endTime.setText(item.getClockEnd());
    }

    @Override
    public int getItemCount() {
        return entryInfos.size();
    }
}
