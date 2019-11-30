package com.example.dalyeodalyeok.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dalyeodalyeok.DbOpenHelper;
import com.example.dalyeodalyeok.MainActivity;
import com.example.dalyeodalyeok.R;
import com.example.dalyeodalyeok.SampleData;
import com.example.dalyeodalyeok.ui.MyAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    public static Context mContext;

    static SharedPreferences sharedPreferences;

    private DbOpenHelper mDbOpenHelper;

    static int getYear, getMonth, getDay;

//    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;

//    String checkListIndex = MainActivity.getList();

    ListView listView = null;
    ArrayList<SampleData> movieDataList = new ArrayList<>();
    MyAdapter myAdapter;

    String deleteKey = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //this.InitializeMovieData();
        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();

        myAdapter = new MyAdapter();
        listView = (ListView)root.findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        String str = getDatabase();
        String[] result = str.split("\n");

        for (int i = 0; i < result.length; i++) {
            SampleData oItem = new SampleData();
            myAdapter.addItem(result[i]);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                SampleData item = (SampleData)parent.getItemAtPosition(position);
                deleteKey = item.gettodo();
                Log.d("111", deleteKey);
                //Toast.makeText(getContext(), myAdapter.getItem(position).gettodo(), Toast.LENGTH_SHORT).show();
                Log.d("클릭 테스트", position + " 클릭됨");
            }
        });

        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();
        Log.d("데이터베이스 가져오기", getDatabase());
//        checkListIndex = getActivity().getIntent().getStringExtra("userCheckList");
//        System.out.println("체크리스트 가져오기 : " + checkListIndex);

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

        mContext = this.getActivity();

//        checkBox1 = (CheckBox)root.findViewById(R.id.checkbox1);
//        checkBox2 = (CheckBox)root.findViewById(R.id.checkbox2);
//        checkBox3 = (CheckBox)root.findViewById(R.id.checkbox3);
//        checkBox4 = (CheckBox)root.findViewById(R.id.checkbox4);
//        checkBox5 = (CheckBox)root.findViewById(R.id.checkbox5);
//
//        checkBox1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox1.isChecked()){
//                    Toast.makeText(container.getContext(), checkBox1.getText()+" checked", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(container.getContext(), checkBox1.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        checkBox2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox2.isChecked()){
//                    Toast.makeText(container.getContext(), checkBox2.getText()+" checked", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(container.getContext(), checkBox2.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        checkBox3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox3.isChecked()){
//                    Toast.makeText(container.getContext(), checkBox3.getText()+" checked", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(container.getContext(), checkBox3.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        checkBox4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox4.isChecked()){
//                    Toast.makeText(container.getContext(), checkBox4.getText()+" checked", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(container.getContext(), checkBox4.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        checkBox5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox5.isChecked()){
//                    Toast.makeText(container.getContext(), checkBox5.getText()+" checked", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(container.getContext(), checkBox5.getText()+" Unchecked", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
        return root;
    }

    public static String getMyDate () {
        String strDate = getYear + "/" + getMonth + "/" + getDay;
        return strDate;
    }

    public String getDatabase() {
        Cursor iCursor = mDbOpenHelper.findUnchecked();
        String result = "";
        while (iCursor.moveToNext()) {
            String tempSubject = iCursor.getString(iCursor.getColumnIndex("subject"));
            String tempReport = iCursor.getString(iCursor.getColumnIndex("report"));

            result += tempSubject + "$" + tempReport + "\n";
        }

        return result;
    }

    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();
        String str = getDatabase();

        String[] result = str.split("\n");
        for (int i = 0; i < result.length; i++) {
            myAdapter.addItem(result[i]);
        }
    }
}