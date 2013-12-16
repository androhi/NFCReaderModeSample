package com.androhi.nfcreadermodesampe;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private NfcAdapter mNfcAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            return;
        }

        mNfcAdapter.enableReaderMode(this, new CustomReaderCallback(), NfcAdapter.FLAG_READER_NFC_A, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mNfcAdapter.disableReaderMode(this);
    }

    private class CustomReaderCallback implements NfcAdapter.ReaderCallback {
        @Override
        public void onTagDiscovered(Tag tag) {
            byte[] rawid = tag.getId();
            final String idm = bytesToString(rawid);
            Log.d(TAG, "Tag ID: " + idm);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(idm);
                }
            });
        }
    }

    private String bytesToString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte bt : bytes) {
            int i = 0xFF & (int)bt;
            String str = Integer.toHexString(i);
            sb.append(str);
        }
        return sb.toString();
    }
}
