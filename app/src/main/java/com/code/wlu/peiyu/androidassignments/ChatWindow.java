package com.code.wlu.peiyu.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ChatWindow";

    private EditText textInput;
    //private View clickListener;
    private ArrayList<String> list; //store chat message
    SQLiteDatabase db;
    ChatDatabaseHelper db_helper;// = new ChatDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView listView = findViewById(R.id.list_view);
        list = new ArrayList<>();

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        textInput = findViewById(R.id.textInput);
        //Button sendButton = findViewById(R.id.sendButton);
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                String message = textInput.getText().toString();
                list.add(message);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                textInput.setText("");
                //write
                ContentValues cv = new ContentValues();
                cv.put("message",message);
                db.insert(ChatDatabaseHelper.TABLE_NAME,null,cv);

            }
        });

        //ChatDatabaseHelper db_helper = new ChatDatabaseHelper(this);
        db_helper = new ChatDatabaseHelper(this);
        //gets a writeable database
        db = db_helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ChatDatabaseHelper.TABLE_NAME,null);
        cursor.moveToFirst();
        //reading from db file
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            //print out the name of each column returned by the cursor
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
            }
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();

//        View.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
//                textInput.setText("");
//            }
//        };
//        sendButton.setOnClickListener(new View.OnClickListener(){



    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context context) {
            super(context,0);
        }

        public int getCount(){
            return list.size();
        }

        public String getItem(int position){
            return (String) list.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }
    //public void send_message(View v){

    //}
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
        //close databasehelpter
        db_helper.close();
        //close database
        db.close();

    }
}