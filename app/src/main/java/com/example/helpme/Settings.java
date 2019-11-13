package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.zip.CheckedOutputStream;


public class Settings extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    private final int REQ_CODE = 100;
    public  static final int RequestPermissionCode  = 1 ;

    String TAG = "ContactsActivityTAG";

    private static final int RESULT_PICK_CONTACT = 1234;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button = (Button) findViewById(R.id.btn1);







        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent cp = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(cp, RESULT_PICK_CONTACT);
            }
        });
    }

    public void showContact(View view) {
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phone, name;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            Log.d(TAG, "ContactPicked NAME: " + name);
            Log.d(TAG, "ContactPicked NUMBER: " + phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int i=0;
    @Override
    protected void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

        super.onActivityResult(RequestCode, ResultCode, ResultIntent);
        switch (RequestCode) {

            case (RESULT_PICK_CONTACT):
                if (ResultCode == Activity.RESULT_OK) {

                    Uri uri = ResultIntent.getData();
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int numberColoumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String num = cursor.getString(numberColoumnIndex);
                    int nameColoumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String name = cursor.getColumnName(nameColoumnIndex);
                    Toast.makeText(Settings.this, "Number=" + num, Toast.LENGTH_LONG).show();
                    // Toast.makeText(MainActivity.this, "Number=" + nam, Toast.LENGTH_LONG).show();

                    MainActivity mainActivity = new MainActivity();
                   // mainActivity.contactNumber = num;
                    TextView tv1 = (TextView) findViewById(R.id.textView1);
                    TextView tv2 = (TextView) findViewById(R.id.textView2);
                    TextView tv3 = (TextView) findViewById(R.id.textView3);
                    String s = name+"   "+num;


                    if(i==0)
                    {
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = openFileOutput("mytextfile.txt",MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        OutputStreamWriter outputWriter=new OutputStreamWriter(fileOutputStream);
                        try {
                            outputWriter.write(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tv1.setText(s);
                    }
                    if(i==1)
                    {
                        FileOutputStream fileOutputStream1 = null;
                        try {
                            fileOutputStream1 = openFileOutput("mytextfile1.txt",MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        OutputStreamWriter outputWriter1=new OutputStreamWriter(fileOutputStream1);

                        try {
                            outputWriter1.write(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputWriter1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tv2.setText(s);
                    }
                    if(i==2)
                    {
                        FileOutputStream fileOutputStream2 = null;
                        try {
                            fileOutputStream2 = openFileOutput("mytextfile2.txt",MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        OutputStreamWriter outputWriter2=new OutputStreamWriter(fileOutputStream2);
                        try {
                            outputWriter2.write(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputWriter2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tv3.setText(s);
                    }
                    i=(i+1)%3;

                    cursor.close();


                }
                break;
        }
    }
}
