package com.example.dalyeodalyeok.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dalyeodalyeok.R;

public class BackgroundFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_background, contatiner, false);

        return root;
    }
}
