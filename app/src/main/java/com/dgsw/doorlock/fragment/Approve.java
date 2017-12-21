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

import com.dgsw.doorlock.adapter.ApproveRecyclerAdapter;
import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.task.ApproveHTTPTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.dgsw.doorlock.activity.Login.isDEBUG;

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
        getActivity().setTitle("출입 승인");
        View view = inflater.inflate(R.layout.activity_approve, container, false);

        recyclerView = view.findViewById(R.id.listview);
        if (!isDEBUG) { //FIXME
            ApproveHTTPTask approveHTTPTask = new ApproveHTTPTask();
            approveHTTPTask.execute();

            try {
                entryList = approveHTTPTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            entryList.add(new EntryInfo("AA", "            ", "            ", "            "));
            entryList.add(new EntryInfo("BB", "            ", "            ", "            "));
            entryList.add(new EntryInfo("CC", "            ", "            ", "            "));
            entryList.add(new EntryInfo("DD", "            ", "            ", "            "));
            entryList.add(new EntryInfo("EE", "            ", "            ", "            "));
            entryList.add(new EntryInfo("FF", "            ", "            ", "            "));
            entryList.add(new EntryInfo("GG", "            ", "            ", "            "));
        }
        approveRecyclerAdapter = new ApproveRecyclerAdapter(entryList);
        recyclerView.setAdapter(approveRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}
