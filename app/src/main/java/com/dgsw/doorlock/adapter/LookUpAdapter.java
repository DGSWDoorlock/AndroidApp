package com.dgsw.doorlock.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by alsdb on 2017-12-20.
 */

public class LookUpAdapter  extends  BaseAdapter{
    private Context context;
    private List<EntryInfo> entryList;

    public LookUpAdapter (Context context, List<EntryInfo> entryList){
        this.context = context;
        this.entryList = entryList;
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Object getItem(int i) {
        return entryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.row_look_up , null);
        TextView id = v.findViewById(R.id.id);
        TextView day = v.findViewById(R.id.day);
        TextView time = v.findViewById(R.id.time);

        id.setText(entryList.get(i).getId());
        day.setText(entryList.get(i).getDate());
        time.setText(entryList.get(i).getClockStart());

        v.setTag(entryList.get(i).getId());
        return v;
    }
}
