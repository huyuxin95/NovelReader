package com.jju.yuxin.novelreader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jju.yuxin.novelreader.R;
import com.jju.yuxin.novelreader.Utils.ParserWeb;
import com.jju.yuxin.novelreader.adapter.NovelAdapter;
import com.jju.yuxin.novelreader.bean.NovelBean;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

/**
 * =============================================================================
 * <p>
 * Copyright (c) 2016  yuxin rights reserved.
 * ClassName MainActivity
 * Created by yuxin.
 * Created time 10-11-2016 20:33.
 * Describe :主界面,通过Jsoup爬虫来获取网络小说
 * History:
 * Version   1.0.
 * <p>
 * ==============================================================================
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private List<NovelBean> novels;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                //获取文章列表成功,更新listview
                listView.setAdapter(new NovelAdapter(MainActivity.this, novels, options));
            }

        }
    };
    private Button get_;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化ImageLoader这用的是universalimageloader库
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(30 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        //初始化Options
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();
        ImageLoader.getInstance().init(configuration);

        listView = (ListView) findViewById(R.id.lv_content);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //从网页爬取数据
        http_getnovel();

        //listview的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                e(TAG, "onItemClick" + position);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        NovelBean novelBean = novels.get(position);
                        String url = novelBean.getNovel_path();
                        String path = ParserWeb.parser_web(url);
                        Intent intent = new Intent(MainActivity.this, ConActivity.class);
                        intent.putExtra("path", path);
                        startActivity(intent);
                    }
                }.start();
            }
        });
    }

    private void http_getnovel() {
//        <div class="detail" style="display: block;">
//        <em class="add jrsj_plus" bookId="609737"></em>
//        <a class="mark63" href="http://book.zongheng.com/book/609737.html" target="_blank"><img src="http://static.zongheng.com/upload/cover/2016/10/1476063094434.jpg" alt="不死龙帝"  style="width: 63px; height: 84px;"><span></span></a>
//        <h3><a style="" href="http://book.zongheng.com/book/609737.html" title="奇幻玄幻: 不死龙帝" target="_blank">不死龙帝</a></h3>
//        <p>作者：<a href="http://t.zongheng.com/25467902" title="从小不爱吃香菜" target="_blank">从小不爱…</a></p>
//        <p>类型：<a href="http://book.zongheng.com/category/1.html" title="奇幻玄幻" target="_blank">奇幻玄幻</a></p>
//        <p class="tr"><a href="http://book.zongheng.com/book/609737.html" target="_blank">【阅读】</a></p>
//        <div class="cl0"></div>
//        </div>
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //获取连接
                    Connection connect = Jsoup.connect("http://www.zongheng.com/category/1.html");
                    //设置超时
                    connect.timeout(10000);
                    Document document = connect.get();
                    Elements detail = document.select("div.detail");
                    novels = new ArrayList<>();
                    for (Element element : detail) {
                        String novel_image = element.getElementsByTag("img").first().attr("src");
                        String novel_name = element.getElementsByTag("h3").first().text();
                        Elements p_element = element.getElementsByTag("p");
                        String novel_autor = p_element.get(0).text();
                        String novel_type = p_element.get(1).text();
                        String novel_path = p_element.get(2).getElementsByTag("a").attr("href");
                        NovelBean novel = new NovelBean(novel_name, novel_type, novel_autor, novel_path, novel_image);
                        novels.add(novel);
                    }
                    mhandler.sendEmptyMessage(123);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
