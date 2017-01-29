package com.darwindeveloper.mrteacher.main_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.classes.Constants;
import com.darwindeveloper.mrteacher.classes.Universidad;
import com.darwindeveloper.mrteacher.database.DataBaseHelper;
import com.darwindeveloper.mrteacher.database.DatabaseManager;
import com.darwindeveloper.mrteacher.main_app.adapters.UniversidadesAdapter;
import com.darwindeveloper.mrteacher.main_app.bottom_sheet.BottomSheetUniversidades;
import com.darwindeveloper.mrteacher.main_app.dialogs.NuevaUniversidadDialogActivity;

import java.io.IOException;
import java.util.ArrayList;

public class UniversidadesActivity extends AppCompatActivity implements UniversidadesAdapter.OnItemClickListener, UniversidadesAdapter.OnLongItemClickListener, BottomSheetUniversidades.OnUpdateEventListener {

    private static final int REQUEST_NUEVA_INSTITUCION = 1;//para realizar un nuevo ingreso de una institucion educativa
    private DatabaseManager databaseManager;//para el manejo de la base de datos
    private Context context;

    //para la lista con las instituciones educativas
    private RecyclerView recyclerView;
    private UniversidadesAdapter mAdapter;
    private ArrayList<Universidad> universidades = new ArrayList<>();


    //para el menu bottom sheet
    private BottomSheetDialogFragment bottomSheetDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = UniversidadesActivity.this;

        DataBaseHelper dataBaseHelper = null;
        try {
            dataBaseHelper = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }


        databaseManager = new DatabaseManager(context, dataBaseHelper.getWritableDatabase());


        setContentView(R.layout.activity_universidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUniversidades);
        //cunado la pantalla del telefono gira
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }

        mAdapter = new UniversidadesAdapter(this, universidades);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLongItemClickListener(this);
        recyclerView.setAdapter(mAdapter);

        loadUniversidades();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UniversidadesActivity.this, NuevaUniversidadDialogActivity.class);
                startActivityForResult(intent, REQUEST_NUEVA_INSTITUCION);
            }
        });


    }


    public void loadUniversidades() {
        new LoadUniversidades("select * from " + Constants.TABLA_INSTITUCIONES_EDUCATIVAS, null).execute();//cargamos los datos
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //cunado la pantalla del telefono gira
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NUEVA_INSTITUCION) {
            if (resultCode == RESULT_OK) {
                int id = data.getIntExtra(NuevaUniversidadDialogActivity.ID, 0);
                String nombre = data.getStringExtra(NuevaUniversidadDialogActivity.NOMBRE);
                String siglas = data.getStringExtra(NuevaUniversidadDialogActivity.SIGLAS);
                String telefono = data.getStringExtra(NuevaUniversidadDialogActivity.TELEFONO);
                String direccion = data.getStringExtra(NuevaUniversidadDialogActivity.DIRECCION);
                String observaciones = data.getStringExtra(NuevaUniversidadDialogActivity.OBSERVACIONES);

                Universidad tmp = new Universidad(id, nombre, siglas, telefono, direccion, observaciones);
                universidades.add(tmp);
                mAdapter.notifyItemInserted(universidades.size() - 1);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * @param universidad institucion educativa seleccionada de la lista
     * @param posicion    posicion que ocupa en la lista
     */
    @Override
    public void itemClickU(Universidad universidad, int posicion) {


        Bundle args = new Bundle();
        args.putInt(BottomSheetUniversidades.U_ID, universidad.getId());
        args.putString(BottomSheetUniversidades.U_NOMBRE, universidad.getNombre());
        args.putString(BottomSheetUniversidades.U_SIGLAS, universidad.getSiglas());
        args.putString(BottomSheetUniversidades.U_TELEFONO, universidad.getTelefono());
        args.putString(BottomSheetUniversidades.U_DIRECCION, universidad.getDireccion());
        args.putString(BottomSheetUniversidades.U_OBS, universidad.getDescripcion());
        BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetUniversidades();
        bottomSheetDialogFragment.setArguments(args);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

    }

    @Override
    public void updateEvent(boolean data_update) {
        if (data_update) {
            loadUniversidades();
        }
    }

    @Override
    public void LongItemClickU(final Universidad universidad, final int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Confirmación requerida");
        builder.setMessage("¿Seguro que desea eliminar " + universidad.getNombre() + "?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //eliminamos un elemento de la base de datos y de la lista
                databaseManager.deleteUniversidad(universidad.getId());
                universidades.remove(posicion);
                mAdapter.notifyItemRemoved(posicion);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();


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

            if (universidades.size() > 0) {
                mAdapter.notifyItemRangeInserted(0, universidades.size() - 1);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

}
