package com.toyoapps.dssforstudents.AHP;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.R;
import com.toyoapps.dssforstudents.helpers.TWEditText;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AKAHPPairwiseComparison extends AppCompatActivity {

    private static final double NSNotFound = 0x7fffffff;

    static public AKAHPPairwiseComparisonDelegate delegate;

    public interface AKAHPPairwiseComparisonDelegate {
        void AKAHPPairwiseComparisonDidFinished(ArrayList<String> alternatives, ArrayList<Double> results);
    }

    private TableLayout tableView;
    private TextView topTextView;
    private TextView bottomTextView;

    private ArrayList<String> alternatives;
    private  ArrayList<Double> results = new ArrayList<Double>();
    private int decisionMakersCount;
    private int currentDecisionMaker = 1;

    List<String> allowableValues = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9");
    List<String> invertedAllowableValues = Arrays.asList("1", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9", "2", "3", "4", "5", "6", "7", "8", "9");

    private ArrayList<ArrayList<TWEditText>> editTexts = new ArrayList<ArrayList<TWEditText>>();
    private ArrayList<ArrayList<Double>> pairwiseDMResults = new ArrayList<ArrayList<Double>>();

    private boolean rangingFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akahp_pairwise_comparison);

        Intent intent = getIntent();

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

        topTextView = (TextView) findViewById(R.id.rangingTopTextView);
        if (topTextView != null) {
            String text = "ЛПР " + currentDecisionMaker + " из " + decisionMakersCount;
            topTextView.setText(text);
        }

        bottomTextView = (TextView) findViewById(R.id.rangingBottomTextView);
        if (bottomTextView != null) {
            bottomTextView.setText("Заполните матрицу попарных сравнений используя предпочтения одной альтернативы по отношению к другой. Наибольшее предпочтений альтернативы над другой - 9. Наибольшее предпочтение противоположной альтернативы - 1/9.");
        }

        // MARK: Get layout

        tableView = (TableLayout) findViewById(R.id.rangingTableLayout);

        for (int i = 0; i < this.alternatives.size() + 1; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            ArrayList<TWEditText> rowEditTexts = new ArrayList<TWEditText>();

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

                final int row = i - 1, column = j - 1;

                final TWEditText editText = new TWEditText(this) {{

                    if (row == column) {
                        setText("1");
                        setKeyListener(null);
                    }
                }};

                // TODO: циферки на клавиатуре
                //editText.setKeyListener(DigitsKeyListener.getInstance("1234567890/"));

                editText.addTextChangedListener(new TextWatcher() {

                    private String beforeTextChanged;

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        beforeTextChanged = charSequence.toString();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String newValue = editable.toString();

                        editText.setBackgroundColor(Color.TRANSPARENT);

                        for (String string: allowableValues) {

                            if (string.equals(newValue)) {
                                int index = allowableValues.indexOf(string);

                                TWEditText invertedEditText = editTexts.get(column).get(row);
                                if (invertedEditText == null) { return; }

                                ArrayList<TextWatcher> watchers = invertedEditText.getTextChangedListeners();
                                invertedEditText.removeAllTextChangedListeners();
                                invertedEditText.setText(invertedAllowableValues.get(index));
                                invertedEditText.setBackgroundColor(Color.TRANSPARENT);
                                invertedEditText.setTextChangedListeners(watchers);
                                return;
                            }
                        }

                        for (String string: allowableValues) {
                            if (string.startsWith(newValue)) {
                                return;
                            }
                        }

                        editText.removeTextChangedListener(this);
                        editText.setText(beforeTextChanged);
                        editText.addTextChangedListener(this);
                    }

                });

                rowEditTexts.add(editText);
                tableRow.addView(editText);

            }

            if (i != 0) {
                editTexts.add(rowEditTexts);
            }

            tableView.addView(tableRow, i);

        }

    }

    public void doneButtonPressed(View v) {

        this.hideSoftKeyBoard();

        if (rangingFinished) {
            if (delegate != null) {
                delegate.AKAHPPairwiseComparisonDidFinished(this.alternatives, this.results);
            }
            finish();
            return;
        }

        double[] middleGeometrics = new double[editTexts.size()];
        double middleGeometricsSum = 0;

        for (ArrayList<TWEditText> rowOfTextEdits: editTexts) {

            double middleGeometric = 0;
            double elementsMultiplicity = 1;

            for (TWEditText editText: rowOfTextEdits) {

                double value = this.valueForEditText(editText);

                if (value == NSNotFound) {
                    Toast.makeText(AKAHPPairwiseComparison.this, "Проверьте заполнение матрицы парных сравнений", Toast.LENGTH_SHORT).show();
                    return;
                }

                elementsMultiplicity *= value;
            }

            int index = editTexts.indexOf(rowOfTextEdits);

            middleGeometric = Math.pow(elementsMultiplicity, 1.0 / rowOfTextEdits.size());
            middleGeometricsSum += middleGeometric;
            middleGeometrics[index] = middleGeometric;

        }

        ArrayList<Double> resultsForDM = new ArrayList<Double>();

        for (double middleGeometric : middleGeometrics) {
            Double res = middleGeometric / middleGeometricsSum;
            resultsForDM.add(res);
        }

        pairwiseDMResults.add(resultsForDM);


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Результаты ранжирования ЛПР №");
        stringBuilder.append(currentDecisionMaker);
        stringBuilder.append(":\n");

        for (int i = 0; i < resultsForDM.size(); i++) {
            stringBuilder.append(i + 1);
            stringBuilder.append(". ");
            stringBuilder.append(this.alternatives.get(i));
            stringBuilder.append(" = ");
            stringBuilder.append(resultsForDM.get(i));
            stringBuilder.append("\n");
        }

        if (currentDecisionMaker == decisionMakersCount) {
            this.rangingFinished = true;
            stringBuilder.append("\n\n");
            stringBuilder.append("Итоговые результаты:\n");

            for (int i = 0; i < pairwiseDMResults.size(); i++) {
                for (int j = 0; j < this.alternatives.size(); j++) {

                    if (this.results.size() < j + 1) {
                        this.results.add(pairwiseDMResults.get(i).get(j));
                    }
                    else  {
                        this.results.set(j, this.results.get(j) + pairwiseDMResults.get(i).get(j));
                    }

                    if (i == pairwiseDMResults.size() - 1) {
                        this.results.set(j, this.results.get(j) / pairwiseDMResults.size());
                    }
                }
            }

            for (int i = 0; i < this.results.size(); i++) {
                stringBuilder.append(i + 1);
                stringBuilder.append(". ");
                stringBuilder.append(this.alternatives.get(i));
                stringBuilder.append(" = ");
                stringBuilder.append(this.results.get(i));
                stringBuilder.append("\n");
            }

            stringBuilder.append("\n\n");
            stringBuilder.append("Для продолжения нажмите \"Готово\"");

        }

        else {
            currentDecisionMaker ++;
            String text = "ЛПР " + currentDecisionMaker + " из " + decisionMakersCount;
            topTextView.setText(text);

            stringBuilder.append("\n\n");
            stringBuilder.append("Передайте приложение следующему ЛПР.");

            for (ArrayList<TWEditText> list: editTexts) {
                for (TWEditText editText: list) {
                    ArrayList<TextWatcher> watchers = editText.getTextChangedListeners();
                    editText.removeAllTextChangedListeners();

                    if (editTexts.indexOf(list) == list.indexOf(editText)) {
                        editText.setText("1");
                    }
                    else {
                        editText.setText("");
                    }

                    editText.setTextChangedListeners(watchers);
                }
            }

        }

        this.bottomTextView.setText(stringBuilder.toString());
    }



    private double valueForEditText (EditText editText) {
        String text = editText.getText().toString();
        boolean textIsValid = false;
        for (String allowable: this.allowableValues) {
            if (text.equalsIgnoreCase(allowable)) {
                textIsValid = true;
                break;
            }
        }

        if (!textIsValid) {
            editText.setBackgroundColor(Color.YELLOW);
            return NSNotFound;
        }

        if (text.contains("/")) {
            String[] numbers = text.split("/");
            if (numbers.length != 2) {
                editText.setBackgroundColor(Color.YELLOW);
                return NSNotFound;
            }

            try {
                return Double.parseDouble(numbers[0]) / Double.parseDouble(numbers[1]);
            }
            catch (Exception e) {
                editText.setBackgroundColor(Color.YELLOW);
                return NSNotFound;
            }
        }

        try {
            return Double.parseDouble(text);
        }
        catch (Exception e) {
            editText.setBackgroundColor(Color.YELLOW);
            return NSNotFound;
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
