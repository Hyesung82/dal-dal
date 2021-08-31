package com.example.dalyeodalyeok;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity {


    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod_date;
    private TextView textView_Time;
    private TimePickerDialog.OnTimeSetListener callbackMethod_time;
    private DbOpenHelper mDbOpenHelper;
    String selDate;
    String selTime;
    String selSchedule;


    public ScheduleActivity() {
    }

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
    SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
    SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
    SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
    String strCurYear= CurYearFormat.format(date);
    String strCurMonth = CurMonthFormat.format(date);
    String strCurDay = CurDayFormat.format(date);
    String strCurHour = CurHourFormat.format(date);
    String strCurMinute = CurMinuteFormat.format(date);

    int numYear = Integer.parseInt(strCurYear);
    int numMonth = Integer.parseInt(strCurMonth);
    int numDay = Integer.parseInt(strCurDay);
    int numHour = Integer.parseInt(strCurHour);
    int numMinute = Integer.parseInt(strCurMinute);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        Spinner s = (Spinner)findViewById(R.id.spinner);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        this.InitializeView_date();
        this.InitializeListener_date();
        this.InitializeView_time();
        this.InitializeListener_time();

        textView_Time = (TextView)findViewById(R.id.textView_time);

        mDbOpenHelper = new DbOpenHelper(getApplicationContext());
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Button finishBtn = findViewById(R.id.complete_button);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Click(v);

                mDbOpenHelper.insertSchedule(selDate, selTime, selSchedule);
                showSchedule();

                finish();
            }
        });
    }

    public void InitializeView_date() {
        textView_Date = (TextView) findViewById(R.id.textView_date);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);

        textView_Date.setText(year + "년 " + month + "월 " + day + "일 " + weekDay + "요일");

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
                Log.d("선택된 날짜", selDate);
                textView_Date.setText(year + "년" + monthOfYear_update+ "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler_date(View view)
    {
        int numMonth_update=numMonth-1;
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_date,numYear,numMonth_update,numDay  );

        dialog.show();
    }

    public void InitializeView_time()
    {
        textView_Time = (TextView)findViewById(R.id.textView_time);
      String inTime = new java.text.SimpleDateFormat("HH").format(new java.util.Date());
      int Time_h =Integer.parseInt(inTime);
      Time_h=Time_h+1;
      if(Time_h<=11)
          textView_Time.setText("오전 "+Integer.toString(Time_h-12)+" : 00");
      else
          textView_Time.setText("오후 "+Integer.toString(Time_h-12)+" : 00");
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
                Log.d("선택된 시간", selTime);
            }
        };
    }
    public void OnClickHandler_time(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod_time, numHour, numMinute, true);

        dialog.show();
    }

    public void btn_Click(View view)
    {
        EditText editText = (EditText)findViewById(R.id.schedule);
        selSchedule = editText.getText().toString();
    }

    public void showSchedule() {
        Cursor iCursor = mDbOpenHelper.sortSchedule("_id");
        StringBuilder result = new StringBuilder();
        while (iCursor.moveToNext()) {
            String tempDate = iCursor.getString(iCursor.getColumnIndex("date"));
            String tempTime = iCursor.getString(iCursor.getColumnIndex("time"));
            String tempSchedule = iCursor.getString(iCursor.getColumnIndex("schedule"));

            result.append(tempDate).append("$").append(tempTime).append("$").append(tempSchedule).append("\n");
        }

        Log.d("스케줄", result.toString());
    }

}
