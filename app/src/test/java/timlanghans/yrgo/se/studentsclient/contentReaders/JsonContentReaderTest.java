package timlanghans.yrgo.se.studentsclient.contentReaders;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by timlanghans on 16-04-24 / 16.
 */
public class JsonContentReaderTest {

    public static String content_All_STUDENTS_OK ="{ \"students\" : [ { \"studentID\" : 666 , \"studentName\" : \"THE BEAST\"}]}";
    public static String content_All_STUDENTS_WRONG ="{ \"students\" : [  \"studentID\" : 666 , \"studentName\" : \"THE BEAST\"}]}";


    private static final  String TAG_JSON_STUDENTS = "students" ;
    private static final String TAG_JSON_STUDENT_ID ="studentID";
    private static final String TAG_JSON_STUDENT_NAME ="studentName" ;
    private static final String LOG_TAG = JsonContentReader.class.getName() ;

    @Test
    public void testReadStudents() throws Exception {
        JsonContentReader reader = new JsonContentReader();

        List<Student> students = reader.readStudents(content_All_STUDENTS_OK);
        assertTrue(students.size() == 1);
        assertEquals(students.get(0).getId(), 666);
        assertEquals(students.get(0).getName(), "THE BEAST");
    }

   /*@Test(expected = NetworkException.class)
   public void ReadStudents() throws Exception{
       JsonContentReader reader = new JsonContentReader();
       List<Student> students = reader.readStudents(content_All_STUDENTS_WRONG);
       fail("NetworkException expected but not thrown!");
   }*/

    @Test
    public void testReadRegistration() throws Exception {
    assertTrue(true);
    }
}