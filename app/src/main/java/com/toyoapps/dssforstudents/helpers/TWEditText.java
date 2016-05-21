package com.toyoapps.dssforstudents.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by toyo on 08/05/16.
 */
public class TWEditText extends EditText {

    private ArrayList<TextWatcher> textChangedListeners = new ArrayList<TextWatcher>();

    public TWEditText(Context context) {
        super(context);
    }

    public TWEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public TWEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }

    @TargetApi(21)
    public TWEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textChangedListeners.add(textWatcher);
        super.addTextChangedListener(textWatcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher textWatcher) {
        this.textChangedListeners.remove(textWatcher);
        super.removeTextChangedListener(textWatcher);
    }

    public void setTextChangedListeners (ArrayList<TextWatcher> textChangedListeners) {
        this.removeAllTextChangedListeners();
        for (TextWatcher textWatcher: textChangedListeners) {
            this.addTextChangedListener(textWatcher);
        }
    }

    public ArrayList<TextWatcher> getTextChangedListeners () {
        return new ArrayList<>(this.textChangedListeners);
    }

    public void removeAllTextChangedListeners() {
        for (TextWatcher textWatcher: textChangedListeners) {
            super.removeTextChangedListener(textWatcher);
        }
        textChangedListeners.clear();
    }

    public void setText(String text, boolean bypassTextChangedListeners) {

        if (!bypassTextChangedListeners) {
            super.setText(text);
            return;
        }

        final int previousCursorPosition = getSelectionStart();

        ArrayList<TextWatcher> listeners = getTextChangedListeners();
        removeAllTextChangedListeners();
        super.setText(text);
        setTextChangedListeners(listeners);

        post(new Runnable() {
            @Override
            public void run() {
                if (previousCursorPosition < TWEditText.this.getText().length()) {
                    setSelection(previousCursorPosition);
                }
                else {
                    setSelection(TWEditText.this.getText().length());
                }
            }
        });

    }

}
