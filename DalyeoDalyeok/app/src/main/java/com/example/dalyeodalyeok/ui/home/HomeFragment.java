package com.example.dalyeodalyeok.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dalyeodalyeok.DbOpenHelper;
import com.example.dalyeodalyeok.R;
import com.example.dalyeodalyeok.SampleData;
import com.example.dalyeodalyeok.ui.MyAdapter;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    public static Context mContext;

    static SharedPreferences sharedPreferences;

    private DbOpenHelper mDbOpenHelper;

    static int getYear, getMonth, getDay;

//    CheckBox checkBox1;

    ListView listView = null;
    MyAdapter myAdapter;
    ImageView background;

    String deleteKey = "";

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();

        myAdapter = new MyAdapter();
        listView = (ListView)root.findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        background = (ImageView)root.findViewById(R.id.ivBackground);
        String imageUri = sharedPreferences.getString("image", "없음");
        if (!imageUri.equals(""))
            background.setImageURI(Uri.parse(sharedPreferences.getString("image", "")));
        Log.d("이미지 uri", imageUri);

        String str = getDatabase();
        String[] result = str.split("\n");

        for (int i = 0; i < result.length; i++) {
            myAdapter.addItem(result[i]);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                SampleData item = (SampleData)parent.getItemAtPosition(position);
                deleteKey = item.gettodo();
                Log.d("삭제", deleteKey);
                Toast.makeText(getContext(), deleteKey, Toast.LENGTH_SHORT).show();

                // 체크박스 체크/해제하는 함수 만들기!
                if (item.isChecked == true) {
                    item.setChecked(false);

                }

                else
                    item.setChecked(true);
            }
        });

        mDbOpenHelper = new DbOpenHelper(getContext());
        mDbOpenHelper.open();
        Log.d("데이터베이스 가져오기", getDatabase());

        CalendarView calendarView = (CalendarView) root.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                String y = Integer.toString(year);
                String m = Integer.toString(month + 1);
                String d = Integer.toString(dayOfMonth);
                System.out.println("날짜 : " + y + "/" + m + "/" + d);

                OnClickHandler(y,m,d);

                Toast.makeText(HomeFragment.this.getContext(), "" + year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                getYear = year;
                getMonth = month + 1;
                getDay = dayOfMonth;
            }
        });

        mContext = this.getActivity();

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

            if (tempSubject.equals("할 일"))
                result += tempReport + "\n";
            else
                result += tempSubject + ": " + tempReport + "\n";
        }

        return result;
    }


    public void OnClickHandler(String y, String m, String d)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(y+"년"+m+"월"+d+"일");
        builder.setMessage("일정");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}