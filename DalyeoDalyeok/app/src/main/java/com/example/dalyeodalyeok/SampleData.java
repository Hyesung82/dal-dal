package com.example.dalyeodalyeok;

import android.widget.CheckBox;
import android.widget.Checkable;

import com.example.dalyeodalyeok.ui.MyAdapter;

public class SampleData {
    private String reports;
    public boolean isChecked = false;

    public void setTodo(String report){
        this.reports = report;
    }

    public String gettodo()
    {
        return reports;
    }

//    public boolean isChecked() {
//    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        MyAdapter.setChecked(checked);
    }
}