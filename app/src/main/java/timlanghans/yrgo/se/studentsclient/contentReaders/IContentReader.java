package timlanghans.yrgo.se.studentsclient.contentReaders;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;

/**
 * Created by timlanghans on 16-04-22 / 16.
 */
public interface IContentReader {

    /**
     * Takes a content String formatted as specified in the Preferences and reads Student-data
     * from it.
     * @param content
     * @return java.util.List of commons.Student
     * @throws NetworkException if content cannot be read
     */
    public List<Student> readStudents(String content) throws NetworkException;

    /**
     * Takes a content String formatted as specified in the Preferences and
     * reads out a students registration-data data from it
     * @param content
     * @return commons.StudentsRegistrations
     * @throws NetworkException if content cannot be read
     */
    public StudentsRegistrations readRegistration(String content) throws NetworkException;


    /**
     * Takes a content String formatted as specified in the Preferences and reads out
     * an Error-message from it.
     * @param content
     * @return java.lang.String of the error message
     * @throws NetworkException if content cannot be read
     */
    public String readErrorMessageFromContent(String content) throws NetworkException;


}
