package com.jju.yuxin.novelreader.bean;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.novelreader.bean
 * Created by yuxin.
 * Created time 2016/11/10 0010 上午 11:09.
 * Version   1.0;
 * Describe : 列表Item  bean
 * History:
 * ==============================================================================
 */

public class NovelBean {

    private String novel_name;
    private String novel_image;
    private String  novel_autor;
    private String novel_type;
    private String novel_path;

    public String getNovel_autor() {
        return novel_autor;
    }

    public void setNovel_autor(String novel_autor) {
        this.novel_autor = novel_autor;
    }

    public String getNovel_image() {
        return novel_image;
    }

    public void setNovel_image(String novel_image) {
        this.novel_image = novel_image;
    }

    public String getNovel_name() {
        return novel_name;
    }

    public void setNovel_name(String novel_name) {
        this.novel_name = novel_name;
    }

    public String getNovel_path() {
        return novel_path;
    }

    public void setNovel_path(String novel_path) {
        this.novel_path = novel_path;
    }

    public String getNovel_type() {
        return novel_type;
    }

    public void setNovel_type(String novel_type) {
        this.novel_type = novel_type;
    }


    public NovelBean() {
    }

    public NovelBean(String novel_name, String novel_type, String novel_autor, String novel_path, String novel_image) {
        this.novel_name = novel_name;
        this.novel_type = novel_type;
        this.novel_autor = novel_autor;
        this.novel_path = novel_path;
        this.novel_image = novel_image;
    }

    @Override
    public String toString() {
        return "NovelBean{" +
                "novel_autor='" + novel_autor + '\'' +
                ", novel_name='" + novel_name + '\'' +
                ", novel_image='" + novel_image + '\'' +
                ", novel_type='" + novel_type + '\'' +
                ", novel_path='" + novel_path + '\'' +
                '}';
    }
}
