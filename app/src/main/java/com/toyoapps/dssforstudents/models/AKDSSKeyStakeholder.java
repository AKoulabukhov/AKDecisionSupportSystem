package com.toyoapps.dssforstudents.models;

import android.support.annotation.NonNull;

/**
 * Created by toyo on 08/05/16.
 */

public class AKDSSKeyStakeholder {

    private String title;
    private double weight;

    public AKDSSKeyStakeholder (@NonNull String title) {
        this.title = title;
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

}
