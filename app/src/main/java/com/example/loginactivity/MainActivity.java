package com.example.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editComment;
    private Button button;
    private Button back;
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
        back=(Button) findViewById(R.id.msg_back);
        editName = (EditText) findViewById(R.id.edit_name);
        editComment = (EditText) findViewById(R.id.edit_comment);

        initMsg();
        adapter = new MsgAdapter(MainActivity.this, R.layout.pinglun_item, msgList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editName.getText().toString();
                String userComment = editComment.getText().toString();
                sendRequestWithOkhttp(userName,userComment);
            }
        });
    }

    private void initMsg() {

    }

    private void sendRequestWithOkhttp(final String username, final String usercomment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user",username)
                            .add("txt",usercomment)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://www.nanshannan331.com/comment.php")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(final String jsonData){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Re re = gson.fromJson(jsonData,Re.class);
                if (re.code == 355 || re.code ==356){
                    Toast.makeText(MainActivity.this,re.message,Toast.LENGTH_SHORT).show();
                }
                if (re.code ==1){
                    Toast.makeText(MainActivity.this,re.message,Toast.LENGTH_SHORT).show();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowTime = df.format(new Date());
                    Msg user = new Msg(re.user,re.txt,nowTime);
                    msgList.add(user);
                    adapter.notifyDataSetChanged();
                    listView.smoothScrollToPosition(msgList.size()-1);
                    editName.setText("");
                    editComment.setText("");
                }
            }
        });

    }

}