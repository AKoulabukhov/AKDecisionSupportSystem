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

import com.toyoapps.dssforstudents.AKDSSLearningModeActivity;
import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.TWEditText;
import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by toyo on 08/05/16.
 */
public class AKDSSKeyStakeholdersAdapter extends ArrayAdapter<AKDSSKeyStakeholder> {

    private final Context context;
    private final ArrayList<AKDSSKeyStakeholder> keyStakeholders;

    public AKDSSKeyStakeholdersAdapter(Context context, ArrayList<AKDSSKeyStakeholder> keyStakeholders) {
        super(context, R.layout.list_item_stakeholder);
        this.context = context;
        this.keyStakeholders = keyStakeholders;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView;

        if (convertView != null) {
            rowView = convertView;
        }
        else {
            rowView = inflater.inflate(R.layout.list_item_key_stakeholder, parent, false);
        }

        AKDSSKeyStakeholder keyStakeholder = keyStakeholders.get(position);
        DecimalFormat format = new DecimalFormat("#.00");

        TextView titleTextView = (TextView) rowView.findViewById(R.id.keyStakeholderTitleTextView);
        titleTextView.setText(keyStakeholder.getTitle());

        TextView weightTextView = (TextView) rowView.findViewById(R.id.keyStakeholderWeightTextView);
        weightTextView.setText(format.format(keyStakeholder.getWeight()));

        Button deleteButton = (Button) rowView.findViewById(R.id.keyStakeholderDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AKDSSSolver.getInstance().removeKeyStakeholder(keyStakeholders.get(position));
                try {
                    AKDSSLearningModeActivity activity = (AKDSSLearningModeActivity) getContext();
                    activity.updateKeyStakeholdersList();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка обновления списка ключевых ЗС", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        if (keyStakeholders == null) {
            return 0;
        }

        return keyStakeholders.size();
    }

    @Override
    public AKDSSKeyStakeholder getItem(int position) {
        return keyStakeholders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
