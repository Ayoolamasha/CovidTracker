package com.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private int positionCountry;
    private TextView country, cases, recovered,
            critical, active, todayDeaths, todaysCases, population, continent,deaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);

        getSupportActionBar().setTitle( "BreakDown " + AffectedCountriesActivity.countriesModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        cases= findViewById(R.id.countryCases);
        country = findViewById(R.id.countryName);
        recovered = findViewById(R.id.numCRecoveredases);
        critical = findViewById(R.id.numCriticaCases);
        active = findViewById(R.id.numActiveCases);
        todayDeaths = findViewById(R.id.countryDailyDeath);
        todaysCases = findViewById(R.id.CountryDailyCases);
        population = findViewById(R.id.numPopulation);
        continent = findViewById(R.id.continent);
        deaths = findViewById(R.id.countryDeaths);

        country.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getCountry());
        cases.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getCases());
        recovered.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getRecovered());
        critical.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getCritical());
        active.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getActive());
        deaths.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getDeaths());
        todaysCases.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getTodayCases());
        todayDeaths.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getTodayDeath());
        population.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getPopulation());
        continent.setText(AffectedCountriesActivity.countriesModelList.get(positionCountry).getContinent());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}