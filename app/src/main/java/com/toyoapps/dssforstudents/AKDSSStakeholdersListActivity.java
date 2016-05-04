package com.toyoapps.dssforstudents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

public class AKDSSStakeholdersListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] typicalStakeholders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akdssstakeholders_list);

        typicalStakeholders = getResources().getStringArray(R.array.typicalStakeholders);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typicalStakeholders);
        ListView listView = (ListView) findViewById(R.id.listView);

        if (listView != null) {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AKDSSSolver.getInstance().addStakeholder(new AKDSSStakeholder(typicalStakeholders[position]));
        Toast.makeText(AKDSSStakeholdersListActivity.this, typicalStakeholders[position], Toast.LENGTH_SHORT).show();

        AKDSSLearningModeActivity activity = AKDSSLearningModeActivity.lastCreatedActivity;
        activity.updateStakeholdersList();

        finish();
    }
}
