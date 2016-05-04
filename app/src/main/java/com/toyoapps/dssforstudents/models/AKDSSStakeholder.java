package com.toyoapps.dssforstudents.models;

import android.support.annotation.NonNull;

import com.toyoapps.dssforstudents.logic.AKDSSSolver;

import java.util.ArrayList;

/**
 * Created by toyo on 03/05/16.
 */

public class AKDSSStakeholder {

    // MARK: User input

    public String title;
    public double influence = 0;
    public double dependence = 0;

    // MARK: Calculated fields

    public ArrayList<AKDSSStakeholder> stakeholders;

    public double importance = 0;
    public double weight = 0;

    public void setImportance(double importance) {}

    public void setInfluence(double influence) {
        this.influence = influence;
        this.updateImportance();
    }

    public void setDependence(double dependence) {
        this.dependence = dependence;
        this.updateImportance();
    }

    private void updateImportance() {
        importance = Math.sqrt(Math.pow(influence, 2) + Math.pow(dependence, 2));
        AKDSSSolver.getInstance().updateStakeholderWeights();
    }

    // Public constructor

    public AKDSSStakeholder (@NonNull String title) {
        this.title = title;
    }
}
