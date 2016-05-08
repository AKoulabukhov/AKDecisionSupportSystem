package com.toyoapps.dssforstudents.AHP;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.TWEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AKAHPPairwiseComparison extends AppCompatActivity {

    static public AKAHPPairwiseComparisonDelegate delegate;

    interface AKAHPPairwiseComparisonDelegate {
        void AKAHPPairwiseComparisonDidFinished(ArrayList<String> alternatives, ArrayList<Number> results);
    }

    TableLayout tableView;

    private ArrayList<String> alternatives;
    private int decisionMakersCount;
    private int currentDecisionMaker = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akahp_pairwise_comparison);

        Intent intent = getIntent();

//        if (delegate == null) {
//            throw new RuntimeException("AKAHPPairwiseComparisonDelegate shouldn't be nil");
//        }

        Object alternatives = intent.getSerializableExtra("alternatives");

        if (alternatives instanceof ArrayList) {
            this.alternatives = (ArrayList<String>) alternatives;
        }
        else {
            throw new RuntimeException("AKAHPPairwiseComparison alternatives not set");
        }

        this.decisionMakersCount = intent.getIntExtra("decisionMakersCount", 1);

        if (this.decisionMakersCount < 1) {
            throw new RuntimeException("AKAHPPairwiseComparison decisionMakersCount < 1");
        }

        // MARK: Get layout

        tableView = (TableLayout) findViewById(R.id.rangingTableLayout);

        for (int i = 0; i < this.alternatives.size() + 1; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < this.alternatives.size() + 1; j++) {

                if (i == 0) {

                    if (j == 0) {
                        tableRow.addView(new FrameLayout(this));
                        continue;
                    }

                    int columnIndex = j - 1;
                    final String firstLetterOfAlternativeName = this.alternatives.get(columnIndex).substring(0,1);
                    tableRow.addView(new TextView(this) {{
                        setText(firstLetterOfAlternativeName);
                    }});
                    continue;

                }

                if (j == 0) {

                    int rowIndex = i - 1;
                    final String alternativeName = this.alternatives.get(rowIndex);
                    tableRow.addView(new TextView(this) {{
                        setText(alternativeName);
                    }});
                    continue;
                }

                final int row = i, column = j;

                tableRow.addView(new TWEditText(this) {{

                    if (row == column) {
                        setText("1");
                    }
                    else {
                        setText("0");
                    }

                }});

            }

            tableView.addView(tableRow, i);

        }

    }
}
