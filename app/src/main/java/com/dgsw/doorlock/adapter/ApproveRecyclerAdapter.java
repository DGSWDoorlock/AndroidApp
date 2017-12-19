package com.dgsw.doorlock.adapter;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;

import java.util.ArrayList;

/**
 * Created by alsdb on 2017-11-11.
 */

public class ApproveRecyclerAdapter extends RecyclerView.Adapter<ApproveViewHolder> {

    private ArrayList<EntryInfo> entryInfos;

    public ApproveRecyclerAdapter(ArrayList<EntryInfo> entryInfos) {
        this.entryInfos = entryInfos;
    }

    @Override
    public ApproveViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_approve, viewGroup, false);

        return new ApproveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ApproveViewHolder viewHolder, final int position) {
        final EntryInfo item = entryInfos.get(position);
        final Animator[] anim = new Animator[2];
        viewHolder.cardView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                int x = viewHolder.cardView.getRight();
                int y = viewHolder.cardView.getBottom();

                int startRadius = 0;
                int endRadius = (int) Math.hypot(viewHolder.cardView.getWidth(), viewHolder.cardView.getHeight());

                anim[0] = ViewAnimationUtils.createCircularReveal(viewHolder.okImageView, x, y, startRadius, endRadius);
                anim[1] = ViewAnimationUtils.createCircularReveal(viewHolder.noImageView, x, y, startRadius, endRadius);
            }
        });

        viewHolder.id.setText(item.getId());
        viewHolder.day.setText(item.getDate());
        viewHolder.startTime.setText(item.getClockStart());
        viewHolder.endTime.setText(item.getClockEnd());
        viewHolder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.okImageView.setVisibility(View.VISIBLE);
                anim[0].start();
                Snackbar.make(view, item.getId() + "의 신청이 승인 됨", Snackbar.LENGTH_SHORT).show();
                anim[0].addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        entryInfos.remove(viewHolder.getAdapterPosition()); //재생성 방지를 위한 값 삭제
                        notifyItemRemoved(viewHolder.getAdapterPosition()); //데이터 삭제
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
        viewHolder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.noImageView.setVisibility(View.VISIBLE);
                anim[1].start();
                Snackbar.make(view, item.getId() + "의 신청이 거절 됨", Snackbar.LENGTH_SHORT).show();
                anim[1].addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        entryInfos.remove(viewHolder.getAdapterPosition()); //재생성 방지를 위한 값 삭제
                        notifyItemRemoved(viewHolder.getAdapterPosition()); //데이터 삭제
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryInfos.size();
    }
}
