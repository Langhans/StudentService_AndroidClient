package timlanghans.yrgo.se.studentsclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import timlanghans.yrgo.se.studentsclient.R;

public class SettingsActivity extends android.preference.PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);

  }





}
