package com.amriksinghpadam.myplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerItemAdapter extends ArrayAdapter<String> {

    private Context context;
    private TextView singerName;
    private String[] singerListArray;

    public SpinnerItemAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context,resource,objects);
        singerListArray = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,parent);
    }

    public View initView(int position, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.spinner_single_item_singer,parent,false);
        singerName = view.findViewById(R.id.spinner_items_id);

        singerName.setText(singerListArray[position]);
        return view;
    }
}
