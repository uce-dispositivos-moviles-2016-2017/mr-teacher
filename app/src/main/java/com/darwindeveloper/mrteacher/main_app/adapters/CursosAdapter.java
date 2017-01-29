package com.darwindeveloper.mrteacher.main_app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Curso;

import java.util.ArrayList;

/**
 * Created by DARWIN on 26/1/2017.
 */

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.CursosHolder> {


    private Context context;
    private ArrayList<Curso> cursos;

    public CursosAdapter(Context context, ArrayList<Curso> cursos) {
        this.context = context;
        this.cursos = cursos;
    }

    @Override
    public CursosHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_curso, parent, false);
        return new CursosHolder(view);

    }

    @Override
    public void onBindViewHolder(CursosHolder holder, int position) {
        Curso curso = cursos.get(position);

        holder.nombre.setText(curso.getNombre());
        holder.paralelo.setText(curso.getParalelo());
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }


    public class CursosHolder extends RecyclerView.ViewHolder {

        TextView nombre, paralelo;

        public CursosHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.textView_nombre);
            paralelo = (TextView) itemView.findViewById(R.id.textView_paralelo);
        }
    }
}
