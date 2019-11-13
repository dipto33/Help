package com.example.helpme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

class myActivity extends AppCompatActivity{
    class PrimeThread extends Thread {


        public void run() {
            Log.e("LOB"," thread 1 ");
            MainActivity mainActivity=new MainActivity();
            mainActivity.calling();
            Log.e("LOB"," thread 2 ");
        }
    }
    void forword()
    {
        Log.e("LOB"," forwarding ");
        /*Intent mStartActivity = new Intent(myActivity.this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(myActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)myActivity.this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        android.os.Process.killProcess(android.os.Process.myPid());*/

        Thread thread = new Thread();
        thread.run();

        Log.e("LOB"," forwarding 2 ");
    }
}







public class ScreenReceiver extends BroadcastReceiver  {


    private static final String TAG = " new ";
    public static boolean wasScreenOn = true;
    long a ,b;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(final Context context, final Intent intent) {


        final Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);







        Log.e("LOB","onReceive");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // do whatever you need to do here
           // vibe.vibrate(0b1111101000);


            a = System.currentTimeMillis();

            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here

            b=System.currentTimeMillis();
            wasScreenOn = true;

        }
        if((a-b<300 && a-b>10)||(b-a<300 && b-a>10))
        {
            vibe.vibrate(300);
            a=0;
            b=0;



            doRestart(context);

            //
            Log.e("LOB"," 1111 ");
            MainActivity mainActivity = new MainActivity();

            //Bundle savedInstanceState = new Bundle();
            myActivity ma = new myActivity();
            ma.forword();
           // mainActivity.test();
            //mainActivity.calling();
            //Bundle bundle = null;
           // Log.e("LOB"," 000000 ");
            //mainActivity.onCreate(new Bundle());
           // mainActivity.onCreate(bundle);
           // Log.e("LOB"," 222222222 ");










        }




    }
    public static void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() , mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Was not able to restart application");
        }
    }





}
