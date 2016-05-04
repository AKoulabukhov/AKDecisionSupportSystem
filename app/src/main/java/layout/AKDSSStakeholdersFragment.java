package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.listadapters.AKDSSStakeholderListViewAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;


public class AKDSSStakeholdersFragment extends Fragment implements IXmlNextStepClickable {

    public ListView stakeholdersListView;

    public AKDSSStakeholdersFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdssstakeholders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {

            stakeholdersListView = (ListView) view.findViewById(R.id.stakeholdersListView);
            View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header_stakeholders,null);
            stakeholdersListView.addHeaderView(header);

            AKDSSStakeholderListViewAdapter adapter = new AKDSSStakeholderListViewAdapter(this.getContext(), AKDSSSolver.getInstance().getStakeholders());
            stakeholdersListView.setAdapter(adapter);

            TextView stakeholdersInitialDescriptionTextView = (TextView) view.findViewById(R.id.stakeholdersInitialDescriptionTextView);
            stakeholdersInitialDescriptionTextView.setText(Html.fromHtml(getString(R.string.stakeholdersDescriptionText)));

        }
    }

    public void updateList() {
        final View view = getActivity().findViewById(android.R.id.content);
        if (view == null) return;
        view.post(new Runnable() {
            public void run() {

                AKDSSStakeholderListViewAdapter adapter = (AKDSSStakeholderListViewAdapter) ((HeaderViewListAdapter)stakeholdersListView.getAdapter()).getWrappedAdapter();
                adapter.notifyDataSetChanged();
//
//                Helps to set height as content height
//
//                ViewGroup.LayoutParams params = stakeholdersListView.getLayoutParams();
//                params.height = stakeholdersListView.getChildAt(0).getHeight() * (adapter.getCount() + 2);
//                stakeholdersListView.setLayoutParams(params);
//                stakeholdersListView.requestLayout();

            }
        });
    }

    @Override
    public boolean nextStepClicked(View view) {
        //Toast.makeText(getContext(), "Hell moto", Toast.LENGTH_LONG).show();
        return true;
    }
}
