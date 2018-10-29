package com.mdlink.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class MdlinkProgressBar {

    private static ProgressDialog progress;

    public static void setProgressBar(Activity activity) {
        progress = new ProgressDialog(activity);
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }
    public static void hideProgressBar(Activity activity) {
        progress.hide();
    }
}
