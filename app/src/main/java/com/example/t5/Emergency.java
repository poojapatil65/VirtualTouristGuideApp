package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.*;
import android.app.PendingIntent;
import android.util.Log;
import androidx.annotation.NonNull;
import android.net.Uri;
import android.Manifest.permission;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Emergency extends AppCompatActivity {
    Button b1, b2, b3;
    TextView t;
    private static final int REQOUEST_CODE_LOCATION_PERMISSION = 1;
    private static final int REQOUEST_CODE_SEND_SMS = 0;
    double latitude;
    double longitude;
    String fulladress;
    Location location;
    Geocoder geocoder;
    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button1);
        b3 = findViewById(R.id.button2);
        t=findViewById(R.id.button3);
      geocoder = new Geocoder(Emergency.this, Locale.getDefault());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0000000000"));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getCurrentLocation();
                }


                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{permission.CALL_PHONE},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    startActivity(callIntent);
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0000000000"));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getCurrentLocation();
                }

                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{permission.CALL_PHONE},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    startActivity(callIntent);
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0000000000"));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getCurrentLocation();
                }

                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Emergency.this, new String[]{permission.CALL_PHONE},
                            REQOUEST_CODE_LOCATION_PERMISSION);
                } else {
                    startActivity(callIntent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == REQOUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.getFusedLocationProviderClient(Emergency.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Emergency.this).
                                removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latesLocationIndex = locationResult.getLocations().size() - 1;

                            latitude =
                                    locationResult.getLocations().get(latesLocationIndex).getLatitude();
                            longitude =
                                    locationResult.getLocations().get(latesLocationIndex).getLongitude();

                            try {

                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                    String address = addresses.get(0).getAddressLine(0);
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalcode = addresses.get(0).getPostalCode();
                                    // String knownname=addresses.get(0).getFeatureName();
                                    fulladress =
                                            address + "," + city + "," + state + "," + country + "," + postalcode;
                                    t.setText(fulladress);
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.SEND_SMS},
                                            REQOUEST_CODE_SEND_SMS);
                                } else {
                                    send_message();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //latLong.setText(String.format("Latitude: %s\nLongitude:%s",latitude,longitude));
                        }

                    }
                }, Looper.getMainLooper());

    }

    private void send_message() {
        String msg = "Address: " +fulladress;
        String smsNumber = "0000000000";

        int permissionCheck =
                ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumber, null, msg, null, null);
            Toast.makeText(Emergency.this, "Message sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Emergency.this, "Message is not sent", Toast.LENGTH_SHORT).show();
        }
    }

}
/* List<Address> addresses= g.getFromLocation( latitude,longitude,1);
                               if(addresses!=null)
                               {
                                   Address returnaddress=addresses.get(0);
                                   StringBuilder stringBuilder=new StringBuilder(" ");
                                   for(int i=0; i<=returnaddress.getMaxAddressLineIndex(); i++)
                                   {
                                       stringBuilder.append(returnaddress.getAddressLine(i)).append("\n");
                                   }
                                   fulladress=stringBuilder.toString();
                                 //  t.setText(fulladress);
                               }*/