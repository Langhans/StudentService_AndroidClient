package timlanghans.yrgo.se.studentsclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.clientCallback.CallbackEvent;
import timlanghans.yrgo.se.studentsclient.clientCallback.ClientCallbackBus;
import timlanghans.yrgo.se.studentsclient.clientCallback.IClientCallbackListener;
import timlanghans.yrgo.se.studentsclient.commons.Course;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;
import timlanghans.yrgo.se.studentsclient.dataManager.DataManager;

public class CourseActivity extends AppCompatActivity implements IClientCallbackListener{

  public static final String LOG_TAG = CourseActivity.class.getName();



  public static Activity courseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseActivity = this;



        int id;
        String name;
        Intent intent = getIntent();
        id = intent.getIntExtra(getString(R.string.TAG_STUDENT_ID), -999);
        name = intent.getStringExtra(getString(R.string.TAG_STUDENT_NAME));

        // request list of courses for this student form DataManager
        DataManager.getInstance().getListOfCourses(id);

        TextView id_fld = (TextView) findViewById(R.id.fld_id);
        TextView name_fld = (TextView) findViewById(R.id.fld_name);
        id_fld.setText(String.valueOf(id));
        name_fld.setText(name);
    }

    private void setUIComponents(StudentsRegistrations reg){
      TextView id_fld = (TextView) findViewById(R.id.fld_id);
      TextView name_fld = (TextView) findViewById(R.id.fld_name);
      id_fld.setText(String.valueOf(reg.getStudent().getId()));
      name_fld.setText(reg.getStudent().getName());

        ListView listView = (ListView)findViewById(R.id.list_Courses);
        List<Course> courses = reg.getCourses();
        ArrayAdapter<Course> adapter = new ArrayAdapter<Course>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                courses);
        listView.setAdapter(adapter);
    }


    @Override
    public void onEvent(CallbackEvent event) {
        switch(event.getTag()){
            case COURSES_UPDATE:
                this.setUIComponents((StudentsRegistrations) event.getValue());
                break;
            case ERROR_MESSAGE:
                Toast.makeText( this , (String)event.getValue() , Toast.LENGTH_SHORT ).show();
              Log.d(LOG_TAG, "TOASTED MESSAGE!" + this.toString());
                break;
            default:
                ;
        }
    }

  @Override
  protected void onStart() {
    super.onStart();
    ClientCallbackBus.getInstance().registerListener(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    ClientCallbackBus.getInstance().unregisterListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ClientCallbackBus.getInstance().unregisterListener(this);
  }

  /**
   * Returns true for each object of CourseActivity. There should only be one
   * object of this Activity in the whole application.
   * @param obj
   * @return true if obj is not null and of type CourseActivity

  @Override
  public boolean equals(Object obj){
    if (obj == null) return false;

    if (!(obj instanceof CourseActivity)){
      return false;
    } else{
      return true;
    }
  }

  */



}
