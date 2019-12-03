package com.example.dalyeodalyeok;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dalyeodalyeok.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;

    private Context mContext;
    private FloatingActionButton fab;
    private FloatingActionButton fab_sub1, fab_sub2;

    private Animation fab_open, fab_close;

    private boolean isFabOpen = false;

    private DbOpenHelper mDbOpenHelper;

    static String userCheckList;
    static SharedPreferences sharedPreferences = null;

    HomeFragment homeFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mContext = getApplicationContext();
        setSupportActionBar(toolbar);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();

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
        Menu menu = navigationView.getMenu();
        MenuItem nav_login = menu.findItem(R.id.nav_lms);
        MenuItem nav_info = menu.findItem(R.id.nav_myinfo);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!sharedPreferences.contains("user")) {
            nav_login.setVisible(true);
            nav_info.setVisible(false);
            mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_lms, R.id.nav_background, R.id.nav_font)
                    .setDrawerLayout(drawer)
                    .build();
        } else {
            nav_login.setVisible(false);
            nav_info.setVisible(true);
            mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_myinfo, R.id.nav_background, R.id.nav_font)
                    .setDrawerLayout(drawer)
                    .build();
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
                break;
            case R.id.fab_sub2: // 일정추가 버튼
                toggleFab();
                Intent intentSchedule = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(intentSchedule);
                break;
        }
    }

        public void OnClickHandler()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add Check List");
            builder.setMessage("추가 할 내용을 입력해 주세요.");

            final EditText name = new EditText(this);
            builder.setView(name);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();

                    userCheckList = name.getText().toString();

                    mDbOpenHelper.insertColumn("할 일", userCheckList, 0);

//                    homeFragment.reloadList();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/pknu-wap/DalDal"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
