package timlanghans.yrgo.se.studentsclient.testContentReaders;


import org.junit.Test;

import android.util.JsonReader;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.contentReaders.JsonContentReader;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by timlanghans on 16-04-23 / 16.
 */

public class testContentReaders_JSON {

    private String content_OK = "{\"students\" : [ { \"name\" : \"test1\" , \"id\" : \"111\" }  ] }";
    private String content_WRONG = "{\"students\" : [  \"name\" : \"test1\" , \"id\" : \"111\" }  ] }";


    @Test
    public void testJsonWriter_readContent_OK() throws NetworkException {

        JsonContentReader reader = new JsonContentReader();
        List<Student> students =  reader.readStudents(content_OK);

        assertTrue(students.size() == 1);

        assertTrue(students.get(0).getName().equals("test1"));

        assertTrue(students.get(0).getId() == 111);

    }

    @Test(expected = NetworkException.class)
    public void testJsonWriter_readContent_WRONGSYNTAX() throws NetworkException {
        JsonContentReader reader = new JsonContentReader();
        List<Student> students =  reader.readStudents(content_WRONG);
        fail("Exception should have been thrown");
    }

}
