package com.dgsw.doorlock.viewholder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dgsw.doorlock.R;

/**
 * Created by kimji on 2017-12-21.
 */

public class NFCListViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout constraintLayout;
    public TextView day, startTime, endTime;

    public NFCListViewHolder(View itemView) {
        super(itemView);
        constraintLayout = itemView.findViewById(R.id.layout);
        day = itemView.findViewById(R.id.day);
        startTime = itemView.findViewById(R.id.startTime);
        endTime = itemView.findViewById(R.id.endTime);
    }
}
