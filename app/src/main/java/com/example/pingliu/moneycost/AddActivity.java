package com.example.pingliu.moneycost;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private TextView edDate;
    private Spinner edInfo;
    private EditText edAmount;
    private int saveYear, saveMonth, saveDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();

    }

    private void findViews() {
        edInfo =  findViewById(R.id.ed_info);
        edAmount = findViewById(R.id.ed_amount);
        edDate =  findViewById(R.id.ed_date);
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        saveYear = year;
                        saveMonth = month + 1;
                        saveDay = day;
                        edDate.setText(saveYear + " 年 " + saveMonth + " 月 " + saveDay + " 日 ");

                    }
                }, mYear, mMonth, mDay).show();

            }

        });
    }

    public void add(View v) {

        String datetime = saveYear + "-" + saveMonth + "-" + saveDay;
        String name = edInfo.getSelectedItem().toString();
        String money = "$" + edAmount.getText().toString().trim();
        if (datetime.equals("0-0-0")||name.equals("")||money.equals("")) {
            Toast.makeText(this, "資料未齊全", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("datetime",datetime );
        values.put("name",name );
        values.put("money",money );
        MyDBHelper.getInstances(AddActivity.this).insert(datetime,name,money);

        finish();

    }

}