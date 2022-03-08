package com.lsw.poward;

import static com.lsw.poward.ErrandActivity.id;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StackActivity extends AppCompatActivity {

    TextView tvGiverName;
    Button ibBackToStamp, ibGoToErrand, add_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tvGiverName = findViewById(R.id.tvGiverName);
        ibBackToStamp = findViewById(R.id.ibBackToStamp);
        ibGoToErrand = findViewById(R.id.ibGoToErrand);
        add_layout = findViewById(R.id.add_layout);

        ibBackToStamp.setOnClickListener(myClick);
        ibGoToErrand.setOnClickListener(myClick);
        add_layout.setOnClickListener(myClick);

        if(id.equals("")) {
            tvGiverName.setText("로그인하세요");
        } else {
            tvGiverName.setText(id + " 님");
        }

    }

    View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(R.id.ibBackToStamp == v.getId()){
                startActivity(new Intent(StackActivity.this, StampActivity.class));
            }
            if(R.id.ibGoToErrand == v.getId()){
                startActivity(new Intent(StackActivity.this, ErrandActivity.class));
            }
            if(R.id.add_layout == v.getId()) {
                Sub n_layout = new Sub(getApplicationContext());
                LinearLayout con = (LinearLayout)findViewById(R.id.con);
                con.addView(n_layout);  //activity_main의 con(container)안에 Sub xml 넣기
                }
            }
        };

    @Override
    public void onBackPressed(){
        startActivity(new Intent(StackActivity.this, StampActivity.class));
    }

}