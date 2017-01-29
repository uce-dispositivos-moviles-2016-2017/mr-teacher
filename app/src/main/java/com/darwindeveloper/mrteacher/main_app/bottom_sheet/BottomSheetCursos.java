package com.darwindeveloper.mrteacher.main_app.bottom_sheet;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.darwindeveloper.mrteacher.R;

/**
 * Created by DARWIN on 26/1/2017.
 */

public class BottomSheetCursos extends BottomSheetDialogFragment {



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

    private Toolbar toolbar;
    AppCompatActivity activity;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View root = View.inflate(getContext(), R.layout.bottom_sheet_cursos, null);
        dialog.setContentView(root);


        activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) root.findViewById(R.id.frtoolbar);
        activity.setSupportActionBar(toolbar);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) root.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            mBottomSheetBehavior = (BottomSheetBehavior) behavior;
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //cambiamos del toolbar normal al toolbar sms chat
        toolbar.getMenu().clear();


        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        inflater.inflate(R.menu.bottom_sheet_events, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_events_cancel:
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}