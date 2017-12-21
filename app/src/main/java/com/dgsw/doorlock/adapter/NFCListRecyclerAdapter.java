package com.dgsw.doorlock.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.activity.Main;
import com.dgsw.doorlock.data.NFCInfo;
import com.dgsw.doorlock.viewholder.NFCListViewHolder;

import java.util.ArrayList;

/**
 * Created by kimji on 2017-12-21.
 */

public class NFCListRecyclerAdapter extends RecyclerView.Adapter<NFCListViewHolder> {

    private ArrayList<NFCInfo> nfcInfos;
    private Activity activity;

    public NFCListRecyclerAdapter(ArrayList<NFCInfo> nfcInfos, Activity activity) {
        this.nfcInfos = nfcInfos;
        this.activity = activity;
    }

    @Override
    public NFCListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nfc, parent, false);
        return new NFCListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NFCListViewHolder holder, int position) {
        final NFCInfo item = nfcInfos.get(position);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Main)activity).selectNFC(item.getRFID());
            }
        });
        holder.day.setText(item.getDate());
        holder.startTime.setText(item.getClockStart());
        holder.endTime.setText(item.getClockEnd());
    }

    @Override
    public int getItemCount() {
        return nfcInfos.size();
    }
}
