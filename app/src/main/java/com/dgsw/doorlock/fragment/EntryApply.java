package com.dgsw.doorlock.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.data.EntryInfo;
import com.dgsw.doorlock.tool.task.EntryHTTPTask;
import com.dgsw.doorlock.tool.task.GetRFIDTask;
import com.dgsw.doorlock.tool.Preference;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class EntryApply extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView dateText, timeStartText, timeEndText;
    String date;
    String startTime, endTime;
    TimePickerDialog startTimePickerDialog, endTimePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("EntryApply");
        View view = inflater.inflate(R.layout.fragment_entry_apply, container, false);

        final EditText idText = view.findViewById(R.id.idText);
        final Calendar calendar = Calendar.getInstance();

        dateText = view.findViewById(R.id.dateText);
        timeStartText = view.findViewById(R.id.timeStartText);
        timeEndText = view.findViewById(R.id.timeEndText);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EntryApply.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                //FIXME
                dpd.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 10);
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        timeStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                startTimePickerDialog = TimePickerDialog.newInstance(
                        EntryApply.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                startTimePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
                startTimePickerDialog.show(getActivity().getFragmentManager(), "StartTimepickerdialog");
            }
        });

        timeEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                endTimePickerDialog = TimePickerDialog.newInstance(
                        EntryApply.this,
                        now.get(Calendar.HOUR_OF_DAY) + 1,
                        now.get(Calendar.MINUTE),
                        false
                );
                endTimePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
                endTimePickerDialog.show(getActivity().getFragmentManager(), "EndTimepickerdialog");
            }
        });

        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idText.getText().toString() == null) return;
                final String id = idText.getText().toString();

                EntryInfo info = new EntryInfo(id, date, startTime, endTime);

                EntryHTTPTask httpTask = new EntryHTTPTask(0);
                httpTask.execute(info);

                try {
                    if (httpTask.get()) {
                        Snackbar.make(view, "신청 성공", Snackbar.LENGTH_SHORT).show();
                        SystemClock.sleep(1000);
                        GetRFIDTask getRFIDTask = new GetRFIDTask(info.getId());
                        getRFIDTask.execute();
                        String RFID = getRFIDTask.get();
                        new Preference(getContext()).putString("RFID", RFID);
                        idText.setText("");
                        dateText.setText("날짜 선택");
                        timeStartText.setText("시작 시간 선택");
                        timeEndText.setText("끝 시간 선택");

                    } else {
                        Snackbar.make(view, "신청 실패", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                //11월 25일 -> 11.25
            }
        });
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        dateText.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
        if (view == startTimePickerDialog) {
            startTime = String.format("%sT%s:%s", date, String.format("%02d", hourOfDay), String.format("%02d", minute));
            timeStartText.setText(String.format("%s:%s", String.format("%02d", hourOfDay), String.format("%02d", minute)));
        } else if (view == endTimePickerDialog) {
            endTime = date + "T" + String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
            timeEndText.setText(String.format("%s:%s", String.format("%02d", hourOfDay), String.format("%02d", minute)));
        }
        //timeTextView.setText(time);
    }
}