package com.jksurajpuriya.bmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import com.google.android.material.snackbar.Snackbar;
import com.jksurajpuriya.bmi.databinding.ActivityMainBinding;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    ActivityMainBinding binding;

    // for SeekBar
    int currentProgress;
    String minimumProgress = "170";

    // for Age Increment and Decrement
    int age1 = 23;
    String age2 = "23";

    // for Weight Increment and Decrement
    int weight1 = 52;
    String weight2 = "52";

    // for Male or Female
    String typeOfUser = "0";

    InAppUpdateManager inAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        seekBar();
        age();
        weight();
        maleAndFemaleSelect();
        calculateBmi();


    }

    private void calculateBmi() {
        binding.calculateBmi.setOnClickListener(v -> {
            if (typeOfUser.equals("0")) {
                Toast.makeText(this, "Select Your Gender First", Toast.LENGTH_SHORT).show();
            } else if (minimumProgress.equals("0")) {
                Toast.makeText(this, "Select Your Height First", Toast.LENGTH_SHORT).show();
            } else if (age1 == 0 || age1 < 0) {
                Toast.makeText(this, "Age is Incorrect", Toast.LENGTH_SHORT).show();
            } else if (weight1 == 0 || weight1 < 0) {
                Toast.makeText(this, "Weight is Incorrect", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("gender", typeOfUser);
                intent.putExtra("height", minimumProgress);
                intent.putExtra("weight", weight2);
                intent.putExtra("age", age2);

                startActivity(intent);
            }
        });
    }

    private void maleAndFemaleSelect() {
        binding.male.setOnClickListener(v -> {
            binding.male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_focus));
            binding.female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_not_focus));
            binding.textMale.setTextColor(BLACK);
            binding.textFemale.setTextColor(WHITE);
            typeOfUser = "Male";

        });
        binding.female.setOnClickListener(v -> {
            binding.female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_focus));
            binding.male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.male_female_not_focus));
            typeOfUser = "Female";
            binding.textFemale.setTextColor(BLACK);
            binding.textMale.setTextColor(WHITE);
        });
    }

    private void weight() {
        binding.incrementWeight.setOnClickListener(v -> {
            weight1 = weight1 + 1;
            weight2 = String.valueOf(weight1);
            binding.currentWeight.setText(weight2);
        });

        binding.decrementWeight.setOnClickListener(v -> {
            weight1 = weight1 - 1;
            weight2 = String.valueOf(weight1);
            binding.currentWeight.setText(weight2);
        });

    }

    private void age() {
        binding.incrementAge.setOnClickListener(v -> {
            age1 = age1 + 1;
            age2 = String.valueOf(age1);
            binding.currentAge.setText(age2);
        });

        binding.decrementAge.setOnClickListener(v -> {
            age1 = age1 - 1;
            age2 = String.valueOf(age1);
            binding.currentAge.setText(age2);
        });
    }

    private void seekBar() {

        binding.seekBar.setMax(250);
        binding.seekBar.setProgress(170);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
                minimumProgress = String.valueOf(currentProgress);
                binding.currentHeight.setText(minimumProgress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void ImmediateUpdate() {
        inAppUpdateManager = InAppUpdateManager.Builder(this, 101)
                .resumeUpdates(true)
                .mode(Constants.UpdateMode.IMMEDIATE)
                .snackBarAction("An update has just been download.")
                .snackBarAction("RESTART")
                .handler(this);
        inAppUpdateManager.checkForAppUpdate();
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {


    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        if (status.isDownloaded()) {
            View view = getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view, "An update has just been download.",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    inAppUpdateManager.completeUpdate();
                }
            });
            snackbar.show();


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmediateUpdate();

    }
}
