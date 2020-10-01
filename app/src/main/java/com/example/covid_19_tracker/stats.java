package com.example.covid_19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class stats extends AppCompatActivity {
    PieChart mPieChart;
    TextView tot,act,actn,dea,dean,rec,recn;
    Button button;
    ProgressBar prog;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        mPieChart = findViewById(R.id.piechart);
        tot=findViewById(R.id.totalcases);
        act=findViewById(R.id.activecases);
        actn=findViewById(R.id.activecasesnew);
        dea=findViewById(R.id.deaths);
        dean=findViewById(R.id.deathsnew);
        rec=findViewById(R.id.recovered);
        recn=findViewById(R.id.recoverednew);
        button=findViewById(R.id.track);
        prog=findViewById(R.id.prog);
        scrollView=findViewById(R.id.scroll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(stats.this, States.class);
                startActivity(intent);
            }
        });
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        String url="https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tot.setText(""+response.getString("totalCases"));
                    act.setText(""+response.getString("activeCases"));
                    actn.setText(""+response.getString("activeCasesNew"));
                    dea.setText(""+response.getString("deaths"));
                    dean.setText(""+response.getString("deathsNew"));
                    rec.setText(""+response.getString("recovered"));
                    recn.setText(""+response.getString("recoveredNew"));
                    mPieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(response.getString("activeCases")), Color.parseColor("#FF8400")));
                    mPieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(response.getString("recovered")), Color.parseColor("#4FA131")));
                    mPieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(response.getString("deaths")), Color.parseColor("#FF3535")));
                    mPieChart.startAnimation();
                    prog.setVisibility(View.GONE);
                    mPieChart.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);

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
                prog.setVisibility(View.GONE);


            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}