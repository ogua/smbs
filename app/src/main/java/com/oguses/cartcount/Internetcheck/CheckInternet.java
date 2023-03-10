package com.oguses.cartcount.Internetcheck;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {

    private Context mcontext;
    private static CheckInternet minstance;

    public CheckInternet(Context mcontext){
        this.mcontext = mcontext;
    }

    public static synchronized CheckInternet getInstance(Context mcontext){
        if (minstance == null){
            minstance = new CheckInternet(mcontext);
        }
        return minstance;
    }

    public boolean IsNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mcontext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            return false;
        }else{
            return true;
        }
    }




}
