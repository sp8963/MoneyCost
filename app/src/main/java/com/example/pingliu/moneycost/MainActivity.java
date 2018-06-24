package com.example.pingliu.moneycost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> list;
    private MyListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv =findViewById(R.id.item_list);
        adapter = new MyListViewAdapter(this,R.layout.finance_row,list);
        lv.setAdapter(adapter);
        List<Map<String, Object>> data = getData();
        adapter.refreshList(data);
         Button in = findViewById(R.id.in);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, AddActivity .class);
                startActivity(in);
            }

        });
        //按下fab跳轉到add頁面
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Object id = list.get(position).get("id");
                int i = Integer.parseInt(id.toString());
                String datetime = list.get(position).get("datetime").toString();
                String name = list.get(position).get("name").toString();
                String money = list.get(position).get("money").toString();
                //將得到的id傳入
                showMyDialog(i, datetime, name, money);
            }
            private void showMyDialog(final int id, final String datetime, final String name, final String money) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //設定標題
                builder.setTitle("請選擇");
                //给dialog设置item
                builder.setItems(new String[]{"修改", "刪除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        switch (position) {
                            case 0://修改
                                Intent re = new Intent(MainActivity.this, CorrectActivity.class);
                                re.putExtra("id", id);
                                re.putExtra("datetime", datetime);
                                re.putExtra("name", name);
                                re.putExtra("money", money);
                                startActivityForResult(re,0);
                                break;
                            case 1://刪除
                                MyDBHelper.getInstances(MainActivity.this).delete(id);
                                List<Map<String, Object>> data = getData();
                                adapter.refreshList(data);
                                //重新查詢,然後顯示
                                Toast.makeText(MainActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                });
                builder.show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        List<Map<String, Object>> data1 = getData();
        adapter.refreshList(data1);

    }
    private List<Map<String, Object>> getData() {
        list = new ArrayList<>();
        Cursor query = MyDBHelper.getInstances(this).query();

        if (query.moveToFirst()) {
            do {String datetime = query.getString(query.getColumnIndex("datetime"));
                String name = query.getString(query.getColumnIndex("name"));
                String money = query.getString(query.getColumnIndex("money"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("datetime", datetime);
                map.put("name", name);
                map.put("money", money);
                list.add(map);
            } while (query.moveToNext());
        }
        query.close();
        return list;
    }

}
