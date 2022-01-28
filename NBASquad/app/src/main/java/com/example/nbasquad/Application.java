package com.example.nbasquad;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Application extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener listener = null;

        //Hide action bar
        getSupportActionBar().hide();

        //Check for internet connection
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
            connected = true;
        }else  {
            connected = false;
        }

        if(connected){
            //Change screen after 2 seconds
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Application.this, ListScreen.class);
                    startActivity(i);
                    finish();
                }
            },3000);
        }else {
            //Set alert dialog title & message
            alertDialogBuilder.setTitle(getResources().getString(R.string.no_internet_connection));
            alertDialogBuilder.setMessage(getResources().getString(R.string.internet_description));

            //Positive button settings
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.internet_s1), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });

            //Negative button settings
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.internet_s2), new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });

            //Show alert dialog in the screen
            AlertDialog alertDialog  = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
