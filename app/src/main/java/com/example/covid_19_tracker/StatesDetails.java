package com.example.covid_19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatesDetails extends AppCompatActivity {
TextView reg,toti,newi,rec,newrec,dea,newdea;
LinearLayout layout;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states_details);
        Intent intent=getIntent();
        final int pos=intent.getIntExtra(States.POS,0);
        reg=findViewById(R.id.region);
        toti=findViewById(R.id.Totalinfect);
        newi=findViewById(R.id.newinfec);
        rec=findViewById(R.id.recovered);
        newrec=findViewById(R.id.recoverednew);
        dea=findViewById(R.id.deaths);
        newdea =findViewById(R.id.deathsnew);
        layout=findViewById(R.id.l1);
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        String url="https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";

        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray states = response.getJSONArray("regionData");
                    JSONObject st = states.getJSONObject(pos);
                    reg.setText(st.getString("region"));
                    toti.setText(st.getString("totalInfected"));
                    newi.setText(st.getString("newInfected"));
                    rec.setText(st.getString("recovered"));
                    newrec.setText(st.getString("newRecovered"));
                    dea.setText(st.getString("deceased"));
                    newdea.setText(st.getString("newDeceased"));
                    layout.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Error,Please Check Your Internet Connection",
                        Toast.LENGTH_LONG);

                toast.show();
                layout.setVisibility(View.VISIBLE);

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}