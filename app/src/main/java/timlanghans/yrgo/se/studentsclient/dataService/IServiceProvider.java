package timlanghans.yrgo.se.studentsclient.dataService;

import java.util.Map;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;

/**
 * Interface specifiing different data sources. By default the program uses a HttpUrlConnection
 * to get data via a HTTP-GET request. Other possible data sources would be file systems,
 * a socket connection et cetera.
 *
 * Created by timlanghans on 16-04-23 / 16.
 */
public interface IServiceProvider {

    /**
     * Returns a java.lang.String of the fetched content data from a data provider
     * @param parameters - key value pairs of different parameters specifiing the data request (if necessary)
     * @return - java.lang.String representation of the fetched data
     * @throws NetworkException
     */
    public String getContent(Map<String, String> parameters) throws NetworkException;

}
