package com.mdlink.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mdlink.App;
import com.mdlink.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class UiHelper {

    private static final String TAG = "UiHelper";

    private static AlertDialog mProgressDialog;

    /**
     * Common method to display a Toast message
     *
     * @param message Message to display
     * @return Created Toast
     */
    public static Toast toast(String message) {
        if (StringHelper.isEmptyOrNull(message)) {
            return null;
        }

        Toast toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    /**
     * Switch visibility of password input persisting text selection
     *
     */
    public static void switchPasswordVisibility(AppCompatEditText editText, boolean toShow) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        editText.setTransformationMethod(toShow ? null : new PasswordTransformationMethod());

        editText.setSelection(start, end);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPixels(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pixelsToDp(float px) {
        return px / ((float) Resources.getSystem().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void showProgress(Activity activity, boolean isCancelable) {
        UiHelper.dismissKeyboard(activity);
        hideProgress();
        if (isActivityAlive(activity)) {
            mProgressDialog = new AlertDialog.Builder(activity)
                    .setCancelable(isCancelable)
                    .setView(R.layout.progress)
                    .create();
            mProgressDialog.show();
        }
    }

    public static void displayMessage(Activity activity, String message, DialogInterface.OnClickListener positiveListener) {
        AlertDialog messageDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(R.string.ok, positiveListener)
                .create();

        messageDialog.show();
    }

    private static boolean isActivityAlive(Activity activity) {
        return null != activity && !activity.isDestroyed() && !activity.isFinishing();
    }

    public static void hideProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);

        if (imm != null && imm.isAcceptingText()) {
            if (null != activity.getCurrentFocus()) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    public static Intent startSharingIntent(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        return shareIntent;
    }
}