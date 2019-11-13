package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;



public class MainActivity<inputBuffer> extends AppCompatActivity {


    private static final int RESULT_PICK_CONTACT = 1234;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CALL = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private final int REQ_CODE = 100;

    static final int READ_BLOCK_SIZE = 100;




    static final Integer CALL = 0x2;

    Bundle savedInstanceState;

    private final static String simSlotName[] = {
            "extra_asus_dial_use_dualsim",
            "com.android.phone.extra.slot",
            "slot",
            "simslot",
            "sim_slot",
            "subscription",
            "Subscription",
            "phone",
            "com.android.phone.DialingMode",
            "simSlot",
            "slot_id",
            "simId",
            "simnum",
            "phone_type",
            "slotId",
            "slotIdx"
    };






    void calling() {
        FileInputStream fin = null;

        {
            try {
                fin = openFileInput("mytextfile.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int c = 0;
        String temp = "";
        while (true) {
            try {
                if (!((c = fin.read()) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = temp + Character.toString((char) c);
        }

        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contactNumber = "" + temp;
        makePhoneCall(contactNumber);
    }
    void calling1()
    {
        FileInputStream fin1 = null;

        {
            try {
                fin1 = openFileInput("mytextfile1.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int c =0 ;
        String temp="";
        while(true){
            try {
                if (!((c = fin1.read()) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = temp + Character.toString((char)c);
        }

        try {
            fin1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contactNumber = ""+temp;
        makePhoneCall(contactNumber);
    }
    void calling2()
    {
        FileInputStream fin2 = null;

        {
            try {
                fin2 = openFileInput("mytextfile2.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int c =0 ;
        String temp="";
        while(true){
            try {
                if (!((c = fin2.read()) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = temp + Character.toString((char)c);
        }

        try {
            fin2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contactNumber = ""+temp;
        makePhoneCall(contactNumber);
    }
    void test()
    {
        calling();
        Log.e("LOB","testing ");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("LOB","testing 2");
        //calling();


        Log.e("LOB"," forwarding 3");
        calling();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calling1();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        calling2();
        Log.e("LOB"," forwarding 4 ");







        // if (checkPermission())
       // {
           // Toast.makeText(MainActivity.this, " permission granted ", Toast.LENGTH_LONG).show();
            startService(new Intent(getApplicationContext(), LockService.class));
       // }


      //
    }

    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                Toast.makeText(MainActivity.this, "Permition denaied", Toast.LENGTH_SHORT).show();
            }
            else if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                Toast.makeText(MainActivity.this, "sim select denied", Toast.LENGTH_SHORT).show();
            }
            else {
                String dial = number;

                //22/9[
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dial));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("com.android.phone.force.slot", true);
                intent.putExtra("Cdma_Supp", true);
                //Add all slots here, according to device.. (different device require different key so put all together)
                for (String s : simSlotName)
                    intent.putExtra(s, 1); //0 or 1 according to sim.......
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    intent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE"," (Parcelable)  getCallCapablePhoneAccounts()");

                //22/9]
                startActivity(intent);


            }

        } else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }

    }




   /* @Override
    protected void onStart() {
        startService(new Intent(getApplicationContext(), LockService.class));
        super.onStart();

    }

    @Override
    protected void onStop() {
        startService(new Intent(getApplicationContext(), LockService.class));
        super.onStop();

    }



    @Override
    protected void onPause() {
        startService(new Intent(getApplicationContext(), LockService.class));
        super.onPause();

    }

    @Override
    protected void onResume() {
        startService(new Intent(getApplicationContext(), LockService.class));
        super.onResume();

    }

    @Override
    protected void onRestart() {
        startService(new Intent(getApplicationContext(), LockService.class));
        super.onRestart();

    }
*/
    @Override
    protected void onDestroy() {

        Toast.makeText(getApplicationContext(),
                "App destryed",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("com.android.ServiceStopped");
        sendBroadcast(intent);

        startService(new Intent(getApplicationContext(), LockService.class));



        super.onDestroy();

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return result == PackageManager.PERMISSION_GRANTED ;
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean callAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (callAccepted) {
                       // startService(new Intent(getApplicationContext(), LockService.class));
                        Toast.makeText(MainActivity.this, " permission ", Toast.LENGTH_LONG).show();
                    }
                    else {

                        Toast.makeText(MainActivity.this," permission no", Toast.LENGTH_LONG).show();


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void goSettings(View view) {
        Intent settings = new Intent(MainActivity.this,Settings.class);
        startActivity(settings);
    }
}
