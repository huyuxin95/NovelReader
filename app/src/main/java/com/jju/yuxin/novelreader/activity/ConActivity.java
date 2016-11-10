package com.jju.yuxin.novelreader.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jju.yuxin.novelreader.R;
import com.jju.yuxin.novelreader.Utils.ParserWeb;
import com.jju.yuxin.novelreader.bean.NovalContentBean;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.novelreader.activity
 * Created by yuxin.
 * Created time 2016/11/10 0010 下午 7:19.
 * Version   1.0;
 * Describe :  文章内容
 * History:
 * ==============================================================================
 */

public class ConActivity extends Activity {


    private NovalContentBean novalContentBean;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_author;
    private TextView tv_time;
    private TextView tv_wd;
    private TextView tv_con;
    private Button bt_pre;
    private Button bt_next;

    //更新文章内容
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {

                tv_title.setText(novalContentBean.getTitle());
                tv_name.setText(novalContentBean.getNovel_name());
                tv_author.setText(novalContentBean.getAuthor());
                tv_time.setText(novalContentBean.getTime());
                tv_wd.setText(novalContentBean.getWdnumber());
                tv_con.setText(novalContentBean.getNv_content().toString());
                //如果上一章不存在
                if (novalContentBean.getPre_link() == "" || novalContentBean.getPre_link() == null) {
                    bt_pre.setVisibility(View.INVISIBLE);
                    bt_pre.setClickable(false);
                }
                //如果下一章不存在
                if (novalContentBean.getNext_link() == "" || novalContentBean.getNext_link() == null) {
                    bt_next.setVisibility(View.INVISIBLE);
                    bt_next.setClickable(false);
                }
                //上一章的点击事件
                bt_pre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ConActivity.this,ConActivity.class);
                        intent.putExtra("path",novalContentBean.getPre_link());
                        startActivity(intent);
                        finish();
                    }
                });
                //下一章的点击事件
                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ConActivity.this,ConActivity.class);
                        intent.putExtra("path",novalContentBean.getNext_link());
                        startActivity(intent);
                        finish();
                    }
                });

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_wd = (TextView) findViewById(R.id.tv_wd);
        tv_con = (TextView) findViewById(R.id.tv_con);
        bt_pre = (Button) findViewById(R.id.bt_pre);
        bt_next = (Button) findViewById(R.id.bt_next);

        final String path = getIntent().getStringExtra("path");
        new Thread() {
            @Override
            public void run() {
                novalContentBean = ParserWeb.parser_nol(path);
                if (novalContentBean != null) {
                    mhandler.sendEmptyMessage(111);
                }
            }
        }.start();
    }
}
