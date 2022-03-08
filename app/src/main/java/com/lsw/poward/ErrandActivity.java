package com.lsw.poward;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ErrandActivity extends AppCompatActivity {

    //디비 접근 변수
    public static MySQLiteOpenHelper helper;

    Context context;
    FrameLayout frameBtn;
    EditText etErrand, tvUntilCont;
    Spinner spinner;
    TextView tvGiverName, tvCountDown;
    Button ibGoToStamp, btnLock, btnUnlock, btnStart, btnStop;


    //디비 조회값 담을 변수
    public static String id = "", pw = "", name = "", content = "", reward = "", until = "";

    CountDownTimer countDownTimer;
    int tpHour; //현재 시
    int tpMinute; //현재 분


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errand);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        frameBtn = findViewById(R.id.frameBtn);
        btnLock = findViewById(R.id.btnLock);
        btnUnlock = findViewById(R.id.btnUnlock);

        etErrand = findViewById(R.id.etErrand);
        spinner = findViewById(R.id.spinner);
        ibGoToStamp = findViewById(R.id.ibGoToStamp);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        tvGiverName = findViewById(R.id.tvGiverName);
        tvUntilCont = findViewById(R.id.tvUntilCont);
        tvCountDown = findViewById(R.id.tvCountDown);

        ibGoToStamp.setOnClickListener(myClick);
        btnLock.setOnClickListener(myClick);
        btnUnlock.setOnClickListener(myClick);
        btnStart.setOnClickListener(myClick);
        btnStop.setOnClickListener(myClick);


        //StampActivity에서 만든 data(DB)에 접근하려고 MySQLiteOpenHelper변수도 당연히 helper로 설정해서 member테이블에 값을 넣으려고하니 계속 오류가 떴다.
        //ErrandActivity에서 StampActivity에 있는 디비에 접근하려면, helper변수를 다시 선언해줘야했는데 그걸 안해서 계속 on null reference뜬거임.
        //그 증거로 밑에보면, context명은 서로 다른데, 디비명은 똑같다.
        //이 모든 걸 압축해서 말하면 MySQLiteOpenHelper의 쓰임에 대해서 완전 잘못 알고 있어서 시간낭비를 했다는 것...
        helper = new MySQLiteOpenHelper(
                ErrandActivity.this, // 현재 화면의 context
                "poward", // 디비명
                null, // 커서 팩토리(디폴트 null)
                1); // 버전 번호


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")); //한국시간 가져오기 (GMT+09시)

        tvUntilCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ErrandActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) { //다이얼로그에서 원하는 시간으로 설정

                        //시분 설정돼 전역변수에 저장
                        tpHour = hour;
                        tpMinute = minute;

                        //화면에 출력할 형식
                        Date date = new Date();
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd " + tpHour+":"+ tpMinute);
                        String until = df.format(date);
                        tvUntilCont.setText(until);
                        Toast.makeText(ErrandActivity.this, until + " 까지", Toast.LENGTH_LONG).show();

                        //디비에 저장할 형식(yyyy/MM/dd)
                        until = until.substring(0, until.indexOf(" "));
                        Log.d("기한 설정", until);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);    //현재시분으로 초기화
                timePickerDialog.show();
            }
        });


        //카운트다운
//        int targetYear = 2022;
//        int targetMonth = 2;   //월은 0부터 시작
//        int targetDay = 24;
//        int targetSec = cal.get((Calendar.SECOND));
//
//        cal.set(targetYear, targetMonth, targetDay, tpHour, tpMinute, targetSec);//2022,2,24,시,분,초 달력에 저장
//        long futureMills = cal.getTimeInMillis();   //미래 밀리세컨즈 환산
//
//        long nowMills = System.currentTimeMillis();  //현재 밀리세컨즈환산
//        long diffMills = futureMills - nowMills;    //미래 - 현재
//
//        DateFormat df = new SimpleDateFormat("dd일 hh시 mm분 ss초 남");
//        String cd = df.format(new Date(diffMills));
//        tvCountDown.setText(cd);


        //스피너(이미지 + 텍스트)/////////////////////////////////////////////////////////////////////////
        int[] spinner_Image = new int[]{0, R.drawable.grape_purple_min, R.drawable.grape_green_min, R.drawable.grape_red_min};
        String[] spinner_Name = new String[]{"보상을 입력하세요", "포도 (1알)", "샤인머스캣 (2알)", "레드로망 (3알)"};

        // 어댑터와 스피너를 연결합니다.
        CustomSpinnerAdapter cpa = new CustomSpinnerAdapter(this, spinner_Image, spinner_Name);    //this여기에 보여주겠다
        spinner.setAdapter(cpa);

//        // 스피너에서 아이템 선택시 호출하도록 합니다.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                pos = spinner.getSelectedItemPosition();
                if (pos != 0) {
                    Toast.makeText(ErrandActivity.this, spinner_Name[pos] + " 선택", Toast.LENGTH_SHORT).show();
                    reward = spinner_Name[pos];
                    Log.d("보상 설정", reward);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //스피너 선택시 소프트키보드 가리기(드롭다운이 위로 펴지는 현상 막기)
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getApplicationContext().getSystemService(context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(spinner.getWindowToken(), 0);
                return false;
            }
        }) ;











        if (id.equals("")) {
            tvGiverName.setText("로그인하세요");
        } else {
            tvGiverName.setText(id + " 님");
            btnLock.setVisibility(View.INVISIBLE);
            btnUnlock.setVisibility(View.VISIBLE);

            //로그인하면 의뢰인 이름이 각 액티비티 상단에 동일하게 떠있도록 SharedPreferences 사용
            //SharedPreferences는 저장할 '그릇', Editor는 담는 '도구'
            SharedPreferences spf = getSharedPreferences("shared", MODE_PRIVATE);
            SharedPreferences.Editor editor = spf.edit();
            editor.putString("ID", id);
            editor.apply();
        }

    }



    View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(@NonNull View view) {
            switch (view.getId()) {
                case R.id.btnLock:
                    l_Dialog();
                    break;
                case R.id.btnUnlock:
                    logout_Dialog();
                    break;
                case R.id.ibGoToStamp:
                    startActivity(new Intent(ErrandActivity.this, StampActivity.class));
                    break;
                case R.id.btnStart:
                    if(id.equals("")) {
                        Toast.makeText(ErrandActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        //공백체크
                        if (etErrand.getText().toString().equals("")) {
                            Toast.makeText(ErrandActivity.this, "내용을 입력하세요", Toast.LENGTH_LONG).show();
                            break;
                        }
                        content = etErrand.getText().toString();
                        Log.d("심부름 설정", content);

                        if (spinner.getSelectedItemPosition() == 0) {
                            Toast.makeText(ErrandActivity.this, "보상을 입력하세요", Toast.LENGTH_LONG).show();
                            break;
                        }

                        if (tvUntilCont.getText().toString().trim().equals("")) {
                            Toast.makeText(ErrandActivity.this, "기한을 입력하세요", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }


                    startActivity(new Intent(ErrandActivity.this, StackActivity.class));
                    break;

                case R.id.btnStop:
                    etErrand.setText("");
                    spinner.setSelection(0);
                    tvUntilCont.setText("");
                    tvCountDown.setText("");
                    Toast.makeText(ErrandActivity.this, "협상이 결렬되었습니다", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //로그아웃 다이얼로그
    public void logout_Dialog() {
        Dialog logout = new Dialog(ErrandActivity.this);
        logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logout.setContentView(R.layout.activity_logout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        logout.show();

        Button btnConfirm = (Button) logout.findViewById(R.id.btnConfirm);//★★★dialog.findViewById!!!
        Button btnCancel = (Button) logout.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (R.id.btnConfirm == v.getId()) {
                    Toast.makeText(ErrandActivity.this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                    btnLock.setVisibility(View.VISIBLE);
                    btnUnlock.setVisibility(View.INVISIBLE);
                    tvGiverName.setText("로그인하세요");
                    id="";
                    logout.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (R.id.btnCancel == v.getId()) {
                    logout.dismiss();
                }
            }
        });


    }


    //로그인 다이얼로그
    public void l_Dialog() {
        Dialog login = new Dialog(ErrandActivity.this);
        login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        login.setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        login.show();


        EditText etIdCont = (EditText) login.findViewById(R.id.etIdCont);  //★★★dialog.findViewById!!!
        EditText etPwCont = (EditText) login.findViewById(R.id.etPwCont);
        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        Button btnJoin = (Button) login.findViewById(R.id.btnJoin);


        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((etIdCont.getText().toString().trim()).equals("")) {
                    Toast.makeText(ErrandActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                String strId = etIdCont.getText().toString().trim();

                if ((etPwCont.getText().toString().trim()).equals("")) {
                    Toast.makeText(ErrandActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                String strPw = etPwCont.getText().toString().trim();


                StampActivity.db = helper.getReadableDatabase();
                Cursor c = StampActivity.db.rawQuery("SELECT * FROM member WHERE id ='" + strId + "'", null);

                while (c.moveToNext()) {
                    id = c.getString(1);
                    pw = c.getString(2);
                }
                c.close();
                StampActivity.db.close();


                if (strId.equals(id)) {
                    if (strPw.equals(pw)) {
                        Toast.makeText(ErrandActivity.this, id + " 님 안녕하세요!", Toast.LENGTH_SHORT).show();
                        login.dismiss();
                        tvGiverName.setText(id + " 님");
                        //프레임 전환(로그인 > 로그아웃 버튼)
                        btnLock.setVisibility(View.INVISIBLE);
                        btnUnlock.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(ErrandActivity.this, "정보를 확인하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnJoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnJoin:
                        login.dismiss();
                        e_Dialog();
                }
            }
        });
    }

    //회원가입 다이얼로그
    public void e_Dialog() {
        Dialog e_Dialog = new Dialog(ErrandActivity.this);
        e_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        e_Dialog.setContentView(R.layout.dialog_login_enroll);
        e_Dialog.show();


        EditText etIdCont = (EditText) e_Dialog.findViewById(R.id.etIdCont);//★★★dialog.findViewById!!!
        EditText etPwCont = (EditText) e_Dialog.findViewById(R.id.etPwCont);
        EditText etNameCont = (EditText) e_Dialog.findViewById(R.id.etNameCont);
        Button btnLogin = (Button) e_Dialog.findViewById(R.id.btnLogin);
        Button btnJoin = (Button) e_Dialog.findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnJoin:
                        if ((etIdCont.getText().toString().trim()).equals("")) {
                            Toast.makeText(ErrandActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();  break;
                        } id = etIdCont.getText().toString().trim();
                        if ((etPwCont.getText().toString().trim()).equals("")) {
                            Toast.makeText(ErrandActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show(); break;
                        } pw = etPwCont.getText().toString().trim();
                        if ((etNameCont.getText().toString().trim()).equals("")) {
                            Toast.makeText(ErrandActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();   break;
                        } name = etNameCont.getText().toString().trim();


                        StampActivity.db = helper.getReadableDatabase();
                        Cursor c = StampActivity.db.rawQuery("SELECT * FROM member", null);
                        String dbId = null;
                        while (c.moveToNext()) {
                            dbId = c.getString(1);
                        }
                        c.close();
                        StampActivity.db.close();


                        if(id.equals(dbId)) {
                            Toast.makeText(getApplicationContext(), "이미 가입된 정보입니다.", Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            insert(id, pw, name);
                            e_Dialog.dismiss();
                        }
                }
            }
        });
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnLogin:
                        e_Dialog.dismiss();
                        l_Dialog();
                }
            }
        });
    }

    //회원정보 디비 저장
    public void insert(String id, String pw, String name) {

        StampActivity.db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("pw", pw);
        values.put("name", name);
        StampActivity.db.insert("member", null, values);
        StampActivity.db.close();

        Toast.makeText(getApplicationContext(), id + " 로 회원가입 완료", Toast.LENGTH_LONG).show();

    }








    //디바이스 뒤로가기 버튼 누를 시
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ErrandActivity.this, StackActivity.class));
    }
}