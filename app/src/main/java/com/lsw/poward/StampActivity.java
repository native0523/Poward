package com.lsw.poward;

import static com.lsw.poward.ErrandActivity.id;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class StampActivity extends AppCompatActivity {

    //SQLITE 데이타베이스 관련변수
    public static SQLiteDatabase db;
    public static MySQLiteOpenHelper helper;

    ToggleButton tgbtn1, tgbtn2, tgbtn3, tgbtn4, tgbtn5, tgbtn6, tgbtn7, tgbtn8, tgbtn9, tgbtn10,
    tgbtn11, tgbtn12, tgbtn13, tgbtn14, tgbtn15, tgbtn16, tgbtn17, tgbtn18, tgbtn19, tgbtn20,
    tgbtn21, tgbtn22, tgbtn23, tgbtn24, tgbtn25, tgbtn26, tgbtn27, tgbtn28, tgbtn29, tgbtn30;
    Button ibBackToErrand, ibGoToStack, btnNewSheet;
    TextView tvGiverName;

    //if조건으로 활용하여 토글버튼의 스탬프 찍는데 사용
    //boolean타입 쓰고 싶었지만, Cursor에서 boolean타입으로 반환하는 메소드가 없어서 쓸수가 없다.
    String stampState[] = new String[30];

    //스탬프 찍을때마다 1씩 증가하여 30이 되면 다이얼로그 호출
    //stampCount를 증가시키더라도 앱이 다시 켜지면 다시 0으로 초기화되기 때문에 디비 사용 필수
    int stampCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        helper = new MySQLiteOpenHelper(
                StampActivity.this, // 현재 화면의 context
                "poward", // 디비명
                null, // 커서 팩토리(디폴트 null)
                1); // 버전 번호


        tgbtn1 = findViewById(R.id.tgbtn1);
        tgbtn2 = findViewById(R.id.tgbtn2);
        tgbtn3 = findViewById(R.id.tgbtn3);
        tgbtn4 = findViewById(R.id.tgbtn4);
        tgbtn5 = findViewById(R.id.tgbtn5);
        tgbtn6 = findViewById(R.id.tgbtn6);
        tgbtn7 = findViewById(R.id.tgbtn7);
        tgbtn8 = findViewById(R.id.tgbtn8);
        tgbtn9 = findViewById(R.id.tgbtn9);
        tgbtn10 = findViewById(R.id.tgbtn10);
        tgbtn11 = findViewById(R.id.tgbtn11);
        tgbtn12 = findViewById(R.id.tgbtn12);
        tgbtn13 = findViewById(R.id.tgbtn13);
        tgbtn14 = findViewById(R.id.tgbtn14);
        tgbtn15 = findViewById(R.id.tgbtn15);
        tgbtn16 = findViewById(R.id.tgbtn16);
        tgbtn17 = findViewById(R.id.tgbtn17);
        tgbtn18 = findViewById(R.id.tgbtn18);
        tgbtn19 = findViewById(R.id.tgbtn19);
        tgbtn20 = findViewById(R.id.tgbtn20);
        tgbtn21 = findViewById(R.id.tgbtn21);
        tgbtn22 = findViewById(R.id.tgbtn22);
        tgbtn23 = findViewById(R.id.tgbtn23);
        tgbtn24 = findViewById(R.id.tgbtn24);
        tgbtn25 = findViewById(R.id.tgbtn25);
        tgbtn26 = findViewById(R.id.tgbtn26);
        tgbtn27 = findViewById(R.id.tgbtn27);
        tgbtn28 = findViewById(R.id.tgbtn28);
        tgbtn29 = findViewById(R.id.tgbtn29);
        tgbtn30 = findViewById(R.id.tgbtn30);
        ibBackToErrand = findViewById(R.id.ibGoToErrand);
        ibGoToStack = findViewById(R.id.ibGoToStack);
        btnNewSheet = findViewById(R.id.btnNewSheet);
        tvGiverName = findViewById(R.id.tvGiverName);

       tgbtn1.setOnClickListener(myClick);
       tgbtn2.setOnClickListener(myClick);
       tgbtn3.setOnClickListener(myClick);
       tgbtn4.setOnClickListener(myClick);
       tgbtn5.setOnClickListener(myClick);
       tgbtn6.setOnClickListener(myClick);
       tgbtn7.setOnClickListener(myClick);
       tgbtn8.setOnClickListener(myClick);
       tgbtn9.setOnClickListener(myClick);
       tgbtn10.setOnClickListener(myClick);
       tgbtn11.setOnClickListener(myClick);
       tgbtn12.setOnClickListener(myClick);
       tgbtn13.setOnClickListener(myClick);
       tgbtn14.setOnClickListener(myClick);
       tgbtn15.setOnClickListener(myClick);
       tgbtn16.setOnClickListener(myClick);
       tgbtn17.setOnClickListener(myClick);
       tgbtn18.setOnClickListener(myClick);
       tgbtn19.setOnClickListener(myClick);
       tgbtn20.setOnClickListener(myClick);
       tgbtn21.setOnClickListener(myClick);
       tgbtn22.setOnClickListener(myClick);
       tgbtn23.setOnClickListener(myClick);
       tgbtn24.setOnClickListener(myClick);
       tgbtn25.setOnClickListener(myClick);
       tgbtn26.setOnClickListener(myClick);
       tgbtn27.setOnClickListener(myClick);
       tgbtn28.setOnClickListener(myClick);
       tgbtn29.setOnClickListener(myClick);
       tgbtn30.setOnClickListener(myClick);


        //이벤트버튼
        ibBackToErrand.setOnClickListener(myClick);
        ibGoToStack.setOnClickListener(myClick);
        btnNewSheet.setOnClickListener(myClick);


        // 테이블 초기화
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM stamp", null);
        String getId = null;
        while (c.moveToNext()) {
            getId = c.getString(1);
        }

        c.close();
        db.close();


        //로그인했다면...
        if(id.equals("")) {
            tvGiverName.setText("로그인하세요");
        } else {
            tvGiverName.setText(id + " 님");
        }

        //디비정보가 있다면...
        if(getId ==null | !(getId.equals(id))) {
            initStamp();
        }
        else {
            loadStamp();
        }

    }


    public void loadStamp() {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM stamp WHERE id='" + id + "'", null);

        while (c.moveToNext()) {
            stampState[0] = c.getString(2);
            stampState[1] = c.getString(3);
            stampState[2] = c.getString(4);
            stampState[3] = c.getString(5);
            stampState[4] = c.getString(6);
            stampState[5] = c.getString(7);
            stampState[6] = c.getString(8);
            stampState[7] = c.getString(9);
            stampState[8] = c.getString(10);
            stampState[9] = c.getString(11);
            stampState[10] = c.getString(12);
            stampState[11] = c.getString(13);
            stampState[12] = c.getString(14);
            stampState[13] = c.getString(15);
            stampState[14] = c.getString(16);
            stampState[15] = c.getString(17);
            stampState[16] = c.getString(18);
            stampState[17] = c.getString(19);
            stampState[18] = c.getString(20);
            stampState[19] = c.getString(21);
            stampState[20] = c.getString(22);
            stampState[21] = c.getString(23);
            stampState[22] = c.getString(24);
            stampState[23] = c.getString(25);
            stampState[24] = c.getString(26);
            stampState[25] = c.getString(27);
            stampState[26] = c.getString(28);
            stampState[27] = c.getString(29);
            stampState[28] = c.getString(30);
            stampState[29] = c.getString(31);
        }
        c.close();
        db.close();

            //여기서는 if-if 여야만 된다 (if-else if의 경우, if가 참이면 다음 else if는 보지 않기 때문)
            if (stampState[0].equals("1")) {
                tgbtn1.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn1.setEnabled(false);
                stampCount++;
            }
            if (stampState[1].equals("1")) {
                tgbtn2.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn2.setEnabled(false);
                stampCount++;
            }
            if (stampState[2].equals("1")) {
                tgbtn3.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn3.setEnabled(false);
                stampCount++;
            }
            if (stampState[3].equals("1")) {
                tgbtn4.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn4.setEnabled(false);
                stampCount++;
            }
            if (stampState[4].equals("1")) {
                tgbtn5.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn5.setEnabled(false);
                stampCount++;
            }
            if (stampState[5].equals("1")) {
                tgbtn6.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn6.setEnabled(false);
                stampCount++;
            }
            if (stampState[6].equals("1")) {
                tgbtn7.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn7.setEnabled(false);
                stampCount++;
            }
            if (stampState[7].equals("1")) {
                tgbtn8.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn8.setEnabled(false);
                stampCount++;
            }
            if (stampState[8].equals("1")) {
                tgbtn9.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn9.setEnabled(false);
                stampCount++;
            }
            if (stampState[9].equals("1")) {
                tgbtn10.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn10.setEnabled(false);
                stampCount++;
            }
            if (stampState[10].equals("1")) {
                tgbtn11.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn11.setEnabled(false);
                stampCount++;
            }
            if (stampState[11].equals("1")) {
                tgbtn12.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn12.setEnabled(false);
                stampCount++;
            }
            if (stampState[12].equals("1")) {
                tgbtn13.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn13.setEnabled(false);
                stampCount++;
            }
            if (stampState[13].equals("1")) {
                tgbtn14.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn14.setEnabled(false);
                stampCount++;
            }
            if (stampState[14].equals("1")) {
                tgbtn15.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn15.setEnabled(false);
                stampCount++;
            }
            if (stampState[15].equals("1")) {
                tgbtn16.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn16.setEnabled(false);
                stampCount++;
            }
            if (stampState[16].equals("1")) {
                tgbtn17.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn17.setEnabled(false);
                stampCount++;
            }
            if (stampState[17].equals("1")) {
                tgbtn18.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn18.setEnabled(false);
                stampCount++;
            }
            if (stampState[18].equals("1")) {
                tgbtn19.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn19.setEnabled(false);
                stampCount++;
            }
            if (stampState[19].equals("1")) {
                tgbtn20.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn20.setEnabled(false);
                stampCount++;
            }
            if (stampState[20].equals("1")) {
                tgbtn21.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn21.setEnabled(false);
                stampCount++;
            }
            if (stampState[21].equals("1")) {
                tgbtn22.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn22.setEnabled(false);
                stampCount++;
            }
            if (stampState[22].equals("1")) {
                tgbtn23.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn23.setEnabled(false);
                stampCount++;
            }
            if (stampState[23].equals("1")) {
                tgbtn24.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn24.setEnabled(false);
                stampCount++;
            }
            if (stampState[24].equals("1")) {
                tgbtn25.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn25.setEnabled(false);
                stampCount++;
            }
            if (stampState[25].equals("1")) {
                tgbtn26.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn26.setEnabled(false);
                stampCount++;
            }
            if (stampState[26].equals("1")) {
                tgbtn27.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn27.setEnabled(false);
                stampCount++;
            }
            if (stampState[27].equals("1")) {
                tgbtn28.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn28.setEnabled(false);
                stampCount++;
            }
            if (stampState[28].equals("1")) {
                tgbtn29.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn29.setEnabled(false);
                stampCount++;
            }
            if (stampState[29].equals("1")) {
                tgbtn30.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn30.setEnabled(false);
                stampCount++;
            }
    }

    //스탬프지 활성화
    public void initStamp() {

        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("podo1", "0");
        values.put("podo2", "0");
        values.put("podo3", "0");
        values.put("podo4", "0");
        values.put("podo5", "0");
        values.put("podo6", "0");
        values.put("podo7", "0");
        values.put("podo8", "0");
        values.put("podo9", "0");
        values.put("podo10", "0");
        values.put("podo11", "0");
        values.put("podo12", "0");
        values.put("podo13", "0");
        values.put("podo14", "0");
        values.put("podo15", "0");
        values.put("podo16", "0");
        values.put("podo17", "0");
        values.put("podo18", "0");
        values.put("podo19", "0");
        values.put("podo20", "0");
        values.put("podo21", "0");
        values.put("podo22", "0");
        values.put("podo23", "0");
        values.put("podo24", "0");
        values.put("podo25", "0");
        values.put("podo26", "0");
        values.put("podo27", "0");
        values.put("podo28", "0");
        values.put("podo29", "0");
        values.put("podo30", "0");
        db.insert("stamp", null, values);
        db.close();
    }

    View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.tgbtn1: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("1");  } break;
                case R.id.tgbtn2: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("2");  } break;
                case R.id.tgbtn3: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("3");  } break;
                case R.id.tgbtn4: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("4");  } break;
                case R.id.tgbtn5: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("5");  } break;
                case R.id.tgbtn6: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("6");  } break;
                case R.id.tgbtn7: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("7");  } break;
                case R.id.tgbtn8: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("8");  } break;
                case R.id.tgbtn9: if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("9");  } break;
                case R.id.tgbtn10:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("10"); } break;
                case R.id.tgbtn11:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("11"); } break;
                case R.id.tgbtn12:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("12"); } break;
                case R.id.tgbtn13:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("13"); } break;
                case R.id.tgbtn14:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("14"); } break;
                case R.id.tgbtn15:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("15"); } break;
                case R.id.tgbtn16:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("16"); } break;
                case R.id.tgbtn17:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("17"); } break;
                case R.id.tgbtn18:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("18"); } break;
                case R.id.tgbtn19:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("19"); } break;
                case R.id.tgbtn20:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("20"); } break;
                case R.id.tgbtn21:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("21"); } break;
                case R.id.tgbtn22:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("22"); } break;
                case R.id.tgbtn23:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("23"); } break;
                case R.id.tgbtn24:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("24"); } break;
                case R.id.tgbtn25:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("25"); } break;
                case R.id.tgbtn26:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("26"); } break;
                case R.id.tgbtn27:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("27"); } break;
                case R.id.tgbtn28:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("28"); } break;
                case R.id.tgbtn29:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("29"); } break;
                case R.id.tgbtn30:if(id.equals("")) {Toast.makeText(StampActivity.this, "로그인 이후 가능합니다", Toast.LENGTH_SHORT).show(); } else {LoginOrNot("30"); } break;
                case R.id.ibGoToErrand:
                    startActivity(new Intent(StampActivity.this, ErrandActivity.class));
                    break;
                case R.id.ibGoToStack:
                    startActivity(new Intent(StampActivity.this, StackActivity.class));
                    break;
                case R.id.btnNewSheet:
                    removeStamp();
                    Log.d("btnNewSheet", "새로운 스탬프지");

            }
        }
    };

//스탬프 저장
    public void attachStamp(String num) {

        Animation ani = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.stamp_togglebtn);

        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();


            //여기서는 if-if, if - else if 모두 잘된다
            if (num == "1") {
                values.put("podo1", "1");
                tgbtn1.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn1.setEnabled(false);
                tgbtn1.startAnimation(ani);
                stampState[0] = "1";
                stampCount++;
            } else if (num == "2") {
                values.put("podo2", "1");
                tgbtn2.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn2.setEnabled(false);
                tgbtn2.startAnimation(ani);
                stampState[1] = "1";
                stampCount++;
            } else if (num == "3") {
                values.put("podo3", "1");
                tgbtn3.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn3.setEnabled(false);
                tgbtn3.startAnimation(ani);
                stampState[2] = "1";
                stampCount++;
            } else if (num == "4") {
                values.put("podo4", "1");
                tgbtn4.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn4.setEnabled(false);
                tgbtn4.startAnimation(ani);
                stampState[3] = "1";
                stampCount++;
            } else if (num == "5") {
                values.put("podo5", "1");
                tgbtn5.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn5.setEnabled(false);
                tgbtn5.startAnimation(ani);
                stampState[4] = "1";
                stampCount++;
            } else if (num == "6") {
                values.put("podo6", "1");
                tgbtn6.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn6.setEnabled(false);
                tgbtn6.startAnimation(ani);
                stampState[5] = "1";
                stampCount++;
            } else if (num == "7") {
                values.put("podo7", "1");
                tgbtn7.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn7.setEnabled(false);
                tgbtn7.startAnimation(ani);
                stampState[6] = "1";
                stampCount++;
            } else if (num == "8") {
                values.put("podo8", "1");
                tgbtn8.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn8.setEnabled(false);
                tgbtn8.startAnimation(ani);
                stampState[7] = "1";
                stampCount++;
            } else if (num == "9") {
                values.put("podo9", "1");
                tgbtn9.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn9.setEnabled(false);
                tgbtn9.startAnimation(ani);
                stampState[8] = "1";
                stampCount++;
            } else if (num == "10") {
                values.put("podo10", "1");
                tgbtn10.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn10.setEnabled(false);
                tgbtn10.startAnimation(ani);
                stampState[9] = "1";
                stampCount++;
            } else if (num == "11") {
                values.put("podo11", "1");
                tgbtn11.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn11.setEnabled(false);
                tgbtn11.startAnimation(ani);
                stampState[10] = "1";
                stampCount++;
            } else if (num == "12") {
                values.put("podo12", "1");
                tgbtn12.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn12.setEnabled(false);
                tgbtn12.startAnimation(ani);
                stampState[11] = "1";
                stampCount++;
            } else if (num == "13") {
                values.put("podo13", "1");
                tgbtn13.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn13.setEnabled(false);
                tgbtn13.startAnimation(ani);
                stampState[12] = "1";
                stampCount++;
            } else if (num == "14") {
                values.put("podo14", "1");
                tgbtn14.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn14.setEnabled(false);
                tgbtn14.startAnimation(ani);
                stampState[13] = "1";
                stampCount++;
            } else if (num == "15") {
                values.put("podo15", "1");
                tgbtn15.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn15.setEnabled(false);
                tgbtn15.startAnimation(ani);
                stampState[14] = "1";
                stampCount++;
            } else if (num == "16") {
                values.put("podo16", "1");
                tgbtn16.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn16.setEnabled(false);
                tgbtn16.startAnimation(ani);
                stampState[15] = "1";
                stampCount++;
            } else if (num == "17") {
                values.put("podo17", "1");
                tgbtn17.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn17.setEnabled(false);
                tgbtn17.startAnimation(ani);
                stampState[16] = "1";
                stampCount++;
            } else if (num == "18") {
                values.put("podo18", "1");
                tgbtn18.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn18.setEnabled(false);
                tgbtn18.startAnimation(ani);
                stampState[17] = "1";
                stampCount++;
            } else if (num == "19") {
                values.put("podo19", "1");
                tgbtn19.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn19.setEnabled(false);
                tgbtn19.startAnimation(ani);
                stampState[18] = "1";
                stampCount++;
            } else if (num == "20") {
                values.put("podo20", "1");
                tgbtn20.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn20.setEnabled(false);
                tgbtn20.startAnimation(ani);
                stampState[19] = "1";
                stampCount++;
            } else if (num == "21") {
                values.put("podo21", "1");
                tgbtn21.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn21.setEnabled(false);
                tgbtn21.startAnimation(ani);
                stampState[20] = "1";
                stampCount++;
            } else if (num == "22") {
                values.put("podo22", "1");
                tgbtn22.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn22.setEnabled(false);
                tgbtn22.startAnimation(ani);
                stampState[21] = "1";
                stampCount++;
            } else if (num == "23") {
                values.put("podo23", "1");
                tgbtn23.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn23.setEnabled(false);
                tgbtn23.startAnimation(ani);
                stampState[22] = "1";
                stampCount++;
            } else if (num == "24") {
                values.put("podo24", "1");
                tgbtn24.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn24.setEnabled(false);
                tgbtn24.startAnimation(ani);
                stampState[23] = "1";
                stampCount++;
            } else if (num == "25") {
                values.put("podo25", "1");
                tgbtn25.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn25.setEnabled(false);
                tgbtn25.startAnimation(ani);
                stampState[24] = "1";
                stampCount++;
            } else if (num == "26") {
                values.put("podo26", "1");
                tgbtn26.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn26.setEnabled(false);
                tgbtn26.startAnimation(ani);
                stampState[25] = "1";
                stampCount++;
            } else if (num == "27") {
                values.put("podo27", "1");
                tgbtn27.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn27.setEnabled(false);
                tgbtn27.startAnimation(ani);
                stampState[26] = "1";
                stampCount++;
            } else if (num == "28") {
                values.put("podo28", "1");
                tgbtn28.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn28.setEnabled(false);
                tgbtn28.startAnimation(ani);
                stampState[27] = "1";
                stampCount++;
            } else if (num == "29") {
                values.put("podo29", "1");
                tgbtn29.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn29.setEnabled(false);
                tgbtn29.startAnimation(ani);
                stampState[28] = "1";
                stampCount++;
            } else if (num == "30") {
                values.put("podo30", "1");
                tgbtn30.setBackground(getDrawable(R.drawable.goodjob));
                tgbtn30.setEnabled(false);
                tgbtn30.startAnimation(ani);
                stampState[29] = "1";
                stampCount++;
            }
            db.update("stamp", values, "id='" + id + "'", null);
            db.close();

    }

    public void removeStamp() {

        //테이블 값 0에서 1로 전환
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("podo1", "0"); tgbtn1.setBackground(null);  tgbtn1.setEnabled(true);   stampState[0]="0";
        values.put("podo2", "0"); tgbtn2.setBackground(null);  tgbtn2.setEnabled(true);   stampState[1]="0";
        values.put("podo3", "0"); tgbtn3.setBackground(null);  tgbtn3.setEnabled(true);   stampState[2]="0";
        values.put("podo4", "0"); tgbtn4.setBackground(null);  tgbtn4.setEnabled(true);   stampState[3]="0";
        values.put("podo5", "0"); tgbtn5.setBackground(null);  tgbtn5.setEnabled(true);   stampState[4]="0";
        values.put("podo6", "0"); tgbtn6.setBackground(null);  tgbtn6.setEnabled(true);   stampState[5]="0";
        values.put("podo7", "0"); tgbtn7.setBackground(null);  tgbtn7.setEnabled(true);   stampState[6]="0";
        values.put("podo8", "0"); tgbtn8.setBackground(null);  tgbtn8.setEnabled(true);   stampState[7]="0";
        values.put("podo9", "0"); tgbtn9.setBackground(null);  tgbtn9.setEnabled(true);   stampState[8]="0";
        values.put("podo10", "0"); tgbtn10 .setBackground(null);  tgbtn10.setEnabled(true);  stampState[9]="0";
        values.put("podo11", "0"); tgbtn11.setBackground(null);   tgbtn11.setEnabled(true);  stampState[10]="0";
        values.put("podo12", "0"); tgbtn12.setBackground(null);   tgbtn12.setEnabled(true);  stampState[11]="0";
        values.put("podo13", "0"); tgbtn13.setBackground(null);   tgbtn13.setEnabled(true);  stampState[12]="0";
        values.put("podo14", "0"); tgbtn14.setBackground(null);   tgbtn14.setEnabled(true);  stampState[13]="0";
        values.put("podo15", "0"); tgbtn15.setBackground(null);   tgbtn15.setEnabled(true);  stampState[14]="0";
        values.put("podo16", "0"); tgbtn16.setBackground(null);   tgbtn16.setEnabled(true);  stampState[15]="0";
        values.put("podo17", "0"); tgbtn17.setBackground(null);   tgbtn17.setEnabled(true);  stampState[16]="0";
        values.put("podo18", "0"); tgbtn18.setBackground(null);   tgbtn18.setEnabled(true);  stampState[17]="0";
        values.put("podo19", "0"); tgbtn19.setBackground(null);   tgbtn19.setEnabled(true);  stampState[18]="0";
        values.put("podo20", "0"); tgbtn20.setBackground(null);   tgbtn20.setEnabled(true);  stampState[19]="0";
        values.put("podo21", "0"); tgbtn21.setBackground(null);   tgbtn21.setEnabled(true);  stampState[20]="0";
        values.put("podo22", "0"); tgbtn22.setBackground(null);   tgbtn22.setEnabled(true);  stampState[21]="0";
        values.put("podo23", "0"); tgbtn23.setBackground(null);   tgbtn23.setEnabled(true);  stampState[22]="0";
        values.put("podo24", "0"); tgbtn24.setBackground(null);   tgbtn24.setEnabled(true);  stampState[23]="0";
        values.put("podo25", "0"); tgbtn25.setBackground(null);   tgbtn25.setEnabled(true);  stampState[24]="0";
        values.put("podo26", "0"); tgbtn26.setBackground(null);   tgbtn26.setEnabled(true);  stampState[25]="0";
        values.put("podo27", "0"); tgbtn27.setBackground(null);   tgbtn27.setEnabled(true);  stampState[26]="0";
        values.put("podo28", "0"); tgbtn28.setBackground(null);   tgbtn28.setEnabled(true);  stampState[27]="0";
        values.put("podo29", "0"); tgbtn29.setBackground(null);   tgbtn29.setEnabled(true);  stampState[28]="0";
        values.put("podo30", "0"); tgbtn30.setBackground(null);   tgbtn30.setEnabled(true);  stampState[29]="0";
        stampCount = 0;

        db.update("stamp", values, "id='" + id + "'", null);
        db.close();
    }

    public void LoginOrNot(String num) {
        Dialog dialog = new Dialog(StampActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_userconfirm);
        dialog.show();

        EditText etPwCont = (EditText) dialog.findViewById(R.id.etPwCont);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputPw = etPwCont.getText().toString().trim();

                String qq ="q";
                if(inputPw.equals(qq)) {
                    Toast.makeText(StampActivity.this, "스탬프를 얻었어요!", Toast.LENGTH_SHORT).show();
                    Log.d("스탬프카운트", "1++");
                    dialog.dismiss();
                    attachStamp(num);
                } else {
                    Toast.makeText(StampActivity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                //순서 상관없이 마지막 토글버튼 누를 시 다이얼로그 뜨도록
                if (stampCount == 30) {
                    CompleteDialog();
                    stampCount = 0;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void CompleteDialog() {
        Dialog dialog = new Dialog(StampActivity.this);                // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);            // 타이틀 제거
        dialog.setContentView(R.layout.dialog_getallstamp);        // xml 레이아웃 파일과 연결
        dialog.show();                                                   // 다이얼로그 띄우기
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 확인버튼
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog.dismiss(); // 다이얼로그 닫기
                startActivity(new Intent(StampActivity.this, StackActivity.class));
                //finish()는 액티비티 종료
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(StampActivity.this, ErrandActivity.class));
    }









}








