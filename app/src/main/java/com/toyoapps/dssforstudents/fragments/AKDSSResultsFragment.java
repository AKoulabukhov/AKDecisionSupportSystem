package com.toyoapps.dssforstudents.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyParameter;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSResultsFragment extends Fragment {


    public AKDSSResultsFragment() { }

    private LayoutInflater inflater;

    private TableLayout slidersTableLayout;
    private TableLayout resultsTableLayout;

    private RadarChart radarChart;
    private TextView resultScoreTextView;
    private TextView resultDescriptionTextView;

    private ArrayList<TextView> localValueTextViews = new ArrayList<>();
    private ArrayList<TextView> stakeholderWeightTextViews = new ArrayList<>();
    private ArrayList<TextView> globalValueTextViews = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_akdss_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view == null) {
            return;
        }

        slidersTableLayout = (TableLayout) view.findViewById(R.id.resultsSlidersTableLayout);
        resultsTableLayout = (TableLayout) view.findViewById(R.id.resultsTableLayout);
        radarChart = (RadarChart) view.findViewById(R.id.resultRadarChart);
        resultScoreTextView = (TextView) view.findViewById(R.id.resultScoreTextView);
        resultDescriptionTextView = (TextView) view.findViewById(R.id.resultDescriptionTextView);

        ArrayList<AKDSSKeyParameter> keyParameters = AKDSSSolver.getInstance().getKeyParameters();

        for (final AKDSSKeyParameter keyParameter: keyParameters) {

            TableRow tableRow = (TableRow) inflater.inflate(R.layout.table_row_key_parameters, null);
            TextView nameTextView = (TextView) tableRow.findViewById(R.id.textLabel);
            String nameText = keyParameter.getName() + " (" + keyParameter.getUnit() + ")";
            nameTextView.setText(nameText);
            final TextView valueTextView = (TextView) tableRow.findViewById(R.id.valueLabel);
            valueTextView.setText(stringFromDouble(keyParameter.setPercentValue(50)));

            SeekBar seekBar = (SeekBar) tableRow.findViewById(R.id.bar);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) { }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    double realValue = keyParameter.setPercentValue(seekBar.getProgress());
                    valueTextView.setText(stringFromDouble(realValue));
                    updateView();
                }
            });

            slidersTableLayout.addView(tableRow);
        }

        localValueTextViews.clear();
        stakeholderWeightTextViews.clear();
        globalValueTextViews.clear();

        for (AKDSSKeyStakeholder stakeholder: AKDSSSolver.getInstance().getKeyStakeholders()) {

            TableRow tableRow = (TableRow) inflater.inflate(R.layout.table_row_results_stakeholder, null);
            ((TextView) tableRow.findViewById(R.id.stakeholderTitleTextView)).setText(stakeholder.getTitle());
            localValueTextViews.add((TextView) tableRow.findViewById(R.id.localValueTextView));
            stakeholderWeightTextViews.add((TextView) tableRow.findViewById(R.id.stakeholderWeightTextView));
            globalValueTextViews.add((TextView) tableRow.findViewById(R.id.globalValueTextView));

            int color;
            if (AKDSSSolver.getInstance().getKeyStakeholders().indexOf(stakeholder) % 2 == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    color = getResources().getColor(R.color.off_white, getContext().getTheme());
                }
                else {
                    color = getResources().getColor(R.color.off_white);
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    color = getResources().getColor(R.color.colorLightGrey, getContext().getTheme());
                }
                else {
                    color = getResources().getColor(R.color.colorLightGrey);
                }
            }

            tableRow.setBackgroundColor(color);

            resultsTableLayout.addView(tableRow);
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        radarChart.getLayoutParams().height = (int)(Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels) / (displayMetrics.density * 2));

        updateView();
    }

    private void updateView() {

        Pair<AKDSSKeyStakeholder, AKDSSNeed> worstSatisfated = null;
        double minimalSatisfaction = 0.0;

        double globalValue = 0.0;

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Entry> oneEntries = new ArrayList<>();
        ArrayList<Entry> realEntries = new ArrayList<>();

        int rowsCount = AKDSSSolver.getInstance().getKeyStakeholders().size();
        for (int i = 0; i < rowsCount; i++) {
            AKDSSKeyStakeholder stakeholder = AKDSSSolver.getInstance().getKeyStakeholders().get(i);

            double localValue = 0.0;

            for (AKDSSNeed need: stakeholder.getNeeds()) {

                double normalizedKeyParameterValue = need.getNormalizedKeyParameterValue();
                double satisfaction = normalizedKeyParameterValue * need.getWeight();
                localValue += satisfaction;

                if (minimalSatisfaction == 0.0 || satisfaction < minimalSatisfaction) {
                    worstSatisfated = new Pair<>(stakeholder, need);
                    minimalSatisfaction = satisfaction;
                }

            }

            oneEntries.add(new Entry(1f, i));
            realEntries.add(new Entry((float)localValue, i));
            labels.add(stakeholder.getTitle());

            localValueTextViews.get(i).setText(stringFromDouble(localValue));
            stakeholderWeightTextViews.get(i).setText(stringFromDouble(stakeholder.getWeight()));
            double stakeholderGlobalValue = localValue * stakeholder.getWeight();
            globalValue += stakeholderGlobalValue;
            globalValueTextViews.get(i).setText(stringFromDouble(stakeholderGlobalValue));
        }

        resultScoreTextView.setText(stringFromDouble(globalValue));
        resultScoreTextView.setTextColor(globalValue >= 1 ? Color.GREEN : Color.RED);

        String description = getResources().getString(globalValue >= 1 ? R.string.globalValueGood : R.string.globalValueBad);
        if (worstSatisfated != null) {
            description = description + String.format(" Меньше всего удовлетворена ЗС \"%s\" показателем \"%s\"", worstSatisfated.first.getTitle(), worstSatisfated.second.getKeyParameter());
        }

        resultDescriptionTextView.setText(description);

        // NOTE: Chart
        RadarDataSet realDataSet = new RadarDataSet(realEntries, "Удовлетворенность ЗС");
        realDataSet.setColor(Color.GREEN);
        realDataSet.setDrawFilled(true);

        RadarDataSet oneDataSet = new RadarDataSet(oneEntries, "Порог удовлетворенности");
        oneDataSet.setColor(Color.RED);
        oneDataSet.setValueTextColor(Color.TRANSPARENT);

        ArrayList<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(oneDataSet);
        dataSets.add(realDataSet);

        RadarData chartData = new RadarData(labels, dataSets);
        radarChart.setData(chartData);
        radarChart.invalidate();
    }


    private String stringFromDouble(double value) {
        return String.format(Locale.getDefault(), "%.2f", value);
    }

}
