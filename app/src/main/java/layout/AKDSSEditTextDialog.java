package layout;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toyoapps.dssforstudents.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AKDSSEditTextDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;

    public interface AKDSSEditTextDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public AKDSSEditTextDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akdssedit_text_dialog, container, false);

        mEditText = (EditText) view.findViewById(R.id.editText);

        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);

        getDialog().setTitle("Добавить ЗС");

        return view;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == i) {
            try {
                AKDSSEditTextDialogListener activity = (AKDSSEditTextDialogListener) getActivity();
                activity.onFinishEditDialog(mEditText.getText().toString());
                this.dismiss();
                return true;
            }
            catch (Exception e) {
                Toast.makeText(AKDSSEditTextDialog.this.getActivity(), "Ошибка добавления", Toast.LENGTH_SHORT).show();
                this.dismiss();
                return true;
            }
        }
        return false;
    }
}
