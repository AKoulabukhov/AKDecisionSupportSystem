package com.toyoapps.dssforstudents.models;

import android.support.annotation.NonNull;

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
        return need;
    }

    public void removeNeed(AKDSSNeed need) {
        this.needs.remove(need);
    }

}
