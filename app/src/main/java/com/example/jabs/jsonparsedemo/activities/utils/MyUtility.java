package com.example.jabs.jsonparsedemo.activities.utils;

/**
 * Created by jabs on 9/7/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.jabs.jsonparsedemo.R;


public class MyUtility {

    private Activity context;
    private Dialog dialog;
    private ProgressDialog prd;

    public MyUtility(Activity context) {
        this.context = context;
    }


    public void hideSoftKeyboard() {
        if (context == null)
            return;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        } catch (StackOverflowError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * method is used for show soft keyboard.
     */
    public void showSoftKeyBord(EditText editText) {
        if (context == null)
            return;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText,
                    InputMethodManager.SHOW_IMPLICIT);
        } catch (StackOverflowError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean checkInternetConnection() {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else

        {
            return false;
        }

    }


    public void setAlertMessage(String title, String msg) {
        if (context == null)
            return;

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.show();
    }

    public void setInternetAlertMessage() {
        if (context == null)
            return;

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.msg_network_error));
        dialog.setMessage(context.getString(R.string.msg_internet_connection));
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.show();
    }

    public void showProgressDialog() {
        if (prd == null) {
            prd = new ProgressDialog(context);
            prd.setTitle("Loading....");
            prd.setMessage("Please wait While Data Loading");
            prd.setCancelable(true);
            prd.show();
        } else if (prd != null && !prd.isShowing()) {
            prd.show();
        }
    }

    public void hideProgressDialog() {
        if (prd != null && prd.isShowing()) {
            prd.dismiss();
        }
    }


}
