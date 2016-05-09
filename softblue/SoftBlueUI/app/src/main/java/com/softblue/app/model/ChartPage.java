package com.softblue.app.model;

import com.softblue.app.ui.ChartFragment;

/**
 * Created by kamil.niezrecki@gmail.com
 */
public class ChartPage {
    private final String mLabel;
    private final String mSubLabel;
    private ChartFragment mFragment;

    public ChartPage(String label, String subLabel) {
        this.mLabel = label;
        this.mSubLabel = subLabel;
    }

    public void initFragment() {
        mFragment = ChartFragment.newInstance(mLabel, mLabel);
    }

    public ChartFragment getFragment() {
        if (mFragment == null) {
            throw new IllegalStateException("Fragment not initialized. Call initFragment first");
        }
        return mFragment;
    }

    public String getLabel() {
        return mLabel;
    }

    public String getSubLabel() {
        return mSubLabel;
    }
}
