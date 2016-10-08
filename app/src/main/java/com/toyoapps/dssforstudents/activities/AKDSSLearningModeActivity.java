package com.toyoapps.dssforstudents.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.toyoapps.dssforstudents.AHP.AKAHPPairwiseComparison;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.fragments.AKDSSNormalizeParametersFragment;
import com.toyoapps.dssforstudents.fragments.AKDSSResultsFragment;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.util.ArrayList;

import com.toyoapps.dssforstudents.fragments.AKDSSEditTextDialog;
import com.toyoapps.dssforstudents.fragments.AKDSSKeyStakeholdersFragment;
import com.toyoapps.dssforstudents.fragments.AKDSSLearningModeStepsFragment;
import com.toyoapps.dssforstudents.fragments.AKDSSNeedsIdentifyingFragment;
import com.toyoapps.dssforstudents.fragments.AKDSSOverviewFragment;
import com.toyoapps.dssforstudents.fragments.AKDSSStakeholdersFragment;

public class AKDSSLearningModeActivity extends AppCompatActivity implements AKDSSEditTextDialog.AKDSSEditTextDialogListener, AKAHPPairwiseComparison.AKAHPPairwiseComparisonDelegate {

    public static AKDSSLearningModeActivity lastCreatedActivity;
    private static int lastSelectedStep = 0;
    private IXmlNextStepClickable currentFragment;

    // Steps fragment
    private AKDSSLearningModeStepsFragment stepsFragment;

    // Algorithm parts com.toyoapps.dssforstudents.fragments
    private AKDSSOverviewFragment overviewFragment;
    private AKDSSStakeholdersFragment stakeholdersFragment;
    private AKDSSKeyStakeholdersFragment keyStakeholdersFragment;
    private AKDSSNeedsIdentifyingFragment needsIdentifyingFragment;
    private AKDSSNormalizeParametersFragment normalizeParametersFragment;
    private AKDSSResultsFragment resultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akdsslearningmode);

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
        this.presentFragment(fragment, false);
    }

    public void presentFragment (Fragment fragment, boolean backward) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!backward) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        if (fragment instanceof  IXmlNextStepClickable) {
            currentFragment = (IXmlNextStepClickable)fragment;
        }
    }

    public void showStepAtIndex(int index) {

        if (index == lastSelectedStep) {
            return;
        }

        boolean backward = index < lastSelectedStep;

        Fragment fragmentToPresent = null;

        switch (index) {

            case 0:
            {
                if (overviewFragment == null) {
                    overviewFragment = new AKDSSOverviewFragment();
                }

                fragmentToPresent = overviewFragment;
                break;
            }
            case 1:
            {
                if (stakeholdersFragment == null) {
                    stakeholdersFragment = new AKDSSStakeholdersFragment();
                }

                fragmentToPresent = stakeholdersFragment;
                break;
            }
            case 2:
            {
                if (keyStakeholdersFragment == null) {
                    keyStakeholdersFragment = new AKDSSKeyStakeholdersFragment();
                }

                fragmentToPresent = keyStakeholdersFragment;
                break;
            }
            case 3:
            {
                if (needsIdentifyingFragment == null) {
                    needsIdentifyingFragment = new AKDSSNeedsIdentifyingFragment();
                }

                fragmentToPresent = needsIdentifyingFragment;
                break;
            }
            case 4:
            {
                if (normalizeParametersFragment == null) {
                    normalizeParametersFragment = new AKDSSNormalizeParametersFragment();
                }

                fragmentToPresent = normalizeParametersFragment;
                break;
            }
            case 5:
            {
                if (resultsFragment == null) {
                    resultsFragment = new AKDSSResultsFragment();
                }

                fragmentToPresent = resultsFragment;
                break;
            }

            default:
                break;
        }

        if (!backward && !currentFragment.nextStepClicked(new View(this))) {
            return;
        }

        if (fragmentToPresent != null) {
            lastSelectedStep = index;
            this.stepsFragment.setSelectedStep(index);
            presentFragment(fragmentToPresent, backward);
            if (fragmentToPresent instanceof IXmlNextStepClickable) {
                currentFragment = (IXmlNextStepClickable) fragmentToPresent;
            }
        }

    }

    public void nextStepClicked(View v) {
        showStepAtIndex(lastSelectedStep + 1);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == overviewFragment) {
            lastSelectedStep = 0;
            super.onBackPressed();
            return;
        }

        showStepAtIndex(lastSelectedStep - 1);

        /*
        if (currentFragment == stakeholdersFragment) {
            this.presentFragment(overviewFragment, true);
            lastSelectedStep = 0;
            this.stepsFragment.setSelectedStep(0);
            return;
        }

        if (currentFragment == keyStakeholdersFragment) {
            this.presentFragment(stakeholdersFragment, true);
            lastSelectedStep = 1;
            this.stepsFragment.setSelectedStep(1);
            return;
        }

        if (currentFragment == needsIdentifyingFragment) {
            this.presentFragment(keyStakeholdersFragment, true);
            lastSelectedStep = 2;
            this.stepsFragment.setSelectedStep(2);
            return;
        }

        if (currentFragment == normalizeParametersFragment) {
            this.presentFragment(needsIdentifyingFragment, true);
            lastSelectedStep = 3;
            this.stepsFragment.setSelectedStep(3);
            return;
        }

        if (currentFragment == resultsFragment) {
            this.presentFragment(normalizeParametersFragment, true);
            lastSelectedStep = 4;
            this.stepsFragment.setSelectedStep(4);
        }
        */
    }

    public void addStakeholderClicked(View v) {
        FragmentManager fm = getSupportFragmentManager();
        AKDSSEditTextDialog editNameDialog = new AKDSSEditTextDialog();
        editNameDialog.dialogType = AKDSSEditTextDialog.DialogType.DIALOG_TYPE_STAKEHOLDER;
        editNameDialog.show(fm, "fragment_akdssedit_text_dialog");
    }

    // MARK: Typical stakeholders logic

    public void typicalStakeholdersListClicked(View v) {
        Intent launchLearningStartActivityIntent = new Intent(AKDSSLearningModeActivity.this, AKDSSStakeholdersListActivity.class);
        startActivity(launchLearningStartActivityIntent);
    }

    // MARK: Updaters

    public void updateStakeholdersList() {
        if (stakeholdersFragment == null) {
            return;
        }
        stakeholdersFragment.updateList();
    }

    public void updateKeyStakeholdersList() {
        if (keyStakeholdersFragment == null) {
            return;
        }
        keyStakeholdersFragment.updateList();
    }

    public void updateNeedsList() {
        if (needsIdentifyingFragment == null) {
            return;
        }
        needsIdentifyingFragment.updateList();
    }

    public void setKeyStakeholdersRangingFinished() {
        if (keyStakeholdersFragment == null) {
            return;
        }
        keyStakeholdersFragment.setRangingFinished(true);
    }

    // MARK: Key stakeholders logic

    public void showStakeholdersPicker(View v) {
        Intent launchLearningStartActivityIntent = new Intent(AKDSSLearningModeActivity.this, AKDSSStakeholdersListActivity.class);
        launchLearningStartActivityIntent.putExtra("real", "true");
        startActivity(launchLearningStartActivityIntent);
    }

    // MARK: AHP

    public void runPairwiseComparisonForKeyStakeholders(View v) {
        AKAHPPairwiseComparison.delegate = this;
        Intent launchPairwiseComparisonActivityIntent = new Intent(AKDSSLearningModeActivity.this, AKAHPPairwiseComparison.class);
        launchPairwiseComparisonActivityIntent.putExtra("alternatives", AKDSSSolver.getInstance().getKeyStakeholdersList());
        startActivity(launchPairwiseComparisonActivityIntent);
    }

    @Override
    public void onFinishEditDialog(AKDSSEditTextDialog dialog, String inputText) {
        if (dialog.dialogType == AKDSSEditTextDialog.DialogType.DIALOG_TYPE_STAKEHOLDER) {
            AKDSSSolver.getInstance().addStakeholder(new AKDSSStakeholder(inputText));
            this.updateStakeholdersList();
        }
        if (dialog.dialogType == AKDSSEditTextDialog.DialogType.DIALOG_TYPE_STAKEHOLDER_NEEDS) {
            AKDSSKeyStakeholder stakeholder = (AKDSSKeyStakeholder) dialog.connectedObject;
            stakeholder.addNeed(inputText);
            this.updateNeedsList();
        }
    }

    @Override
    public void AKAHPPairwiseComparisonDidFinished(ArrayList<String> alternatives, ArrayList<Double> results) {
        for (int i = 0; i < AKDSSSolver.getInstance().getKeyStakeholders().size(); i++) {
            AKDSSSolver.getInstance().getKeyStakeholders().get(i).setWeight(results.get(i));
        }
        this.updateKeyStakeholdersList();
        this.setKeyStakeholdersRangingFinished();
    }

    // MARK: Stakeholder needs logic

    public void addNeedForKeyStakeholder(AKDSSKeyStakeholder stakeholder) {
        FragmentManager fm = getSupportFragmentManager();
        AKDSSEditTextDialog editNameDialog = new AKDSSEditTextDialog();
        editNameDialog.dialogType = AKDSSEditTextDialog.DialogType.DIALOG_TYPE_STAKEHOLDER_NEEDS;
        editNameDialog.connectedObject = stakeholder;
        editNameDialog.show(fm, "fragment_akdssedit_text_dialog");
    }

    // MARK: Results

    public void showCalculationDetails(View v) {
        Toast.makeText(AKDSSLearningModeActivity.this, "В разработке", Toast.LENGTH_SHORT).show();
    }

}
