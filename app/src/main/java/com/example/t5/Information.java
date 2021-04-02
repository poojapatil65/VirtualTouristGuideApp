package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;


public class Information extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    TextView lat;
    TextView name;
    // ResultReceiver resultReceiver;
    Geocoder geocoder;
    List<Address> address;
    Button b1,b2;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        lat = findViewById(R.id.textLatLong);
        name = findViewById(R.id.textname);
        b1 = findViewById(R.id.mapB);
        progressDialog =
                new ProgressDialog(Information.this);

        geocoder = new Geocoder(this, Locale.getDefault());

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Information.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Information.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getCurrentLocation() {
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(Information.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Information.this).
                                removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latesLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude =
                                    locationResult.getLocations().get(latesLocationIndex).getLatitude();
                            double longitude =
                                    locationResult.getLocations().get(latesLocationIndex).getLongitude();

                            DecimalFormatSymbols formatSymbols =
                                    new DecimalFormatSymbols(Locale.getDefault());
                            formatSymbols.setDecimalSeparator(',');
                            DecimalFormat df = new DecimalFormat("##.##", formatSymbols);


                            String value1 = df.format(latitude);
                            final String value2 = df.format(longitude);
                            String to = value1 + value2;
                            //lat.setText(to);
                            //lat.setText(value);
                            DatabaseReference databaseReference =
                                    FirebaseDatabase.getInstance().getReference().child("Table1");
                            databaseReference.child(to).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String place =
                                            String.valueOf(dataSnapshot.child("Name").getValue());
                                    String information1 =
                                            String.valueOf(dataSnapshot.child("Info1").getValue());
                                    String information2 =
                                            String.valueOf(dataSnapshot.child("Info2").getValue());

                                    String Value = information1 + "\n\n" + information2;
                                    name.setText(place + "\n");

                                    lat.setText(Value);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        /* try {
                             List<Address> addresses=geocoder.getFromLocation(latitude,longitude,1);
                             address= geocoder.getFromLocation(latitude,longitude,1);
                             String ad=address.get(0).getAddressLine(0);

                             lat.setText(ad);
                         } catch (IOException e) {
                             e.printStackTrace();
                         }*/

                        }
                    }
                }, Looper.getMainLooper());

    }

    public void nearhospitals(View view)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.justdial.com/Pune/Private-Hospitals/nct-10390288"));
        startActivity(intent);
    }

    public void bookcab(View view)
    {

        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.uber.com/looking?_ga=2.12137780.1879349710.1591956495-80096343.1577714833&pickup=%7B%22addressLine1%22%3A%22Sai+Aangan%22%2C%22addressLine2%22%3A%2216%2F4%2F2%2C+Ambegaon+Pathar%2C+Ambegaon+BK%2C+Pune%2C+Maharashtra%22%2C%22latitude%22%3A18.4615651%2C%22longitude%22%3A73.8418116%7D&uclick_id=631d88f4-5b6a-4b10-ab9c-137208544c98#_"));
        startActivity(intent);
    }

    public void nearbyhotel(View view)
    {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tripadvisor.in/Restaurants-g297654-Pune_Pune_District_Maharashtra.html"));
        startActivity(intent);
    }
}

