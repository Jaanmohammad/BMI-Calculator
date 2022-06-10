package com.jksurajpuriya.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    RelativeLayout contentLayout;
    TextView bmiDisplay, genderDisplay, bmiCategory;
    Button recalculateBmi;

    String height1, weight1, bmi1;
    float height2, weight2, bmi2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        init();
        dataReceivedAndConvertToFloat();
        validation();
        bmiDisplay.setText(bmi1);
        buttonClick();



    }

    private void buttonClick() {
        recalculateBmi.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void validation() {
        if (bmi2<16){
            bmiCategory.setText("Severe Thinness");
            contentLayout.setBackgroundColor(Color.RED);
            imageView.setImageResource(R.drawable.denger);
        }
        else if (bmi2<16.9 && bmi2>16){
            bmiCategory.setText("Moderate Thinness");
            contentLayout.setBackgroundColor(Color.YELLOW);
            imageView.setImageResource(R.drawable.warning);
        }
        else if (bmi2<18.4 && bmi2>17){
            bmiCategory.setText("Mild Thinness");
            contentLayout.setBackgroundColor(Color.YELLOW);
            imageView.setImageResource(R.drawable.warning);
        }
        else if(bmi2<25 && bmi2>18.4){
            bmiCategory.setText("Normal");
            contentLayout.setBackgroundColor(Color.GREEN);
            imageView.setImageResource(R.drawable.normal);
        }
        else if (bmi2<29.4 && bmi2>25){
            bmiCategory.setText("Overweight");
            contentLayout.setBackgroundColor(Color.YELLOW);
            imageView.setImageResource(R.drawable.warning);
        }
        else{
            bmiCategory.setText("Obese Class I");
            contentLayout.setBackgroundColor(Color.YELLOW);
            imageView.setImageResource(R.drawable.warning);
        }
    }

    private void dataReceivedAndConvertToFloat() {
        Intent intent = getIntent();
        height1=intent.getStringExtra("height");
        weight1=intent.getStringExtra("weight");
        genderDisplay.setText(intent.getStringExtra("gender"));

        height2=Float.parseFloat(height1);
        weight2=Float.parseFloat(weight1);

        height2=height2/100;

        bmi2=weight2/(height2*height2);

        bmi1=Float.toString(bmi2);



    }

    private void init(){
        imageView=findViewById(R.id.imageView);
        contentLayout=findViewById(R.id.contentLayout);
        bmiDisplay=findViewById(R.id.bmiDisplay);
        genderDisplay=findViewById(R.id.genderDisplay);
        bmiCategory=findViewById(R.id.bmiCategory);
        recalculateBmi=findViewById(R.id.recalculateBmi);
    }

}