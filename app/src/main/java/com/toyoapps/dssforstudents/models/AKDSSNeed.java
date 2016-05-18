package com.toyoapps.dssforstudents.models;

/**
 * Created by toyo on 15/05/16.
 */
public class AKDSSNeed {

    private String name;
    private String keyParameter;
    private String keyParameterUnit;

    private double importance;
    private double weight;

    private AKDSSKeyStakeholder stakeholder;

    private AKDSSNeed() {}
    public AKDSSNeed(String name, AKDSSKeyStakeholder stakeholder) {
        this.name = name;
        this.stakeholder = stakeholder;
    }

    public void setKeyParameter(String keyParameter) {
        this.keyParameter = keyParameter;
    }

    public String getKeyParameter() {
        return keyParameter;
    }


    public String getName() {
        return name;
    }

    public String getKeyParameterUnit() {
        return keyParameterUnit;
    }

    public void setKeyParameterUnit(String keyParameterUnit) {
        this.keyParameterUnit = keyParameterUnit;
    }

    public double getImportance() {
        return importance;
    }

    public void setImportance(double importance) {
        this.importance = importance;

        double importanceSum = 0;
        for (AKDSSNeed need: this.stakeholder.getNeeds()) {
            importanceSum+= need.getImportance();
        }

        if (importanceSum == 0) {
            return;
        }

        for (AKDSSNeed need: this.stakeholder.getNeeds()) {
            need.setWeight(need.getImportance()/importanceSum);
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AKDSSKeyStakeholder getStakeholder() { return this.stakeholder; }

}
