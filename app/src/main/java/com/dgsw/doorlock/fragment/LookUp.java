package com.dgsw.doorlock.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.adapter.LookUpAdapter;
import com.dgsw.doorlock.data.EntryInfo;

import java.util.ArrayList;
import java.util.List;

public class LookUp extends AppCompatActivity {

    private  ListView listView ;
    private LookUpAdapter adapter;
    private List<EntryInfo> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_look_up);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listview);
        entryList  = new ArrayList<EntryInfo>();

        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));
        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));
        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));
        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));
        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));
        entryList.add(new EntryInfo("점점점","8/7","8시","10시"));



        adapter = new LookUpAdapter(getApplicationContext(),entryList);
        listView.setAdapter(adapter);

    }
}
