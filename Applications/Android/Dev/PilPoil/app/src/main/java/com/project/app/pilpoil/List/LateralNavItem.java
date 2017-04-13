package com.project.app.pilpoil.List;

import android.content.Intent;

public class  LateralNavItem {
    public String mTitle;
    public String mSubtitle;
    public int mIcon;
    public Intent mActivityIntent;

    public LateralNavItem(String title, String subtitle, int icon, Intent activityIntent) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
        mActivityIntent = activityIntent;
    }
}