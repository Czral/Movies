package com.example.android.movies;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.android.movies.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    public SettingsActivity() {

        super(R.layout.activity_settings);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.activity_settings, SettingsFragment.class, null)
                    .commit();
        }

   //     FragmentContainerView view = findViewById(R.id.activity_settings);
//

    //    setContentView(view);
   //     ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
    //    View root = binding.getRoot();
    //    setContentView(view);

    //    view.addView(root);



        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
