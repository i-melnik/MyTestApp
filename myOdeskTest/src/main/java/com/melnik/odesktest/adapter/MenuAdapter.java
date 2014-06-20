package com.melnik.odesktest.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.melnik.odesktest.R;

/**
 * Created by i.melnik on 30.05.2014.
 */
public class MenuAdapter implements ListAdapter {

    private Context context;
    private String[] data;
    private LayoutInflater inflater;

    public MenuAdapter(Context context, String[] data)
    {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.menu_item, null);

            holder.item = (TextView) convertView.findViewById(R.id.menu_item_text);
            holder.arrow = (ImageView) convertView.findViewById(R.id.menu_item_arrow);
            holder.image = (ImageView) convertView.findViewById(R.id.menu_item_icon);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setText(data[position]);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder
    {
        protected TextView item;
        protected ImageView image;
        protected ImageView arrow;
    }
}
