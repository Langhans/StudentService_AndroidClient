package timlanghans.yrgo.se.studentsclient.dataManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.activities.MainActivity;
import timlanghans.yrgo.se.studentsclient.clientCallback.CallbackEvent;
import timlanghans.yrgo.se.studentsclient.clientCallback.ClientCallbackBus;
import timlanghans.yrgo.se.studentsclient.clientCallback.EventTag;
import timlanghans.yrgo.se.studentsclient.clientCallback.IClientCallbackBus;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;
import timlanghans.yrgo.se.studentsclient.dbCache.DbHandler;
import timlanghans.yrgo.se.studentsclient.dataService.ServiceGetARegistration;
import timlanghans.yrgo.se.studentsclient.dataService.ServiceGetAllStudents;

/**
 * Created by timlanghans on 16-04-19 / 16.
 *
 * The DataManager is the central controlling unit in this app. A
 * Singleton object which forms the UIs connection to all data from the
 * student database, cached on the local device. The DataManager even handles
 * the fetching of data from IDataProviders, different data resources, which are
 * then cached in the local database. Even callbacks to the UI via the CallbackBus
 * for updating the View are handled by the DataManager.
 */
public class DataManager implements IDataManager , ICallbackHandler, SharedPreferences.OnSharedPreferenceChangeListener {

    // todo Move to global Settings/Preferences file and read from it
    private static final String DATE_FORMAT_STRING = "yyyy-M-dd HH:mm"; //
    // default

    private static final String LOG_TAG = DataManager.class.getName();
    private static DataManager manager =  new DataManager();

    private IClientCallbackBus clientBus = ClientCallbackBus.getInstance();

    private Context context =  null;
    private SharedPreferences pref = null;


    private DataManager() {
      context = MainActivity.mainActivity.getBaseContext();
      pref = PreferenceManager.getDefaultSharedPreferences
              (context);
      pref.registerOnSharedPreferenceChangeListener(this);
    }

    public static DataManager getInstance() {
        return manager;
    }


    @Override
    public void getListOfStudents() {
        List<Student> students;
        try {
            students =
                    DbHandler.getInstance(MainActivity.mainActivity.getApplicationContext())
                             .getAllStudentsFromCache();

            clientBus.broadcastEvent(
                    new CallbackEvent(EventTag.STUDENTS_UPDATE, students));

            // start a background task
          // TODO cancel function! Only one at a time may run!
            new ServiceGetAllStudents().execute();
        } catch (Exception e){
            Log.d(LOG_TAG, "Error while fetching students in backgrtound", e);
        }
    }


    // TODO Fetch Registrations
    public void getListOfCourses(final int id) {

        try {
            new ServiceGetARegistration().execute(id);
        } catch (Exception e){
            Log.d(LOG_TAG , "Error while fetching courses on other thread " , e );
        }
    }

    // SERVICE CALL BACK HANDLING


    @Override
    public void receiveListOfStudents(List<Student> students) {
        Log.d(LOG_TAG, "CACHING STUDENTS FROM BACKGROUND TASK");
        cacheStudents(students);
        setNewTimestampForCachedAt();
        callCachedAtChanged();
        clientBus.broadcastEvent(new CallbackEvent(
                EventTag.STUDENTS_UPDATE, getCachedStudents()));

        Log.d(LOG_TAG,"Broadcast list of students");
    }


  @Override
    public void receiveAStudentsRegistrations(StudentsRegistrations registrations) {
      ClientCallbackBus.getInstance().broadcastEvent(new
              CallbackEvent(EventTag.COURSES_UPDATE, registrations));
      Log.d(LOG_TAG, "Broadcast registrations to Client");
    }

    @Override
    public void receiveAnErrorMessage(String message){
        clientBus.broadcastEvent(
                new CallbackEvent(EventTag.ERROR_MESSAGE,
                        message));
    }

    //
    // DATABASE HANDLING
    //
    // TODO Exception handling?
      // TODO on background thread?
    private void cacheStudents(List<Student> students) {
        DbHandler handler = DbHandler.getInstance(MainActivity.mainActivity.getApplicationContext());
            handler.cacheAllStudents(students);
    }
  // TODO on background thread?
    // TODO exception handling?
    private List<Student> getCachedStudents() {
        DbHandler handler = DbHandler.getInstance(MainActivity.mainActivity.getApplicationContext());
        return handler.getAllStudentsFromCache();
    }

    //
    // help functions
    //
  private void callCachedAtChanged() {
    String cachedAt = getFormattedDate();
    clientBus.broadcastEvent(new CallbackEvent(
            EventTag.CACHED_AT_CHANGED, cachedAt));
    Log.d(LOG_TAG , "call to clients, cachedAt changed to " + cachedAt);
  }

  // sets the time of last caching of students in local database( uses java.util
  // .Date)
  private void setNewTimestampForCachedAt() {
    pref.edit().putLong("CACHED_AT_TIMESTAMP" , new Date().getTime()).commit();
  }

    // returns a formatted CachedAt-String for the label in UI
    private String getFormattedDate() {
      String formatString = pref.getString("DATEFORMAT" , DATE_FORMAT_STRING);
      Log.d(LOG_TAG , "CachedAt label pref: " + formatString);

      if (formatString == null){
        formatString = DATE_FORMAT_STRING;
      }
      long time =  pref.getLong("CACHED_AT_TIMESTAMP",0);
      Date date  = new Date(time);
      SimpleDateFormat formatter = new SimpleDateFormat(formatString);
      String resultString = formatter.format(date);
      Log.d(LOG_TAG , "New CachedAt-String: " + resultString);
      return resultString;
    }


  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    // if (sharedPreferences != this.pref) return;
    Log.i(LOG_TAG, "sharedPref " + sharedPreferences + " + key : " + key);

    if (key.equals("DATEFORMAT")){
       this.callCachedAtChanged();
    }
  }
}
