package com.toyoapps.dssforstudents.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.listadapters.AKDSSKeyStakeholdersNeedsAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSNeedsIdentifyingFragment extends Fragment implements IXmlNextStepClickable {

    public ListView needsListView;

    public AKDSSNeedsIdentifyingFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdss_needs_identifying, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {
            needsListView = (ListView) view.findViewById(R.id.needsListView);
            View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header_needs, null);
            needsListView.addHeaderView(header);

            AKDSSKeyStakeholdersNeedsAdapter adapter = new AKDSSKeyStakeholdersNeedsAdapter(this.getContext(), AKDSSSolver.getInstance().getKeyStakeholders());
            needsListView.setAdapter(adapter);
        }
    }

    @Override
    public boolean nextStepClicked(View view) {

        for (AKDSSKeyStakeholder stakeholder: AKDSSSolver.getInstance().getKeyStakeholders()) {

            if (stakeholder.getNeeds().size() < 2) {
                Toast.makeText(this.getContext(), "Добавьте хотя бы по 2 потребности для каждой ключевой заинтересованной стороны", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (stakeholder.getNeeds().size() > 7) {
                Toast.makeText(this.getContext(), "Допустимо не более 7 потребностей для каждой заинтересованной стороны", Toast.LENGTH_SHORT).show();
                return false;
            }

            for (AKDSSNeed need: stakeholder.getNeeds()) {

                if ("".equals(need.getKeyParameter())) {
                    Toast.makeText(this.getContext(), "Заполните столбец ключевых показателей", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if ("".equals(need.getKeyParameterUnit())) {
                    Toast.makeText(this.getContext(), "Заполните столбец единиц измерений", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (need.getImportance() == 0) {
                    Toast.makeText(this.getContext(), "Заполните столбец важности показателей", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }

        }

        return true;
    }
}
