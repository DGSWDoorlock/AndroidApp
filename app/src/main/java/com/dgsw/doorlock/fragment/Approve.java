package com.dgsw.doorlock.fragment;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgsw.doorlock.adapter.ApproveRecyclerAdapter;
import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.task.ApproveHTTPTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Approve extends Fragment {

    private ArrayList<EntryInfo> entryInfos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("출입 승인");
        View view = inflater.inflate(R.layout.fragment_approve, container, false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.layout_no_item);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        ApproveHTTPTask approveHTTPTask = new ApproveHTTPTask();
        approveHTTPTask.execute();
        try {
            entryInfos = approveHTTPTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList<EntryInfo> entryInfoArrayList = new ArrayList<>();
        for (int i = 0; i < entryInfos.size(); i++) {
            Log.d("test"+i,entryInfos.get(i).getState());
            if (entryInfos.get(i).getState().equals("0")) {
                entryInfoArrayList.add(entryInfos.get(i));
            }
        }
        if (entryInfoArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        } else {
            ApproveRecyclerAdapter approveRecyclerAdapter = new ApproveRecyclerAdapter(entryInfoArrayList);
            recyclerView.setAdapter(approveRecyclerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        return view;
    }
}
