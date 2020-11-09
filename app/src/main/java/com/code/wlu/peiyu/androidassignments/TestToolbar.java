package com.code.wlu.peiyu.androidassignments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.google.android.material.snackbar.Snackbar.*;

public class TestToolbar extends AppCompatActivity {


    String message = "You select item 1";
    Snackbar snackbar;
    View view;
    boolean is_changed = false;

//
    //private Object TestToolbar;


//    public void TestToolbar (AppCompatActivity TestToolbar,
//                                  Snackbar snackbar) {
//        this.snackbar = snackbar ;
//        //this.TestToolbar=test_toorbar;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //boolean x = onOptionsItemSelected(findViewById(R.))

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make(view, "You selected item 1", LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu m)  {
        getMenuInflater().inflate(R.menu.menu_main, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int item_id = item.getItemId();
        switch (item_id){
            case R.id.action_one:
                Log.i("Toolbar","Option 1 selected");
                //Start an activity
                //snackbar= snackbar.make(view,message, LENGTH_LONG); //this is the ListActivity
                //snackbar.show(); //display your message box
                snackbar.make(findViewById(R.id.action_one), message, Snackbar.LENGTH_LONG)
                        .setAction("Action",new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                snackbar = snackbar.setText(message);

                            }
                        }).show();
                break;


            case R.id.action_two:
                Log.i("Toolbar","Option 2 selected");
                //Start an activity…
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.go_back);
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;



            case R.id.action_three:
                Log.i("Toolbar","Option 3 selected");
                //Start an activity…
                AlertDialog.Builder builder_three = new AlertDialog.Builder(this);
//                builder_three.setTitle("Do you want to go back?");
//                LayoutInflater inflater = mainActivity.getLayoutInflater();
//                final View view = inflater.inflate(R.layout.custom_dialog, null);
//                builder_three.setView(view);
                LayoutInflater inflater = this.getLayoutInflater();
                view = inflater.inflate(R.layout.custom_dialog, null);
                builder_three.setView(view)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText edit = view.findViewById(R.id.dialog_message_box);
                                message = edit.getText().toString();
                                is_changed = true;
//                                snackbar.setText(message);
//                                finish();

                            }
                        })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                //// Create the AlertDialog
                AlertDialog dialog_three = builder_three.create();
                dialog_three.show();
                //break;
        }


        return true;
    }

    public void onOptionItemSelected(MenuItem item) {
        Toast toast = Toast.makeText(this , "Version 1.0 by Peiyu Lu", Toast.LENGTH_LONG); //this is the ListActivity
        toast.show(); //display your message box
    }
}