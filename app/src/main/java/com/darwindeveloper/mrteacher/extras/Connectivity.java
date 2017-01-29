package com.darwindeveloper.mrteacher.extras;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by DARWIN on 24/1/2017.
 */

public class Connectivity {

    private Context context;

    public Connectivity(Context context) {
        this.context = context;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }
}
