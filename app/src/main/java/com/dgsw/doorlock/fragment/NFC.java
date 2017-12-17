package com.dgsw.doorlock.fragment;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgsw.doorlock.R;
import com.dgsw.doorlock.tool.Preference;

public class NFC extends Fragment implements NfcAdapter.CreateNdefMessageCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("NFC");
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (mAdapter == null) {
            Snackbar.make(view, "이 장치가 NFC를 지원하지 않습니다.", Snackbar.LENGTH_SHORT).show();
        }

        if (!mAdapter.isEnabled()) {
            Snackbar.make(view, "NFC가 꺼저 있습니다. NFC를 켜주세요.", Snackbar.LENGTH_SHORT).show();
        }

        mAdapter.setNdefPushMessageCallback(this, getActivity());

        return view;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String RFID = new Preference(getActivity()).getString("RFID", "00000000"); //shared_pref.xml
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", RFID.getBytes());
        return new NdefMessage(ndefRecord);
    }
}
