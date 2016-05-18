package com.toyoapps.dssforstudents.listadapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.activities.AKDSSLearningModeActivity;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.TWEditText;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by toyo on 03/05/16.
 */

public class AKDSSStakeholderListViewAdapter extends ArrayAdapter<AKDSSStakeholder> {

    private final Context context;
    private final ArrayList<AKDSSStakeholder> stakeholders;

    public AKDSSStakeholderListViewAdapter(Context context, ArrayList<AKDSSStakeholder> stakeholders) {
        super(context, R.layout.list_item_stakeholder);
        this.context = context;
        this.stakeholders = stakeholders;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        System.out.println("getView " + position + " " + convertView);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView;

        if (convertView != null) {
            rowView = convertView;
        }
        else {
            rowView = inflater.inflate(R.layout.list_item_stakeholder, parent, false);
        }

        AKDSSStakeholder stakeholder = stakeholders.get(position);
        DecimalFormat format = new DecimalFormat("#.00");

        TextView titleTextView = (TextView) rowView.findViewById(R.id.stakeholderTitleTextView);
        titleTextView.setText(stakeholder.title);

        TWEditText influenceEditText = (TWEditText) rowView.findViewById(R.id.stakeholderInfluenceEditText);
        influenceEditText.removeAllTextChangedListeners();
        influenceEditText.setText(format.format(stakeholder.getInfluence()));
        influenceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                double previousValue = stakeholders.get(position).getInfluence();
                double newValue = doubleFromString(editable.toString());

                if (previousValue == newValue) {
                    return;
                }

                stakeholders.get(position).setInfluence(newValue);

                try {
                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
                    activity.updateStakeholdersList();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка обновления списка ЗС", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TWEditText dependenceEditText = (TWEditText) rowView.findViewById(R.id.stakeholderDependenceEditText);
        dependenceEditText.removeAllTextChangedListeners();
        dependenceEditText.setText(format.format(stakeholder.getDependence()));
        dependenceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

                double previousValue = stakeholders.get(position).getDependence();
                double newValue = doubleFromString(editable.toString());

                if (previousValue == newValue) {
                    return;
                }

                stakeholders.get(position).setDependence(newValue);

                try {
                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
                    activity.updateStakeholdersList();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка обновления списка ЗС", Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView importanceTextView = (TextView) rowView.findViewById(R.id.stakeholderImportanceTextView);
        importanceTextView.setText(format.format(stakeholder.importance));

        TextView weightTextView = (TextView) rowView.findViewById(R.id.stakeholderWeightTextView);
        weightTextView.setText(format.format(stakeholder.weight));

        Button deleteButton = (Button) rowView.findViewById(R.id.stakeholderDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AKDSSSolver.getInstance().removeStakeholder(stakeholders.get(position));
                try {
                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
                    activity.updateStakeholdersList();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка обновления списка ЗС", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        if (stakeholders == null) {
            return 0;
        }

        return stakeholders.size();
    }

    @Override
    public AKDSSStakeholder getItem(int position) {
        return stakeholders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
                if (value > 1) value = 1.0;
                if (value < 0) value = 0;
                return value;
            }
            catch (Exception e) {
                return 0.0;
            }
        }
    }

}
