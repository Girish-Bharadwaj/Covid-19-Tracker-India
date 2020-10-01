package com.example.covid_19_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
Button button;
TextView totalg,recoveredg,deathsg,activeg;
LinearLayout card1,card2;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.trackc);
        totalg=findViewById(R.id.totalg);
        recoveredg=findViewById(R.id.recoveredg);
        deathsg=findViewById(R.id.deathsg);
        activeg=findViewById(R.id.activeg);
        progressBar=findViewById(R.id.progress);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, stats.class);
                startActivity(intent);
            }
        });
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        String url="https://disease.sh/v3/covid-19/all?yesterday=true&twoDaysAgo=false&allowNull=false";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalg.setText(""+response.getString("cases"));
                    recoveredg.setText(""+response.getString("recovered"));
                    deathsg.setText(""+response.getString("deaths"));
                    activeg.setText(""+response.getString("active"));
                    progressBar.setVisibility(View.GONE);
                    card1.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);

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
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}