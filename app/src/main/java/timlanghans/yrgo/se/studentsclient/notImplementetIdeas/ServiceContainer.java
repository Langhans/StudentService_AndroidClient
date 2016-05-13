package timlanghans.yrgo.se.studentsclient.notImplementetIdeas;

/**
 * Created by timlanghans on 16-04-26 / 17.
 */
public class ServiceContainer {

    private Service service;

    private String result;


    public ServiceContainer(Service service , String result){
        this.service = service;
        this.result = result;
    }


    public String getResult() {
        return result;
    }

    public Service getService() {
        return service;
    }
}
