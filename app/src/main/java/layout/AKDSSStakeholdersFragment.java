package layout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.RectRegion;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.listadapters.AKDSSStakeholderListViewAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.util.ArrayList;


public class AKDSSStakeholdersFragment extends Fragment implements IXmlNextStepClickable {

    public ListView stakeholdersListView;
    public XYPlot stakeholdersMap;

    private ArrayList<SimpleXYSeries> currentMapSeries = new ArrayList<SimpleXYSeries>();

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

            stakeholdersMap = (XYPlot) view.findViewById(R.id.stakeholdersMap);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            float screenHeightDp = displayMetrics.heightPixels / displayMetrics.density;
            stakeholdersMap.getLayoutParams().height = Math.round(0.8f * Math.min(screenHeightDp,screenWidthDp));

            stakeholdersMap.setDomainBoundaries(0, 1, BoundaryMode.FIXED);
            stakeholdersMap.setRangeBoundaries(0, 1, BoundaryMode.FIXED);

            this.updateStakeholdersMap();
        }
    }



    public void updateList() {
        final View view = getActivity().findViewById(android.R.id.content);
        if (view == null) return;
        view.post(new Runnable() {
            public void run() {
                AKDSSStakeholderListViewAdapter adapter = (AKDSSStakeholderListViewAdapter) ((HeaderViewListAdapter)stakeholdersListView.getAdapter()).getWrappedAdapter();
                adapter.notifyDataSetChanged();
                updateStakeholdersMap();
            }
        });
    }

    private void updateStakeholdersMap() {

        for (SimpleXYSeries series: currentMapSeries) {
            stakeholdersMap.removeSeries(series);
        }

        this.currentMapSeries = AKDSSSolver.getInstance().stakeholderMapDataSeries();
        ArrayList<LineAndPointFormatter> formatters = AKDSSSolver.getInstance().stakeholdersMapDataSeriesFormatters();

        for (int i = 0; i < this.currentMapSeries.size(); i++) {
            if (formatters.get(i) != null) {
                stakeholdersMap.addSeries(this.currentMapSeries.get(i), formatters.get(i));
            }
        }
    }

    @Override
    public boolean nextStepClicked(View view) {
        // TODO: Change to minimum 5 stakeholders
        if (AKDSSSolver.getInstance().getStakeholders().size() < 3) {
            Toast.makeText(getContext(), "Пожалуйста добавьте не менее 5 заинтересованных в системе сторон, скорее всего Вы про кого-то забыли", Toast.LENGTH_LONG).show();
            return false;
        }
        if (AKDSSSolver.getInstance().getStakeholders().size() > 9) {
            Toast.makeText(getContext(), "В списке более 9 ЗС. Скорее всего для кого-то из них система не так уж важна, как Вы думаете. Попробуйте сократить список", Toast.LENGTH_LONG).show();
            return false;
        }

        for (AKDSSStakeholder stakeholder: AKDSSSolver.getInstance().getStakeholders()) {
            if (stakeholder.importance == 0) {
                Toast.makeText(getContext(), "Пожалуйста, заполните значения влияния и зависимости для каждой ЗС", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

}
