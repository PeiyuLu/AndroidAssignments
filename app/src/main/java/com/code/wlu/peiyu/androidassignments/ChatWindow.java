package com.code.wlu.peiyu.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ChatWindow";

    private EditText textInput;
    private ArrayList<String> list;
    ChatAdapter adapter;
    ChatDatabaseHelper db_helper;
    SQLiteDatabase db;
    Cursor cursor;
    FrameLayout frameLayout;
    boolean is_exist;
    private int current;

    MessageDetails.MessageFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView listView = findViewById(R.id.list_view);

        list = new ArrayList<>();
        adapter = new ChatAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current = position;
                if (is_exist) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    fragment = new MessageDetails.MessageFragment(id, list.get(position), true, ChatWindow.this);
                    ft.replace(R.id.frame_layout, fragment);
                    ft.commit();
                } else {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("id", id);
                    intent.putExtra("message", list.get(position));
                    startActivityForResult(intent, 1001);
                }
            }
        });

        textInput = findViewById(R.id.textInput);
        Button sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textInput.getText().toString();
                list.add(msg);
                adapter.notifyDataSetChanged();
                textInput.setText("");
                ContentValues values = new ContentValues();
                values.put("message", msg);
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
            }
        });

        db_helper = new ChatDatabaseHelper(this);
        db = db_helper.getWritableDatabase();
        cursor = db.rawQuery("select * from " + ChatDatabaseHelper.TABLE_NAME,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
            }
            Log.i(ACTIVITY_NAME, cursor.getLong(0) + " " + cursor.getString(1));
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }

        frameLayout = findViewById(R.id.frame_layout);
        is_exist = frameLayout != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            assert data != null;
            long id = data.getLongExtra("id", 0);
            db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + "=?", new String[]{id + ""});
            list.remove(current);
            adapter.notifyDataSetChanged();
        }
    }

    void remove_fragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            ft.remove(fragment);
            ft.commit();
        }
    }

    @Override
    protected void onDestroy() {//close cursor and database
        super.onDestroy();
        cursor.close();
        db_helper.close();
        db.close();
    }

    class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(@NonNull Context context) {
            super(context, 0);
        }

        @Override
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            return cursor.getLong(0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
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
}