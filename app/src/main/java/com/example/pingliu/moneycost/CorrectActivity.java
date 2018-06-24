package com.example.pingliu.moneycost;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CorrectActivity extends AppCompatActivity {
    private int id;
    private TextView chDate;
    private EditText chInfo;
    private EditText chAmount;
    private int saveYear, saveMonth, saveDay;
    private boolean dateClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);
        findViews();
        id = getIntent().getIntExtra("id",0);

    }

    private void findViews() {
        chInfo = (EditText) findViewById(R.id.ch_info);
        chAmount = (EditText) findViewById(R.id.ch_amount);
        chDate = (TextView) findViewById(R.id.ch_date);
        chDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(
                        CorrectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        saveYear = year;
                        saveMonth = month + 1;
                        saveDay = day;
                        dateClick = true;
                        chDate.setText(saveYear + " 年 " + saveMonth + " 月 " + saveDay + " 日 ");

                    }
                }, mYear, mMonth, mDay).show();

            }

        });
    }

    public void correct(View v) {

        String datetime = saveYear + "-" + saveMonth + "-" + saveDay;
        String name = chInfo.getText().toString();
        String money = chAmount.getText().toString().trim();
        if (datetime.equals("0-0-0")||name.equals("")||money.equals("")) {
            Toast.makeText(this, "資料未齊全", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("datetime",datetime );
        values.put("name",name );
        values.put("money",money );
        MyDBHelper.getInstances(CorrectActivity.this).updata(id,datetime,name,money);

        finish();

    }

}
