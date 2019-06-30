package com.mdlinkhealth.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Abstract Text watcher class for more readability and customized threshold for search query
 * Created by mandip on 11/10/18
 */
public abstract class SearchTextWatcher implements TextWatcher {

    private final int SEARCH_THRESHOLD = 0;

    private String mActiveQuery;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > SEARCH_THRESHOLD) {
            startSearch(s.toString());
            mActiveQuery = s.toString();
        } else {
            closeSearch();
            mActiveQuery = null;
        }
    }

    public String getActiveQuery() {
        return mActiveQuery;
    }

    public abstract void startSearch(String query);

    public abstract void closeSearch();

}
