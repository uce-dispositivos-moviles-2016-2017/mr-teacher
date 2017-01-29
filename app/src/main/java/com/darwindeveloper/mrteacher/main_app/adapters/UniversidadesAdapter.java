package com.darwindeveloper.mrteacher.main_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Universidad;

import java.util.ArrayList;

/**
 * Created by SONY on 28/1/2017.
 */

public class UniversidadesAdapter extends RecyclerView.Adapter<UniversidadesAdapter.UniversidadesHolder> {


    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;
    private Context context;
    private ArrayList<Universidad> universidades;

    public UniversidadesAdapter(Context context, ArrayList<Universidad> universidades) {
        this.context = context;
        this.universidades = universidades;
    }

    @Override
    public UniversidadesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_universidad, parent, false);
        return new UniversidadesHolder(view);

    }

    @Override
    public void onBindViewHolder(UniversidadesHolder holder, final int position) {
        final Universidad universidad = universidades.get(position);


        holder.nombre.setText(universidad.getNombre());
        holder.siglas.setText(universidad.getSiglas());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClickU(universidad, position);
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongItemClickListener.LongItemClickU(universidad, position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return universidades.size();
    }


    public class UniversidadesHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView nombre, siglas;

        public UniversidadesHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.container_layout);
            nombre = (TextView) itemView.findViewById(R.id.textView_nombre);
            siglas = (TextView) itemView.findViewById(R.id.textView_siglas);

        }
    }


    public interface OnItemClickListener {
        /**
         * @param universidad nstitucion educativa seleccionada de la lista
         * @param posicion    posicion que ocupa en la lista
         */
        void itemClickU(Universidad universidad, int posicion);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnLongItemClickListener {
        void LongItemClickU(Universidad universidad, int posicion);
    }


    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }
}
