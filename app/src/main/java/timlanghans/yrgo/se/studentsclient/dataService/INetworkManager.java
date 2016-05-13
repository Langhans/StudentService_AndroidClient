package timlanghans.yrgo.se.studentsclient.dataService;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;

/**
 * Created by timlanghans on 16-04-19 / 16.
 */
public interface INetworkManager {

  /**
   * Manages the process of fetching a students registration data from a
   * service provider and translating the fetched content to the returned format
   * of a commons.StudentsRegistrations object.
   * @param id id number of the student to fetch data for
   * @return commons.StudentsRegistrations
   * @throws NetworkException
   */
    public StudentsRegistrations getCoursesForStudent(int id) throws NetworkException;

  /**
   * Manages the process of fetching the data of all students from a service
   * provider and translating the fetched content to the returned format of a
   * list of Student objects.
   * @return java.lang.List of commons.Student
   * @throws NetworkException
   */
    public List<Student> getAllStudents() throws NetworkException;

}
