package com.jju.yuxin.novelreader.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.yuxin.novelreader.R;
import com.jju.yuxin.novelreader.bean.NovelBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.novelreader.adapter
 * Created by yuxin.
 * Created time 2016/11/10 0010 上午 11:25.
 * Version   1.0;
 * Describe : listview的列表
 * History:
 * ==============================================================================
 */

public class NovelAdapter extends BaseAdapter {
    private Context context;
    private List<NovelBean> novels;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public NovelAdapter(Context context, List<NovelBean> novels, DisplayImageOptions options) {
        this.context = context;
        this.novels = novels;
        this.options = options;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return novels.size();
    }

    @Override
    public Object getItem(int position) {
        return novels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.novel_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.novel_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.author = (TextView) convertView.findViewById(R.id.author);
            viewHolder.path = (TextView) convertView.findViewById(R.id.path);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //通过库加载图片
        ImageLoader.getInstance().displayImage(
                novels.get(position).getNovel_image(), viewHolder.imageView, options);
        viewHolder.name.setText(novels.get(position).getNovel_name());
        viewHolder.author.setText(novels.get(position).getNovel_autor());
        viewHolder.type.setText(novels.get(position).getNovel_type());
        viewHolder.path.setText("【阅读】");

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView author;
        TextView type;
        TextView path;
    }
}
