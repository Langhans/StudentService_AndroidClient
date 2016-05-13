package timlanghans.yrgo.se.studentsclient.dataService;

/**
 * Created by timlanghans on 16-04-23 / 16.
 */
public class ServiceProviderFactory {

    public static IServiceProvider getServiceProvider(){
        return new HttpGetService();
    }

}
