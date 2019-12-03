package com.example.dalyeodalyeok.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dalyeodalyeok.R;
import com.example.dalyeodalyeok.SampleData;
import com.example.dalyeodalyeok.ViewHolder;
import com.example.dalyeodalyeok.ui.home.HomeFragment;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private static ArrayList<SampleData> sample = new ArrayList<SampleData>();
    static ViewHolder viewHolder;
    static CheckBox oTextReport;

    public MyAdapter(HomeFragment homeFragment, int simple_list_item_multiple_choice, ArrayList<String> items) {

    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.check_box, null);

            viewHolder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkbox1));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setClickable(false);
        viewHolder.checkBox.setFocusable(false);
        viewHolder.checkBox.setChecked(false);
        viewHolder.checkBox.setChecked(((ListView)parent).isItemChecked(position));

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int getPosition = (Integer)buttonView.getTag();
//                sample.get(getPosition).setSelected(buttonView.isChecked());
                viewHolder.checkBox.setChecked(!isChecked);
            }
        });

//        viewHolder.checkBox.setChecked(item.isChecked());

        oTextReport = (CheckBox) convertView.findViewById(R.id.checkbox1);

        SampleData item = sample.get(position);
        oTextReport.setText(item.gettodo());

        return convertView;
    }

    public void addItem(String report) {
        SampleData item = new SampleData();

        item.setTodo(report);

        sample.add(item);
    }

    public void checkConfirm(int position) {
        SampleData item = sample.get(position);
//        item.setChecked(!item.isChecked());
    }

    public static void setChecked(int position, boolean checked) {
//        final int tempSize = sample.size();
        if (checked == true) {
            sample.get(position).setCheckBoxState(true);
        } else {
            sample.get(position).setCheckBoxState(false);
        }

    }
}