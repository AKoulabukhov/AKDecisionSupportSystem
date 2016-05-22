package com.toyoapps.dssforstudents.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toyoapps.dssforstudents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSResultsFragment extends Fragment {


    public AKDSSResultsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdss_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view == null) {
            return;
        }



    }

}
