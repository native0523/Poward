package com.lsw.poward;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AddListViewActivity extends AppCompatActivity {

    Button add_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlistview);

        //버튼 클릭 시 리스트뷰 생성
        add_layout = findViewById(R.id.add_layout);
        add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub n_layout = new Sub(getApplicationContext());
                LinearLayout con = (LinearLayout)findViewById(R.id.con);
                con.addView(n_layout);  //activity_main의 con(container)안에 Sub xml 넣기

            }
        });
    }
}