package com.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    private TextView cases,recovered,critical,
            active, todayCases, todaysDeath, totalDeath, affectedCountries;
    private SimpleArcLoader simpleArcLoader;
    private ScrollView scrollView;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSetUp();
        fetchData();
    }

    private void viewSetUp(){
        cases = findViewById(R.id.numCases);
        recovered = findViewById(R.id.numRecovered);
        critical = findViewById(R.id.numCritical);
        active = findViewById(R.id.numActive);
        todayCases = findViewById(R.id.numTotalCases);
        todaysDeath = findViewById(R.id.nuTodaysDeath);
        totalDeath = findViewById(R.id.numTotalDeath);
        affectedCountries = findViewById(R.id.numAffectedCountries);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.pieChart);

    }

    private void fetchData(){
        String url = "https://disease.sh/v3/covid-19/all/";
        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            cases.setText(jsonObject.getString("cases"));
                            recovered.setText(jsonObject.getString("recovered"));
                            critical.setText(jsonObject.getString("critical"));
                            active.setText(jsonObject.getString("active"));
                            todayCases.setText(jsonObject.getString("todayCases"));
                            totalDeath.setText(jsonObject.getString("deaths"));
                            todaysDeath.setText(jsonObject.getString("todayDeaths"));
                            affectedCountries.setText(jsonObject.getString("affectedCountries"));

                           pieChart.addPieSlice(new PieModel("Total Cases", Integer.parseInt(cases.getText().toString()),
                                   Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered.getText().toString()),
                                    Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(totalDeath.getText().toString()),
                                    Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(active.getText().toString()),
                                    Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



    public void onTrackCountries(View view) {
        Intent intent = new Intent(getApplicationContext(), AffectedCountriesActivity.class);
        startActivity(intent);
    }
}