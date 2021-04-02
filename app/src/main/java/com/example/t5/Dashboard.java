package com.example.t5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView drawermenu;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private CardView beaches, hotels, hospitals, temples, forts, markets, church, vehicles;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        upLoader();
        beaches = findViewById(R.id.beaches);
        hotels = findViewById(R.id.hotels);
        hospitals = findViewById(R.id.hospitals);
        temples = findViewById(R.id.temples);
        forts = findViewById(R.id.forts);
        markets = findViewById(R.id.markets);
        church = findViewById(R.id.church);
        vehicles = findViewById(R.id.vehicles);
        progressDialog =
                new ProgressDialog(Dashboard.this);

        beaches.setOnClickListener(this);
        hotels.setOnClickListener(this);
        hospitals.setOnClickListener(this);
        temples.setOnClickListener(this);
        forts.setOnClickListener(this);
        markets.setOnClickListener(this);
        church.setOnClickListener(this);
        vehicles.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawermenu = (NavigationView) findViewById(R.id.drawermenu);
        drawermenu.setNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        //bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(), Post.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0, 0);

                        finish();
                        return true;

                }
                return false;
            }
        });
    }

    private void upLoader() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //navigation_drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_home:

                intent = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
                break;
            case R.id.nav_emergency:
                intent = new Intent(Dashboard.this, Emergency.class);
                startActivity(intent);
                break;
            case R.id.nav_share:

                intent = new Intent(Intent.ACTION_SEND);
                final String appname = "Go Goa";
                String applink = "";
                try {
                    applink = "http://www.google.com" + appname;
                } catch (android.content.ActivityNotFoundException anfe) {
                    applink = "http://www.google.com" + appname;
                }

                intent.setType("text/link");
                String share = "Hey!" + "\n" + "" + applink;
                String share1 = "App Name";
                intent.putExtra(Intent.EXTRA_SUBJECT, share);
                intent.putExtra(Intent.EXTRA_TEXT, share1);
                startActivity(Intent.createChooser(intent, "Share Using"));

                break;
            case R.id.nav_contact:
                intent = new Intent(Dashboard.this, Contact.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                intent = new Intent(Dashboard.this, Main3Activity.class);
                startActivity(intent);
                finish();
                Toast.makeText(Dashboard.this, "Successfully Logout", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_rate:
                intent = new Intent(Dashboard.this, Rate.class);
                startActivity(intent);
                break;
            case R.id.nav_prof:
                intent = new Intent(Dashboard.this, ProfessionalAccount.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //card_layout
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {

            case R.id.beaches:
                intent = new Intent(Dashboard.this, Beach.class);
                startActivity(intent);
                break;

            case R.id.hotels:
                intent = new Intent(Dashboard.this, Hotel.class);
                startActivity(intent);
                break;

            case R.id.hospitals:
                intent = new Intent(Dashboard.this, Hospital.class);
                startActivity(intent);
                break;

            case R.id.temples:
                intent = new Intent(Dashboard.this, Temple.class);
                startActivity(intent);
                break;
            case R.id.forts:
                intent = new Intent(Dashboard.this, Fort.class);
                startActivity(intent);
                break;
            case R.id.markets:
                intent = new Intent(Dashboard.this, Market.class);
                startActivity(intent);
                break;
            case R.id.church:
                intent = new Intent(Dashboard.this, Church.class);
                startActivity(intent);
                break;
            case R.id.vehicles:
                intent = new Intent(Dashboard.this, Vehicle.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void viewpost(View view)
    {
        Intent intent = new Intent(Dashboard.this, ViewPostActivity.class);
        startActivity(intent);
    }

    public void newpost(View view)
    {
        Intent intent = new Intent(Dashboard.this, NewPostActivity.class);
        startActivity(intent);
    }
}
