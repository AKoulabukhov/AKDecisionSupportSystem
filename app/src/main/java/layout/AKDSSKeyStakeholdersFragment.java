package layout;


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
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.IXmlNextStepClickable;
import com.toyoapps.dssforstudents.listadapters.AKDSSKeyStakeholdersAdapter;
import com.toyoapps.dssforstudents.listadapters.AKDSSStakeholderListViewAdapter;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.util.ArrayList;


public class AKDSSKeyStakeholdersFragment extends Fragment implements IXmlNextStepClickable {

    public ListView keyStakeholdersListView;
    public XYPlot stakeholdersMap;

    private boolean rangingFinished = false;

    public AKDSSKeyStakeholdersFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdsskey_stakeholders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {

            keyStakeholdersListView = (ListView) view.findViewById(R.id.keyStakeholdersListView);
            View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header_key_stakeholders,null);
            keyStakeholdersListView.addHeaderView(header);

            AKDSSKeyStakeholdersAdapter adapter = new AKDSSKeyStakeholdersAdapter(this.getContext(), AKDSSSolver.getInstance().getKeyStakeholders());
            keyStakeholdersListView.setAdapter(adapter);

            stakeholdersMap = (XYPlot) view.findViewById(R.id.stakeholdersMap);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            float screenHeightDp = displayMetrics.heightPixels / displayMetrics.density;
            stakeholdersMap.getLayoutParams().height = Math.round(0.7f * Math.min(screenHeightDp,screenWidthDp));

            stakeholdersMap.setDomainBoundaries(0, 1, BoundaryMode.FIXED);
            stakeholdersMap.setRangeBoundaries(0, 1, BoundaryMode.FIXED);

            this.setupStakeholdersMap();
        }
    }



    public void updateList() {
        final View view = getActivity().findViewById(android.R.id.content);
        if (view == null) return;
        view.post(new Runnable() {
            public void run() {
                AKDSSKeyStakeholdersAdapter adapter = (AKDSSKeyStakeholdersAdapter) ((HeaderViewListAdapter)keyStakeholdersListView.getAdapter()).getWrappedAdapter();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupStakeholdersMap() {

        ArrayList<SimpleXYSeries> series = AKDSSSolver.getInstance().stakeholderMapDataSeries();
        ArrayList<LineAndPointFormatter> formatters = AKDSSSolver.getInstance().stakeholdersMapDataSeriesFormatters();

        for (int i = 0; i < series.size(); i++) {
            if (formatters.get(i) != null) {
                stakeholdersMap.addSeries(series.get(i), formatters.get(i));
            }
        }
    }

    public void setRangingFinished(boolean finished) {
        this.rangingFinished = finished;
        try {
            getView().findViewById(R.id.keyStakeholdersRangeFinishedTextView).setVisibility(finished ? View.VISIBLE : View.GONE);
            getView().findViewById(R.id.keyStakeholdersNextButton).setVisibility(finished ? View.VISIBLE : View.GONE);
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка обновления интерфейса - " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean nextStepClicked(View view) {
//        if (AKDSSSolver.getInstance().getStakeholders().size() < 3) {
//            Toast.makeText(getContext(), "Пожалуйста добавьте не менее 3 ключевых заинтересованных сторон, скорее всего Вы про кого-то забыли", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (AKDSSSolver.getInstance().getStakeholders().size() > 7) {
//            Toast.makeText(getContext(), "В списке более 7 ключевых ЗС. Скорее всего для кого-то из них система не так уж важна, как Вы думаете. Попробуйте сократить список", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        for (AKDSSStakeholder stakeholder: AKDSSSolver.getInstance().getStakeholders()) {
//            if (stakeholder.importance == 0) {
//                Toast.makeText(getContext(), "Пожалуйста, заполните значения влияния и зависимости для каждой ЗС", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }

        if (!rangingFinished) {
            Toast.makeText(getContext(), "Для перехода на следующий этап нужно проранжировать ЗС", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
