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
    private double keyParameterValue = 0.0;

    private AKDSSNeedNormalizeParameters normalizeParameters;

    public class AKDSSNeedNormalizeParameters {
        public Double worstValue = 0.0;
        public Double normalValue = 0.0;
        public Double bestPossibleValue = 0.0;
    }

    private AKDSSKeyStakeholder stakeholder;

    private AKDSSNeed() {}
    public AKDSSNeed(String name, AKDSSKeyStakeholder stakeholder) {
        this.name = name;
        this.stakeholder = stakeholder;
        this.normalizeParameters = new AKDSSNeedNormalizeParameters();
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

    public AKDSSNeedNormalizeParameters getNormalizeParameters() {
        return this.normalizeParameters;
    }

    public AKDSSKeyStakeholder getStakeholder() { return this.stakeholder; }

    public double getKeyParameterValue() {
        return keyParameterValue;
    }

    public void setKeyParameterValue(double value) {
        keyParameterValue = value;
    }

    public double getNormalizedKeyParameterValue() {
        if (keyParameterValue <= normalizeParameters.worstValue) {
            return 0;
        }

        if (keyParameterValue > normalizeParameters.worstValue && keyParameterValue <= normalizeParameters.normalValue) {
            return (keyParameterValue - normalizeParameters.worstValue) / (normalizeParameters.normalValue - normalizeParameters.worstValue);
        }

        if (keyParameterValue > normalizeParameters.normalValue && keyParameterValue <= normalizeParameters.bestPossibleValue) {
            return 1 + (keyParameterValue - normalizeParameters.normalValue) / (normalizeParameters.bestPossibleValue - normalizeParameters.normalValue);
        }

        if (keyParameterValue > normalizeParameters.bestPossibleValue) {
            return 2;
        }

        return 0;
    }
}
