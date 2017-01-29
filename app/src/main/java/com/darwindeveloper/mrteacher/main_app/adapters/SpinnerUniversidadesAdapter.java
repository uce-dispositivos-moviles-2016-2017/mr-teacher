package com.darwindeveloper.mrteacher.main_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Universidad;

import java.util.ArrayList;

/**
 * Created by SONY on 28/1/2017.
 */

public class SpinnerUniversidadesAdapter extends ArrayAdapter<Universidad> {
    private Context context;
    private ArrayList<Universidad> universidades;

    public SpinnerUniversidadesAdapter(Context context, int resource, ArrayList<Universidad> universidades) {
        super(context, resource, universidades);
        this.context = context;
        this.universidades = universidades;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return initView(position, convertView);
    }


    private View initView(int position, View convertView) {
        Universidad universidad = universidades.get(position);
        if (convertView == null)
            convertView = View.inflate(getContext(),
                    android.R.layout.simple_list_item_1,
                    null);
        TextView tvText1 = (TextView) convertView.findViewById(android.R.id.text1);
        tvText1.setText(universidad.getNombre());
        return convertView;
    }


}
