package com.example.dalyeodalyeok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dalyeodalyeok.ui.home.HomeFragment;

public class ScheduleActivity extends AppCompatActivity {

    //int year;
    //int month;
    //int day;

    String strY;
    String strM;
    String strD;

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        this.InitializeView();
        this.InitializeListener();

//        year = getIntent().getIntExtra("year", 2000);
//        month = getIntent().getIntExtra("month", 8);
//        day = getIntent().getIntExtra("day", 4);

//        year = getIntent().getIntExtra("strY", 2000);
//        month = getIntent().getIntExtra("strM", 8);
//        day = getIntent().getIntExtra("strD", 4);

        strY = getIntent().getStringExtra("strY");
        strM = getIntent().getStringExtra("strM");
        strD = getIntent().getStringExtra("strD");


       // Toast.makeText(getApplicationContext(), year+"/"+month+"/"+day, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), strY+"/"+strM+"/"+strD, Toast.LENGTH_LONG).show();

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
    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_Date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }

    public void btn_Click(View view)
    {
        TextView textView = (TextView)findViewById(R.id.textView2);
        EditText editText = (EditText)findViewById(R.id.schedule);


        textView.setText(editText.getText());
    }
}
