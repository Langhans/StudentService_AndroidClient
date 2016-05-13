package timlanghans.yrgo.se.studentsclient.dataService;

/**
 * Created by timlanghans on 16-04-27 / 17.
 */

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.activities.MainActivity;
import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.dataManager.DataManager;

public class ServiceGetAllStudents extends AsyncTask<Void,Void,AsyncTaskResult<List<Student>, NetworkException>> {

  ProgressBar pb = null;

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    pb = (ProgressBar)MainActivity.mainActivity.findViewById(R.id
            .progressBar);
    pb.setVisibility(View.VISIBLE);
    pb.setIndeterminate(true);
  }

  @Override
    protected  AsyncTaskResult<List<Student>, NetworkException> doInBackground(Void... params) {
        try{
            INetworkManager nwManager = NetworkManager.getInstance();
            List<Student> students = nwManager.getAllStudents();
            return new AsyncTaskResult<List<Student>, NetworkException>(students);
        } catch (NetworkException nwe){
            return new AsyncTaskResult<List<Student>, NetworkException>(nwe);
        }
    }


    @Override
    protected void onPostExecute(AsyncTaskResult<List<Student>, NetworkException> result) {
        super.onPostExecute(result);

      pb.setIndeterminate(false);
      pb.setVisibility(View.GONE);

        DataManager dataManager = DataManager.getInstance();

        if (result.getError() == null){
            dataManager.receiveListOfStudents(result.getResult());
        } else{
            dataManager.receiveAnErrorMessage(result.getError().getMessage());
        }
    }
}
