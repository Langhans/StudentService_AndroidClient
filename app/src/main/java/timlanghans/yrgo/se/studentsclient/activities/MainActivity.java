package timlanghans.yrgo.se.studentsclient.activities;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.clientCallback.CallbackEvent;
import timlanghans.yrgo.se.studentsclient.clientCallback.ClientCallbackBus;
import timlanghans.yrgo.se.studentsclient.clientCallback.IClientCallbackListener;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentSorterById;
import timlanghans.yrgo.se.studentsclient.commons.StudentSorterByName;
import timlanghans.yrgo.se.studentsclient.dataManager.DataManager;
import timlanghans.yrgo.se.studentsclient.dataManager.IDataManager;

public class MainActivity extends AppCompatActivity implements IClientCallbackListener{

  // this activity shares itself so other classes can get the App-Context
  public static Activity mainActivity;

  private static final String LOG_TAG =
          MainActivity.class.getName();

  private IDataManager data ;
  private Comparator<Student> sorter = null;
  private ArrayAdapter<Student> adapter = null;

  public void onSortButtonClicked(View view ){
      switch(view.getId()){
        case R.id.rBtn_byId:
          sorter = new StudentSorterById();
          break;
        case R.id.rBtn_byName:
          sorter = new StudentSorterByName();
          break;
      }
      updateListView();
  }

  private void updateListView(){
    adapter.sort(sorter);
    adapter.notifyDataSetChanged();
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(LOG_TAG, "onCreate()");

    mainActivity = this;
    ClientCallbackBus.getInstance().registerListener(this);
    data = DataManager.getInstance();

    Button button;
    button = (Button) findViewById(R.id.btn_UpdateStud);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(LOG_TAG,"Fetch Students button pressed");
        data.getListOfStudents();
      }
    });

    TextView isCachedLabel = (TextView) findViewById(R.id.lbl_isCached);
    isCachedLabel.setText("Please refresh data!");
    isCachedLabel.setTextColor(Color.BLUE);
    data.getListOfStudents();
  }


  /**
   * activates the Menu in the ActionBar
   * @param menu
   * @return true to make menu visible
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.mainmenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_settings) {
      Intent intent = new Intent(this, SettingsActivity.class);
      startActivity(intent);
    }

    return true;
  }

  @Override
  public void onEvent(CallbackEvent event) {
    switch (event.getTag()){
      case CACHED_AT_CHANGED:
        this.setCachedAtLabel((String) event.getValue());
        break;
      case STUDENTS_UPDATE:
        this.setStudentList((List<Student>) event.getValue());
        break;
      case ERROR_MESSAGE:
        Toast.makeText(this, (String) event.getValue(), Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG , "TOASTED MESSAGE!" + this.toString());
        break;
      default:
        return;
    }
  }

  // TODO find a way to avoid creating a new Adapter and ClickListeners each time!
  private void setStudentList(final List<Student> students) {
    adapter = new ArrayAdapter<Student> (
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            students);
    final ListView playerView = (ListView) findViewById(R.id.list_Students);
    playerView.setAdapter(adapter);
    playerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Student clickedStud = (Student) playerView.getAdapter().getItem(position);
        Intent intent = new Intent("timlanghans.yrgo.se.studentsclient.activities.CourseActivity");
        intent.putExtra(getString(R.string.TAG_STUDENT_NAME), clickedStud.getName());
        intent.putExtra(getString(R.string.TAG_STUDENT_ID), clickedStud.getId());
        startActivity(intent);
      }
    });
  }

  private void setCachedAtLabel(final String cachedAt){
    final TextView cachedAtLabel = (TextView)findViewById(R.id.lbl_isCached);
    cachedAtLabel.post(new Runnable() {
      @Override
      public void run() {
        cachedAtLabel.setText( getString(R.string.TEXT_LABLE_CACHEDAT) + cachedAt);
      }
    });
    Log.d(LOG_TAG, "chacheAt set to " + cachedAt);
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(LOG_TAG, "onStart()");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(LOG_TAG, "onPause()");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(LOG_TAG, "onResume()");
  }

  @Override
  public void onStop() {
    Log.d(LOG_TAG, "onStop()");
    super.onStop();
  }
}
