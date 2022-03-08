package com.lsw.poward;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private TextView stNum, stGivername, stReward, stDate;

Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);


        // 뷰 참조
//        stNum = (TextView) findViewById(R.id.stNum);
//        stGivername = (TextView) findViewById(R.id.stGiverName);
//        stReward = (TextView) findViewById(R.id.stReward);
//        stDate = (TextView) findViewById(R.id.stDate);
        listView = (ListView) findViewById(R.id.listview);

        adapter = new ListViewAdapter(ListViewActivity.this);
        listView.setAdapter(adapter);

//        // 데이터 추가하기
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.addItem(edt_title.getText().toString(), edt_sub.getText().toString());
//                edt_title.setText("");
//                edt_sub.setText("");
//
//                adapter.notifyDataSetChanged();
//
//            }
//        });

    }
}
