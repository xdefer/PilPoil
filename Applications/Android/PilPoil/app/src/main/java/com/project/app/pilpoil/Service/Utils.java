package com.project.app.pilpoil.Service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.project.app.pilpoil.R;

public class Utils {

    public static final String TAG_GooglePlayServices = "GooglePlayServices";
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    private static int TYPE = TYPE_NOT_CONNECTED;
    private static Context context;
    //protected static final int CREATE_INFORMATION_DIALOG = 1320;

    public Utils(Context mContext){
        context = mContext;
    }

    /* Check if Google Play Services is available */
    public static boolean isGooglePlayServicesAvailable(Context context) {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG_GooglePlayServices, GooglePlayServicesUtil.getErrorString(status));
            return false;
        } else {
            Log.i(TAG_GooglePlayServices, GooglePlayServicesUtil.getErrorString(status));
            return true;
        }
    }

    public void checkNetwork(){
        // If not OK Popup to active wifi or data mobile
        if (!isMobileDataEnabled(context) && !isWifiEnabled(context))
            showNetworkAlert();
    }

    public void checkGPS() {
        if (!isGPSEnabled())
            showGPSAlert();
    }

    public boolean isWifiEnabled(Context context) {
        if (getConnectivityStatus(context) == TYPE_WIFI)
            return true;
        else
            return false;
    }

    public boolean isMobileDataEnabled(Context context) {
        if (getConnectivityStatus(context) == TYPE_MOBILE)
            return true;
        else
            return false;
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /* Function to show settings alert dialog. On pressing Utils button will lauch Utils Options */
    private static void showNetworkAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.location_settings);
        // Setting Dialog Message
        alertDialog.setMessage(R.string.network_not_allowed);
        // On pressing Utils button
        alertDialog.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton(R.string.dialog_close_program, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ((Activity)context).finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /* Function to show settings alert dialog. On pressing Utils button will lauch Utils Options */
    private static void showGPSAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.location_settings);
        // Setting Dialog Message
        alertDialog.setMessage(R.string.location_not_allowed);
        // On pressing Utils button
        alertDialog.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /* Generic Dialog */
    /*private Dialog createDialog(int type) {
        AlertDialog dialog = null;
        switch (type) {
            case CREATE_INFORMATION_DIALOG:
                dialog = new AlertDialog.Builder(context)
                        .setTitle("Information")
                        .setMessage("Download was finished successfully.")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int whichButton) {

                            }
                        })
                        .create();
                break;
        }
        return dialog;
    }*/

    private static int getConnectivityStatus(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                TYPE = TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                TYPE = TYPE_MOBILE;
        }
        return TYPE;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = "";
        if (conn == TYPE_WIFI) {
            status = String.valueOf(R.string.wifi_enabled);
        } else if (conn == TYPE_MOBILE) {
            status = String.valueOf(R.string.mobile_data_enabled);
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = String.valueOf(R.string.not_connected_to_internet);
        }
        return status;
    }
}
