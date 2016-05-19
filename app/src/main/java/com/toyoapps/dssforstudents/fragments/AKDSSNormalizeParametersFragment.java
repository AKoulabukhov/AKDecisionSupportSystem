package com.toyoapps.dssforstudents.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.helpers.TWEditText;
import com.toyoapps.dssforstudents.listadapters.AKDSSKeyStakeholdersNeedsAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;

import java.util.ArrayList;


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
                        setText(need.getName());
                        setTextSize(16);
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());
                    }});

                    needTableRow.addView(new TWEditText(this.getContext()) {{
                        setHint(need.getKeyParameterUnit());
                    }});

                    tableLayout.addView(needTableRow);

                }

            }

        }
    }

    @Override
    public boolean nextStepClicked(View view) {
        return false;
    }
}
