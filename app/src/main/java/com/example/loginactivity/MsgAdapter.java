package com.example.loginactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Msg msg = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView username = (TextView) view.findViewById(R.id.pinglun_name);
        TextView usercomment = (TextView) view.findViewById(R.id.pinglun_comment);
        TextView nowtime = (TextView) view.findViewById(R.id.pinglun_time);
        username.setText(msg.getName());
        usercomment.setText(msg.getComment());
        nowtime.setText(msg.time);
        return view;
    }
}

