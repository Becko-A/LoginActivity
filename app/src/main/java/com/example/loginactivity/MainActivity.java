package com.example.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editComment;
    private Button button;
    private MsgAdapter adapter;
    private ListView listView;
    private List<Msg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null){
            actionBar.hide();
        }

        button = (Button) findViewById(R.id.button);
        editName = (EditText) findViewById(R.id.edit_name);
        editComment = (EditText) findViewById(R.id.edit_comment);

        initMsg();
        adapter = new MsgAdapter(MainActivity.this, R.layout.pinglun_item, msgList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editName.getText().toString();
                String userComment = editComment.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowTime = df.format(new Date());
                if (userName.equals("") || userComment.equals("")) {
                    Toast.makeText(MainActivity.this, "昵称和评论不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Msg user = new Msg(userName, userComment, nowTime);
                msgList.add(user);
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(msgList.size() - 1);
                editName.setText("");
                editComment.setText("");
            }
        });
    }

    private void initMsg() {

    }
}