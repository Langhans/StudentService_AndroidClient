package timlanghans.yrgo.se.studentsclient.dbCache;

import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.DBCacheException;
import timlanghans.yrgo.se.studentsclient.commons.Student;

public interface IDbHandler {

    /**
     * Retrieves all cached Students from the local database
     * @return java.util.List of commons.Student
     */
    public List<Student> getAllStudentsFromCache() ;

    /**
     * Stores the list of students, input parameter, in the local database
     * @param students java.util.List of commons.Student
     */
    public void         cacheAllStudents(List<Student> students) ;
}
