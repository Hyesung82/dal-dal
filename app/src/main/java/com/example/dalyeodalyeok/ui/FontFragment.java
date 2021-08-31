package com.example.dalyeodalyeok.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dalyeodalyeok.R;

public class FontFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_font, contatiner, false);
    }
}
