package com.lsw.poward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity {

    ImageView imgAppLogo;
    TextView tvAppName, tvAppIntro;
    Animation ani;
    long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        imgAppLogo = (ImageView) findViewById(R.id.imgAppLogo);
        Glide.with(this).load(R.drawable.grape_monster).into(imgAppLogo);

        tvAppName = (TextView) findViewById(R.id.tvAppName);
        tvAppIntro = (TextView) findViewById(R.id.tvAppIntro);

        ani = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.intro_logo);
        imgAppLogo.startAnimation(ani);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(IntroActivity.this, ErrandActivity.class));
                finish();
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(IntroActivity.this, "뒤로가기 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_LONG).show();
        }
        //2번째 백버튼 클릭 (종료)
        else {
            AppFinish();
        }
    }

    //앱종료
    public void AppFinish() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}