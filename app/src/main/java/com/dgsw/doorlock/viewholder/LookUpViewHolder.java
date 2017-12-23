package com.dgsw.doorlock.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgsw.doorlock.R;

/**
 * Created by kimji on 2017-12-21.
 */

public class LookUpViewHolder extends RecyclerView.ViewHolder {

    public ImageView statusView;
    public TextView date, startTime, endTime;

    public LookUpViewHolder(View itemView) {
        super(itemView);
        statusView = itemView.findViewById(R.id.statusView);
        date = itemView.findViewById(R.id.date);
        startTime = itemView.findViewById(R.id.startTime);
        endTime = itemView.findViewById(R.id.endTime);
    }
}