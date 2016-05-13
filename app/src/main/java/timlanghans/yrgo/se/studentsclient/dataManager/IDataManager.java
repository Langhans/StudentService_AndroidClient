package timlanghans.yrgo.se.studentsclient.dataManager;

/**
 * Created by timlanghans on 16-04-19 / 16.
 */
public interface IDataManager {

    /**
     * Initiates an asynchronous background task that handles the complete process of
     * fetching data from a ServiceProvider and call back the result to the IDataManager
     * @see timlanghans.yrgo.se.studentsclient.dataService.ServiceGetAllStudents
     */
    public void getListOfStudents();

    /**
     * Initiates an asynchronous background task that handles the complete process of fetching data
     * from a ServiceProvider and then informs the UI via the CallbackBus
     * @see timlanghans.yrgo.se.studentsclient.dataService.ServiceGetARegistration
     * @param id - Students id to fetch course registrations for
     */
    public void getListOfCourses(int id);



}
