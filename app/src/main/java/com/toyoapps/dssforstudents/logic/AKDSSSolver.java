package com.toyoapps.dssforstudents.logic;

import android.graphics.Color;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.toyoapps.dssforstudents.models.AKDSSKeyParameter;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by toyo on 02/05/16.
 */

public class AKDSSSolver {

    private boolean learningMode = false;
    private boolean demoMode = false;
    public boolean needKeyParametersUpdate = true;

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;

        if (stakeholders.size() == 0 && demoMode) {
            AKDSSStakeholder stakeholder = new AKDSSStakeholder("Абоненты");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.98);
            stakeholder.setDependence(0.98);

            stakeholder = new AKDSSStakeholder("Акционеры");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.5);
            stakeholder.setDependence(0.56);

            stakeholder = new AKDSSStakeholder("Собственники");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.06);
            stakeholder.setDependence(0.04);

            stakeholder = new AKDSSStakeholder("Корп. служащие");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.12);
            stakeholder.setDependence(0.14);

            stakeholder = new AKDSSStakeholder("Получ. франшизы");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.14);
            stakeholder.setDependence(0.12);

            stakeholder = new AKDSSStakeholder("Конкуренты");
            this.addStakeholder(stakeholder);
            stakeholder.setInfluence(0.01);
            stakeholder.setDependence(0.12);
        }

        if (keyStakeholders.size() == 0 && demoMode) {

            AKDSSKeyStakeholder stakeholder = new AKDSSKeyStakeholder("Акционеры");
            this.addKeyStakeholder(stakeholder);
            stakeholder.setWeight(0.2);

            AKDSSNeed need = stakeholder.addNeed("Быстрый возврат инвестиций");
            need.setKeyParameter("Доходность");
            need.setKeyParameterUnit("%");
            need.setImportance(9.8);
            need.getNormalizeParameters().worstValue = 10.0;
            need.getNormalizeParameters().normalValue = 15.0;
            need.getNormalizeParameters().bestPossibleValue = 25.0;

            stakeholder = new AKDSSKeyStakeholder("Абоненты");
            this.addKeyStakeholder(stakeholder);
            stakeholder.setWeight(0.4);

            need = stakeholder.addNeed("Широкое покрытие");
            need.setKeyParameter("Покрытие");
            need.setKeyParameterUnit("%");
            need.setImportance(9.8);
            need.getNormalizeParameters().worstValue = 30.0;
            need.getNormalizeParameters().normalValue = 40.0;
            need.getNormalizeParameters().bestPossibleValue = 70.0;


            need = stakeholder.addNeed("Гибкость тарифов");
            need.setKeyParameter("Число тарифов и опций");
            need.setKeyParameterUnit("шт");
            need.setImportance(6.9);
            need.getNormalizeParameters().worstValue = 15.0;
            need.getNormalizeParameters().normalValue = 35.0;
            need.getNormalizeParameters().bestPossibleValue = 150.0;


            stakeholder = new AKDSSKeyStakeholder("Получатели франшизы");
            this.addKeyStakeholder(stakeholder);
            stakeholder.setWeight(0.1);

            need = stakeholder.addNeed("Большая доля рынка");
            need.setKeyParameter("Доля рынка");
            need.setKeyParameterUnit("%");
            need.setImportance(6.5);
            need.getNormalizeParameters().worstValue = 5.0;
            need.getNormalizeParameters().normalValue = 20.0;
            need.getNormalizeParameters().bestPossibleValue = 75.0;


            need = stakeholder.addNeed("Высокая прибыль");
            need.setKeyParameter("Прибыльность");
            need.setKeyParameterUnit("шт");
            need.setImportance(9.8);
            need.getNormalizeParameters().worstValue = 5.0;
            need.getNormalizeParameters().normalValue = 10.0;
            need.getNormalizeParameters().bestPossibleValue = 20.0;

        }

    }

    public void setLearningMode(boolean learningMode) {
        this.learningMode = learningMode;

        if (stakeholders.size() == 0 && learningMode) {
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

    // MARK: Data sources

    // MARK: Stakeholders

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
    public String[] getStakeholdersList() {
        String[] stakeholdersList = new String[this.stakeholders.size()];
        for (int i = 0; i < this.stakeholders.size(); i++) {
            stakeholdersList[i] = stakeholders.get(i).title;
        }
        return stakeholdersList;
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

    public ArrayList<SimpleXYSeries> stakeholderMapDataSeries() {

        ArrayList<SimpleXYSeries> series = new ArrayList<SimpleXYSeries>();

        for (AKDSSStakeholder stakeholder: stakeholders) {
            SimpleXYSeries serie = new SimpleXYSeries(stakeholder.title);
            serie.addLast(stakeholder.getDependence(), stakeholder.getInfluence());
            series.add(serie);
        }

        return series;
    }

    public ArrayList<LineAndPointFormatter> stakeholdersMapDataSeriesFormatters() {

        ArrayList<LineAndPointFormatter> formatters = new ArrayList<LineAndPointFormatter>();

        Random rnd = new Random();

        for (AKDSSStakeholder stakeholder: stakeholders) {
            LineAndPointFormatter formatter = new LineAndPointFormatter();
            formatter.getLinePaint().setColor(Color.TRANSPARENT);
            formatter.getVertexPaint().setStrokeWidth(10);

            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            formatter.getVertexPaint().setColor(color);

            formatter.getFillPaint().setColor(Color.TRANSPARENT);
            formatters.add(formatter);
        }

        return formatters;
    }

    // MARK: Key stakeholders

    private ArrayList<AKDSSKeyStakeholder> keyStakeholders;

    public void addKeyStakeholder (AKDSSKeyStakeholder keyStakeholder) {
        this.keyStakeholders.add(keyStakeholder);
    }

    public void removeKeyStakeholder (AKDSSKeyStakeholder keyStakeholder) {
        this.keyStakeholders.remove(keyStakeholder);
    }

    public ArrayList<AKDSSKeyStakeholder> getKeyStakeholders() { return this.keyStakeholders; }
    public ArrayList<String> getKeyStakeholdersList() {
        ArrayList<String> keyStakeholdersList = new ArrayList<String>();

        for (AKDSSKeyStakeholder keyStakeholder: keyStakeholders) {
            keyStakeholdersList.add(keyStakeholder.getTitle());
        }

        return keyStakeholdersList;
    }

    // MARK: Parameters & needs

    private ArrayList<AKDSSKeyParameter> keyParameters;

    public ArrayList<AKDSSKeyParameter> getKeyParameters() {
        if (keyParameters == null) {
            keyParameters = new ArrayList<>();
        }

        if (needKeyParametersUpdate) {

            keyParameters.clear();

            for (AKDSSKeyStakeholder stakeholder: keyStakeholders) {
                for (AKDSSNeed need: stakeholder.getNeeds()) {

                    boolean needParameterAlreadyExist = false;

                    for (AKDSSKeyParameter parameter: keyParameters) {

                        if (parameter.getName().equalsIgnoreCase(need.getKeyParameter()) &&
                                parameter.getUnit().equalsIgnoreCase(need.getKeyParameterUnit())) {

                            parameter.addAssociatedNeed(need);
                            needParameterAlreadyExist = true;
                            break;
                        }
                    }

                    if (!needParameterAlreadyExist) {
                        AKDSSKeyParameter parameter = new AKDSSKeyParameter(need.getKeyParameter(), need.getKeyParameterUnit());
                        parameter.addAssociatedNeed(need);
                        keyParameters.add(parameter);
                    }

                }
            }

            needKeyParametersUpdate = false;
        }

        return keyParameters;
    }

    // MARK: Singleton

    private static AKDSSSolver ourInstance = new AKDSSSolver();

    public static AKDSSSolver getInstance() {
        return ourInstance;
    }

    private AKDSSSolver() {
        stakeholders = new ArrayList<AKDSSStakeholder>();
        keyStakeholders = new ArrayList<AKDSSKeyStakeholder>();
    }
}
