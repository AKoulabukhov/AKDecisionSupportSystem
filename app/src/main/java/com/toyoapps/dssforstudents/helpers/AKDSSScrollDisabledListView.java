package com.toyoapps.dssforstudents.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by alexander koulabuhov on 04/05/16.
 */

public class AKDSSScrollDisabledListView extends ListView {
    public AKDSSScrollDisabledListView  (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AKDSSScrollDisabledListView  (Context context) {
        super(context);
    }

    public AKDSSScrollDisabledListView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
