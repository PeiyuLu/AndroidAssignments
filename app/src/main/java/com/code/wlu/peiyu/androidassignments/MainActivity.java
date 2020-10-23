package com.code.wlu.peiyu.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);

            }
        });

        //startChat = ;
//        findViewById(R.id.button_chat).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
//                startActivity(new Intent(MainActivity.this, ChatWindow.class));
//            }
//        });
    }
    public void start_chat(View v){
        Log.i(ACTIVITY_NAME,"Users click the Start Chat");
        findViewById(R.id.button_chat).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_start_chat = new Intent(MainActivity.this,ChatWindow.class);
                startActivity(intent_start_chat);
            }
        });
    }
//    public void startAnotherActivity(View v){
//        Intent sIntent = new Intent(MainActivity.this,
//                ListItemsActivity.class);
//        startActivity(sIntent);
//
//    }

    public void onActivityResult(int requestCode, int responseCode, Intent data) {

        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            //CharSequence text = "ListItemsActivity passed: My information to share";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(this , R.string.passActivity, duration); //this is the ListActivity
            toast.show(); //display your message box

        }else{
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}