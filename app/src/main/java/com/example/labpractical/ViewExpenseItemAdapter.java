package com.example.labpractical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ViewExpenseItemAdapter extends ArrayAdapter<ViewExpenseItem>
{
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ViewExpenseItem> myExpenseitems;
    public ViewExpenseItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ViewExpenseItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewExpenseItem expenseItem = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_custom__expenses__list,parent,false);
        }

        TextView etitle = convertView.findViewById(R.id.TitleExp);
        TextView ctitle = convertView.findViewById(R.id.categorytitle);
        TextView amtexp = convertView.findViewById(R.id.AmountExp);
        TextView date = convertView.findViewById(R.id.expdate);

        etitle.setText(""+expenseItem.getTitle());
        ctitle.setText(""+expenseItem.getCtitle());
        amtexp.setText(""+expenseItem.getAmount());
        date.setText(""+expenseItem.getDate());

        return convertView;
    }
}
