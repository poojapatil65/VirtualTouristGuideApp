package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;

public class ProfessionalAccount extends AppCompatActivity {

    RadioGroup g1;
    RadioButton radioButton;

    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_account);
        g1=findViewById(R.id.G1);

        back=findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId=g1.getCheckedRadioButtonId();
              //  radioButton=findViewById(radioId);

                if(radioId==R.id.hotel)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,HotelAdmin.class);
                    startActivity(intent);
                }
                if(radioId==R.id.hospital)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,HospitalAdmin.class);
                    startActivity(intent);
                }
                if(radioId==R.id.vehicle)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,VehicleAdmin.class);
                    startActivity(intent);
                }
            }

        });
    }
}

/*   g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.hotel)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,HotelAdmin.class);
                    startActivity(intent);
                }
                if(i==R.id.hospital)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,HospitalAdmin.class);
                    startActivity(intent);
                }
                if(i==R.id.vehicle)
                {
                    Intent intent=new Intent(ProfessionalAccount.this,VehicleAdmin.class);
                    startActivity(intent);
                }
            }
        });*/