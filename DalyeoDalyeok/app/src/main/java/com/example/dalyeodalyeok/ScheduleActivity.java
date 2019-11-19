package com.example.dalyeodalyeok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dalyeodalyeok.ui.home.HomeFragment;

public class ScheduleActivity extends AppCompatActivity {

    int year;
    int month;
    int day;

    String strY;
    String strM;
    String strD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

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


    public void btn_Click(View view)
    {
        TextView textView = (TextView)findViewById(R.id.textView2);
        EditText editText = (EditText)findViewById(R.id.schedule);


        textView.setText(editText.getText());
    }
}
