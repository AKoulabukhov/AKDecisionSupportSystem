package com.toyoapps.dssforstudents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.logic.AKDSSSolver;
import com.toyoapps.dssforstudents.models.AKDSSKeyStakeholder;
import com.toyoapps.dssforstudents.models.AKDSSStakeholder;

public class AKDSSStakeholdersListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] typicalStakeholders;
    private boolean typical = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akdssstakeholders_list);

        Intent intent = getIntent();
        if (intent.getStringExtra("real") != null) {
            typicalStakeholders = AKDSSSolver.getInstance().getStakeholdersList();
        }
        else {
            typicalStakeholders = getResources().getStringArray(R.array.typicalStakeholders);
            typical = true;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typicalStakeholders);
        ListView listView = (ListView) findViewById(R.id.listView);

        if (listView != null) {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(AKDSSStakeholdersListActivity.this, typicalStakeholders[position], Toast.LENGTH_SHORT).show();

        AKDSSLearningModeActivity activity = AKDSSLearningModeActivity.lastCreatedActivity;
        String stakeholderName = typicalStakeholders[position];
        if (typical) {
            AKDSSSolver.getInstance().addStakeholder(new AKDSSStakeholder(stakeholderName));
            activity.updateStakeholdersList();
        }
        else {
            AKDSSSolver.getInstance().addKeyStakeholder(new AKDSSKeyStakeholder(stakeholderName));
            activity.updateKeyStakeholdersList();
        }

        finish();
    }
}
