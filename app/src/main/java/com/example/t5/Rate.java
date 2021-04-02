package com.example.t5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Rate extends AppCompatActivity {

    TextView rateCount, showRating;
    EditText review;
    Button submit;
    RatingBar ratingBar;
    float rateValue;
    String temp,getRating,getReview;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        storageReference = FirebaseStorage.getInstance().getReference("Ratedb");
        databaseReference = FirebaseDatabase.getInstance().getReference("Ratedb");

        rateCount = findViewById(R.id.rateCount);
        ratingBar = findViewById(R.id.ratingBar);
        review = findViewById(R.id.review);
        submit = findViewById(R.id.submitbtn);
        showRating=findViewById(R.id.showRating);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                rateValue = ratingBar.getRating();
                if (rateValue <= 1 && rateValue > 0)
                    rateCount.setText("Bad  " + rateValue + "/5");
                else if (rateValue <= 2 && rateValue > 1)
                    rateCount.setText("Ok  " + rateValue +"/5");
                else if (rateValue <= 3 && rateValue > 2)
                    rateCount.setText("Good " + rateValue +"/5");
                else if (rateValue <= 4 && rateValue > 3)
                    rateCount.setText("Very Good  " + rateValue +"/5");
                else if (rateValue <= 5 && rateValue > 4)
                    rateCount.setText("Best  " + rateValue +"/5");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp=rateCount.getText().toString();
                getReview=review.getText().toString();
                showRating.setText("Your Rating: \n" +temp+ "\n" +review.getText());

                Ratedb db=new Ratedb();

                db.setRatecount(temp);
                db.setReview(getReview);
                databaseReference.push().setValue(db);

                review.setText("");
                ratingBar.setRating(0);
                rateCount.setText("");
                Toast.makeText(Rate.this,"Thank You ",Toast.LENGTH_LONG).show();
            }
        });
    }
}
