package timlanghans.yrgo.se.studentsclient.contentReaders;

import java.util.ArrayList;
import java.util.List;

import timlanghans.yrgo.se.studentsclient.application.NetworkException;
import timlanghans.yrgo.se.studentsclient.commons.Course;
import timlanghans.yrgo.se.studentsclient.commons.Student;
import timlanghans.yrgo.se.studentsclient.commons.StudentsRegistrations;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by timlanghans on 16-04-23 / 16.
 */
public class JsonContentReader implements IContentReader {

    private static final String LOG_TAG = JsonContentReader.class.getName();

    // TODO has to be put in global settings file, match specifications from server
    private static final String TAG_JSON_STUDENTS = "students";
    private static final String TAG_JSON_STUDENT_ID = "studentID";
    private static final String TAG_JSON_STUDENT_NAME = "studentName";
    private static final String TAG_REGISTRATIONS_COURSES_ARRAY = "courses";
    private static final String TAG_JSON_COURSECODE = "courseCode";
    private static final String TAG_JSON_COURSEID = "courseID";
    private static final String TAG_JSON_ERROR_MESSAGE = "message";


    @Override
    public List<Student> readStudents(String content) throws NetworkException {

        try {
            JSONObject jsonObj = new JSONObject(content);
            JSONArray students_json = jsonObj.getJSONArray(TAG_JSON_STUDENTS);
            List<Student> students = new ArrayList<>();

            for (int i = 0; i < students_json.length(); i++) {
                JSONObject student = students_json.getJSONObject(i);
                students.add(new Student(
                        student.getInt(TAG_JSON_STUDENT_ID),
                        student.getString(TAG_JSON_STUDENT_NAME)));
            }
            return students;

        } catch (JSONException e) {
            Log.d(LOG_TAG, TAG_JSON_ERROR_MESSAGE);
            throw getContentNotValidException(e);
        }
    }


    @Override
    public StudentsRegistrations readRegistration(String content) throws NetworkException {
        StudentsRegistrations regs;
        Student student;
        List<Course> list_courses = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(content);
            JSONArray courses = obj.getJSONArray(TAG_REGISTRATIONS_COURSES_ARRAY);

            for (int i = 0; i < courses.length(); i++) {
                JSONObject course = courses.getJSONObject(i);
                list_courses.add(new Course(
                        course.getInt(TAG_JSON_COURSEID),
                        course.getString(TAG_JSON_COURSECODE)));
            }
            student = new Student(
                    obj.getInt(TAG_JSON_STUDENT_ID),
                    obj.getString(TAG_JSON_STUDENT_NAME));
            regs = new StudentsRegistrations(student, list_courses);
            return regs;
        } catch (JSONException e) {
            Log.d(LOG_TAG, TAG_JSON_ERROR_MESSAGE);
            throw getContentNotValidException(e);
        }
    }


    @Override
    public String readErrorMessageFromContent (String content) throws NetworkException {
        try {
            JSONObject obj = new JSONObject(content);
            String message = obj.getString(TAG_JSON_ERROR_MESSAGE);
          System.out.println("READER PARSED : " + message);
            return message;
        } catch (JSONException e) {
            Log.d(LOG_TAG, TAG_JSON_ERROR_MESSAGE);
            throw getContentNotValidException(e);
        }
    }


    // help function, returns the same NetworkException that all three methods throw
    private NetworkException getContentNotValidException (JSONException e) {
        return new NetworkException(TAG_JSON_ERROR_MESSAGE, e.getCause())
                .setErrorType(NetworkException.ErrorType.CONTENT_NOT_VALID);
    }
        
}