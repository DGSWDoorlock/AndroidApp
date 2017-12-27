package com.dgsw.doorlock.fragment;

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

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.adapter.LookUpRecyclerAdapter;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.task.GetEntryHTTPTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LookUp extends Fragment {

    private ArrayList<EntryInfo> entryInfos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("기록");
        View view = inflater.inflate(R.layout.fragment_look_up, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        GetEntryHTTPTask getEntryHTTPTask = new GetEntryHTTPTask();
        getEntryHTTPTask.execute();

        try {
            entryInfos = getEntryHTTPTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        LookUpRecyclerAdapter lookUpRecyclerAdapter = new LookUpRecyclerAdapter(getActivity(), entryInfos);
        recyclerView.setAdapter(lookUpRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
