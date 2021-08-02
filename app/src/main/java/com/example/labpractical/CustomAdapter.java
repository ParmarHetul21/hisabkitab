package com.example.labpractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<CustomItems> {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<CustomItems> customItems;
    ArrayList<Integer> ImageData;
    int resources;

    public CustomAdapter(Context context, int resources,ArrayList<CustomItems> customList,ArrayList<Integer> array_image) {
        super(context, 0, customList);
        this.context = context;
        this.resources = resources;
        this.customItems = customList;
        ImageData = array_image;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CustomItems  customItems = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_spinner, parent, false);
        }
        CustomItems items = getItem(position);
        ImageView spinnerImage = convertView.findViewById(R.id.ivCustomSpinner);
        TextView spinnerName = convertView.findViewById(R.id.tvCustomSpinner);

        spinnerName.setText(""+customItems.getSpinnerText());
        spinnerImage.setImageResource(ImageData.get(position));

        return convertView;
    }
}
