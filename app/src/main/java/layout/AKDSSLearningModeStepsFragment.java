package layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.toyoapps.dssforstudents.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AKDSSLearningModeStepsFragment extends Fragment {

    private ArrayList<TextView> stepTextViews;
    private HorizontalScrollView scrollView;

    public void setSelectedStep(int selectedStepIndex) {

        if (selectedStepIndex < 0 || selectedStepIndex >= stepTextViews.size()) {
            System.out.println("AKDSSLearningModeStepsFragment: Attempt to set selected step out of bounds. Ignoring.");
            return;
        }

        Context context = AKDSSLearningModeStepsFragment.this.getContext();
        TextView selectedTextView = null;

        for (int i = 0; i < stepTextViews.size(); i++) {
            if (i < selectedStepIndex) {
                stepTextViews.get(i).setBackgroundColor(ContextCompat.getColor(context, R.color.colorAKDSSLearningModeStepsFragmentStepBGColorCompleted));
            }
            else if (i == selectedStepIndex) {
                selectedTextView = stepTextViews.get(i);
                selectedTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAKDSSLearningModeStepsFragmentStepBGColorInProgress));
            }
            else {
                stepTextViews.get(i).setBackgroundColor(ContextCompat.getColor(context, R.color.colorAKDSSLearningModeStepsFragmentStepBGColorInactive));
            }
        }

        if (selectedTextView != null) {
            final TextView finalSelectedTextView = selectedTextView;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    int vLeft = finalSelectedTextView.getLeft();
                    int vRight = finalSelectedTextView.getRight();
                    int sWidth = scrollView.getWidth();
                    scrollView.smoothScrollTo(((vLeft + vRight - sWidth) / 2), 0);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akdsslearning_mode_steps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (view != null) {

            final View myView = view;

            this.stepTextViews = new ArrayList<TextView>() {{
                add((TextView) myView.findViewById(R.id.firstStepTextView));
                add((TextView) myView.findViewById(R.id.secondStepTextView));
                add((TextView) myView.findViewById(R.id.thirdStepTextView));
                add((TextView) myView.findViewById(R.id.fourthStepTextView));
                add((TextView) myView.findViewById(R.id.fifthStepTextView));
                add((TextView) myView.findViewById(R.id.sixthStepTextView));
            }};

            this.scrollView = (HorizontalScrollView) myView.findViewById(R.id.horizontalScrollView);

        }
    }

}
