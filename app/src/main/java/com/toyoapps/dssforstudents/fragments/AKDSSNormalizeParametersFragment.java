package com.toyoapps.dssforstudents.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.helpers.TWEditText;
import com.toyoapps.dssforstudents.listadapters.AKDSSKeyStakeholdersNeedsAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSNormalizeParametersFragment extends Fragment implements IXmlNextStepClickable {


    public AKDSSNormalizeParametersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdss_normalize_parameters, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {
            TableLayout tableLayout = (TableLayout) view.findViewById(R.id.normalizeTableLayout);
            if (tableLayout == null) {
                return;
            }

            ArrayList<AKDSSKeyStakeholder> keyStakeholders = AKDSSSolver.getInstance().getKeyStakeholders();

            for (final AKDSSKeyStakeholder stakeholder: keyStakeholders) {

                TableRow tableRow = new TableRow(this.getContext());
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.off_white, getContext().getTheme()));
                }
                else {
                    tableRow.setBackgroundColor(getResources().getColor(R.color.off_white));
                }

                tableRow.addView(new TextView(this.getContext()){{
                    setText(stakeholder.getTitle());
                    setTextSize(18);
                }});

                for (int i = 0; i < 3; i++) {
                    tableRow.addView(new FrameLayout(this.getContext()));
                }

                tableLayout.addView(tableRow);

                for (final AKDSSNeed need: stakeholder.getNeeds()) {

                    TableRow needTableRow = new TableRow(this.getContext());
                    needTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    needTableRow.addView(new TextView(this.getContext()) {{
                        setText(need.getKeyParameter());
                        setTextSize(16);
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());

                        double value = need.getNormalizeParameters().worstValue;
                        if (value != 0.0) {
                            setText(String.valueOf(value));
                        }

                        setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void afterTextChanged(Editable editable) {
                                double newValue = doubleFromString(editable.toString());
                                need.getNormalizeParameters().worstValue = newValue;
                                setText(String.valueOf(newValue), true);
                            }

                        });
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());

                        double value = need.getNormalizeParameters().normalValue;
                        if (value != 0.0) {
                            setText(String.valueOf(value));
                        }

                        setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void afterTextChanged(Editable editable) {
                                double newValue = doubleFromString(editable.toString());
                                need.getNormalizeParameters().normalValue = newValue;
                                setText(String.valueOf(newValue), true);
                            }
                        });
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());

                        double value = need.getNormalizeParameters().bestPossibleValue;
                        if (value != 0.0) {
                            setText(String.valueOf(value));
                        }

                        setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                            @Override
                            public void afterTextChanged(Editable editable) {
                                double newValue = doubleFromString(editable.toString());
                                need.getNormalizeParameters().bestPossibleValue = newValue;
                                setText(String.valueOf(newValue), true);
                            }
                        });
                    }});

                    tableLayout.addView(needTableRow);

                }

            }

        }
    }

    @Override
    public boolean nextStepClicked(View view) {

        for (AKDSSKeyStakeholder stakeholder: AKDSSSolver.getInstance().getKeyStakeholders()) {

            for (AKDSSNeed need: stakeholder.getNeeds()) {

                AKDSSNeed.AKDSSNeedNormalizeParameters parameters = need.getNormalizeParameters();

                if (parameters.normalValue <= parameters.worstValue || parameters.bestPossibleValue <= parameters.normalValue) {
                    Toast.makeText(this.getActivity(), "Ошибка заполнения параметра " + need.getKeyParameter() + ". Худшее значение должно быть самым маленьким, лучшее - самым большим", Toast.LENGTH_LONG).show();
                    return false;
                }

            }

        }

        return true;
    }


    // MARK: Helper

    public double doubleFromString(String string) {

        if(string == null || string.isEmpty()) {

            return 0.0;

        } else {

            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            try {
                Number number = format.parse(string);
                double value = number.doubleValue();
                if (value > 999999) value = 999999;
                if (value < 0) value = 0;
                return value;
            }
            catch (Exception e) {
                return 0.0;
            }
        }
    }

}
