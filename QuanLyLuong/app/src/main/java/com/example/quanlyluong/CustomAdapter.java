package com.example.quanlyluong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<byte[]> images;
    List<String> names;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<byte[]> images, List<String> names) {
        this.context = applicationContext;
        this.images = images;
        this.names = names;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(names.get(i).split(" - ")[0]);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView text = (TextView) view.findViewById(R.id.textView);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeByteArray(images.get(i), 0, images.get(i).length, options);

        icon.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));

        text.setText(names.get(i));
        return view;
    }
}