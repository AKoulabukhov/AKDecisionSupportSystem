package com.toyoapps.dssforstudents.models;

import android.support.annotation.NonNull;

import com.toyoapps.dssforstudents.logic.AKDSSSolver;

import java.util.ArrayList;

/**
 * Created by toyo on 08/05/16.
 */

public class AKDSSKeyStakeholder {

    private String title;
    private double weight;
    private ArrayList<AKDSSNeed> needs;

    private AKDSSKeyStakeholder() {};
    public AKDSSKeyStakeholder (@NonNull String title) {
        this.title = title;
        needs = new ArrayList<AKDSSNeed> ();
    }

    public String getTitle() {
        return this.title;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        if (weight < 0) {
            weight = 0;
        }
        if (weight > 1) {
            weight = 1;
        }

        this.weight = weight;
    }

    public ArrayList<AKDSSNeed> getNeeds() {
        return needs;
    }

    public AKDSSNeed addNeed(String name) {
        AKDSSNeed need = new AKDSSNeed(name, this);
        this.needs.add(need);
        AKDSSSolver.getInstance().needKeyParametersUpdate = true;
        return need;
    }

    public void removeNeed(AKDSSNeed need) {
        AKDSSSolver.getInstance().needKeyParametersUpdate = true;
        this.needs.remove(need);
    }

}
