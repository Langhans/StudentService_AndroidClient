package timlanghans.yrgo.se.studentsclient.dataService;

/**
 * Created by timlanghans on 16-04-27 / 17.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.HttpURLConnection;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.activities.CourseActivity;
import timlanghans.yrgo.se.studentsclient.activities.MainActivity;
import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;
import timlanghans.yrgo.se.studentsclient.dataManager.DataManager;

public class ServiceGetARegistration extends AsyncTask<Integer,Void,
        AsyncTaskResult<StudentsRegistrations, NetworkException>> {

  private final ProgressDialog progressDialog;

  public ServiceGetARegistration(){
    progressDialog = new ProgressDialog(CourseActivity.courseActivity);
    progressDialog.setTitle(MainActivity.mainActivity.getString(R.string.TEXT_PLEASE_WAIT));
    progressDialog.setMessage("Fetching a registration...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.setCancelable(true);
    progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
      @Override
      public void onCancel(DialogInterface dialog) {
        // actually could set running = false; right here, but I'll
        // stick to contract.
        progressDialog.cancel();
      }
    });
  }

  @Override
  protected void onPreExecute() {
    progressDialog.show();
  }

  @Override
  protected void onCancelled() {
    //running = false;
    Toast.makeText(CourseActivity.courseActivity,
            "CANCELDED" , Toast.LENGTH_SHORT).show();
    progressDialog.cancel();
  }



  @Override
  protected AsyncTaskResult<StudentsRegistrations,NetworkException>
    doInBackground(Integer... params) {
            while (!isCancelled()) {
              INetworkManager nwManager = NetworkManager.getInstance();
              try {
                StudentsRegistrations result =
                        nwManager.getCoursesForStudent(params[0]);
                return new AsyncTaskResult<StudentsRegistrations, NetworkException>(result);
              } catch (NetworkException nwe) {
                return new AsyncTaskResult<StudentsRegistrations, NetworkException>(nwe);
              }
            }

            return null;
  }

  @Override
  protected void onPostExecute(
          AsyncTaskResult<StudentsRegistrations,NetworkException> result) {
    super.onPostExecute(result);
    progressDialog.cancel();
    DataManager dataManager = DataManager.getInstance();

    if (result.getError() == null) {
      dataManager.receiveAStudentsRegistrations(result.getResult());
    } else if (result.getError().getErrorType() != NetworkException.ErrorType.NOT_SPECIFIED) {
      dataManager.receiveAnErrorMessage(result.getError().getMessage());
    } else {
      // just ignore unspecified Exceptions, already logged before!
    }
  }
}
