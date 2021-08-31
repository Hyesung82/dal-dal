package com.example.dalyeodalyeok;

import android.widget.CheckBox;

public class SampleData {
    private String reports;
    CheckBox checkBox;
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

//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }

    public void setCheckBoxState(boolean checkBoxState) {
        this.isChecked = checkBoxState;
    }

    public void checkBoxSet(boolean checked) {
        this.checkBox.setChecked(checked);
    }
}