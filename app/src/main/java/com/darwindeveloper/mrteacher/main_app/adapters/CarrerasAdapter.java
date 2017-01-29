package com.darwindeveloper.mrteacher.main_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Carrera;

import java.util.ArrayList;

/**
 * Created by DARWIN on 29/1/2017.
 */

public class CarrerasAdapter extends RecyclerView.Adapter<CarrerasAdapter.CarrerasHolder> {


    private Context context;
    private ArrayList<Carrera> carreras;

    public CarrerasAdapter(Context context, ArrayList<Carrera> carreras) {
        this.context = context;
        this.carreras = carreras;
    }

    @Override
    public CarrerasHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_carrera, parent, false);
        return new CarrerasHolder(view);

    }

    @Override
    public void onBindViewHolder(CarrerasHolder holder, int position) {
        Carrera carrera = carreras.get(position);

        holder.nombre.setText(carrera.getNombre());

    }

    @Override
    public int getItemCount() {
        return carreras.size();
    }


    public class CarrerasHolder extends RecyclerView.ViewHolder {

        TextView nombre;

        public CarrerasHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.textView_nombre);
        }
    }
}

