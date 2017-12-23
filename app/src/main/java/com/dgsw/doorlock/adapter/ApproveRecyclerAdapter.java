package com.dgsw.doorlock.adapter;

import android.animation.Animator;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.task.EntryHTTPTask;
import com.dgsw.doorlock.viewholder.ApproveViewHolder;

import java.util.ArrayList;

/**
 * Created by alsdb on 2017-11-11.
 */

public class ApproveRecyclerAdapter extends RecyclerView.Adapter<ApproveViewHolder> {

    private ArrayList<EntryInfo> entryInfos;

    private static Integer staticY = null;

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

        viewHolder.name.setText(item.getName());
        viewHolder.day.setText(item.getDate());
        viewHolder.startTime.setText(item.getClockStart());
        viewHolder.endTime.setText(item.getClockEnd());
        viewHolder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewImage = viewHolder.okImageView;


                final int x = viewHolder.cardView.getRight();
                final int y = viewHolder.cardView.getBottom();

                if (viewHolder.getAdapterPosition() == 0) {
                    staticY = viewHolder.cardView.getBottom();
                }

                final int startRadius = 0;
                final float endRadius = (float) Math.hypot(viewHolder.cardView.getWidth(), viewHolder.cardView.getHeight());

                Animator animator;

                if (staticY != null) { // 애니매이션이 완전히 채워지지 않은 상태로 끝나는 것을 방지
                    animator = ViewAnimationUtils.createCircularReveal(viewImage, x, staticY, startRadius, endRadius);
                } else {
                    animator = ViewAnimationUtils.createCircularReveal(viewImage, x, y, startRadius, endRadius);
                }

                viewHolder.okImageView.setVisibility(View.VISIBLE);
                EntryHTTPTask httpTask = new EntryHTTPTask(1);
                httpTask.execute(item);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        entryInfos.remove(viewHolder.getAdapterPosition()); //재생성 방지를 위한 값 삭제
                        notifyItemRemoved(viewHolder.getAdapterPosition()); //데이터 삭제
                        viewHolder.okImageView.setVisibility(View.INVISIBLE); //중복 방지
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                if (!animator.isRunning())
                    animator.start();
                Snackbar.make(view, item.getId() + "의 신청이 승인 됨", Snackbar.LENGTH_SHORT).show();
            }
        });
        viewHolder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View viewImage = viewHolder.noImageView;

                final int x = viewHolder.cardView.getRight();
                final int y = viewHolder.cardView.getBottom();

                if (viewHolder.getAdapterPosition() == 0) {
                    staticY = viewHolder.cardView.getBottom();
                }

                final int startRadius = 0;
                final float endRadius = (float) Math.hypot(viewHolder.cardView.getWidth(), viewHolder.cardView.getHeight());

                Animator animator;
                if (staticY != null) { // 애니매이션이 완전히 채워지지 않은 상태로 끝나는 것을 방지
                    animator = ViewAnimationUtils.createCircularReveal(viewImage, x, staticY, startRadius, endRadius);
                } else {
                    animator = ViewAnimationUtils.createCircularReveal(viewImage, x, y, startRadius, endRadius);
                }

                viewHolder.noImageView.setVisibility(View.VISIBLE);
                EntryHTTPTask httpTask = new EntryHTTPTask(-1);
                httpTask.execute(item);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        entryInfos.remove(viewHolder.getAdapterPosition()); //재생성 방지를 위한 값 삭제
                        notifyItemRemoved(viewHolder.getAdapterPosition()); //데이터 삭제
                        viewHolder.noImageView.setVisibility(View.INVISIBLE); //중복 방지
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                if (!animator.isRunning())
                    animator.start();
                Snackbar.make(view, item.getId() + "의 신청이 거절 됨", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryInfos.size();
    }
}
