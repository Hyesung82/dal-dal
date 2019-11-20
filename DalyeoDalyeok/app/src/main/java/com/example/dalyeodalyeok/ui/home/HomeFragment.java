package com.example.dalyeodalyeok.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dalyeodalyeok.MainActivity;
import com.example.dalyeodalyeok.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;
<<<<<<< HEAD
    public static Context mContext;

    static int getYear, getMonth, getDay;
=======
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
>>>>>>> b862339f5091f0f26dfbf7be0856b124500c8de8

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        CalendarView calendarView = (CalendarView) root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Toast.makeText(HomeFragment.this.getContext(), "" + year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);

                getYear = year;
                getMonth = month + 1;
                getDay = dayOfMonth;

                String y = Integer.toString(year);
                String m = Integer.toString(month + 1);
                String d = Integer.toString(dayOfMonth);
                System.out.println("날짜 : " + y + "/" + m + "/" + d);
                intent.putExtra("calendarYear", y);
                intent.putExtra("calendarMonth", m);
                intent.putExtra("calendarDay", d);
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        mContext = this.getActivity();
=======
            checkBox1 = (CheckBox)root.findViewById(R.id.checkbox1);
            checkBox2 = (CheckBox)root.findViewById(R.id.checkbox2);
            checkBox3 = (CheckBox)root.findViewById(R.id.checkbox3);
            checkBox4 = (CheckBox)root.findViewById(R.id.checkbox4);
            checkBox5 = (CheckBox)root.findViewById(R.id.checkbox5);

            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox1.isChecked()){
                        Toast.makeText(container.getContext(), checkBox1.getText()+" checked", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(container.getContext(), checkBox1.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkBox2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox2.isChecked()){
                        Toast.makeText(container.getContext(), checkBox2.getText()+" checked", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(container.getContext(), checkBox2.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkBox3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox3.isChecked()){
                        Toast.makeText(container.getContext(), checkBox3.getText()+" checked", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(container.getContext(), checkBox3.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkBox4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox4.isChecked()){
                        Toast.makeText(container.getContext(), checkBox4.getText()+" checked", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(container.getContext(), checkBox4.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkBox5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox5.isChecked()){
                        Toast.makeText(container.getContext(), checkBox5.getText()+" checked", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(container.getContext(), checkBox5.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
>>>>>>> b862339f5091f0f26dfbf7be0856b124500c8de8

        return root;
    }

<<<<<<< HEAD
    public static String getMyDate () {
        String strDate = getYear + "/" + getMonth + "/" + getDay;
        return strDate;
    }
=======

>>>>>>> b862339f5091f0f26dfbf7be0856b124500c8de8
}