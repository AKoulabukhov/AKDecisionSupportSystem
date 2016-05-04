package com.toyoapps.dssforstudents.logic;

import android.support.annotation.NonNull;

import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.util.ArrayList;

/**
 * Created by toyo on 02/05/16.
 */

public class AKDSSSolver {

    private boolean learningMode = false;

    public void setLearningMode(boolean learningMode) {
        this.learningMode = learningMode;

        if (stakeholders.size() == 0) {
            AKDSSStakeholder stakeholder = new AKDSSStakeholder("Абоненты");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.5);
            stakeholder.setDependence(0.9);

            stakeholder = new AKDSSStakeholder("Акционеры");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.7);
            stakeholder.setDependence(0.8);

            stakeholder = new AKDSSStakeholder("Собственники");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.3);
            stakeholder.setDependence(0.7);
        }
    }

    // Data sources
    private ArrayList<AKDSSStakeholder> stakeholders;

    public void addStakeholder (AKDSSStakeholder stakeholder) {
        stakeholder.stakeholders = this.stakeholders;
        this.stakeholders.add(stakeholder);
        this.updateStakeholderWeights();
    }

    public void removeStakeholder (AKDSSStakeholder stakeholder) {
        this.stakeholders.remove(stakeholder);
        this.updateStakeholderWeights();
    }

    public ArrayList<AKDSSStakeholder> getStakeholders() {
        return this.stakeholders;
    }

    public void updateStakeholderWeights() {

        double importanceSum = 0;
        for (int i = 0; i < this.stakeholders.size(); i++) {
            importanceSum+= stakeholders.get(i).importance;
        }

        if (importanceSum == 0) {
            return;
        }

        for (int i = 0; i < this.stakeholders.size(); i++) {
            stakeholders.get(i).weight = stakeholders.get(i).importance / importanceSum;
        }
    }

    // Singletone

    private static AKDSSSolver ourInstance = new AKDSSSolver();

    public static AKDSSSolver getInstance() {
        return ourInstance;
    }

    private AKDSSSolver() {
        stakeholders = new ArrayList<AKDSSStakeholder>();
    }
}
