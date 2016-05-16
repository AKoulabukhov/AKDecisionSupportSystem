package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.listadapters.AKDSSKeyStakeholdersNeedsAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSNeedsIdentifyingFragment extends Fragment {

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

}
