package com.code.wlu.peiyu.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="StartActivity(LoginActivity)";
    private EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        loadData();
    }

    public void startActivity(View view) {
        String file_name = getString(R.string.LoginName);
        SharedPreferences mPrefs = getSharedPreferences(file_name, MODE_PRIVATE);
        SharedPreferences.Editor myEditor = mPrefs.edit();
        myEditor.clear();
        String email_key  = getString(R.string.LoginName);
        String new_email_entered = ((EditText) findViewById(R.id.editEmail))
                .getText().toString();
        myEditor.putString(email_key, new_email_entered);
        myEditor.commit();
        Intent mIntent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(mIntent);
    }

    public void loadData(){
        String file_name=getString(R.string.LoginName);
        SharedPreferences refs = getSharedPreferences(file_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = refs.edit();
        String  email_key = getString(R.string.LoginName);
        String new_email = refs.getString(email_key,"");
        ((EditText) findViewById(R.id.editEmail)).setText(new_email);
        myEdit.putString(email_key,new_email);
        myEdit.commit();

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
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}