package com.dgsw.doorlock.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgsw.doorlock.R;

/**
 * Created by kimji on 2017-12-17.
 */

public class ApproveViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    ImageView okImageView, noImageView;
    TextView id, day, startTime, endTime;
    Button ok, no;

    public ApproveViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        okImageView = itemView.findViewById(R.id.checkView);
        noImageView = itemView.findViewById(R.id.closeView);
        id = itemView.findViewById(R.id.id);
        day = itemView.findViewById(R.id.day);
        startTime = itemView.findViewById(R.id.startTime);
        endTime = itemView.findViewById(R.id.endTime);
        ok = itemView.findViewById(R.id.ok);
        no = itemView.findViewById(R.id.no);
    }
}
