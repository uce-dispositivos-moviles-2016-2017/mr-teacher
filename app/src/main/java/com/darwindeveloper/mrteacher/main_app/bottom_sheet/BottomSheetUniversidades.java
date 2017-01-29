package com.darwindeveloper.mrteacher.main_app.bottom_sheet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.mrteacher.R;
import com.darwindeveloper.mrteacher.database.DataBaseHelper;
import com.darwindeveloper.mrteacher.database.DatabaseManager;

import java.io.IOException;

/**
 * Created by SONY on 28/1/2017.
 */

public class BottomSheetUniversidades extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    AppCompatActivity activity;
    BottomSheetBehavior mBottomSheetBehavior;

    public static String U_ID = "com.darwindeveloper.mrteacher.bottomshetu.u_id";
    public static String U_NOMBRE = "com.darwindeveloper.mrteacher.bottomshetu.u_nombre";
    public static String U_SIGLAS = "com.darwindeveloper.mrteacher.bottomshetu.u_siglas";
    public static String U_TELEFONO = "com.darwindeveloper.mrteacher.bottomshetu.u_telefono";
    public static String U_DIRECCION = "com.darwindeveloper.mrteacher.bottomshetu.u_direccion";
    public static String U_OBS = "com.darwindeveloper.mrteacher.bottomshetu.u_obs";


    int id;
    String nombre, siglas, telefono, direccion, obs;


    TextView textView_nombre, textView_siglas, textView_telefono, textView_direccion, textView_obs;

    EditText editText_nombre, editText_siglas, editText_telefono, editText_direccion, editText_obs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle mArgs = getArguments();
        id = mArgs.getInt(U_ID, -1);
        nombre = mArgs.getString(U_NOMBRE);
        siglas = mArgs.getString(U_SIGLAS);
        telefono = mArgs.getString(U_TELEFONO);
        direccion = mArgs.getString(U_DIRECCION);
        obs = mArgs.getString(U_OBS);
        setHasOptionsMenu(true);

    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View root = View.inflate(getContext(), R.layout.bottom_sheet_universidades, null);
        dialog.setContentView(root);


        activity = (AppCompatActivity) getActivity();


        textView_nombre = (TextView) root.findViewById(R.id.textView_nombre);
        textView_siglas = (TextView) root.findViewById(R.id.textView_siglas);
        textView_telefono = (TextView) root.findViewById(R.id.textView_telefono);
        textView_direccion = (TextView) root.findViewById(R.id.textView_direccion);
        textView_obs = (TextView) root.findViewById(R.id.textView_obs);


        editText_nombre = (EditText) root.findViewById(R.id.editText_nombre);
        editText_siglas = (EditText) root.findViewById(R.id.editText_siglas);
        editText_telefono = (EditText) root.findViewById(R.id.editText_telefono);
        editText_direccion = (EditText) root.findViewById(R.id.editText_direccion);
        editText_obs = (EditText) root.findViewById(R.id.editText_obs);


        final ImageButton btn_edit = (ImageButton) root.findViewById(R.id.imageButtonE);
        final ImageButton btn_save = (ImageButton) root.findViewById(R.id.imageButtonS);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) root.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            mBottomSheetBehavior = (BottomSheetBehavior) behavior;
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }


        textView_nombre.setText(nombre);
        textView_siglas.setText(siglas);
        textView_telefono.setText(telefono);
        textView_direccion.setText(direccion);
        textView_obs.setText(obs);


        ((ImageButton) root.findViewById(R.id.imageButtonM)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btn_edit.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);

                textView_nombre.setVisibility(View.GONE);
                textView_siglas.setVisibility(View.GONE);
                textView_telefono.setVisibility(View.GONE);
                textView_direccion.setVisibility(View.GONE);
                textView_obs.setVisibility(View.GONE);

                editText_nombre.setVisibility(View.VISIBLE);
                editText_direccion.setVisibility(View.VISIBLE);
                editText_siglas.setVisibility(View.VISIBLE);
                editText_telefono.setVisibility(View.VISIBLE);
                editText_obs.setVisibility(View.VISIBLE);


                editText_nombre.setText(textView_nombre.getText());
                editText_telefono.setText(textView_telefono.getText());
                editText_siglas.setText(textView_siglas.getText());
                editText_direccion.setText(textView_direccion.getText());
                editText_obs.setText(textView_obs.getText());


            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = null;
                try {
                    dataBaseHelper = new DataBaseHelper(activity);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                DatabaseManager databaseManager = new DatabaseManager(activity, dataBaseHelper.getWritableDatabase());

                nombre = editText_nombre.getText().toString();
                siglas = editText_siglas.getText().toString();
                telefono = editText_telefono.getText().toString();
                direccion = editText_direccion.getText().toString();
                obs = editText_obs.getText().toString();


                databaseManager.updateUniversidad(id, nombre, siglas, telefono, direccion, obs);

                Toast.makeText(activity, "Cambios Guardados", Toast.LENGTH_SHORT).show();

                updateEventListener.updateEvent(true);

                dismiss();
            }
        });

    }


    public interface OnUpdateEventListener {
        public void updateEvent(boolean data_update);
    }

    OnUpdateEventListener updateEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            updateEventListener = (OnUpdateEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


}