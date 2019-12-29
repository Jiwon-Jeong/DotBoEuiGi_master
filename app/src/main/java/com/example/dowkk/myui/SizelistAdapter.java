package com.example.dowkk.myui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SizelistAdapter extends BaseAdapter {

    Activity activity;
    String table[][];

    public SizelistAdapter(Activity activity, String[][] table) {
        super();
        this.activity = activity;
        this.table = table;
    }

    @Override
    public int getCount() {
        return table.length;
    }

    @Override
    public Object getItem(int position) {
        return table[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView column1, column2, column3, column4, column5, column6, column7;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        int contentSize = table[position].length;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            switch (contentSize) {
                case 7 : holder.column7 = (TextView) convertView.findViewById(R.id.column7);
                case 6 : holder.column6 = (TextView) convertView.findViewById(R.id.column6);
                case 5 : holder.column5 = (TextView) convertView.findViewById(R.id.column5);
                case 4 : holder.column4 = (TextView) convertView.findViewById(R.id.column4);
                case 3 : holder.column3 = (TextView) convertView.findViewById(R.id.column3);
                case 2 : holder.column2 = (TextView) convertView.findViewById(R.id.column2);
                case 1 : holder.column1 = (TextView) convertView.findViewById(R.id.column1);
                default:break;
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (contentSize) {
            case 7 : holder.column7.setText(table[position][6]);
                holder.column7.setVisibility(View.VISIBLE);
                holder.column7.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][6] + table[position][6]);
                if(position == 0)
                    holder.column7.setContentDescription(table[position][6]);
            case 6 : holder.column6.setText(table[position][5]);
                holder.column6.setVisibility(View.VISIBLE);
                holder.column6.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][5] + table[position][5]);
                if(position == 0)
                    holder.column6.setContentDescription(table[position][5]);
            case 5 : holder.column5.setText(table[position][4]);
                holder.column5.setVisibility(View.VISIBLE);
                holder.column5.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][4] + table[position][4]);
                if(position == 0)
                    holder.column5.setContentDescription(table[position][4]);
            case 4 : holder.column4.setText(table[position][3]);
                holder.column4.setVisibility(View.VISIBLE);
                holder.column4.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][3] + table[position][3]);
                if(position == 0)
                    holder.column4.setContentDescription(table[position][3]);
            case 3 : holder.column3.setText(table[position][2]);
                holder.column3.setVisibility(View.VISIBLE);
                holder.column3.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][2] + table[position][2]);
                if(position == 0)
                    holder.column3.setContentDescription(table[position][2]);
            case 2 : holder.column2.setText(table[position][1]);
                holder.column2.setVisibility(View.VISIBLE);
                holder.column2.setContentDescription(table[0][0] + table[position][0] + "에서" + table[0][1] + table[position][1]);
                if(position == 0)
                    holder.column2.setContentDescription(table[position][1]);
            case 1 : holder.column1.setText(table[position][0]);
                holder.column1.setVisibility(View.VISIBLE);
            default:break;
        }

        return convertView;
    }
}