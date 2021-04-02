package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardAdmin extends AppCompatActivity  {
    private CardView beaches, hotels, temples, forts, markets, church,hospital,vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        beaches = findViewById(R.id.dash_beaches);
        hotels = findViewById(R.id.dash_hotels);
        temples = findViewById(R.id.dash_temples);
        forts = findViewById(R.id.dash_forts);
        markets = findViewById(R.id.dash_markets);
        church = findViewById(R.id.dash_church);
        hospital=findViewById(R.id.dash_hospitals);
        vehicle=findViewById(R.id.dash_vehicles);

        beaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BeachAdmin.class));
            }
        });
        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HotelAdmin.class));
            }
        });
        temples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TempleAdmin.class));
            }
        });
        forts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FortAdmin.class));
            }
        });
        markets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MarketAdmin.class));
            }
        });
        church.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChurchAdmin.class));
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HospitalAdmin.class));
            }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VehicleAdmin.class));
            }
        });

    }

}