package com.lsw.poward;

import android.util.Log;

public class DebugTest {
    public static void log(String tag)
    {
        Log.d(tag, "동작여부체크");
    }
    public static void log(String tag, int num1)
    {
        Log.d(tag, "num1 : "+num1);
    }
    public static void log(String tag, int num1, int num2)
    {
        Log.d(tag, "num1 : "+num1);
        Log.d(tag, "num2 : "+num2);
    }
    public static void log(String tag, int num1, int num2, int num3)
    {
        Log.d(tag, "num1 : "+num1);
        Log.d(tag, "num2 : "+num2);
        Log.d(tag, "num3 : "+num3);
    }
    public static void log(String tag,String msg)
    {
        Log.d(tag, "msg:"+ msg);
    }

}
