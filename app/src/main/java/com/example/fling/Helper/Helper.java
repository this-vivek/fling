package com.example.fling.Helper;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class Helper
{
    public static void makeToast(Context context, String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    public static  void createLog(String type, String msg)
    {
        Log.d(type,msg);

    }
    public static  void createStrictMode()
    {
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
}
