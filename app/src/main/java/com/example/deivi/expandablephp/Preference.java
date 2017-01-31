package com.example.deivi.expandablephp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Deivi on 29/01/2017.
 */

public class Preference extends PreferenceActivity {
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
