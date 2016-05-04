package com.toyoapps.dssforstudents;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import layout.AKDSSEditTextDialog;
import layout.AKDSSLearningModeStepsFragment;
import layout.AKDSSOverviewFragment;
import layout.AKDSSStakeholdersFragment;

public class AKDSSLearningModeActivity extends AppCompatActivity implements AKDSSEditTextDialog.AKDSSEditTextDialogListener {

    public static AKDSSLearningModeActivity lastCreatedActivity;

    // Steps fragment
    private AKDSSLearningModeStepsFragment stepsFragment;

    // Algorithm parts fragments
    private AKDSSOverviewFragment overviewFragment;
    private AKDSSStakeholdersFragment stakeholdersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akdsslearningmode);

        // Intansiate solver with learning mode
        AKDSSSolver.getInstance().setLearningMode(true);

        this.stepsFragment = (AKDSSLearningModeStepsFragment) this.getSupportFragmentManager().findFragmentById(R.id.stepsFragment);

        if (this.stepsFragment != null) {
            this.stepsFragment.setSelectedStep(0);
        }

        this.overviewFragment = new AKDSSOverviewFragment();
        this.presentFragment(this.overviewFragment);

        lastCreatedActivity = this;
    }

    // MARK: Navigation actions

    public void presentFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void nextStepClicked(View v) {
        switch (v.getId()) {
            case R.id.overviewNextButton:

                if (!overviewFragment.nextStepClicked(v)) {
                    return;
                }

                AKDSSStakeholdersFragment fragment;

                if (this.stakeholdersFragment != null) {
                    fragment = this.stakeholdersFragment;
                }
                else {
                    this.stakeholdersFragment = new AKDSSStakeholdersFragment();
                    fragment = this.stakeholdersFragment;
                }

                this.presentFragment(fragment);
                this.stepsFragment.setSelectedStep(1);
                break;

            default:
                break;
        }
    }

    public void addStakeholderClicked(View v) {
        FragmentManager fm = getSupportFragmentManager();
        AKDSSEditTextDialog editNameDialog = new AKDSSEditTextDialog();
        editNameDialog.show(fm, "fragment_akdssedit_text_dialog");
    }

    public void removeStakeholderClicked(View v) {

    }

    // MARK: Typical stakeholders logic

    public void typicalStakeholdersListClicked(View v) {
        Intent launchLearningStartActivityIntent = new Intent(AKDSSLearningModeActivity.this, AKDSSStakeholdersListActivity.class);
        startActivity(launchLearningStartActivityIntent);
    }

    public void updateStakeholdersList() {
        if (stakeholdersFragment == null) {
            return;
        }
        stakeholdersFragment.updateList();
    }

    // MARK: Dialog listener

    @Override
    public void onFinishEditDialog(String inputText) {
        AKDSSSolver.getInstance().addStakeholder(new AKDSSStakeholder(inputText));
        this.updateStakeholdersList();
    }
}
