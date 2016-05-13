package timlanghans.yrgo.se.studentsclient.dataManager;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;

/**
 * Created by timlanghans on 16-04-27 / 17.
 */
public interface ICallbackHandler {

    /**
     *  Method initiates caching of new data in the local
     * database and informing the UI via the CallbackBus to be updated.
     * Used after new data has been fetched.
     * @param students - newly fetched java.util.List<commons.Student>
     */
    public void receiveListOfStudents(List<Student> students);

    /**
     * Method sends fetched data to the UI via the CallbackBus.
     * Shall be used after new data has been fetched.
     * @param registrations - newly fetched commons.StudentsRegistrations object
     */
    public void receiveAStudentsRegistrations(StudentsRegistrations registrations);

    /**
     * Called on errors while fetching new data. Method sends an error message to the
     * UI via the CallbackBus
     * @param message - error message as java.lang.String
     */
    public void receiveAnErrorMessage(String message);








}
