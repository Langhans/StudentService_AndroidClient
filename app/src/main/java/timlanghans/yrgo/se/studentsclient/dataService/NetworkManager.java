package timlanghans.yrgo.se.studentsclient.dataService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import timlanghans.yrgo.se.studentsclient.activities.MainActivity;
import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;
import timlanghans.yrgo.se.studentsclient.contentReaders.ContentReaderFactory;
import timlanghans.yrgo.se.studentsclient.contentReaders.IContentReader;

/**
 * Handling the process of fetching data from a IServiceProvider and using
 * a IContentReader to translate the data. Returns
 *
 * Created by timlanghans on 16-04-19 / 16.
 */
public class NetworkManager implements INetworkManager {

    public static final String PARAMETER_TAG_ID = "id";
    public static final String PARAMETER_ID_ALL = "all";
    public static final String PARAMETER_TAG_FORMAT = "format";
    public static final String GLOBAL_FORMAT_PARAMETER="json";
    public  static final String ERROR_MESSAGE_NO_INTERNET="No Internet " +
            "Connection";
    public static final String ERROR_MESSAGE_NOT_SPECIFIED_ERROR = "Not " +
          "specified error";


  private static String LOG_TAG = NetworkManager.class.getName();

    private static INetworkManager nwl;

    IContentReader reader = ContentReaderFactory.getFactory().getReader();


    private NetworkManager(){
        Log.d(LOG_TAG , "constructor called...");
    }

    public static INetworkManager getInstance(){
        return new NetworkManager();
    }

    @Override
    public StudentsRegistrations getCoursesForStudent(int id) throws NetworkException {
      // make service request to server and get content

      checkForInternetConnectionAndThrowException();

      try {
        // TODO ignores Server errors, Status 4XX server answers
        Map<String, String> parameters = new TreeMap<>();
        parameters.put(PARAMETER_TAG_FORMAT, GLOBAL_FORMAT_PARAMETER);
        parameters.put(PARAMETER_TAG_ID, String.valueOf(id));
        IServiceProvider provider = ServiceProviderFactory.getServiceProvider();
        String content = provider.getContent(parameters);
        StudentsRegistrations registrations = reader.readRegistration(content);
        Log.d(LOG_TAG, "WRITER returned: " + registrations);
        return registrations;
      } catch (NetworkException e) {
        if (e.getErrorType() == NetworkException.ErrorType.STATUS_4XX_ERROR) {
          throw getStatus4XXException(e);
        } else {
          Log.d(LOG_TAG, "rethrown: " + e.getMessage(), e);
          throw e;
        }
      }
    }



    @Override
    public List<Student> getAllStudents() throws NetworkException {

        checkForInternetConnectionAndThrowException();
        try {
          Map<String, String> parameters = new TreeMap<>();
          //TODO specify global settings for both host and parameters!
          parameters.put(PARAMETER_TAG_ID, PARAMETER_ID_ALL);
          parameters.put(PARAMETER_TAG_FORMAT, GLOBAL_FORMAT_PARAMETER);
          IServiceProvider provider = ServiceProviderFactory.getServiceProvider();
          String content = provider.getContent(parameters);
          List<Student> students = null;
          students = reader.readStudents(content);
          Log.d(LOG_TAG, "WRITER returned: " + Arrays.deepToString(students.toArray()));
          return students;
        } catch (NetworkException e) {
          if (e.getErrorType() == NetworkException.ErrorType.STATUS_4XX_ERROR) {
            throw getStatus4XXException(e);
          } else {
            Log.d(LOG_TAG, "rethrown: " + e.getMessage(), e);
            throw e;
          }
        }
      }


    /*
     *   Checks if the unit has an internet connection
     *
     *    adapted from http://stackoverflow.com/questions/1560788/, answer by gar
     */
  private NetworkException getStatus4XXException(NetworkException e) throws
          NetworkException {
    try {
      IContentReader reader = ContentReaderFactory.getFactory().getReader();
      String errorMessage = reader.readErrorMessageFromContent(e.getMessage());
      throw new NetworkException(errorMessage).setErrorType(NetworkException
              .ErrorType.STATUS_4XX_ERROR);
    } catch (NetworkException nwe) {
      throw nwe;
    }
  }


  private boolean isInternetConnectionEstablished(){
        ConnectivityManager cm =
                (ConnectivityManager) MainActivity.mainActivity.
                        getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /*  help function - throws NetworkException if unit does not have connection
        to the internet */
    private void checkForInternetConnectionAndThrowException() throws NetworkException {
        if (!isInternetConnectionEstablished()){
            Log.d(LOG_TAG, ERROR_MESSAGE_NO_INTERNET);
            throw new NetworkException(ERROR_MESSAGE_NO_INTERNET)
                    .setErrorType(NetworkException.ErrorType.NO_INTERNET);
        } else {
            return;
        }
    }

}

