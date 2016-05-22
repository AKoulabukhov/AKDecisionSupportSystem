package com.toyoapps.dssforstudents.listadapters;

import android.app.Activity;
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
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSNeed;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by toyo on 15/05/16.
 */
public class AKDSSKeyStakeholdersNeedsAdapter extends ArrayAdapter<Object> {

    enum RowType {
        ROW_TYPE_NOT_FOUND,
        ROW_TYPE_NEED,
        ROW_TYPE_STAKEHOLDER
    }

    private final Context context;
    private final ArrayList<AKDSSKeyStakeholder> keyStakeholders;

    private final ArrayList<TextView> weightTextViews;
    private final ArrayList<AKDSSNeed> needs;

    public AKDSSKeyStakeholdersNeedsAdapter(Context context, ArrayList<AKDSSKeyStakeholder> keyStakeholders) {
        super(context, R.layout.list_item_need);
        this.context = context;
        this.keyStakeholders = keyStakeholders;
        this.weightTextViews = new ArrayList<TextView>();
        this.needs = new ArrayList<AKDSSNeed>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RowType rowType = this.rowTypeAtPosition(position);

        if (rowType == RowType.ROW_TYPE_NOT_FOUND) {
            return new View(this.context);
        }

        View rowView = new View(context);

        if (rowType == RowType.ROW_TYPE_STAKEHOLDER) {

            if (convertView != null && convertView.findViewById(R.id.needStakeholderTitleTextView) != null) {
                rowView = convertView;
            }
            else {
                rowView = inflater.inflate(R.layout.list_item_need_stakeholder, parent, false);
            }

            final AKDSSKeyStakeholder stakeholder = this.stakeholderFromPosition(position);

            if (stakeholder == null) {
                return new View(this.context);
            }

            TextView textView = (TextView) rowView.findViewById(R.id.needStakeholderTitleTextView);
            textView.setText(stakeholder.getTitle());

            Button addNeedButton = (Button) rowView.findViewById(R.id.needAddButton);
            addNeedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
                        activity.addNeedForKeyStakeholder(stakeholder);
                    }
                    catch (Exception e) {
                        Toast.makeText(getContext(), "Ошибка добавления потребности", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        if (rowType == RowType.ROW_TYPE_NEED) {
            if (convertView != null && convertView.findViewById(R.id.needTitleTextView) != null) {
                rowView = convertView;
            }
            else {
                rowView = inflater.inflate(R.layout.list_item_need, parent, false);
            }

            final AKDSSNeed need = this.needFromPosition(position);

            if (need == null) {
                return new View(this.context);
            }

            TextView titleTextView = (TextView) rowView.findViewById(R.id.needTitleTextView);
            titleTextView.setText(need.getName());

            final TWEditText parameterEditText = (TWEditText) rowView.findViewById(R.id.needParameterEditText);
            parameterEditText.removeAllTextChangedListeners();
            parameterEditText.setText(need.getKeyParameter());
            parameterEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    need.setKeyParameter(editable.toString());
                }
            });

            TWEditText parameterUnitEditText = (TWEditText) rowView.findViewById(R.id.needParameterUnitEditText);
            parameterUnitEditText.removeAllTextChangedListeners();
            parameterUnitEditText.setText(need.getKeyParameterUnit());
            parameterUnitEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    need.setKeyParameterUnit(editable.toString());
                }
            });


            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            TextView weightTextView = (TextView) rowView.findViewById(R.id.needWeightTextView);
            if (need.getWeight() != 0) {
                weightTextView.setText(decimalFormat.format(need.getWeight()));
            }

            if (this.needs.contains(need)) {
                int index = this.needs.indexOf(need);
                this.weightTextViews.set(index, weightTextView);
            }
            else {
                this.needs.add(need);
                this.weightTextViews.add(weightTextView);
            }



            final DecimalFormat format = new DecimalFormat("#");

            final TWEditText parameterImporatnceEditText = (TWEditText) rowView.findViewById(R.id.needImportanceTextView);
            parameterImporatnceEditText.removeAllTextChangedListeners();
            parameterImporatnceEditText.setText(format.format(need.getImportance()));
            parameterImporatnceEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    need.setImportance(doubleFromString(editable.toString()));
//                    ArrayList<TextWatcher> listeners = parameterImporatnceEditText.getTextChangedListeners();
//                    parameterImporatnceEditText.removeAllTextChangedListeners();
//                    parameterImporatnceEditText.setText(format.format(need.getImportance()));
//                    parameterImporatnceEditText.setTextChangedListeners(listeners);

                    ((Activity) AKDSSKeyStakeholdersNeedsAdapter.this.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AKDSSKeyStakeholdersNeedsAdapter.this.updateWeights();
                        }
                    });
                }
            });

            Button deleteButton = (Button) rowView.findViewById(R.id.needDeleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    need.getStakeholder().removeNeed(need);
                    AKDSSKeyStakeholdersNeedsAdapter.this.notifyDataSetChanged();
                }
            });

        }

        return rowView;
    }

    private RowType rowTypeAtPosition(int position) {

        int counter = 0;

        for (int i = 0; i < this.keyStakeholders.size(); i++) {

            if (counter == position) {
                return RowType.ROW_TYPE_STAKEHOLDER;
            }

            counter++;

            for (int j = 0; j < this.keyStakeholders.get(i).getNeeds().size(); j++) {

                if (counter == position) {
                    return RowType.ROW_TYPE_NEED;
                }

                counter++;

            }
        }

        return RowType.ROW_TYPE_NOT_FOUND;
    }

    private AKDSSKeyStakeholder stakeholderFromPosition(int position) {
        int counter = 0;

        for (int i = 0; i < this.keyStakeholders.size(); i++) {

            if (counter == position) {
                return this.keyStakeholders.get(i);
            }

            counter++;

            for (int j = 0; j < this.keyStakeholders.get(i).getNeeds().size(); j++) {
                counter++;
            }
        }

        return null;
    }

    private AKDSSNeed needFromPosition(int position) {
        int counter = 0;

        for (int i = 0; i < this.keyStakeholders.size(); i++) {
            counter++;

            for (int j = 0; j < this.keyStakeholders.get(i).getNeeds().size(); j++) {

                if (counter == position) {
                    return this.keyStakeholders.get(i).getNeeds().get(j);
                }

                counter++;

            }
        }

        return null;
    }

    @Override
    public int getCount() {
        if (keyStakeholders == null) {
            return 0;
        }

        int needsCount = 0;

        for (AKDSSKeyStakeholder stakeholder: this.keyStakeholders) {
            for (AKDSSNeed need: stakeholder.getNeeds()) {
                needsCount++;
            }
        }

        return keyStakeholders.size() + needsCount;
    }

    @Override
    public Object getItem(int position) {

        RowType objectType = this.rowTypeAtPosition(position);

        switch (objectType) {
            case ROW_TYPE_STAKEHOLDER:
                return this.stakeholderFromPosition(position);
            case ROW_TYPE_NEED:
                return this.needFromPosition(position);
            case ROW_TYPE_NOT_FOUND:
                return null;
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // MARK: Cool updater

    private void updateWeights() {
        DecimalFormat format = new DecimalFormat("#.00");
        for (int i = 0; i < needs.size(); i++) {
            final TextView textView = weightTextViews.get(i);
            AKDSSNeed need = needs.get(i);
            double weight = need.getWeight();
            final String weightString = format.format(weight);
            textView.setText(weightString);
        }
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
                if (value > 10) value = 10.0;
                if (value < 0) value = 0;
                return value;
            }
            catch (Exception e) {
                return 0.0;
            }
        }
    }

}
