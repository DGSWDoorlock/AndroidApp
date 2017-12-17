package com.dgsw.doorlock.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgsw.doorlock.adapter.ApproveRecyclerAdapter;
import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;

import java.util.ArrayList;

public class Approve extends Fragment {

    private RecyclerView recyclerView;
    private ApproveRecyclerAdapter approveRecyclerAdapter;
    private ArrayList<EntryInfo> entryList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Approve");
        View view = inflater.inflate(R.layout.activity_approve, container, false);

        recyclerView = view.findViewById(R.id.listview);

        entryList.add(new EntryInfo("홍길동","8월9일","8시", "9시"));
        entryList.add(new EntryInfo("김갑수","7월4일","11시", "12시"));
        entryList.add(new EntryInfo("유관순","5월9일","12시", "1시"));
        entryList.add(new EntryInfo("덜덜덜","7월10일","1시", "2시"));

        approveRecyclerAdapter = new ApproveRecyclerAdapter(entryList);
        recyclerView.setAdapter(approveRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
