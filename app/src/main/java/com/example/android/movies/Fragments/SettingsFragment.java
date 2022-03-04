package com.example.android.movies.Fragments;

import static android.content.Context.MODE_PRIVATE;
import static com.example.android.movies.Data.Constants.GENRE;
import static com.example.android.movies.Data.Constants.KEYWORD;
import static com.example.android.movies.Data.Constants.PREFERENCES;
import static com.example.android.movies.Data.Constants.SORT_BY;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.android.movies.R;

/**
 * Created by XXX on 01-Aug-18.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        EditTextPreference editTextPreference = findPreference(KEYWORD);
        if (editTextPreference != null) {

            editTextPreference.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    if (editText.getText().toString().trim().length() > 0) {

                        sharedPreferences.edit().putString(KEYWORD, editText.getText().toString().trim()).apply();
                    }
                }
            });
        }

        ListPreference genreListPreference = findPreference(GENRE);
        if (genreListPreference != null) {

            genreListPreference.setOnPreferenceChangeListener((preference, newValue) -> {

                int index = genreListPreference.findIndexOfValue(String.valueOf(newValue));
                genreListPreference.setValue(String.valueOf(newValue));
                sharedPreferences.edit().putString(GENRE, (String) newValue).apply();
                genreListPreference.setSummary(genreListPreference.getEntries()[index]);
                return true;
            });
        }

        ListPreference sortByListPreference = findPreference(SORT_BY);
        if (sortByListPreference != null) {

            sortByListPreference.setOnPreferenceChangeListener((preference, newValue) -> {

                int index = sortByListPreference.findIndexOfValue(String.valueOf(newValue));
                sortByListPreference.setValue(String.valueOf(newValue));
                sharedPreferences.edit().putString(SORT_BY, String.valueOf(newValue)).apply();
                sortByListPreference.setSummary(sortByListPreference.getEntries()[index]);
                return true;
            });
        }

    }
}