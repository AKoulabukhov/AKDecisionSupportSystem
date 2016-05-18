package com.toyoapps.dssforstudents.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;

public class AKDSSOverviewFragment extends Fragment implements IXmlNextStepClickable {

    // Class

    public AKDSSOverviewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdssoverview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {

            TextView overviewTextView = (TextView) view.findViewById(R.id.overviewTextView);
            overviewTextView.setText(Html.fromHtml(getString(R.string.overviewText)));

            TextView exerciseTextView = (TextView) view.findViewById(R.id.exerciseTextView);
            exerciseTextView.setText(Html.fromHtml(getString(R.string.exerciseText)));
        }
    }

    @Override
    public boolean nextStepClicked(View view) {
        //Toast.makeText(getContext(), "Hell moto", Toast.LENGTH_LONG).show();
        return true;
    }

}
