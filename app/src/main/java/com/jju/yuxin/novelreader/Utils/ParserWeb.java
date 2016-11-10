package com.jju.yuxin.novelreader.Utils;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jju.yuxin.novelreader.bean.NovalContentBean;
import com.jju.yuxin.novelreader.bean.NovelBean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.novelreader.Utils
 * Created by yuxin.
 * Created time 2016/11/10 0010 下午 2:15.
 * Version   1.0;
 * Describe : 文章内容
 * History:
 * ==============================================================================
 */

public class ParserWeb {


    private static final String TAG = ParserWeb.class.getSimpleName();

    private static NovalContentBean novalContentBean;

    //解析书籍开始阅读地址
    public static String parser_web(String url) {
        String attr = "";
        try {

            e(TAG, "parser_web" + url);
            Document document = Jsoup.connect(url).get();
            Elements book_btn = document.select("div.book_btn");
            attr = book_btn.first().getElementsByTag("a").attr("href");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return attr;
    }

    //解析文章内容,返回完整的文章对象
    public static NovalContentBean parser_nol(String url) {
        NovalContentBean novalcon = new NovalContentBean();
        List<String> con = new ArrayList<>();
        try {
            Document novel = Jsoup.connect(url).get();
            Elements read_con = novel.select("div.pane");
            //<div class="tc txt">
            //<h1><em itemprop="headline">序章 尸山之下</em></h1>
            //</div>
            String title = read_con.first().getElementsByTag("h1").text();
            novalcon.setTitle(title);
            // e(TAG, "parser_nol" + title);
            Elements select = read_con.select("div.bread_crumb");
            String novel_name = select.first().getElementsByTag("a").get(3).text();
            novalcon.setNovel_name(novel_name);
            //  e(TAG, "parser_nol:novel_name" + novel_name);
            String author = read_con.select("span.author").text();
            novalcon.setAuthor(author);
            //e(TAG, "parser_nol!!!" + author);
            String time = read_con.select("span.number").text();
            String[] strs = time.split("字");
            novalcon.setTime(strs[0]);
            novalcon.setWdnumber("字" + strs[1]);
            // e(TAG, "parser_nol" + strs[0]);
            //e(TAG, "parser_nol::" + "字" + strs[1]);
            Elements select1 = read_con.select("div.content");
            for (Element ele : select1) {
                String p = ele.getElementsByTag("p").text();
                con.add(p);
            }
            novalcon.setNv_content(con);
            // e(TAG, "parser_nol" + con.toString() + "\n");

            Elements select2 = read_con.select("div.tc");
            Elements elements = select2.get(3).getElementsByTag("a");

            Elements select3 = elements.select("a.marr");
            String pre = "";
            String next = "";
            //判断是否存在上一章
            if (select3.size() == 2) {
                pre = select3.first().attr("href");
            }
            //判断是否存在下一章
            Elements select4 = elements.select("a.next");
            if (select4 != null) {
                next = select4.first().attr("href");
            }
            // e(TAG, "parser_nol" + "pre"+pre+"next"+next);
            novalcon.setPre_link(pre);
            novalcon.setNext_link(next);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return novalcon;

    }
}
