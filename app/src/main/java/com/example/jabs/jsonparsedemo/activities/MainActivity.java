package com.example.jabs.jsonparsedemo.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jabs.jsonparsedemo.R;
import com.example.jabs.jsonparsedemo.activities.adapter.RowAdapter;
import com.example.jabs.jsonparsedemo.activities.asynctask.GetAsyncTask;
import com.example.jabs.jsonparsedemo.activities.interfaces.OnAsyncResult;
import com.example.jabs.jsonparsedemo.activities.pojo.Popultion;
import com.example.jabs.jsonparsedemo.activities.utils.MyUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Popultion> populationLists;
    RecyclerView recycler_view_List;
    private RecyclerView.LayoutManager mLayoutManager;
    private RowAdapter rowAdapter;
    private MyUtility objUtility;
    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objUtility = new MyUtility(MainActivity.this);
        initControls();
    }

    private void initControls() {
        recycler_view_List = (RecyclerView) findViewById(R.id.recycler_view);
        populationLists = new ArrayList<>();

        if (objUtility.checkInternetConnection()) {
            apiCallForShowList();
        } else {
            objUtility.setInternetAlertMessage();
        }
    }

    private void apiCallForShowList() {
        objUtility.showProgressDialog();
        String url = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";
        GetAsyncTask getAsyncTask = new GetAsyncTask(url, onAsyncResultForLoadList);
        getAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    OnAsyncResult onAsyncResultForLoadList = new OnAsyncResult() {
        @Override
        public void OnSuccess(String result) {
            objUtility.hideProgressDialog();
            Log.e(TAG, "Population Result:" + result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (!jsonObject.isNull("worldpopulation")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("worldpopulation");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objFriendList = jsonArray.getJSONObject(i);
                        if (objFriendList != null) {
                            Popultion objPop = new Popultion();

                            if (!objFriendList.isNull("rank"))
                                objPop.setRank(objFriendList.getString("rank"));

                            if (!objFriendList.isNull("country"))
                                objPop.setCountry(objFriendList.getString("country"));

                            if (!objFriendList.isNull("population"))
                                objPop.setPopulation(objFriendList.getString("population"));

                            if (!objFriendList.isNull("flag"))
                                objPop.setFlag(objFriendList.getString("flag"));


                            populationLists.add(objPop);
                            showFriendList();
                        }
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnFailure(String result) {
            Log.e("log_tag", "browse list failed" + result);
            objUtility.hideProgressDialog();
        }
    };


    private void showFriendList() {
        recycler_view_List.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recycler_view_List.setLayoutManager(mLayoutManager);
        if (populationLists != null) {
            rowAdapter = new RowAdapter(MainActivity.this, populationLists);
            recycler_view_List.setAdapter(rowAdapter);
        }

    }

}
