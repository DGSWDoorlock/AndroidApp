package com.dgsw.doorlock.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.adapter.LookUpRecyclerAdapter;
import com.dgsw.doorlock.data.EntryInfo;

import java.util.ArrayList;

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

        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "0"));
        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "0"));
        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "-1"));
        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "-1"));
        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "1"));
        entryInfos.add(new EntryInfo("점점점", "준준준", "8/7", "8시", "10시", "1"));


        LookUpRecyclerAdapter lookUpRecyclerAdapter = new LookUpRecyclerAdapter(getActivity(), entryInfos);
        recyclerView.setAdapter(lookUpRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
