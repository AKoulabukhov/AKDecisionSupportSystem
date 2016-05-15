package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSNeedsIdentifyingFragment extends Fragment {


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

//            keyStakeholdersListView = (ListView) view.findViewById(R.id.keyStakeholdersListView);
//            View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header_key_stakeholders,null);
//            keyStakeholdersListView.addHeaderView(header);
//
//            AKDSSKeyStakeholdersAdapter adapter = new AKDSSKeyStakeholdersAdapter(this.getContext(), AKDSSSolver.getInstance().getKeyStakeholders());
//            keyStakeholdersListView.setAdapter(adapter);

        }
    }

}
