package com.example.dalyeodalyeok.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dalyeodalyeok.R;

public class MyinfoFragment extends Fragment {

    private TextView tvMyinfo;

    static SharedPreferences sharedPreferences = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String strMyinfo = "";
        strMyinfo += sharedPreferences.getString("userName", "");
        strMyinfo += "\n";
        strMyinfo += sharedPreferences.getString("userPhone", "");
        strMyinfo += "\n";
        strMyinfo += sharedPreferences.getString("userEmail", "");
        strMyinfo += "\n";
        Log.d("userInfo", strMyinfo);

        tvMyinfo = (TextView)root.findViewById(R.id.tvMyinfo);
        tvMyinfo.setText(strMyinfo);

        return root;
    }
}
