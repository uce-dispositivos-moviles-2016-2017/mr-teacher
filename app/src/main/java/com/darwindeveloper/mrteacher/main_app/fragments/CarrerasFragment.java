package com.darwindeveloper.mrteacher.main_app.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Carrera;
import com.darwindeveloper.mrteacher.classes.Constants;
import com.darwindeveloper.mrteacher.classes.Universidad;
import com.darwindeveloper.mrteacher.database.DataBaseHelper;
import com.darwindeveloper.mrteacher.database.DatabaseManager;
import com.darwindeveloper.mrteacher.main_app.adapters.CarrerasAdapter;
import com.darwindeveloper.mrteacher.main_app.adapters.SpinnerUniversidadesAdapter;
import com.darwindeveloper.mrteacher.main_app.dialogs.NuevaCarreraDialogActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DARWIN on 29/1/2017.
 */

public class CarrerasFragment extends Fragment {


    private final int REQUEST_NUEVO_CARRERA = 1;


    private Context context;
    private View rootView;
    LinearLayout main_content, no_data;
    private RecyclerView recyclerView;
    private CarrerasAdapter mCarrerasAdapter;

    private ArrayList<Carrera> carreraArrayList = new ArrayList<>();


    private DatabaseManager databaseManager;
    private Spinner spinner;//spinner para filtrar los cursos por universidades
    private ArrayList<Universidad> universidades = new ArrayList<>();
    int universidad_id;//condicion por el cual se buscara los cursos (se buscara los cursos segun la universidad a la que pertenezcan)

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        DataBaseHelper dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Log.i("DB NAME", dataBaseHelper.getDatabaseName());
        databaseManager = new DatabaseManager(context, dataBaseHelper.getWritableDatabase());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_carreras, container, false);


        main_content = (LinearLayout) rootView.findViewById(R.id.main_content);
        no_data = (LinearLayout) rootView.findViewById(R.id.no_data);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewCursos);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        mCarrerasAdapter = new CarrerasAdapter(context, carreraArrayList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(mCarrerasAdapter);

        ImageButton fab = (ImageButton) rootView.findViewById(R.id.btn_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (universidades.size() == 0) {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    mBuilder.setMessage("ERROR: para poder crear un nuevo curso primero debes crear una universidad, colegio o alguna institucion educativa");
                    mBuilder.setPositiveButton("ENTENDIDO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mBuilder.create().show();
                } else {
                    Intent intent = new Intent(context, NuevaCarreraDialogActivity.class);
                    startActivityForResult(intent, REQUEST_NUEVO_CARRERA);
                }

            }
        });


        return rootView;

    }


    @Override
    public void onResume() {
        super.onResume();
        universidades.clear();
        new LoadUniversidades("select * from " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS, null).execute();
    }

    private class LoadCarreras extends AsyncTask<Void, Void, Void> {


        private String query;
        private String[] selectionArgs;

        public LoadCarreras(String query, String[] selectionArgs) {
            this.query = query;
            this.selectionArgs = selectionArgs;
        }


        @Override
        protected void onPreExecute() {
            main_content.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            int tmp = carreraArrayList.size();
            if (tmp > 0) {
                carreraArrayList.clear();
                mCarrerasAdapter.notifyItemRangeRemoved(0, tmp - 1);
                mCarrerasAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            carreraArrayList.addAll(databaseManager.getCarreras(query, selectionArgs));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (carreraArrayList.size() > 0) {
                no_data.setVisibility(View.GONE);
                main_content.setVisibility(View.VISIBLE);
                mCarrerasAdapter.notifyItemRangeInserted(0, carreraArrayList.size() - 1);
                mCarrerasAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_NUEVO_CARRERA) {
            if (resultCode == Activity.RESULT_OK) {

                String selectionArgs[] = {universidad_id + ""};
                new LoadCarreras("select * from " + Constants.TABLA_CARRERAS + " where institucion_id = ?", selectionArgs).execute();
            }
        }
    }


    private class LoadUniversidades extends AsyncTask<Void, Void, Void> {

        String query;
        String[] selectionArgs;


        public LoadUniversidades(String query, String[] selectionArgs) {
            this.query = query;
            this.selectionArgs = selectionArgs;
        }

        @Override
        protected void onPreExecute() {
            universidades.clear();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            universidades.addAll(databaseManager.get_instituciones_educativas(query, selectionArgs));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SpinnerUniversidadesAdapter spinnerAdapter = new SpinnerUniversidadesAdapter(context, R.layout.item_sppiner_universidades, universidades);
            spinner.setAdapter(spinnerAdapter);
            setClickSpinner();
        }
    }


    private void setClickSpinner() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                universidad_id = universidades.get(position).getId();
                String[] selectionArgs = {universidad_id + ""};//para la condicion where del query
                new LoadCarreras("select * from " + Constants.TABLA_CARRERAS + " where institucion_id = ?", selectionArgs).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}