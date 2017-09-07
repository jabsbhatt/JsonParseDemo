package com.example.jabs.jsonparsedemo.activities.asynctask;

/**
 * Created by jabs on 9/7/2017.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.jabs.jsonparsedemo.activities.interfaces.OnAsyncResult;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class GetAsyncTask extends AsyncTask<Void, Void, String> {

    private String url;
    private OnAsyncResult onAsyncResult;
    private Boolean resultFlag;

    public GetAsyncTask(String url, OnAsyncResult listner) {
        this.url = url;
        this.onAsyncResult = listner;
        resultFlag = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String final_url = url.replaceAll(" ", "%20");
        /*DefaultHttpClient httpClient = new DefaultHttpClient();        HttpGet httpGet = new HttpGet(final_url);        try {            HttpResponse httpResponse = httpClient.execute(httpGet);            HttpEntity httpEntity = httpResponse.getEntity();            return EntityUtils.toString(httpEntity);        } catch (Exception e) {
            e.printStackTrace();            return "error";        }*/
        try {
            URL url = new URL(final_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(20000);

            Log.e("Response Code:", "Response Code: " + httpURLConnection.getResponseCode());
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            Log.e("Response : ", response);
            resultFlag = true;
            return response;
        } catch (SocketTimeoutException e1) {
            //e1.printStackTrace();            resultFlag = false;
            return "Connection has timed out. Do you want to retry?";

        } catch (Exception e) {
            e.printStackTrace();
            resultFlag = false;
            return "Unexpected error has occured";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (resultFlag) {
            if (onAsyncResult != null) {
                onAsyncResult.OnSuccess(result);
            }
        } else {
            if (onAsyncResult != null) {
                onAsyncResult.OnFailure(result);
            }

        }
    }
}