package com.example.dalyeodalyeok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dalyeodalyeok.ui.home.HomeFragment;

public class ScheduleActivity extends AppCompatActivity {

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod_date;
    private TextView textView_Time;
    private TimePickerDialog.OnTimeSetListener callbackMethod_time;
    String selDate;
    String selTime;
    String selSchedule;


    public ScheduleActivity() {
    }
    public static final int sub = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        this.InitializeView_date();
        this.InitializeListener_date();
        this.InitializeView_time();
        this.InitializeListener_time();

        textView_Time = (TextView)findViewById(R.id.textView_time);

        Button finishBtn = findViewById(R.id.complete_button);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button OK = (Button)findViewById(R.id.OK);
        OK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(view.getId() == R.id.OK) {
                    btn_Click(view);
                }
            }
        });
    }
    public void InitializeView_date()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener_date()
    {
        callbackMethod_date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                int monthOfYear_update = monthOfYear + 1;
                selDate = Integer.toString(year) + "/" + Integer.toString(monthOfYear_update) + "/" + Integer.toString(dayOfMonth);

                textView_Date.setText(year + "년" + monthOfYear_update + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler_date(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_date, 2019, 5, 24);

        dialog.show();
    }
    public void InitializeView_time()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener_time()
    {
        callbackMethod_time = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {

                selTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                textView_Time.setText(hourOfDay + "시" + minute + "분");

            }
        };
    }
    public void OnClickHandler_time(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod_time, 8, 10, true);

        dialog.show();
    }


    public void btn_Click(View view)
    {
        TextView textView = (TextView)findViewById(R.id.textView2);
        EditText editText = (EditText)findViewById(R.id.schedule);

        selSchedule = editText.getText().toString();
        textView.setText(editText.getText());
    }
}
