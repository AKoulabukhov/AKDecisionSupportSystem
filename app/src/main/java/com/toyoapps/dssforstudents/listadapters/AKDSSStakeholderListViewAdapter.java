package com.toyoapps.dssforstudents.listadapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.AKDSSLearningModeActivity;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;

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

        EditText influenceEditText = (EditText) rowView.findViewById(R.id.stakeholderInfluenceEditText);
        influenceEditText.setText(format.format(stakeholder.influence));
        influenceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {

//                stakeholders.get(position).influence = doubleFromString(editable.toString());
//
//                try {
//                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
//                    activity.updateStakeholdersList();
//                }
//                catch (Exception e) {
//                    Toast.makeText(getContext(), "Ошибка обновления списка ЗС", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        EditText dependenceEditText = (EditText) rowView.findViewById(R.id.stakeholderDependenceEditText);
        dependenceEditText.setText(format.format(stakeholder.dependence));
        dependenceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
//                stakeholders.get(position).dependence = doubleFromString(editable.toString());
//
//                try {
//                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
//                    activity.updateStakeholdersList();
//                }
//                catch (Exception e) {
//                    Toast.makeText(getContext(), "Ошибка обновления списка ЗС", Toast.LENGTH_SHORT).show();
//                }
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

            return Double.parseDouble(string);

        }
    }

}
