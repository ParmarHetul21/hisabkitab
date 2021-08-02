package com.example.labpractical;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<MyDataItem> myDataItems;
    ArrayList<Integer> ImageData;
    int resources;

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MyDataItem> objects, ArrayList<Integer> array_image) {
        super(context, resource, objects);

        this.context = context;
        this.resources = resource;
        myDataItems = objects;
        ImageData = array_image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MyDataItem myDataItem = (MyDataItem) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list__display, parent, false);
        }

        TextView nm =  convertView.findViewById(R.id.tvTitle);
        ImageView view = convertView.findViewById(R.id.imgIcon);

        nm.setText(""+myDataItem.getName());
        view.setImageResource(ImageData.get(position));
        return convertView;
    }
}
