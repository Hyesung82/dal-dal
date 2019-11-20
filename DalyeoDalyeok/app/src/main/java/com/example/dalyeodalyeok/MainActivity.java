package com.example.dalyeodalyeok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;

    private Context mContext;
    private  FloatingActionButton fab;
    private FloatingActionButton fab_sub1, fab_sub2;

    private Animation fab_open, fab_close;

    private boolean isFabOpen = false;

    int year;
    int month;
    int day;

    String strY;
    String strM;
    String strD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();
        setSupportActionBar(toolbar);
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);

        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab = findViewById(R.id.fab);
        fab_sub1 = (FloatingActionButton) findViewById(R.id.fab_sub1);

        fab_sub2 = (FloatingActionButton) findViewById(R.id.fab_sub2);
        fab.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_lms, R.id.nav_background, R.id.nav_font)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        year = getIntent().getIntExtra("calendarYear", 1999);
//        month = getIntent().getIntExtra("calendarMonth", 4);
//        day = getIntent().getIntExtra("calendarDay", 19);
//
        strY = getIntent().getStringExtra("calendarYear");
        strM = getIntent().getStringExtra("calendarMonth");
        strD = getIntent().getStringExtra("calendarDay");
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab:

                toggleFab();

                break;

            case R.id.fab_sub1: // add check list button - 팝업창

                toggleFab();

                OnClickHandler();
                Toast.makeText(this, "Camera Open-!", Toast.LENGTH_SHORT).show();

                break;



            case R.id.fab_sub2: // 일정추가 버튼

                toggleFab();

                Toast.makeText(this, "Map Open-!", Toast.LENGTH_SHORT).show();
                Intent intentSchedule = new Intent(MainActivity.this, ScheduleActivity.class);
//                intentSchedule.putExtra("year", year);
//                intentSchedule.putExtra("month", month);
//                intentSchedule.putExtra("day", day);
                intentSchedule.putExtra("strY", strY);
                intentSchedule.putExtra("strM", strM);
                intentSchedule.putExtra("strD", strD);
                startActivity(intentSchedule);

                break;

        }

    }

        public void OnClickHandler()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("버튼추가");
            builder.setMessage("Plz, input yourname");

            final EditText name = new EditText(this);
            builder.setView(name);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                    String username = name.getText().toString();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "Neutral Click", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    private void toggleFab() {

        if (isFabOpen) {

            fab_sub1.startAnimation(fab_close);

            fab_sub2.startAnimation(fab_close);

            fab_sub1.setClickable(false);

            fab_sub2.setClickable(false);

            isFabOpen = false;

        } else {

//            fab.setImageResource(R.drawable.ic_close);

            fab_sub1.startAnimation(fab_open);

            fab_sub2.startAnimation(fab_open);

            fab_sub1.setClickable(true);

            fab_sub2.setClickable(true);

            isFabOpen = true;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
