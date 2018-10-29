package com.mdlink;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdlink.helper.UiHelper;

import static com.mdlink.util.Constants.INVALID;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    private BaseActivity mActivity;

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    /**
     * Set up support toolbar and home icon
     */
    public void setUpToolbar(Toolbar toolbar, int homeIconColor) {
        mToolbar = toolbar;

        if (null == mToolbar) {
            return;
        }

        setSupportActionBar(mToolbar);

        boolean showHome = INVALID != homeIconColor;

        setUpHome(showHome);

        if (showHome) {
            if (null != toolbar.getNavigationIcon()) {
                toolbar.getNavigationIcon().setColorFilter(
                        ContextCompat.getColor(this, homeIconColor), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    /**
     * Decorate and display toolbar title
     *
     * @param title    Title text to display
     * @param colorRes Color resource value to apply to title
     */
    public void setToolbarTitle(String title, int colorRes) {
        TextView txtToolbarTitle = mToolbar.findViewById(R.id.txt_toolbarTitle);

        if (null == txtToolbarTitle) {
            return;
        }

        txtToolbarTitle.setVisibility(View.VISIBLE);
        txtToolbarTitle.setText(title);
        txtToolbarTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        txtToolbarTitle.setTextColor(ContextCompat.getColor(this, colorRes));
    }

    /**
     * Show or hide relevant Home icon (Typically back arrow)
     *
     * @param showHome true if to show, false otherwise
     */
    private void setUpHome(boolean showHome) {
        getSupportActionBar().setHomeButtonEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
    }

    public Toast toast(String message) {
        return UiHelper.toast(message);
    }

    public Toast toast(int messageRes) {
        return UiHelper.toast(getString(messageRes));
    }

    public void showProgress(boolean isCancelable) {
        UiHelper.showProgress(this, isCancelable);
    }

    public void hideProgress() {
        UiHelper.hideProgress();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UiHelper.dismissKeyboard(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}