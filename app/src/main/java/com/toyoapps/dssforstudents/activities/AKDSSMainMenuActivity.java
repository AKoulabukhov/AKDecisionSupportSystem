package com.toyoapps.dssforstudents.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;

public class AKDSSMainMenuActivity extends AppCompatActivity {

    private Button learningModeButton;
    private Button baseModeButton;
    private Button demoModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akdssmain_menu);

        demoModeButton = (Button) findViewById(R.id.demoModeButton);
        learningModeButton = (Button) findViewById(R.id.learningModeButton);
        baseModeButton = (Button) findViewById(R.id.baseModeButton);

        demoModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show start activity of learning mode
                AKDSSSolver.getInstance().setDemoMode(true);
                Intent launchLearningStartActivityIntent = new Intent(AKDSSMainMenuActivity.this, AKDSSLearningModeActivity.class);
                startActivity(launchLearningStartActivityIntent);
            }
        });

        learningModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show start activity of learning mode
                AKDSSSolver.getInstance().setLearningMode(true);
                Intent launchLearningStartActivityIntent = new Intent(AKDSSMainMenuActivity.this, AKDSSLearningModeActivity.class);
                startActivity(launchLearningStartActivityIntent);
            }
        });

        baseModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show base activity

            }
        });
    }
}
