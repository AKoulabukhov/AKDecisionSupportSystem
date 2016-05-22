package com.toyoapps.dssforstudents.models;

import java.util.ArrayList;

/**
 * Created by toyo on 22/05/16.
 */
public class AKDSSKeyParameter {

    private String name;
    private double value = 0.0;
    private String unit;
    private ArrayList<AKDSSNeed> associatedNeeds = new ArrayList<AKDSSNeed>();


    public AKDSSKeyParameter(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public String getUnit() {
        return this.unit;
    }

    public void addAssociatedNeed(AKDSSNeed need) {
        associatedNeeds.add(need);
    }

    public void removeAssociatedNeed(AKDSSNeed need) {
        associatedNeeds.remove(need);
    }

    public ArrayList<AKDSSNeed> getAssociatedNeeds() {
        return this.associatedNeeds;
    }

    public void setValue(double value) {
        this.value = value;

        for (AKDSSNeed need: this.associatedNeeds) {
            need.setKeyParameterValue(value);
        }
    }

    public double setPercentValue(double percentValue) {

        double minimalValue = 0.0;
        double maximumValue = 0.0;

        for (AKDSSNeed need: associatedNeeds) {
            if (minimalValue == 0.0 || minimalValue > need.getNormalizeParameters().worstValue) {
                minimalValue = need.getNormalizeParameters().worstValue;
            }

            if (maximumValue == 0.0 || maximumValue < need.getNormalizeParameters().bestPossibleValue) {
                maximumValue = need.getNormalizeParameters().bestPossibleValue;
            }
        }

        double realValue = minimalValue + (maximumValue - minimalValue) * percentValue / 100;
        setValue(realValue);
        return realValue;
    }

    public double getValue() {
        return this.value;
    }
}
