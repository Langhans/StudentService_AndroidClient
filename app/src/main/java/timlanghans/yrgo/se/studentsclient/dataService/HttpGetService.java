package timlanghans.yrgo.se.studentsclient.dataService;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import timlanghans.yrgo.se.studentsclient.R;
import timlanghans.yrgo.se.studentsclient.activities.MainActivity;
import timlanghans.yrgo.se.studentsclient.application.NetworkException;


/**
 * Gets data from a HTTP GET webservice via a java.net.HttpUrlConnection.
 * Has to be run on a background-thread!
 *
 * Implementation of IServiceProvider.getContent():
 *
 * On success (http-status 200) a String representation of the fetched content
 * is returned. On status 404(Bad Request) and 400 (Not Found) a
 * NetworkException of ErrorType Status_4XX_ERROR is thrown where the response
 * content is added as the Exception message.
 *
 * Created by timlanghans on 16-04-23 / 16.
 */
public class HttpGetService implements IServiceProvider {

    // TODO Add and read all application settings from global Settings-file(Preferences)


   // public static final String ADDRESS_HTTP_GET_HOST =
   //         "http://192.168.0.113:8080/StudentService/service";



    private static final String LOG_TAG = HttpGetService.class.getName();

    private static final int timeout = 10000;
  private static final String ERROR_MESSAGE_NO_SERVICE ="Service not " +
          "available" ;
  private static final String ERROR_MESSAGE_STATUS_400 = "Not found";
    // TODO OBS! change adress for Localhost if not run on Emulator!
  private static final String ADRESS_HTTP_GET_HOST = "http://10.0.2" +
          ".2:8080/StudentService/service";


  @Override
    public String getContent(Map<String, String> parameters) throws NetworkException {
        try {
          String hostUrl = null;
            try {
              hostUrl = PreferenceManager.getDefaultSharedPreferences
                              (MainActivity.mainActivity.getBaseContext())
                              .getString("HTTP_GET_SERVICE_HOST",
                                      null);
            } catch (Exception e){
              Log.d(LOG_TAG , "cannot fetch preferences" , e);
            }

            if (hostUrl == null){
              Log.d(LOG_TAG,
                      hostUrl + " changed to DEFAULT!!");
              hostUrl = ADRESS_HTTP_GET_HOST;
            }

            String urlString = hostUrl + createHTTPGetParameters(parameters);
            Log.d(LOG_TAG, "URL-STRING: " + urlString);

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // con.setUseCaches(false);
            //con.setAllowUserInteraction(false);
            con.setDoOutput(false);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            con.connect();
            int status = con.getResponseCode();

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader;
            String in;

            if (status == 200){
              reader = new BufferedReader(
                      new InputStreamReader(con.getInputStream()));
              while ((in = reader.readLine()) != null) {
                buffer.append(in);
              }
              return buffer.toString();
            } else if ( status == 400 || status == 404) {
              reader = new BufferedReader(
                      new InputStreamReader(con.getErrorStream()));
              while ((in = reader.readLine()) != null) {
                buffer.append(in);
              }
              String errorMessage = buffer.toString();
              Log.d(LOG_TAG, "Status400/404-Exception with content thrown: "
                      + errorMessage);
              throw new NetworkException(errorMessage)
                      .setErrorType(NetworkException.ErrorType
                              .STATUS_4XX_ERROR);
            } else{
              throw new IllegalArgumentException("NOT EXPECTED RESPONSE");
          }
        } catch (NetworkException e){
           throw e;
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage() , e);
            throw new NetworkException
                    (ERROR_MESSAGE_NO_SERVICE , e)
            .setErrorType(NetworkException.ErrorType.HTTP_SERVICE_ERROR);
        }
    }


    // help method to generate correct HTTP GET url parameters
    private static String createHTTPGetParameters(Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();
        builder.append("?");
        Set<Map.Entry<String, String>> entries = parameters.entrySet();

        for (Map.Entry e : entries) {
            builder.append((String) e.getKey())
                    .append("=")
                    .append((String) e.getValue())
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}